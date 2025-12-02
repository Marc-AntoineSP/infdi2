package fr.awu.annuaire.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import fr.awu.annuaire.model.Person;
import fr.awu.annuaire.utils.HibernateUtil;

public class PersonRepository {
    public void save(Person person) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(person);
            tx.commit();
        }
    }
    
    public List<Person> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Person", Person.class).list();
        }
    }

    public Person findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Person where email = :email", Person.class)
                .setParameter("email", email)
                .uniqueResult();
        }
    }
    
    public void update(Person person) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(person);
            tx.commit();
        }
    }
    
    public void delete (Person person) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(person);
            tx.commit();
        }
    }

}
