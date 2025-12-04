package fr.awu.annuaire.service;

import org.mindrot.jbcrypt.BCrypt;

import fr.awu.annuaire.enums.Roles;
import fr.awu.annuaire.model.Person;

public class AuthService {
    private PersonService personService;
    private Person currentPerson;

    public AuthService(PersonService personService) {
        this.personService = personService;
    }

    public boolean login(String email, String password) {
        Person user = this.personService.findByEmail(email);
        if(user == null) {
            return false;
        }
        if(BCrypt.checkpw(password, user.getHashedPassword())) {
            this.currentPerson = user;
            return true;
        }
        return false;
    }

    public boolean checkRoleAdmin(){
        return currentPerson.getRole() == Roles.ADMIN;
    }

    public Person getCurrentPerson() {
        return currentPerson;
    }
}
