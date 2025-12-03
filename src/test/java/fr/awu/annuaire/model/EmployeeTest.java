package fr.awu.annuaire.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class PersonValidationTest {

    private static Validator validator;

    @BeforeAll
    static void initValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Employee createValidEmployee() {
        Service service = new Service("Informatique");
        Site site = new Site("Paris");

        return new Employee(
                "John",
                "Doe",
                "john.doe@example.com",
                "0123456789",
                "0987654321",
                service,
                site,
                "password123");
    }

    @Test
    void validEmployeeShouldHaveNoViolations() {
        Employee employee = createValidEmployee();

        Set<ConstraintViolation<Employee>> violations = validator
                .validate(employee);

        assertTrue(violations.isEmpty(), () -> "Violations : " + violations);
    }

    @Test
    void blankFirstNameShouldProduceViolationOnFirstName() {
        Employee employee = createValidEmployee();
        employee.setFirstName("");

        Set<ConstraintViolation<Employee>> violations = validator
                .validate(employee);

        assertFalse(violations.isEmpty());

        boolean hasFirstNameViolation = violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().equals("firstName")
                        && v.getMessage().contains("cannot be blank"));

        assertTrue(hasFirstNameViolation, "Expected a violation on firstName");
    }

    @Test
    void invalidEmailShouldProduceViolationOnEmail() {
        Employee employee = createValidEmployee();
        employee.setEmail("");

        Set<ConstraintViolation<Employee>> violations = validator
                .validate(employee);

        boolean hasEmailViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")
                        && v.getMessage().contains("valid"));

        assertTrue(hasEmailViolation, "Expected an email validation error");
    }

    @Test
    void shouldTriggerPhonePresentViolation() {
        Employee employee = createValidEmployee();
        employee.setHomePhone(null);
        employee.setMobilePhone(null);

        Set<ConstraintViolation<Employee>> violations = validator
                .validate(employee);

        assertFalse(violations.isEmpty());

        boolean hasPhonePresentViolation = violations.stream().anyMatch(v -> v
                .getPropertyPath().toString().equals("phonePresent")
                && v.getMessage().contains("At least one phone number"));

        assertTrue(hasPhonePresentViolation,
                "Expected a violation on phonePresent (At least one phone number...)");
    }

    @Test
    void shouldProduceLengthViolation() {
        Employee employee = createValidEmployee();
        employee.setHomePhone("01234");

        Set<ConstraintViolation<Employee>> violations = validator
                .validate(employee);

        boolean hasHomePhoneViolation = violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().equals("homePhone")
                        && v.getMessage().contains("Phone = 10 digits"));

        assertTrue(hasHomePhoneViolation,
                "Expected a length violation on homePhone");
    }

    @Test
    void shouldProduceViolationOnService() {
        Employee employee = createValidEmployee();
        employee.setService(null);

        Set<ConstraintViolation<Employee>> violations = validator
                .validate(employee);

        boolean hasServiceViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("service")
                        && v.getMessage().contains("cannot be null"));

        assertTrue(hasServiceViolation, "Expected a violation on service");
    }

    @Test
    void shouldProduceViolationOnSite() {
        Employee employee = createValidEmployee();
        employee.setSite(null);

        Set<ConstraintViolation<Employee>> violations = validator
                .validate(employee);

        boolean hasSiteViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("site")
                        && v.getMessage().contains("cannot be null"));

        assertTrue(hasSiteViolation, "Expected a violation on site");
    }
}
