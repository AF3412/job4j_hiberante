package ru.af3412.cars;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HbmRun {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
            try (Session session = sf.openSession()) {
                session.beginTransaction();
                CarModel modelOne = new CarModel("one");
                session.save(modelOne);
                CarModel modelTwo = new CarModel("two");
                session.save(modelTwo);
                CarModel modelThree = new CarModel("three");
                session.save(modelThree);
                CarModel modelFour = new CarModel("four");
                session.save(modelFour);
                CarModel modelFive = new CarModel("five");
                session.save(modelFive);
                CarProducer carProducer = new CarProducer("Producer");
                carProducer.addCarModel(session.load(CarModel.class, 1));
                carProducer.addCarModel(session.load(CarModel.class, 2));
                carProducer.addCarModel(session.load(CarModel.class, 3));
                carProducer.addCarModel(session.load(CarModel.class, 4));
                carProducer.addCarModel(session.load(CarModel.class, 5));
                session.save(carProducer);

                session.getTransaction().commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
