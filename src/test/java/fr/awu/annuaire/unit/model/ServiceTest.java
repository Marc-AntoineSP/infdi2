package fr.awu.annuaire.unit.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import fr.awu.annuaire.model.Service;

class ServiceTest {

    @Test
    void createServiceWithConstructorShouldSuccess() {
        Service service = new Service("Informatique");
        assertEquals("Informatique", service.getName());
    }

    @Test
    void createServiceWithSetterShouldSuccess() {
        Service service = new Service("Initial");
        service.setName("Informatique");
        assertEquals("Informatique", service.getName());
    }

    @Test
    void serviceEqualsShouldWorkCorrectly() {
        Service service1 = new Service("Test");
        Service service2 = new Service("Test");

        assertEquals(service1, service1);
    }

    @Test
    void serviceHashCodeShouldBeConsistent() {
        Service service = new Service("Test");

        int hashCode1 = service.hashCode();
        int hashCode2 = service.hashCode();

        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void serviceToStringShouldNotThrowException() {
        Service service = new Service("Test");
        assertDoesNotThrow(() -> service.toString());
    }
}
