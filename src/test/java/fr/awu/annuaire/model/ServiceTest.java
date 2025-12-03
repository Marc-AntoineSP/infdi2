package fr.awu.annuaire.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.awu.annuaire.service.ServiceService;

class ServiceTest {
    private ServiceService serviceService = new ServiceService();
    
    @Test
    void createServiceTestShouldSuccess(){
        Service service = new Service();
        Service service2 = new Service("Sans setter");
        service.setName("Informatique");
        assertEquals("Informatique", service.getName());
        assertEquals("Sans setter", service2.getName());
    }
    
    @Test
    void addServiceToDatabaseTestShouldSuccess(){
        Service service = new Service();
        service.setName("Informatique");
        serviceService.save(service);
        assertTrue(serviceService.getAll().contains(service));
    }
    
    @Test
    void addServiceToDatabaseTestShouldFail(){
        Service service = new Service("");
        assertThrows(IllegalArgumentException.class, () -> serviceService.save(service));
    }
}
