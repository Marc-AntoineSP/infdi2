package fr.awu.annuaire.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import fr.awu.annuaire.model.Service;
import fr.awu.annuaire.utils.HibernateUtil;

public class ServiceRepository {
    public void save(Service service) {
        try (Session session = HibernateUtil.getSessionFactory()
                .openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(service);
            tx.commit();
        }
    }

    public Service getById(int id) {
        try (Session session = HibernateUtil.getSessionFactory()
                .openSession()) {
            return session.get(Service.class, id);
        }
    }

    public List<Service> getAll() {
        try (Session session = HibernateUtil.getSessionFactory()
                .openSession()) {
            return session.createQuery("from Service", Service.class).list();
        }
    }

    public void update(Service service) {
        try (Session session = HibernateUtil.getSessionFactory()
                .openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(service);
            tx.commit();
        }
    }

    public void delete(Service service) {
        try (Session session = HibernateUtil.getSessionFactory()
                .openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(service);
            tx.commit();
        }
    }
}
