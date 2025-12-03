package fr.awu.annuaire.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "admins")
public class Admin extends Person {
    protected Admin() {
        // Hibernate
    }

    public Admin(String firstName, String lastName, String email,
            String homePhone, String mobilePhone,
            Service service, Site site, String motDePasseEnClair) {
        super(firstName, lastName, email, homePhone, mobilePhone, service, site,
                fr.awu.annuaire.enums.Roles.ADMIN, motDePasseEnClair);
    }

}
