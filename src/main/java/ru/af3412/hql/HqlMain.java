package ru.af3412.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.Query;

public class HqlMain {

    public static void main(String[] args) {

        Candidate candidate = null;

        try (final StandardServiceRegistry registry =
                     new StandardServiceRegistryBuilder().configure().build();
             final SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
             final Session session = sf.openSession()
        ) {
            /*createCandidate(session);
            viewAllCandidate(session);
            viewCandidateById(session, 1);
            viewCandidateByName(session, "Second");
            updateCandidateById(session, 1, "new name");
            viewCandidateById(session, 1);
            deleteCandidateById(session, 3);
            viewCandidateById(session, 3);*/

            /*createData(session);*/

            candidate = viewCandidateBaseVacancy(session);


        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(candidate);
    }

    private static void createData(Session session) {
        session.beginTransaction();
        BaseVacanies baseVacanies = new BaseVacanies();
        Vacancy vacancy1 = new Vacancy("vacancy 1");
        Vacancy vacancy2 = new Vacancy("vacancy 2");
        Vacancy vacancy3 = new Vacancy("vacancy 3");
        baseVacanies.addVacancy(vacancy1);
        baseVacanies.addVacancy(vacancy2);
        baseVacanies.addVacancy(vacancy3);

        Candidate candidate1 = new Candidate("Alex", 1, 34.4f, baseVacanies);
        session.save(candidate1);

        session.getTransaction().commit();
    }

    private static void viewAllCandidate(Session session) {
        System.out.println("===== All Candidates =======");
        session.beginTransaction();
        Query query = session.createQuery("from Candidate");
        query.getResultList().forEach(System.out::println);
        session.getTransaction().commit();
    }

    private static void viewCandidateById(Session session, long id) {
        System.out.println("===== GET Candidate by ID =======");
        session.beginTransaction();
        Query query = session.createQuery("from Candidate where id=:id")
                .setParameter("id", id);
        query.getResultList().forEach(System.out::println);
        session.getTransaction().commit();
    }

    private static void viewCandidateByName(Session session, String name) {
        System.out.println("===== GET Candidate by NAME =======");
        session.beginTransaction();
        Query query = session.createQuery("from Candidate where name=:name")
                .setParameter("name", name);
        query.getResultList().forEach(System.out::println);
        session.getTransaction().commit();
    }

    private static void updateCandidateById(Session session, long id, String newName) {
        System.out.println("===== UPDATE Candidate =======");
        session.beginTransaction();
        Query query = session.createQuery("update Candidate c set name = :name where id=:id")
                .setParameter("name", newName)
                .setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    private static void deleteCandidateById(Session session, long id) {
        System.out.println("===== DELETE Candidate =======");
        session.beginTransaction();
        Query query = session.createQuery("delete Candidate c where c.id=:id")
                .setParameter("id", id);
        query.executeUpdate();
        session.getTransaction().commit();
    }

    private static void save(Session session, Object entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }

    private static Candidate viewCandidateBaseVacancy(Session session) {
        System.out.println("=========== BASE VACANCY ===========");
        session.beginTransaction();
        Query query = session.createQuery(
                "from Candidate c " +
                        "join fetch c.baseVacanies bv " +
                        "join fetch bv.vacancies where c.id = :id",
                Candidate.class)
                .setParameter("id", 5L);
        Candidate candidate = (Candidate) query.getSingleResult();
        session.getTransaction().commit();
        return candidate;
    }

}
