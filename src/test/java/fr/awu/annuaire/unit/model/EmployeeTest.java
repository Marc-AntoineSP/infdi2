package fr.awu.annuaire.unit.model;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;

import fr.awu.annuaire.enums.Roles;
import fr.awu.annuaire.model.Employee;
import fr.awu.annuaire.model.Service;
import fr.awu.annuaire.model.Site;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class EmployeeTest {
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
    void employeeShouldHaveEmployeeRole() {
        Employee employee = createValidEmployee();
        assertEquals(Roles.EMPLOYEE, employee.getRole());
    }

    @Test
    void employeePasswordShouldBeHashed() {
        Employee employee = createValidEmployee();
        String hashedPassword = employee.getHashedPassword();

        assertNotNull(hashedPassword);
        assertNotEquals("password123", hashedPassword);
        assertTrue(BCrypt.checkpw("password123", hashedPassword));
    }

    @Test
    void employeeGettersShouldReturnCorrectValues() {
        Service service = new Service("Informatique");
        Site site = new Site("Paris");
        Employee employee = new Employee("John", "Doe", "john@example.com",
                "0123456789", "0987654321", service, site, "password");

        assertEquals("John", employee.getFirstName());
        assertEquals("Doe", employee.getLastName());
        assertEquals("john@example.com", employee.getEmail());
        assertEquals("0123456789", employee.getHomePhone());
        assertEquals("0987654321", employee.getMobilePhone());
        assertEquals(service, employee.getService());
        assertEquals(site, employee.getSite());
    }

    @Test
    void employeeSettersShouldModifyValues() {
        Employee employee = createValidEmployee();
        Service newService = new Service("RH");
        Site newSite = new Site("Lyon");

        employee.setFirstName("Jane");
        employee.setLastName("Smith");
        employee.setEmail("jane@example.com");
        employee.setHomePhone("1111111111");
        employee.setMobilePhone("2222222222");
        employee.setService(newService);
        employee.setSite(newSite);

        assertEquals("Jane", employee.getFirstName());
        assertEquals("Smith", employee.getLastName());
        assertEquals("jane@example.com", employee.getEmail());
        assertEquals("1111111111", employee.getHomePhone());
        assertEquals("2222222222", employee.getMobilePhone());
        assertEquals(newService, employee.getService());
        assertEquals(newSite, employee.getSite());
    }

    @Test
    void blankFirstNameShouldProduceViolationOnFirstName() {
        Employee employee = createValidEmployee();
        employee.setFirstName("");

        Set<ConstraintViolation<Employee>> violations = validator
                .validate(employee);

        assertFalse(violations.isEmpty());

        boolean hasFirstNameViolation = violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().equals("firstName"));

        assertTrue(hasFirstNameViolation, "Expected a violation on firstName");
    }

    @Test
    void blankLastNameShouldProduceViolationOnLastName() {
        Employee employee = createValidEmployee();
        employee.setLastName("");

        Set<ConstraintViolation<Employee>> violations = validator
                .validate(employee);

        boolean hasLastNameViolation = violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().equals("lastName"));

        assertTrue(hasLastNameViolation, "Expected a violation on lastName");
    }

    @Test
    void invalidEmailShouldProduceViolationOnEmail() {
        Employee employee = createValidEmployee();
        employee.setEmail("");

        Set<ConstraintViolation<Employee>> violations = validator
                .validate(employee);

        boolean hasEmailViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email"));

        assertTrue(hasEmailViolation, "Expected an email validation error");
    }

    @Test
    void invalidEmailFormatShouldProduceViolation() {
        Employee employee = createValidEmployee();
        employee.setEmail("not-an-email");

        Set<ConstraintViolation<Employee>> violations = validator
                .validate(employee);

        boolean hasEmailViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email")
                        && v.getMessage().contains("should be valid"));

        assertTrue(hasEmailViolation, "Expected email format violation");
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
    void shouldAllowOnlyHomePhone() {
        Employee employee = createValidEmployee();
        employee.setMobilePhone(null);

        Set<ConstraintViolation<Employee>> violations = validator
                .validate(employee);

        assertTrue(violations.isEmpty(),
                "Should be valid with only home phone");
    }

    @Test
    void shouldAllowOnlyMobilePhone() {
        Employee employee = createValidEmployee();
        employee.setHomePhone(null);

        Set<ConstraintViolation<Employee>> violations = validator
                .validate(employee);

        assertTrue(violations.isEmpty(),
                "Should be valid with only mobile phone");
    }

    @Test
    void shouldProduceLengthViolationOnHomePhone() {
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
    void shouldProduceLengthViolationOnMobilePhone() {
        Employee employee = createValidEmployee();
        employee.setMobilePhone("123");

        Set<ConstraintViolation<Employee>> violations = validator
                .validate(employee);

        boolean hasMobilePhoneViolation = violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().equals("mobilePhone")
                        && v.getMessage().contains("Phone = 10 digits"));

        assertTrue(hasMobilePhoneViolation,
                "Expected a length violation on mobilePhone");
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
