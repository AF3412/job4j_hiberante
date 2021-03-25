package ru.af3412.lazyInitialization;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.ArrayList;
import java.util.List;

public class BoatStart {

    public static void main(String[] args) {
        List<BoatProducer> list = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            /*BoatProducer producer = BoatProducer.of("honda");
            session.save(producer);
            Boat one = Boat.of("small", producer);
            session.save(one);
            Boat two = Boat.of("medium", producer);
            session.save(two);
            Boat three = Boat.of("big", producer);
            session.save(three);*/

            list = session.createQuery(
                    "select distinct p from BoatProducer p join fetch p.boats"
            ).list();

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (var boatProducers : list) {
            for (var boat : boatProducers.getBoats()) {
                System.out.println(boat);
            }
        }
    }

}
