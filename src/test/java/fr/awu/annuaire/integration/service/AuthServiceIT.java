package fr.awu.annuaire.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.awu.annuaire.enums.Roles;
import fr.awu.annuaire.model.Admin;
import fr.awu.annuaire.model.Employee;
import fr.awu.annuaire.model.Person;
import fr.awu.annuaire.model.Service;
import fr.awu.annuaire.model.Site;
import fr.awu.annuaire.service.AuthService;
import fr.awu.annuaire.service.PersonService;
import fr.awu.annuaire.service.ServiceService;
import fr.awu.annuaire.service.SiteService;

class AuthServiceIT {
    private AuthService authService;
    private PersonService personService;
    private ServiceService serviceService;
    private SiteService siteService;
    private static int testCounter = 0;
    private String employeeEmail;
    private String adminEmail;

    @BeforeEach
    void setUp() {
        personService = new PersonService();
        serviceService = new ServiceService();
        siteService = new SiteService();
        authService = new AuthService(personService);

        testCounter++;
        employeeEmail = "john.doe" + testCounter + "@example.com";
        adminEmail = "jane.smith" + testCounter + "@example.com";

        Service service = new Service("IT" + testCounter);
        Site site = new Site("Paris" + testCounter);
        serviceService.save(service);
        siteService.save(site);

        Employee employee = new Employee("John", "Doe", employeeEmail,
                "0123456789", "0987654321", service, site, "password123");
        Admin admin = new Admin("Jane", "Smith", adminEmail,
                "0123456789", "0987654321", service, site, "adminpass");

        personService.save(employee);
        personService.save(admin);
    }

    @Test
    void loginWithValidCredentialsShouldReturnPerson() {
        Person person = authService.login(employeeEmail, "password123");

        assertNotNull(person);
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
        assertEquals(employeeEmail, person.getEmail());
    }

    @Test
    void loginWithInvalidPasswordShouldReturnNull() {
        Person person = authService.login(employeeEmail, "wrongpassword");

        assertNull(person);
    }

    @Test
    void loginWithNonExistentEmailShouldReturnNull() {
        Person person = authService.login("nonexistent@example.com",
                "password123");

        assertNull(person);
    }

    @Test
    void loginShouldSetCurrentPerson() {
        authService.login(employeeEmail, "password123");

        Person currentPerson = authService.getCurrentPerson();
        assertNotNull(currentPerson);
        assertEquals(employeeEmail, currentPerson.getEmail());
    }

    @Test
    void logoutShouldClearCurrentPerson() {
        authService.login(employeeEmail, "password123");
        assertNotNull(authService.getCurrentPerson());

        authService.logout();

        assertNull(authService.getCurrentPerson());
    }

    @Test
    void checkRoleAdminShouldReturnTrueForAdmin() {
        authService.login(adminEmail, "adminpass");

        assertTrue(authService.checkRoleAdmin());
    }

    @Test
    void checkRoleAdminShouldReturnFalseForEmployee() {
        authService.login(employeeEmail, "password123");

        assertFalse(authService.checkRoleAdmin());
    }

    @Test
    void getCurrentPersonShouldReturnNullWhenNotLoggedIn() {
        assertNull(authService.getCurrentPerson());
    }

    @Test
    void multipleLoginsShouldUpdateCurrentPerson() {
        authService.login(employeeEmail, "password123");
        assertEquals(employeeEmail, authService.getCurrentPerson().getEmail());

        authService.login(adminEmail, "adminpass");
        assertEquals(adminEmail, authService.getCurrentPerson().getEmail());
        assertEquals(Roles.ADMIN, authService.getCurrentPerson().getRole());
    }

    @Test
    void loginAfterLogoutShouldWork() {
        authService.login(employeeEmail, "password123");
        authService.logout();
        assertNull(authService.getCurrentPerson());

        Person person = authService.login(employeeEmail, "password123");
        assertNotNull(person);
        assertEquals(employeeEmail, authService.getCurrentPerson().getEmail());
    }
}
