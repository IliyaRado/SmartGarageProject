package com.example.smartgarage.repositories;

import com.example.smartgarage.exceptions.EntityNotFoundException;
import com.example.smartgarage.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class UserRepositoryImpl implements UserRepository {

    private SessionFactory sessionFactory;

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<User> getUsers() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    public User getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("User", id);
            }
            return user;
        }
    }

    public User getByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where username = :username", User.class);
            query.setParameter("username", username);

            List<User> result = query.list();
            if (result.size() == 0) {
                throw new EntityNotFoundException("User", "name", username);
            }

            return result.get(0);
        }
    }

    public User create(User user) {
        return null;
    }

    public void delete(int id) {
    }

    public User update(User user) {
        return null;
    }

    public User makeEmployee(int userId) {
        return null;
    }

    public User removeEmployee(int userId) {
        return null;
    }
}
