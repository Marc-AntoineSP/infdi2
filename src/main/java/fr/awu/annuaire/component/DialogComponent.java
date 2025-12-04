package fr.awu.annuaire.component;

import java.util.List;
import java.util.function.Consumer;

import fr.awu.annuaire.model.Person;
import fr.awu.annuaire.model.Service;
import fr.awu.annuaire.model.Site;
import javafx.scene.control.Alert;
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
    private List<Service> services;
    private List<Site> sites;

    // Store field references for validation
    private DialogField firstNameField;
    private DialogField lastNameField;
    private DialogField emailField;
    private DialogField mobileField;
    private DialogField homePhoneField;

    public DialogComponent(Person person, boolean isAdmin,
            Consumer<Person> onUpdate, Consumer<Person> onDelete,
            List<Service> services, List<Site> sites) {
        this.person = person;
        this.isAdmin = isAdmin;
        this.onUpdate = onUpdate;
        this.onDelete = onDelete;
        this.services = services;
        this.sites = sites;
    }

    public Person getPerson() {
        return person;
    }

    public void showAndWait() {
        Dialog<ButtonType> root = new Dialog<>();
        ButtonType deleteButtonType = null;
        root.setTitle("Détails de " + this.person.getFirstName() + " "
                + this.person.getLastName());
        if (isAdmin) {
            deleteButtonType = new ButtonType("Supprimer",
                    ButtonBar.ButtonData.LEFT);
            root.getDialogPane().getButtonTypes().addAll(deleteButtonType,
                    ButtonType.CLOSE, ButtonType.OK);
        } else {
            root.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
        }

        firstNameField = new DialogField("Prénom",
                this.person.getFirstName(), isAdmin,
                name -> name != null && !name.trim().isEmpty(),
                "Le prénom ne peut pas être vide");
        lastNameField = new DialogField("Nom",
                this.person.getLastName(), isAdmin,
                name -> name != null && !name.trim().isEmpty(),
                "Le nom ne peut pas être vide");
        emailField = new DialogField("Courrier électronique",
                this.person.getEmail(), isAdmin,
                email -> email != null && !email.trim().isEmpty()
                        && email.matches(
                                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"),
                "Email invalide (format: exemple@domaine.com)");

        mobileField = new DialogField("GSM",
                this.person.getMobilePhone(), isAdmin,
                phone -> phone == null || phone.trim().isEmpty()
                        || phone.matches("^\\d{10}$"),
                "Le GSM doit contenir exactement 10 chiffres");
        homePhoneField = new DialogField("Socotel",
                this.person.getHomePhone(), isAdmin,
                phone -> phone == null || phone.isEmpty()
                        || phone.matches("^\\d{10}$"),
                "Le Socotel doit contenir exactement 10 chiffres");

        DialogOption<Service> serviceField = new DialogOption<>(
                "Service",
                this.person.getService(),
                isAdmin,
                services, Service::getName);

        DialogOption<Site> siteField = new DialogOption<>(
                "Site",
                this.person.getSite(),
                isAdmin,
                sites,
                Site::getVille);

        VBox container = new VBox(10,
                firstNameField.render(),
                lastNameField.render(),
                emailField.render(),
                mobileField.render(),
                homePhoneField.render(),
                serviceField.render(),
                siteField.render());

        root.getDialogPane().setContent(container);

        if (isAdmin) {
            // Bouton OK → UPDATE
            Button okButton = (Button) root.getDialogPane()
                    .lookupButton(ButtonType.OK);

            Runnable checkValidation = () -> {
                boolean isValid = true;

                isValid &= firstNameField.validate();
                isValid &= lastNameField.validate();
                isValid &= emailField.validate();
                isValid &= mobileField.validate();
                isValid &= homePhoneField.validate();

                String mobile = mobileField.getCurrentValue();
                String home = homePhoneField.getCurrentValue();
                boolean hasAtLeastOnePhone = (mobile != null
                        && !mobile.trim().isEmpty()) ||
                        (home != null && !home.trim().isEmpty());

                isValid &= hasAtLeastOnePhone;

                okButton.setDisable(!isValid);
            };

            firstNameField.getValueTF().textProperty()
                    .addListener((obs, old, newVal) -> checkValidation.run());
            lastNameField.getValueTF().textProperty()
                    .addListener((obs, old, newVal) -> checkValidation.run());
            emailField.getValueTF().textProperty()
                    .addListener((obs, old, newVal) -> checkValidation.run());
            mobileField.getValueTF().textProperty()
                    .addListener((obs, old, newVal) -> checkValidation.run());
            homePhoneField.getValueTF().textProperty()
                    .addListener((obs, old, newVal) -> checkValidation.run());

            checkValidation.run();

            okButton.setOnAction(e -> {
                try {
                    person.setFirstName(firstNameField.getCurrentValue());
                    person.setLastName(lastNameField.getCurrentValue());
                    person.setEmail(emailField.getCurrentValue());
                    person.setMobilePhone(mobileField.getCurrentValue());
                    person.setHomePhone(homePhoneField.getCurrentValue());

                    if (onUpdate != null) {
                        onUpdate.accept(person);
                    }
                    root.close();
                } catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur de mise à jour");
                    alert.setHeaderText(
                            "Impossible de mettre à jour la personne");
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }
            });
            if (deleteButtonType != null) {
                Button deleteButton = (Button) root.getDialogPane()
                        .lookupButton(deleteButtonType);
                deleteButton.setOnAction(e -> {
                    try {
                        if (onDelete != null) {
                            onDelete.accept(person);
                        }
                        root.close();
                    } catch (Exception ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de suppression");
                        alert.setHeaderText(
                                "Impossible de supprimer la personne");
                        alert.setContentText(ex.getMessage());
                        alert.showAndWait();
                    }
                });
            }
        }

        root.showAndWait();
    }
}