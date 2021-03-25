package ru.af3412.lazyInitialization;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class Start {

    public static void main(String[] args) {
        List<Category> list = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            /*Category category = Category.of("first category");
            session.save(category);
            Task one = Task.of("one", category);
            session.save(one);
            Task two = Task.of("two", category);
            session.save(two);
            Task three = Task.of("three", category);
            session.save(three);*/

            list = session.createQuery(
                    "select distinct c from Category c join fetch c.tasks"
            ).list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (Category category : list) {
            for (Task task : category.getTasks()) {
                System.out.println(task);
            }
        }
    }

}
