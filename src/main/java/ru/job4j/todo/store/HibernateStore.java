package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;

import java.util.List;
import java.util.function.Function;

public class HibernateStore implements AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
    private final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();

    private static final class Lazy {
        private static final HibernateStore INST = new HibernateStore();
    }

    public static HibernateStore instOf() {
        return Lazy.INST;
    }

    public void createItem(Item item, String[] categoriesId) {
        transaction(session -> {
            for (String id : categoriesId) {
                Category category = session.find(Category.class, Integer.parseInt(id));
                item.addCategory(category);
            }
            session.save(item);
            return true;
        });
    }

    public void createUser(User user) {
        transaction(session -> session.save(user));
    }

    public void updateItem(Item item) {
        transaction(session -> {
            var query = session.createQuery(
                    "UPDATE ru.job4j.todo.model.Item SET done = :status WHERE id = :itemId"
            );
            query.setParameter("status", item.isDone());
            query.setParameter("itemId", item.getId());
            query.executeUpdate();
            return true;
        });
    }

    public List<Item> findAllItems() {
        return transaction(session -> session.createQuery(
                        "SELECT DISTINCT i FROM Item i JOIN FETCH i.categories ORDER BY i.done, i.created"
                ).list()
        );
    }

    public User findUserByEmail(String email) {
        return (User) transaction(session -> session.createQuery(
                "FROM User WHERE email = :email"
        ).setParameter("email", email).uniqueResult());
    }

    public List<Category> findAllCategories() {
        return transaction(session -> session.createQuery(
                "FROM Category"
        ).list());
    }

    @Override
    public void close() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    private <T> T transaction(final Function<Session, T> command) {
        try (Session session = sf.openSession()) {
            final Transaction tx = session.beginTransaction();
            try {
                T rsl = command.apply(session);
                tx.commit();
                return rsl;
            } catch (final Exception e) {
                session.getTransaction().rollback();
                throw e;
            }
        }
    }
}
