package fr.awu.annuaire.service;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import fr.awu.annuaire.model.Person;
import fr.awu.annuaire.repository.PersonRepository;
import fr.awu.annuaire.utils.HibernateUtil;

public class PersonService {
    private PersonRepository personRepository;

    public PersonService() {
        this.personRepository = new PersonRepository();
    }

    public void save(Person person) {
        this.personRepository.save(person);
    }
    
    public List<Person> getAll() {
        return this.personRepository.getAll();
    }
    
    public void update(Person person) throws IllegalArgumentException {
        if(person.getFirstName() == null || person.getFirstName().isBlank()) {
            throw new IllegalArgumentException("CreationError: Person first name cannot be null");
        }
        if(person.getLastName() == null || person.getLastName().isBlank()) {
            throw new IllegalArgumentException("CreationError: Person last name cannot be null");
        }
        if(person.getEmail() == null || person.getEmail().isBlank()) {
            throw new IllegalArgumentException("CreationError: Person email cannot be null");
        }
        if((person.getHomePhone() == null || person.getHomePhone().isBlank()) && (person.getMobilePhone() == null || person.getMobilePhone().isBlank())) {
            throw new IllegalArgumentException("CreationError: Person phone number cannot be null");
        }
        if(person.getService() == null || person.getService().getName().isBlank()) {
            throw new IllegalArgumentException("CreationError: Person service cannot be null");
        }
        if(person.getSite() == null || person.getSite().getVille().isBlank()) {
            throw new IllegalArgumentException("CreationError: Person site cannot be null");
        }
        this.personRepository.update(person);
    }
    
    public void delete (Person person) {
        this.personRepository.delete(person);
    }
}
