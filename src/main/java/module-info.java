module fr.awu.annuaire {
    requires javafx.controls;
    requires javafx.fxml;

    exports fr.awu.annuaire.enums;

    requires java.sql;
    requires java.net.http;
    requires com.google.gson;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires itextpdf;
    requires jbcrypt;
    requires jakarta.mail;
    requires org.hibernate.validator;
    requires jakarta.el;
    requires jakarta.validation;

    exports fr.awu.annuaire;
    exports fr.awu.annuaire.model;
    exports fr.awu.annuaire.service;
    exports fr.awu.annuaire.repository;

    opens fr.awu.annuaire to javafx.fxml;
    // opens fr.awu.annuaire.controller to javafx.fxml;

    opens fr.awu.annuaire.model
            to org.hibernate.orm.core, org.hibernate.validator;
    opens fr.awu.annuaire.service to org.hibernate.orm.core;

    opens fr.awu.annuaire.repository to org.hibernate.orm.core;

}
