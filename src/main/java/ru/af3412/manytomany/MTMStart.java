package ru.af3412.manytomany;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class MTMStart {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book one = new Book("Captain");
            Book two = new Book("Voyager");

            Author pushkin = new Author("Pushkin");
            pushkin.getBooks().add(one);
            pushkin.getBooks().add(two);

            Author yesenin = new Author("Yesenin");
            yesenin.getBooks().add(two);

//            session.save(pushkin);
//            session.save(yesenin);
            Author author = session.get(Author.class, 6);
            session.remove(author);



            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}
