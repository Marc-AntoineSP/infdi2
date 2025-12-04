package fr.awu.annuaire.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import fr.awu.annuaire.model.Service;
import fr.awu.annuaire.service.ServiceService;

class ServiceServiceIT {
    private ServiceService serviceService = new ServiceService();

    @Test
    void addServiceToDatabaseTestShouldSuccess() {
        Service service = new Service("Informatique");
        serviceService.save(service);
        assertTrue(serviceService.getAll().contains(service));
    }

    @Test
    void addServiceToDatabaseTestShouldFail() {
        Service service = new Service("");
        assertThrows(IllegalArgumentException.class,
                () -> serviceService.save(service));
    }

    @Test
    void addNullServiceNameShouldFail() {
        Service service = new Service("Valid");
        service.setName(null);
        assertThrows(IllegalArgumentException.class,
                () -> serviceService.save(service));
    }

    @Test
    void updateServiceShouldSuccess() {
        Service service = new Service("Informatique");
        serviceService.save(service);

        service.setName("IT");
        serviceService.update(service);

        Service retrieved = serviceService.getById(service.getId());
        assertEquals("IT", retrieved.getName());
    }

    @Test
    void updateServiceWithEmptyNameShouldFail() {
        Service service = new Service("Informatique");
        serviceService.save(service);

        service.setName("");
        assertThrows(IllegalArgumentException.class,
                () -> serviceService.update(service));
    }

    @Test
    void deleteServiceShouldSuccess() {
        Service service = new Service("ToDelete");
        serviceService.save(service);
        int id = service.getId();

        serviceService.delete(service);

        assertNull(serviceService.getById(id));
    }

    @Test
    void getByIdShouldReturnCorrectService() {
        Service service = new Service("FindMe");
        serviceService.save(service);
        int id = service.getId();

        Service found = serviceService.getById(id);
        assertNotNull(found);
        assertEquals("FindMe", found.getName());
        assertEquals(id, found.getId());
    }

    @Test
    void getByIdWithInvalidIdShouldReturnNull() {
        Service found = serviceService.getById(99999);
        assertNull(found);
    }

    @Test
    void getAllShouldReturnAllServices() {
        int initialSize = serviceService.getAll().size();

        Service service1 = new Service("Service1");
        Service service2 = new Service("Service2");
        serviceService.save(service1);
        serviceService.save(service2);

        assertEquals(initialSize + 2, serviceService.getAll().size());
    }
}
