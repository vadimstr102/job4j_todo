package ru.job4j.todo.servlet;

import ru.job4j.todo.model.Category;
import ru.job4j.todo.store.HibernateStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> categories = HibernateStore.instOf().findAllCategories();
        req.setAttribute("categories", categories);
        req.getRequestDispatcher("/view/index.jsp").forward(req, resp);
    }
}
