package fr.awu.annuaire.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import fr.awu.annuaire.model.Site;
import fr.awu.annuaire.utils.HibernateUtil;

public class SiteRepository {

    public void save(Site service) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(service);
            tx.commit();
        }
    }
    
    public List<Site> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Site", Site.class).list();
        }
    }
    
    public void update(Site service) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.merge(service);
            tx.commit();
        }
    }
    
    public void delete (Site service) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            session.remove(service);
            tx.commit();
        }
    }
}
