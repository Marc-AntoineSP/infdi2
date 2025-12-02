package fr.awu.annuaire.service;

import java.util.List;

import fr.awu.annuaire.model.Person;

public class PersonListManipulationService {
    List<Person> personList;

    public PersonListManipulationService(List<Person> personList) {
        this.personList = personList;
    }

    public List<Person> filterByName(String name) {
        return this.personList.stream().filter(p -> (p.getFirstName().contains(name) || p.getFirstName().contains(name))).toList();
    }

    public List<Person> filterByService(String service) {
        return this.personList.stream().filter(p -> p.getService().getName().contains(service)).toList();
    }

    public List<Person> filterBySite(String site) {
        return this.personList.stream().filter(p -> p.getSite().getVille().contains(site)).toList();
    }
}
