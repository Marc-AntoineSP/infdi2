package fr.awu.annuaire.utils;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;

import fr.awu.annuaire.enums.Roles;
import fr.awu.annuaire.model.Admin;
import fr.awu.annuaire.model.Employee;
import fr.awu.annuaire.model.Person;
import fr.awu.annuaire.model.Service;
import fr.awu.annuaire.model.Site;
import fr.awu.annuaire.service.PersonService;
import fr.awu.annuaire.service.ServiceService;
import fr.awu.annuaire.service.SiteService;

public class PopulateDB {
    private static final Gson gson = new Gson();
    private static final ServiceService serviceService = new ServiceService();
    private static final SiteService siteService = new SiteService();
    private static final PersonService personService = new PersonService();
    private static final Random random = new Random();

    private PopulateDB() {
        // Utility class
    }

    private static void populateServices(){
        Service info = new Service("Informatique");
        Service hr = new Service("Ressources Humaines");
        Service sales = new Service("Ventes");
        serviceService.save(info);
        serviceService.save(hr);
        serviceService.save(sales);
    }

    private static void populateSites(){
        Site reims = new Site("Reims");
        Site paris = new Site("Paris");
        Site marseille = new Site("Marseille");
        siteService.save(reims);
        siteService.save(paris);
        siteService.save(marseille);
    }

    private static String fetchPersons() {
        try(HttpClient client = HttpClient.newHttpClient()){
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(java.net.URI.create("https://randomuser.me/api/?results=25&nat=fr"))
                    .build();
            HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());

            if(res.statusCode() != 200){
                throw new IllegalStateException("Fail to fetch");
            }
            return res.body();
        }catch(IOException e){
            e.printStackTrace();
            throw new IllegalStateException("Fail to fetch", e);
        }catch(InterruptedException e){
            e.printStackTrace();
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Fail to fetch", e);
        }
    }

    private static Person mapToPerson(ApiUserResult result, Roles role, Service service, Site site){
        String firstName = result.name.first;
        String lastName = result.name.last;
        String email = result.email;
        String homePhone = result.phone.replaceAll("-", "");
        String mobilePhone = result.cell.replaceAll("-", "");
        String password = result.login.password;

        return (role != Roles.ADMIN) ? new Employee(firstName, lastName, email,
                homePhone, mobilePhone, service, site, password)
                : new Admin(firstName, lastName, email,
                        homePhone, mobilePhone, service, site, password);
    }

    private static List<Person> parsePerson(String json){
        ApiUserResponse response = gson.fromJson(json, ApiUserResponse.class);
        List<Service> services = serviceService.getAll();
        List<Site> sites = siteService.getAll();
        
        if (services.isEmpty() || sites.isEmpty()) {
            throw new IllegalStateException("Database must have at least one Service and one Site before populating persons");
        }
        
        return response.results.stream().map((ApiUserResult p) -> mapToPerson(p, random.nextInt(10) == 9 ? Roles.ADMIN : Roles.EMPLOYEE, services.get(random.nextInt(services.size())),
                sites.get(random.nextInt(sites.size())))).toList();
    }

    public static void populate(){
        populateServices();
        populateSites();
        String json = fetchPersons();
        List<Person> persons = parsePerson(json);
        persons.forEach(personService::save);
    }

}