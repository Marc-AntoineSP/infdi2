package fr.awu.annuaire.unit.utils;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class DtoTest {

    @Test
    void apiUserResultClassShouldExist() throws Exception {
        Class<?> apiUserResultClass = Class
                .forName("fr.awu.annuaire.utils.ApiUserResult");
        assertNotNull(apiUserResultClass);
    }

    @Test
    void apiUserResponseClassShouldExist() throws Exception {
        Class<?> apiUserResponseClass = Class
                .forName("fr.awu.annuaire.utils.ApiUserResponse");
        assertNotNull(apiUserResponseClass);
    }

    @Test
    void nameClassShouldExist() throws Exception {
        Class<?> nameClass = Class.forName("fr.awu.annuaire.utils.Name");
        assertNotNull(nameClass);
    }

    @Test
    void loginClassShouldExist() throws Exception {
        Class<?> loginClass = Class.forName("fr.awu.annuaire.utils.Login");
        assertNotNull(loginClass);
    }

    @Test
    void personTableDtoClassShouldExist() throws Exception {
        Class<?> personTableDtoClass = Class
                .forName("fr.awu.annuaire.utils.PersonTableDto");
        assertNotNull(personTableDtoClass);
    }

    @Test
    void apiUserResultShouldHaveNameField() throws Exception {
        Class<?> apiUserResultClass = Class
                .forName("fr.awu.annuaire.utils.ApiUserResult");
        assertNotNull(apiUserResultClass.getDeclaredField("name"));
    }

    @Test
    void apiUserResultShouldHaveEmailField() throws Exception {
        Class<?> apiUserResultClass = Class
                .forName("fr.awu.annuaire.utils.ApiUserResult");
        assertNotNull(apiUserResultClass.getDeclaredField("email"));
    }

    @Test
    void apiUserResultShouldHaveLoginField() throws Exception {
        Class<?> apiUserResultClass = Class
                .forName("fr.awu.annuaire.utils.ApiUserResult");
        assertNotNull(apiUserResultClass.getDeclaredField("login"));
    }

    @Test
    void apiUserResultShouldHavePhoneField() throws Exception {
        Class<?> apiUserResultClass = Class
                .forName("fr.awu.annuaire.utils.ApiUserResult");
        assertNotNull(apiUserResultClass.getDeclaredField("phone"));
    }

    @Test
    void apiUserResultShouldHaveCellField() throws Exception {
        Class<?> apiUserResultClass = Class
                .forName("fr.awu.annuaire.utils.ApiUserResult");
        assertNotNull(apiUserResultClass.getDeclaredField("cell"));
    }

    @Test
    void apiUserResponseShouldHaveResultsField() throws Exception {
        Class<?> apiUserResponseClass = Class
                .forName("fr.awu.annuaire.utils.ApiUserResponse");
        assertNotNull(apiUserResponseClass.getDeclaredField("results"));
    }

    @Test
    void nameShouldHaveFirstField() throws Exception {
        Class<?> nameClass = Class.forName("fr.awu.annuaire.utils.Name");
        assertNotNull(nameClass.getDeclaredField("first"));
    }

    @Test
    void nameShouldHaveLastField() throws Exception {
        Class<?> nameClass = Class.forName("fr.awu.annuaire.utils.Name");
        assertNotNull(nameClass.getDeclaredField("last"));
    }

    @Test
    void loginShouldHavePasswordField() throws Exception {
        Class<?> loginClass = Class.forName("fr.awu.annuaire.utils.Login");
        assertNotNull(loginClass.getDeclaredField("password"));
    }

    @Test
    void personTableDtoShouldHaveFirstNameField() throws Exception {
        Class<?> personTableDtoClass = Class
                .forName("fr.awu.annuaire.utils.PersonTableDto");
        assertNotNull(personTableDtoClass.getDeclaredField("firstName"));
    }

    @Test
    void personTableDtoShouldHaveLastNameField() throws Exception {
        Class<?> personTableDtoClass = Class
                .forName("fr.awu.annuaire.utils.PersonTableDto");
        assertNotNull(personTableDtoClass.getDeclaredField("lastName"));
    }

    @Test
    void personTableDtoShouldHaveServiceField() throws Exception {
        Class<?> personTableDtoClass = Class
                .forName("fr.awu.annuaire.utils.PersonTableDto");
        assertNotNull(personTableDtoClass.getDeclaredField("service"));
    }

    @Test
    void personTableDtoShouldHaveSiteField() throws Exception {
        Class<?> personTableDtoClass = Class
                .forName("fr.awu.annuaire.utils.PersonTableDto");
        assertNotNull(personTableDtoClass.getDeclaredField("site"));
    }

    @Test
    void apiUserResultShouldBeInstantiable() throws Exception {
        Class<?> apiUserResultClass = Class
                .forName("fr.awu.annuaire.utils.ApiUserResult");
        Constructor<?> constructor = apiUserResultClass
                .getDeclaredConstructor();
        constructor.setAccessible(true);

        Object instance = assertDoesNotThrow(() -> constructor.newInstance());
        assertNotNull(instance);
    }

    @Test
    void apiUserResponseShouldBeInstantiable() throws Exception {
        Class<?> apiUserResponseClass = Class
                .forName("fr.awu.annuaire.utils.ApiUserResponse");
        Constructor<?> constructor = apiUserResponseClass
                .getDeclaredConstructor();
        constructor.setAccessible(true);

        Object instance = assertDoesNotThrow(() -> constructor.newInstance());
        assertNotNull(instance);
    }

    @Test
    void nameShouldBeInstantiable() throws Exception {
        Class<?> nameClass = Class.forName("fr.awu.annuaire.utils.Name");
        Constructor<?> constructor = nameClass.getDeclaredConstructor();
        constructor.setAccessible(true);

        Object instance = assertDoesNotThrow(() -> constructor.newInstance());
        assertNotNull(instance);
    }

    @Test
    void loginShouldBeInstantiable() throws Exception {
        Class<?> loginClass = Class.forName("fr.awu.annuaire.utils.Login");
        Constructor<?> constructor = loginClass.getDeclaredConstructor();
        constructor.setAccessible(true);

        Object instance = assertDoesNotThrow(() -> constructor.newInstance());
        assertNotNull(instance);
    }

    @Test
    void personTableDtoShouldBeInstantiable() throws Exception {
        Class<?> personTableDtoClass = Class
                .forName("fr.awu.annuaire.utils.PersonTableDto");
        Constructor<?> constructor = personTableDtoClass
                .getDeclaredConstructor();
        constructor.setAccessible(true);

        Object instance = assertDoesNotThrow(() -> constructor.newInstance());
        assertNotNull(instance);
    }

    @Test
    void dtoClassesShouldBePackagePrivate() throws Exception {
        Class<?> apiUserResultClass = Class
                .forName("fr.awu.annuaire.utils.ApiUserResult");
        Class<?> apiUserResponseClass = Class
                .forName("fr.awu.annuaire.utils.ApiUserResponse");
        Class<?> nameClass = Class.forName("fr.awu.annuaire.utils.Name");
        Class<?> loginClass = Class.forName("fr.awu.annuaire.utils.Login");
        Class<?> personTableDtoClass = Class
                .forName("fr.awu.annuaire.utils.PersonTableDto");

        assertTrue(!java.lang.reflect.Modifier
                .isPublic(apiUserResultClass.getModifiers()));
        assertTrue(!java.lang.reflect.Modifier
                .isPublic(apiUserResponseClass.getModifiers()));
        assertTrue(
                !java.lang.reflect.Modifier.isPublic(nameClass.getModifiers()));
        assertTrue(!java.lang.reflect.Modifier
                .isPublic(loginClass.getModifiers()));
        assertTrue(!java.lang.reflect.Modifier
                .isPublic(personTableDtoClass.getModifiers()));
    }
}
