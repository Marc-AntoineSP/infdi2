package fr.awu.annuaire.model;

import jakarta.persistence.Entity;

@Entity
public class Employee extends Person {

    public Employee() {
        // Hibernate
    }

    public Employee(String firstName, String lastName, String email,
            String homePhone, String mobilePhone,
            Service service, Site site, String motDePasseEnClair) {
        super(firstName, lastName, email, homePhone, mobilePhone, service, site,
                fr.awu.annuaire.enums.Roles.EMPLOYEE, motDePasseEnClair);
    }
}
