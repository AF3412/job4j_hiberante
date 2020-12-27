package ru.af3412;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.util.List;

public class HbmTracker implements Store, AutoCloseable {
    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();
    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public Item add(Item item) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
            return item;
        }
    }

    @Override
    public boolean replace(String id, Item item) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(item);
            session.getTransaction().commit();
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Item item = new Item(null);
            item.setId(id);
            session.delete(item);
            session.getTransaction().commit();
        }
        return true;
    }

    @Override
    public List<Item> findAll() {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            List result = session.createQuery("from ru.af3412.Item").list();
            session.getTransaction().commit();
            return result;
        }
    }

    @Override
    public List<Item> findByName(String key) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from ru.af3412.Item where name = :name");
            query.setParameter("name", key);
            List result = session.createQuery("from ru.af3412.Item where name = :key").list();
            session.getTransaction().commit();
            return result;
        }
    }

    @Override
    public Item findById(int id) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Item result = session.get(Item.class, id);
            session.getTransaction().commit();
            return result;
        }

    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }
}
