package fr.awu.annuaire.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.awu.annuaire.service.PersonService;
import fr.awu.annuaire.service.ServiceService;
import fr.awu.annuaire.service.SiteService;

class EmployeeTest {

    private PersonService employeeService = new PersonService();
    private ServiceService serviceService = new ServiceService();
    private SiteService siteService = new SiteService();

    @Test
    void createEmployeeTestShouldSuccess(){
        Service service = new Service("Informatique");
        Site site = new Site("Paris");
        Employee employee = new Employee("John", "Doe", "john.doe@example.com", 123456789, 987654321, service, site, "password123");
        assert employee != null;
        assert employee.getFirstName().equals("John");
        assert employee.getLastName().equals("Doe");
        assert employee.getEmail().equals("john.doe@example.com");
        assert employee.getHomePhone() == 123456789;
        assert employee.getMobilePhone() == 987654321;
        assert employee.getService().equals(service);
        assert employee.getSite().equals(site);
    }

    @Test
    void addEmployeeToDatabaseTestShouldSuccess(){
        Service service = new Service("Informatique");
        Site site = new Site("Paris");
        siteService.save(site);
        serviceService.save(service);
        Employee employee = new Employee("John", "Doe", "john.doe@example.com", 123456789, 987654321, service, site, "password123");
        employeeService.save(employee);
        assert employeeService.findByEmail("john.doe@example.com") != null;
    }

    @Test
    void addEmployeeToDatabaseTestShouldFail(){
        Service service = new Service("ServiceTest");
        Site site = new Site("TestSite");
        siteService.save(site);
        serviceService.save(service);
        Employee employee = new Employee("", "Doe", "john.doe@example.com", 123456789, 987654321, service, site, "password123");
        assertThrows(IllegalArgumentException.class, () -> employeeService.save(employee));
    }
}
