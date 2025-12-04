package fr.awu.annuaire.unit.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.awu.annuaire.model.Employee;
import fr.awu.annuaire.model.Service;
import fr.awu.annuaire.model.Site;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class ValidationUtilsTest {
    private static Validator validator;

    @BeforeAll
    static void initValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void buildErrorMapShouldGroupViolationsByPropertyPath() throws Exception {
        Service service = new Service("IT");
        Site site = new Site("Paris");
        Employee employee = new Employee("", "", "invalid-email",
                "123", "456", service, site, "pass");

        Set<ConstraintViolation<Employee>> violations = validator
                .validate(employee);

        Class<?> validationUtilsClass = Class
                .forName("fr.awu.annuaire.utils.ValidationUtils");
        Constructor<?> constructor = validationUtilsClass
                .getDeclaredConstructor();
        constructor.setAccessible(true);
        Object validationUtils = constructor.newInstance();

        Method buildErrorMapMethod = validationUtilsClass.getDeclaredMethod(
                "buildErrorMap", Set.class);
        buildErrorMapMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, List<String>> errorMap = (Map<String, List<String>>) buildErrorMapMethod
                .invoke(validationUtils, violations);

        assertTrue(errorMap.containsKey("firstName"));
        assertTrue(errorMap.containsKey("lastName"));
        assertTrue(errorMap.containsKey("email"));
        assertTrue(errorMap.containsKey("homePhone"));
        assertTrue(errorMap.containsKey("mobilePhone"));

        assertTrue(errorMap.get("firstName") instanceof List);
        assertTrue(errorMap.get("firstName").size() > 0);
    }

    @Test
    void buildErrorMapShouldReturnEmptyMapForValidObject() throws Exception {
        Service service = new Service("IT");
        Site site = new Site("Paris");
        Employee employee = new Employee("John", "Doe", "john@example.com",
                "0123456789", "0987654321", service, site, "password");

        Set<ConstraintViolation<Employee>> violations = validator
                .validate(employee);

        Class<?> validationUtilsClass = Class
                .forName("fr.awu.annuaire.utils.ValidationUtils");
        Constructor<?> constructor = validationUtilsClass
                .getDeclaredConstructor();
        constructor.setAccessible(true);
        Object validationUtils = constructor.newInstance();

        Method buildErrorMapMethod = validationUtilsClass.getDeclaredMethod(
                "buildErrorMap", Set.class);
        buildErrorMapMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, List<String>> errorMap = (Map<String, List<String>>) buildErrorMapMethod
                .invoke(validationUtils, violations);

        assertTrue(errorMap.isEmpty());
    }

    @Test
    void validationUtilsConstructorShouldBePrivate() throws Exception {
        Class<?> validationUtilsClass = Class
                .forName("fr.awu.annuaire.utils.ValidationUtils");
        Constructor<?> constructor = validationUtilsClass
                .getDeclaredConstructor();

        assertTrue(
                java.lang.reflect.Modifier
                        .isPrivate(constructor.getModifiers()),
                "Constructor should be private for utility class");
    }

    @Test
    void buildErrorMapShouldHandleMultipleErrorsOnSameField() throws Exception {
        Service service = new Service("IT");
        Site site = new Site("Paris");
        Employee employee = new Employee("John", "Doe", "john@example.com",
                null, null, service, site, "password");

        Set<ConstraintViolation<Employee>> violations = validator
                .validate(employee);

        Class<?> validationUtilsClass = Class
                .forName("fr.awu.annuaire.utils.ValidationUtils");
        Constructor<?> constructor = validationUtilsClass
                .getDeclaredConstructor();
        constructor.setAccessible(true);
        Object validationUtils = constructor.newInstance();

        Method buildErrorMapMethod = validationUtilsClass.getDeclaredMethod(
                "buildErrorMap", Set.class);
        buildErrorMapMethod.setAccessible(true);

        @SuppressWarnings("unchecked")
        Map<String, List<String>> errorMap = (Map<String, List<String>>) buildErrorMapMethod
                .invoke(validationUtils, violations);

        assertTrue(errorMap.containsKey("phonePresent"));
        assertEquals(1, errorMap.size());
    }
}
