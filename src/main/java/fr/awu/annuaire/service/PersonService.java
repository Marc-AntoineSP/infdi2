package fr.awu.annuaire.service;

import java.util.List;
import java.util.Set;

import fr.awu.annuaire.errors.PersonValidationException;
import fr.awu.annuaire.model.Person;
import fr.awu.annuaire.repository.PersonRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class PersonService {
    private final PersonRepository personRepository;
    private final ValidatorFactory factory = Validation
            .buildDefaultValidatorFactory();
    private final Validator validator = factory.getValidator();

    public PersonService() {
        this.personRepository = new PersonRepository();
    }

    public void save(Person person) {
        Set<ConstraintViolation<Person>> violations = validator
                .validate(person);
        if (!violations.isEmpty()) {
            throw new PersonValidationException("CreationError", violations);
        }
        this.personRepository.save(person);

    }

    public List<Person> getAll() {
        return this.personRepository.getAll();
    }

    public Person findByEmail(String email) {
        return this.personRepository.findByEmail(email);
    }

    public void update(Person person) throws IllegalArgumentException {
        Set<ConstraintViolation<Person>> violations = validator
                .validate(person);
        if (!violations.isEmpty()) {
            throw new PersonValidationException("UpdateError", violations);
        }
        this.personRepository.update(person);
    }

    public void delete(Person person) {
        this.personRepository.delete(person);
    }
}
