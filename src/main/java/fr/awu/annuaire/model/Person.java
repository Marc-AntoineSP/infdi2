package fr.awu.annuaire.model;

import java.util.UUID;

import org.hibernate.validator.constraints.Length;
import org.mindrot.jbcrypt.BCrypt;

import fr.awu.annuaire.enums.Roles;
import fr.awu.annuaire.interfaces.IEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "persons")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person implements IEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(nullable = false, name = "first_name")
    @NotBlank(message = "First name cannot be blank")
    private String firstName;
    @Column(nullable = false, name = "last_name")
    @NotBlank(message = "Last name cannot be blank")
    private String lastName;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;
    @Column(name = "home_phone")
    @Length(min = 10, max = 10, message = "Phone = 10 digits")
    private String homePhone;
    @Column(name = "mobile_phone")
    @Length(min = 10, max = 10, message = "Phone = 10 digits")
    private String mobilePhone;
    @ManyToOne
    @NotNull(message = "Service cannot be null")
    private Service service;
    @ManyToOne
    @NotNull(message = "Site cannot be null")
    private Site site;
    @Enumerated(EnumType.STRING)
    private Roles role;
    @Column(name = "hashed_password", nullable = false)
    private String hashedPassword;

    protected Person() {
        // Hibernate
    }

    protected Person(String firstName, String lastName, String email,
            String homePhone, String mobilePhone,
            Service service, Site site, Roles role, String motDePasseEnClair) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.homePhone = homePhone;
        this.mobilePhone = mobilePhone;
        this.service = service;
        this.site = site;
        this.role = role;
        this.hashedPassword = BCrypt.hashpw(motDePasseEnClair,
                BCrypt.gensalt());
    }

    @AssertTrue(message = "At least one phone number must be provided")
    private boolean isPhonePresent() {
        return (this.homePhone != null && !this.homePhone.isBlank())
                || (this.mobilePhone != null && !this.mobilePhone.isBlank());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public UUID getId() {
        return id;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

}
