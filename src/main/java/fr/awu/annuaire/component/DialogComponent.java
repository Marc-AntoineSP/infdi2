package fr.awu.annuaire.component;

import fr.awu.annuaire.enums.Roles;
import fr.awu.annuaire.model.Person;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;

public class DialogComponent {
    private Person person;
    private boolean isAdmin;
    public DialogComponent(Person person, boolean isAdmin) {
        this.person = person;
        this.isAdmin = isAdmin;
    }

    public Person getPerson() {
        return person;
    }

    public void showAndWait(){
        Dialog<ButtonType> root = new Dialog<>();
        root.setTitle("Détails de "+ this.person.getFirstName() + " " + this.person.getLastName());
        if(isAdmin){
            root.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE, ButtonType.OK);
        }else{
            root.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
        }

        DialogField firsNameField = new DialogField("Prénom", this.person.getFirstName(), isAdmin);
        DialogField lastNameField = new DialogField("Nom", this.person.getFirstName(), isAdmin);
        DialogField emailField = new DialogField("Courrier électronique", this.person.getEmail(), isAdmin);
        DialogField mobileField = new DialogField("GSM", this.person.getMobilePhone(), isAdmin);
        DialogField homePhoneField = new DialogField("Socotel", this.person.getHomePhone(), isAdmin);
        DialogField serviceField = new DialogField("Service", this.person.getService().getName(), isAdmin);
        DialogField siteField = new DialogField("Site", this.person.getSite().getVille(), isAdmin);

        VBox container = new VBox(10,
            firsNameField.render(),
            lastNameField.render(),
            emailField.render(),
            mobileField.render(),
            homePhoneField.render(),
            serviceField.render(),
            siteField.render()
        );

        root.getDialogPane().setContent(container);

        root.showAndWait();
    }
}
