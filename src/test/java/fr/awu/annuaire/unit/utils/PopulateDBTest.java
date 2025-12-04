package fr.awu.annuaire.unit.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import fr.awu.annuaire.utils.PopulateDB;

class PopulateDBTest {

    @Test
    void populateDBConstructorShouldBePrivate() throws Exception {
        Constructor<PopulateDB> constructor = PopulateDB.class
                .getDeclaredConstructor();

        assertTrue(
                java.lang.reflect.Modifier
                        .isPrivate(constructor.getModifiers()),
                "Constructor should be private for utility class");
    }

    @Test
    void constructorShouldBeAccessibleViaReflection() throws Exception {
        Constructor<PopulateDB> constructor = PopulateDB.class
                .getDeclaredConstructor();
        constructor.setAccessible(true);

        PopulateDB instance = constructor.newInstance();
        assertNotNull(instance);
    }

    @Test
    void populateMethodShouldExist() throws Exception {
        Method populateMethod = PopulateDB.class.getDeclaredMethod("populate");
        assertNotNull(populateMethod);
        assertTrue(
                java.lang.reflect.Modifier
                        .isPublic(populateMethod.getModifiers()),
                "populate() method should be public");
        assertTrue(
                java.lang.reflect.Modifier
                        .isStatic(populateMethod.getModifiers()),
                "populate() method should be static");
    }

    @Test
    void populateServicesMethodShouldExist() throws Exception {
        Method method = PopulateDB.class.getDeclaredMethod("populateServices");
        assertNotNull(method);
        assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()),
                "populateServices() should be private");
        assertTrue(java.lang.reflect.Modifier.isStatic(method.getModifiers()),
                "populateServices() should be static");
    }

    @Test
    void populateSitesMethodShouldExist() throws Exception {
        Method method = PopulateDB.class.getDeclaredMethod("populateSites");
        assertNotNull(method);
        assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()),
                "populateSites() should be private");
        assertTrue(java.lang.reflect.Modifier.isStatic(method.getModifiers()),
                "populateSites() should be static");
    }

    @Test
    void fetchPersonsMethodShouldExist() throws Exception {
        Method method = PopulateDB.class.getDeclaredMethod("fetchPersons");
        assertNotNull(method);
        assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()),
                "fetchPersons() should be private");
        assertTrue(java.lang.reflect.Modifier.isStatic(method.getModifiers()),
                "fetchPersons() should be static");
        assertEquals(String.class, method.getReturnType(),
                "fetchPersons() should return String");
    }

    @Test
    void parsePersonMethodShouldExist() throws Exception {
        Method method = PopulateDB.class.getDeclaredMethod("parsePerson",
                String.class);
        assertNotNull(method);
        assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()),
                "parsePerson() should be private");
        assertTrue(java.lang.reflect.Modifier.isStatic(method.getModifiers()),
                "parsePerson() should be static");
    }

    @Test
    void mapToPersonMethodShouldExist() throws Exception {
        Class<?> apiUserResultClass = Class
                .forName("fr.awu.annuaire.utils.ApiUserResult");
        Class<?> rolesClass = Class.forName("fr.awu.annuaire.enums.Roles");
        Class<?> serviceClass = Class.forName("fr.awu.annuaire.model.Service");
        Class<?> siteClass = Class.forName("fr.awu.annuaire.model.Site");

        Method method = PopulateDB.class.getDeclaredMethod("mapToPerson",
                apiUserResultClass, rolesClass, serviceClass, siteClass);
        assertNotNull(method);
        assertTrue(java.lang.reflect.Modifier.isPrivate(method.getModifiers()),
                "mapToPerson() should be private");
        assertTrue(java.lang.reflect.Modifier.isStatic(method.getModifiers()),
                "mapToPerson() should be static");
    }

    private void assertEquals(Class<String> expected, Class<?> actual,
            String message) {
        assertTrue(expected.equals(actual), message);
    }
}
