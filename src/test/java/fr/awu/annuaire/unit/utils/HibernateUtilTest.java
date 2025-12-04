package fr.awu.annuaire.unit.utils;

import java.lang.reflect.Constructor;

import org.hibernate.SessionFactory;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import fr.awu.annuaire.utils.HibernateUtil;

class HibernateUtilTest {

    @Test
    void getSessionFactoryShouldReturnNonNullSessionFactory() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        assertNotNull(sessionFactory);
    }

    @Test
    void getSessionFactoryShouldReturnSameInstanceOnMultipleCalls() {
        SessionFactory sessionFactory1 = HibernateUtil.getSessionFactory();
        SessionFactory sessionFactory2 = HibernateUtil.getSessionFactory();

        assertSame(sessionFactory1, sessionFactory2,
                "SessionFactory should be a singleton");
    }

    @Test
    void sessionFactoryShouldNotBeClosed() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        assertTrue(!sessionFactory.isClosed(),
                "SessionFactory should be open");
    }

    @Test
    void hibernateUtilConstructorShouldBePrivate() throws Exception {
        Constructor<HibernateUtil> constructor = HibernateUtil.class
                .getDeclaredConstructor();

        assertTrue(
                java.lang.reflect.Modifier
                        .isPrivate(constructor.getModifiers()),
                "Constructor should be private for utility class");
    }

    @Test
    void constructorShouldBeAccessibleViaReflection() throws Exception {
        Constructor<HibernateUtil> constructor = HibernateUtil.class
                .getDeclaredConstructor();
        constructor.setAccessible(true);

        HibernateUtil instance = constructor.newInstance();
        assertNotNull(instance);
    }

    @Test
    void sessionFactoryShouldBeConfiguredCorrectly() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        assertNotNull(sessionFactory.getMetamodel(),
                "SessionFactory should have metadata");
        assertTrue(sessionFactory.getMetamodel().getEntities().size() > 0,
                "SessionFactory should have registered entities");
    }
}
