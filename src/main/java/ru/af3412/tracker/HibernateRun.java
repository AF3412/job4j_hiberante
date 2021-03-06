package ru.af3412.tracker;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;
import java.sql.Timestamp;
import java.util.List;

public class HibernateRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Item item = create(new Item("Learn Hibernate"), sf);
            create(new Item("Hibernate 1", "Learn Hibernate 1", new Timestamp(System.nanoTime())), sf);
            create(new Item("Hibernate 2", "Learn Hibernate 2", new Timestamp(System.nanoTime())), sf);
            create(new Item("Hibernate 3", "Learn Hibernate 3", new Timestamp(System.nanoTime())), sf);
            System.out.println(item);
            item.setName("Learn Hibernate 5.");
            update(item, sf);
            System.out.println(item);
            Item rsl = findById(item.getId(), sf);
            System.out.println(rsl);
            delete(rsl.getId(), sf);
            List<Item> list = findAll(sf);
            for (Item it : list) {
                System.out.println(it);
            }
            Item fbn = findByName("Hibernate 2", sf);
            System.out.println(fbn);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static Item create(Item item, SessionFactory sf) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.save(item);
            session.getTransaction().commit();
            return item;
        }
    }

    public static void update(Item item, SessionFactory sf) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            session.update(item);
            session.getTransaction().commit();
        }
    }

    public static void delete(Integer id, SessionFactory sf) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Item item = new Item(null);
            item.setId(id);
            session.delete(item);
            session.getTransaction().commit();
        }


    }

    public static List<Item> findAll(SessionFactory sf) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            List result = session.createQuery("from ru.af3412.tracker.Item").list();
            session.getTransaction().commit();
            return result;
        }
    }

    public static Item findById(Integer id, SessionFactory sf) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Item result = session.get(Item.class, id);
            session.getTransaction().commit();
            return result;
        }
    }

    public static Item findByName(String name, SessionFactory sf) {
        try (Session session = sf.openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from Item where name = :name");
            query.setParameter("name", name);
            System.out.println(query.getFirstResult());
            Item result = (Item) query.getResultList().get(0);
            session.getTransaction().commit();
            return result;
        }
    }
}
