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
import fr.awu.annuaire.model.Admin;
import fr.awu.annuaire.model.Service;
import fr.awu.annuaire.model.Site;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class AdminTest {
    private static Validator validator;

    @BeforeAll
    static void initValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private Admin createValidAdmin() {
        Service service = new Service("Direction");
        Site site = new Site("Paris");

        return new Admin(
                "Alice",
                "Manager",
                "alice.manager@example.com",
                "0123456789",
                "0987654321",
                service,
                site,
                "adminPass123");
    }

    @Test
    void validAdminShouldHaveNoViolations() {
        Admin admin = createValidAdmin();

        Set<ConstraintViolation<Admin>> violations = validator
                .validate(admin);

        assertTrue(violations.isEmpty(), () -> "Violations : " + violations);
    }

    @Test
    void adminShouldHaveAdminRole() {
        Admin admin = createValidAdmin();
        assertEquals(Roles.ADMIN, admin.getRole());
    }

    @Test
    void adminPasswordShouldBeHashed() {
        Admin admin = createValidAdmin();
        String hashedPassword = admin.getHashedPassword();

        assertNotNull(hashedPassword);
        assertNotEquals("adminPass123", hashedPassword);
        assertTrue(BCrypt.checkpw("adminPass123", hashedPassword));
    }

    @Test
    void adminGettersShouldReturnCorrectValues() {
        Service service = new Service("Direction");
        Site site = new Site("Paris");
        Admin admin = new Admin("Alice", "Manager", "alice@example.com",
                "0123456789", "0987654321", service, site, "password");

        assertEquals("Alice", admin.getFirstName());
        assertEquals("Manager", admin.getLastName());
        assertEquals("alice@example.com", admin.getEmail());
        assertEquals("0123456789", admin.getHomePhone());
        assertEquals("0987654321", admin.getMobilePhone());
        assertEquals(service, admin.getService());
        assertEquals(site, admin.getSite());
    }

    @Test
    void adminSettersShouldModifyValues() {
        Admin admin = createValidAdmin();
        Service newService = new Service("RH");
        Site newSite = new Site("Lyon");

        admin.setFirstName("Bob");
        admin.setLastName("Boss");
        admin.setEmail("bob@example.com");
        admin.setHomePhone("1111111111");
        admin.setMobilePhone("2222222222");
        admin.setService(newService);
        admin.setSite(newSite);

        assertEquals("Bob", admin.getFirstName());
        assertEquals("Boss", admin.getLastName());
        assertEquals("bob@example.com", admin.getEmail());
        assertEquals("1111111111", admin.getHomePhone());
        assertEquals("2222222222", admin.getMobilePhone());
        assertEquals(newService, admin.getService());
        assertEquals(newSite, admin.getSite());
    }

    @Test
    void blankFirstNameShouldProduceViolation() {
        Admin admin = createValidAdmin();
        admin.setFirstName("");

        Set<ConstraintViolation<Admin>> violations = validator
                .validate(admin);

        assertFalse(violations.isEmpty());
        boolean hasFirstNameViolation = violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().equals("firstName"));
        assertTrue(hasFirstNameViolation);
    }

    @Test
    void blankLastNameShouldProduceViolation() {
        Admin admin = createValidAdmin();
        admin.setLastName("");

        Set<ConstraintViolation<Admin>> violations = validator
                .validate(admin);

        boolean hasLastNameViolation = violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().equals("lastName"));
        assertTrue(hasLastNameViolation);
    }

    @Test
    void invalidEmailShouldProduceViolation() {
        Admin admin = createValidAdmin();
        admin.setEmail("not-an-email");

        Set<ConstraintViolation<Admin>> violations = validator
                .validate(admin);

        boolean hasEmailViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("email"));
        assertTrue(hasEmailViolation);
    }

    @Test
    void shouldTriggerPhonePresentViolation() {
        Admin admin = createValidAdmin();
        admin.setHomePhone(null);
        admin.setMobilePhone(null);

        Set<ConstraintViolation<Admin>> violations = validator
                .validate(admin);

        boolean hasPhonePresentViolation = violations.stream().anyMatch(v -> v
                .getPropertyPath().toString().equals("phonePresent")
                && v.getMessage().contains("At least one phone number"));

        assertTrue(hasPhonePresentViolation);
    }

    @Test
    void shouldAllowOnlyHomePhone() {
        Admin admin = createValidAdmin();
        admin.setMobilePhone(null);

        Set<ConstraintViolation<Admin>> violations = validator
                .validate(admin);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldAllowOnlyMobilePhone() {
        Admin admin = createValidAdmin();
        admin.setHomePhone(null);

        Set<ConstraintViolation<Admin>> violations = validator
                .validate(admin);

        assertTrue(violations.isEmpty());
    }

    @Test
    void shouldProduceLengthViolationOnHomePhone() {
        Admin admin = createValidAdmin();
        admin.setHomePhone("123");

        Set<ConstraintViolation<Admin>> violations = validator
                .validate(admin);

        boolean hasHomePhoneViolation = violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().equals("homePhone")
                        && v.getMessage().contains("Phone = 10 digits"));

        assertTrue(hasHomePhoneViolation);
    }

    @Test
    void shouldProduceLengthViolationOnMobilePhone() {
        Admin admin = createValidAdmin();
        admin.setMobilePhone("12345678901234");

        Set<ConstraintViolation<Admin>> violations = validator
                .validate(admin);

        boolean hasMobilePhoneViolation = violations.stream().anyMatch(
                v -> v.getPropertyPath().toString().equals("mobilePhone")
                        && v.getMessage().contains("Phone = 10 digits"));

        assertTrue(hasMobilePhoneViolation);
    }

    @Test
    void shouldProduceViolationOnNullService() {
        Admin admin = createValidAdmin();
        admin.setService(null);

        Set<ConstraintViolation<Admin>> violations = validator
                .validate(admin);

        boolean hasServiceViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("service")
                        && v.getMessage().contains("cannot be null"));

        assertTrue(hasServiceViolation);
    }

    @Test
    void shouldProduceViolationOnNullSite() {
        Admin admin = createValidAdmin();
        admin.setSite(null);

        Set<ConstraintViolation<Admin>> violations = validator
                .validate(admin);

        boolean hasSiteViolation = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals("site")
                        && v.getMessage().contains("cannot be null"));

        assertTrue(hasSiteViolation);
    }
}
