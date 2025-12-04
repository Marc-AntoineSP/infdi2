package fr.awu.annuaire.component;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.List;

import fr.awu.annuaire.model.Service;
import fr.awu.annuaire.model.Site;
import fr.awu.annuaire.service.ServiceService;
import fr.awu.annuaire.service.SiteService;

public class ServiceSiteDialog extends Dialog<ButtonType> {

    private final ServiceService serviceService;
    private final SiteService siteService;

    private ComboBox<Service> serviceComboBox;
    private ComboBox<Site> siteComboBox;
    private TextField serviceTextField;
    private TextField siteTextField;

    public ServiceSiteDialog(ServiceService serviceService,
            SiteService siteService) {
        this.serviceService = serviceService;
        this.siteService = siteService;

        setTitle("Gérer Services et Sites");
        getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);

        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setPrefWidth(500);

        // Services Section
        VBox servicesSection = createServicesSection();

        // Sites Section
        VBox sitesSection = createSitesSection();

        content.getChildren().addAll(servicesSection, sitesSection);
        getDialogPane().setContent(content);

        // Initial load
        refreshServiceComboBox();
        refreshSiteComboBox();
    }

    private VBox createServicesSection() {
        VBox section = new VBox(10);

        Label sectionTitle = new Label("SERVICES");
        sectionTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Creation row
        HBox createRow = new HBox(10);
        createRow.setAlignment(Pos.CENTER_LEFT);

        Label createLabel = new Label("Créer un service :");
        createLabel.setPrefWidth(120);

        serviceTextField = new TextField();
        serviceTextField.setPromptText("Nom du service");
        HBox.setHgrow(serviceTextField, Priority.ALWAYS);

        Button addButton = new Button("Add +");
        addButton.setOnAction(e -> addService());

        createRow.getChildren().addAll(createLabel, serviceTextField,
                addButton);

        // Deletion row
        HBox deleteRow = new HBox(10);
        deleteRow.setAlignment(Pos.CENTER_LEFT);

        Label deleteLabel = new Label("Supprimer :");
        deleteLabel.setPrefWidth(120);

        serviceComboBox = new ComboBox<>();
        serviceComboBox.setPromptText("Sélectionner un service");
        serviceComboBox.setPrefWidth(250);
        serviceComboBox
                .setButtonCell(new javafx.scene.control.ListCell<Service>() {
                    @Override
                    protected void updateItem(Service item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : item.getName());
                    }
                });
        serviceComboBox.setCellFactory(
                param -> new javafx.scene.control.ListCell<Service>() {
                    @Override
                    protected void updateItem(Service item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : item.getName());
                    }
                });
        HBox.setHgrow(serviceComboBox, Priority.ALWAYS);

        Button deleteButton = new Button("Supp -");
        deleteButton.setOnAction(e -> deleteService());

        deleteRow.getChildren().addAll(deleteLabel, serviceComboBox,
                deleteButton);

        section.getChildren().addAll(sectionTitle, createRow, deleteRow);
        return section;
    }

    private VBox createSitesSection() {
        VBox section = new VBox(10);

        Label sectionTitle = new Label("SITES");
        sectionTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        // Creation row
        HBox createRow = new HBox(10);
        createRow.setAlignment(Pos.CENTER_LEFT);

        Label createLabel = new Label("Créer un site :");
        createLabel.setPrefWidth(120);

        siteTextField = new TextField();
        siteTextField.setPromptText("Nom du site");
        HBox.setHgrow(siteTextField, Priority.ALWAYS);

        Button addButton = new Button("Add +");
        addButton.setOnAction(e -> addSite());

        createRow.getChildren().addAll(createLabel, siteTextField, addButton);

        // Deletion row
        HBox deleteRow = new HBox(10);
        deleteRow.setAlignment(Pos.CENTER_LEFT);

        Label deleteLabel = new Label("Supprimer :");
        deleteLabel.setPrefWidth(120);

        siteComboBox = new ComboBox<>();
        siteComboBox.setPromptText("Sélectionner un site");
        siteComboBox.setPrefWidth(250);
        siteComboBox.setButtonCell(new javafx.scene.control.ListCell<Site>() {
            @Override
            protected void updateItem(Site item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : item.getVille());
            }
        });
        siteComboBox.setCellFactory(
                param -> new javafx.scene.control.ListCell<Site>() {
                    @Override
                    protected void updateItem(Site item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty || item == null ? null : item.getVille());
                    }
                });
        HBox.setHgrow(siteComboBox, Priority.ALWAYS);

        Button deleteButton = new Button("Supp -");
        deleteButton.setOnAction(e -> deleteSite());

        deleteRow.getChildren().addAll(deleteLabel, siteComboBox, deleteButton);

        section.getChildren().addAll(sectionTitle, createRow, deleteRow);
        return section;
    }

    private void addService() {
        String serviceName = serviceTextField.getText().trim();

        if (serviceName.isEmpty()) {
            showError("Le nom du service ne peut pas être vide");
            return;
        }

        try {
            Service service = new Service(serviceName);
            serviceService.save(service);
            serviceTextField.clear();
            refreshServiceComboBox();
            showSuccess("Service créé avec succès");
        } catch (Exception e) {
            showError(
                    "Erreur lors de la création du service: " + e.getMessage());
        }
    }

    private void deleteService() {
        Service selectedService = serviceComboBox.getValue();

        if (selectedService == null) {
            showError("Veuillez sélectionner un service à supprimer");
            return;
        }

        try {
            serviceService.delete(selectedService);
            refreshServiceComboBox();
            showSuccess("Service supprimé avec succès");
        } catch (Exception e) {
            showError("Erreur lors de la suppresion du service: "
                    + e.getMessage());
        }
    }

    private void addSite() {
        String siteName = siteTextField.getText().trim();

        if (siteName.isEmpty()) {
            showError("Le nom du site ne peut pas être vide");
            return;
        }

        try {
            Site site = new Site(siteName);
            siteService.save(site);
            siteTextField.clear();
            refreshSiteComboBox();
            showSuccess("Site créé avec succès");
        } catch (Exception e) {
            showError("Erreur lors de la création du site (web, lol): "
                    + e.getMessage());
        }
    }

    private void deleteSite() {
        Site selectedSite = siteComboBox.getValue();

        if (selectedSite == null) {
            showError("Veuillez sélectionner un site à supprimer");
            return;
        }

        try {
            siteService.delete(selectedSite);
            refreshSiteComboBox();
            showSuccess("Site supprimé avec succès");
        } catch (Exception e) {
            showError(
                    "Erreur lors de la suppression du site: " + e.getMessage());
        }
    }

    private void refreshServiceComboBox() {
        List<Service> services = serviceService.getAll();
        serviceComboBox.getItems().clear();
        serviceComboBox.getItems().addAll(services);
    }

    private void refreshSiteComboBox() {
        List<Site> sites = siteService.getAll();
        siteComboBox.getItems().clear();
        siteComboBox.getItems().addAll(sites);
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
