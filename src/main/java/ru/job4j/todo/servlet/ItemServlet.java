package ru.job4j.todo.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.store.HibernateStore;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.List;

public class ItemServlet extends HttpServlet {
    private static final Gson GSON = new GsonBuilder().create();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json; charset=utf-8");
        OutputStream output = resp.getOutputStream();
        List<Item> items = HibernateStore.instOf().findAllItems();
        String json = GSON.toJson(items);
        output.write(json.getBytes(StandardCharsets.UTF_8));
        output.flush();
        output.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String description = req.getParameter("description");
        String[] categoriesId = req.getParameterValues("categories");
        Item item = new Item();
        item.setUser(user);
        item.setDescription(description);
        item.setCreated(new Timestamp(System.currentTimeMillis()));
        HibernateStore.instOf().createItem(item, categoriesId);
        resp.sendRedirect(req.getContextPath() + "/index.do");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        Item item = GSON.fromJson(req.getReader(), Item.class);
        item.setDone(true);
        HibernateStore.instOf().updateItem(item);
    }
}
