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
        assert service.getName().equals("Informatique");
        assert service2.getName().equals("Sans setter");
    }

    @Test
    void addServiceToDatabaseTestShouldSuccess(){
        Service service = new Service();
        service.setName("Informatique");
        serviceService.save(service);
        assert serviceService.getAll().contains(service);
    }

    @Test
    void addServiceToDatabaseTestShouldFail(){
        Service service = new Service("");
        assertThrows(IllegalArgumentException.class, () -> serviceService.save(service));
    }
}
