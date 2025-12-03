package fr.awu.annuaire.controller;

import java.util.List;

import fr.awu.annuaire.model.Person;
import fr.awu.annuaire.service.PersonService;
import fr.awu.annuaire.utils.PopulateDB;

public class StartController {
/**
 *  On load depuis l'API
 *  On balance tout dans la list de l'init pour les users.
 *  On load la page de connexion.
 *  TODO: Ajouter la gestion de la scene de connexion.
 */
    public StartController(List<Person> persons, PersonService personService) {
        PopulateDB.populate();
        persons.addAll(personService.getAll());
    }
}
