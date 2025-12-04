package fr.awu.annuaire.component;

import java.util.function.Consumer;

import fr.awu.annuaire.enums.Roles;
import fr.awu.annuaire.model.Person;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.VBox;

public class DialogComponent {
    private Person person;
    private boolean isAdmin;
    private Consumer<Person> onUpdate;
    private Consumer<Person> onDelete;
    public DialogComponent(Person person, boolean isAdmin, Consumer<Person> onUpdate, Consumer<Person> onDelete) {
        this.person = person;
        this.isAdmin = isAdmin;
        this.onUpdate = onUpdate;
        this.onDelete = onDelete;
    }

    public Person getPerson() {
        return person;
    }

    public void showAndWait(){
        Dialog<ButtonType> root = new Dialog<>();
        ButtonType deleteButtonType = null;
        root.setTitle("Détails de "+ this.person.getFirstName() + " " + this.person.getLastName());
        if(isAdmin){
            deleteButtonType = new ButtonType("Supprimer", ButtonBar.ButtonData.LEFT);
            root.getDialogPane().getButtonTypes().addAll(deleteButtonType, ButtonType.CLOSE, ButtonType.OK);
        }else{
            root.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
        }

        DialogField firsNameField = new DialogField("Prénom", this.person.getFirstName(), isAdmin);
        DialogField lastNameField = new DialogField("Nom", this.person.getLastName(), isAdmin);
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

        if (isAdmin) {
            // Bouton OK → UPDATE
            Button okButton = (Button) root.getDialogPane().lookupButton(ButtonType.OK);
            okButton.setOnAction(e -> {
                // On récupère les valeurs des champs (Label ou TextField)
                person.setFirstName(firsNameField.getCurrentValue());
                person.setLastName(lastNameField.getCurrentValue());
                person.setEmail(emailField.getCurrentValue());
                person.setMobilePhone(mobileField.getCurrentValue());
                person.setHomePhone(homePhoneField.getCurrentValue());

                if (onUpdate != null) {
                    onUpdate.accept(person);
                }
                root.close();
            });
            if (deleteButtonType != null) {
                Button deleteButton = (Button) root.getDialogPane().lookupButton(deleteButtonType);
                deleteButton.setOnAction(e -> {
                    if (onDelete != null) {
                        onDelete.accept(person);
                    }
                    root.close();
                });
            }
        }

        root.showAndWait();
    }
}