package ru.job4j.todo.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.todo.model.Item;

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

    public void create(Item item) {
        transaction(session -> session.save(item));
    }

    public void update(Item item) {
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

    public List<Item> findAll() {
        return transaction(session -> session.createQuery(
                "FROM ru.job4j.todo.model.Item ORDER BY done, created"
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
