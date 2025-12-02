package fr.awu.annuaire.service;

import org.mindrot.jbcrypt.BCrypt;

import fr.awu.annuaire.enums.Roles;
import fr.awu.annuaire.model.Person;

public class AuthService {
    private PersonService personService;

    public boolean login(String email, String password) {
        Person user = this.personService.findByEmail(email);
        if(user == null) {
            return false;
        }
        return BCrypt.checkpw(password, user.getHashedPassword());
    }

    public boolean checkRoleAdmin(Person person){
        return person.getRole() == Roles.ADMIN;
    }
}
