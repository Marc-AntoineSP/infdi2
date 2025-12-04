package fr.awu.annuaire.ui;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fr.awu.annuaire.component.ButtonPrimary;
import fr.awu.annuaire.component.ButtonSecondary;
import fr.awu.annuaire.component.DialogComponent;
import fr.awu.annuaire.component.DialogOption;
import fr.awu.annuaire.component.ServiceSiteDialog;
import fr.awu.annuaire.model.Employee;
import fr.awu.annuaire.model.Person;
import fr.awu.annuaire.model.Service;
import fr.awu.annuaire.model.Site;
import fr.awu.annuaire.service.AuthService;
import fr.awu.annuaire.service.PdfExportService;
import fr.awu.annuaire.service.PersonService;
import fr.awu.annuaire.service.ServiceService;
import fr.awu.annuaire.service.SiteService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;

public class MainUI {

    private final ObservableList<Person> tablePersonList;
    private final AuthService authService;
    private Runnable onLogout;
    private PersonService personService;
    private SiteService siteService;
    private ServiceService serviceService;
    private PdfExportService pdfExportService;

    private MenuButton siteFilterButton;
    private MenuButton serviceFilterButton;
    private Set<String> selectedServices;
    private Set<String> selectedSites;
    private Runnable applyFilter;

    public MainUI(ObservableList<Person> tablePersonList,
            AuthService authService, Runnable onLogout,
            PersonService personService, SiteService siteService,
            ServiceService serviceService) {
        this.tablePersonList = tablePersonList;
        this.authService = authService;
        this.onLogout = onLogout;
        this.personService = personService;
        this.siteService = siteService;
        this.serviceService = serviceService;
        this.pdfExportService = new PdfExportService();
    }

    public Parent render() {

        ButtonPrimary addButton = new ButtonPrimary("Ajouter");
        addButton.getButton().setOnAction(e -> openCreatePersonDialog());

        TextField searchField = new TextField();
        searchField.setPromptText("Recherche (nom, prénom");

        selectedServices = new HashSet<>();
        selectedSites = new HashSet<>();

        siteFilterButton = new MenuButton("Site");
        serviceFilterButton = new MenuButton("Service");

        TableView<Person> tableView = new TableView<>();
        tableView.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        TableColumn<Person, String> firstNameCol = new TableColumn<>("Prénom");
        firstNameCol
                .setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Person, String> lastNameCol = new TableColumn<>("Nom");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn<Person, String> serviceCol = new TableColumn<>("Service");
        serviceCol.setCellValueFactory(c -> {
            Service service = c.getValue().getService();
            String serviceName = service != null ? service.getName() : "";
            return new SimpleStringProperty(serviceName);
        });

        TableColumn<Person, String> siteCol = new TableColumn<>("Site");
        siteCol.setCellValueFactory(c -> {
            Site site = c.getValue().getSite();
            String siteVille = site != null ? site.getVille() : "";
            return new SimpleStringProperty(siteVille);
        });

        TableColumn<Person, Void> printCol = new TableColumn<>("Actions");
        printCol.setCellFactory(
                param -> new javafx.scene.control.TableCell<>() {
                    private final Button printButton = createPrintButton();

                    private Button createPrintButton() {
                        SVGPath printerIcon = new SVGPath();
                        printerIcon.setContent(
                                "M6 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v2h2a2 2 0 0 1 2 2v6a2 2 0 0 1-2 2h-2v2a2 2 0 0 1-2 2H8a2 2 0 0 1-2-2v-2H4a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h2V2zm0 2v2h12V2H8zM4 8v4h2V8H4zm14 0v4h2V8h-2zM8 14v2h8v-2H8z");
                        printerIcon.setScaleX(1.2);
                        printerIcon.setScaleY(1.2);
                        printerIcon.setStyle("-fx-fill: -fx-text-fill;");

                        Button btn = new Button();
                        btn.setGraphic(printerIcon);
                        btn.setStyle("-fx-padding: 6 10 6 10;");
                        return btn;
                    }

                    {
                        printButton.setOnAction(event -> {
                            Person person = getTableView().getItems()
                                    .get(getIndex());
                            try {
                                String filePath = pdfExportService
                                        .exportEmployeeToPdf(person);
                                System.out
                                        .println("PDF généré: " + filePath);

                                Alert alert = new Alert(
                                        Alert.AlertType.INFORMATION);
                                alert.setTitle("PDF Export");
                                alert.setHeaderText(
                                        "VCS euh PDF PRÊT !");
                                alert.setContentText(
                                        "Fichier sauvegardé là: " + filePath);
                                alert.showAndWait();
                            } catch (Exception e) {
                                e.printStackTrace();
                                Alert alert = new Alert(
                                        Alert.AlertType.ERROR);
                                alert.setTitle("PDF Export Error");
                                alert.setHeaderText("Error : PDF mal généré");
                                alert.setContentText(e.getMessage());
                                alert.showAndWait();
                            }
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(printButton);
                        }
                    }
                });

        tableView.getColumns().addAll(firstNameCol, lastNameCol, serviceCol,
                siteCol, printCol);

        FilteredList<Person> filteredPerson = new FilteredList<>(
                tablePersonList, p -> true);

        applyFilter = () -> {
            String query = normalize(searchField.getText());

            filteredPerson.setPredicate(person -> {
                if (person == null)
                    return false;

                boolean textOk;
                if (query.isEmpty()) {
                    textOk = true;
                } else {
                    String firstName = normalize(person.getFirstName());
                    String lastName = normalize(person.getLastName());

                    textOk = firstName.contains(query)
                            || lastName.contains(query);
                }

                String personSite = person.getSite() != null
                        ? person.getSite().getVille()
                        : null;
                boolean siteOk = selectedSites.isEmpty()
                        || (personSite != null
                                && selectedSites.contains(personSite));

                String personService = person.getService() != null
                        ? person.getService().getName()
                        : null;
                boolean serviceOk = selectedServices.isEmpty()
                        || (personService != null
                                && selectedServices.contains(personService));

                return textOk && siteOk && serviceOk;
            });
        };

        searchField.textProperty().addListener(
                (observable, oldValue, newValue) -> applyFilter.run());

        // Initial population of filter menus
        refreshFilterMenus();

        applyFilter.run();

        SortedList<Person> sortedPerson = new SortedList<>(filteredPerson);
        sortedPerson.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedPerson);

        ButtonSecondary logoutButton = new ButtonSecondary("Logout");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        ButtonSecondary manageButton = new ButtonSecondary(
                "Gérer Services/Sites");
        manageButton.getButton().setOnAction(e -> openManageEntitiesDialog());

        HBox horizontalBoxLogout = new HBox(8, addButton.getButton(),
                manageButton.getButton(), spacer, logoutButton.getButton());
        logoutButton.getButton().setOnAction(e -> onLogout.run());
        HBox searchAndFilters = new HBox(8, searchField, siteFilterButton,
                serviceFilterButton);
        HBox.setHgrow(searchField, Priority.ALWAYS);

        VBox root = new VBox(10, horizontalBoxLogout, searchAndFilters,
                tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        tableView.setRowFactory(t -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2) {
                    System.out.println("CLICK");
                    Person data = row.getItem();
                    boolean isAdmin = authService.checkRoleAdmin();
                    List<Service> servicesAllowed = serviceService.getAll();
                    List<Site> sitesAllowed = siteService.getAll();

                    DialogComponent dialog = new DialogComponent(
                            data,
                            isAdmin,
                            updatedPerson -> {
                                personService.update(updatedPerson);
                                tableView.refresh();
                            },
                            deletedPerson -> {
                                personService.delete(deletedPerson);
                                tablePersonList.remove(deletedPerson);
                            },
                            servicesAllowed,
                            sitesAllowed);
                    dialog.showAndWait();
                    tableView.refresh();
                }
            });
            return row;
        });

        return root;
    }

    private static String normalize(String s) {
        return s == null ? "" : s.trim().toLowerCase();
    }

    private void openCreatePersonDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Ajouter une personne");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL,
                ButtonType.OK);

        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Prénom");

        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Nom");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        TextField mobileField = new TextField();
        mobileField.setPromptText("GSM");

        TextField homePhoneField = new TextField();
        homePhoneField.setPromptText("Socotel");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");

        List<Service> services = serviceService.getAll();
        List<Site> sites = siteService.getAll();

        DialogOption<Service> serviceOption = new DialogOption<>(
                "Service",
                services.isEmpty() ? null : services.get(0),
                true,
                services,
                Service::getName);

        DialogOption<Site> siteOption = new DialogOption<>(
                "Site",
                sites.isEmpty() ? null : sites.get(0),
                true,
                sites,
                Site::getVille);

        VBox content = new VBox(10,
                makeRow("Prénom", firstNameField),
                makeRow("Nom", lastNameField),
                makeRow("Email", emailField),
                makeRow("GSM", mobileField),
                makeRow("Socotel", homePhoneField),
                serviceOption.render(),
                siteOption.render(),
                makeRow("Mot de passe", passwordField));

        dialog.getDialogPane().setContent(content);

        var result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String mobile = mobileField.getText().trim();
            String home = homePhoneField.getText().trim();
            String password = passwordField.getText();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty()
                    || password.isEmpty()) {
                System.out.println("Champs obligatoires manquants");
                return;
            }

            Service service = serviceOption.getSelectedValue();
            Site site = siteOption.getSelectedValue();

            if (service == null || site == null) {
                System.out.println("Service/Site manquant");
                return;
            }

            Employee newEmployee = new Employee(
                    firstName,
                    lastName,
                    email,
                    home,
                    mobile,
                    service,
                    site,
                    password);

            try {
                personService.save(newEmployee);
                tablePersonList.add(newEmployee);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private HBox makeRow(String label, TextField field) {
        Label lbl = new Label(label + " : ");
        HBox row = new HBox(10, lbl, field);
        return row;
    }

    private void openManageEntitiesDialog() {
        ServiceSiteDialog dialog = new ServiceSiteDialog(serviceService,
                siteService);
        dialog.showAndWait();

        tablePersonList.setAll(personService.getAll());

        refreshFilterMenus();
    }

    private void refreshFilterMenus() {
        serviceFilterButton.getItems().clear();
        siteFilterButton.getItems().clear();

        List<String> services = tablePersonList.stream()
                .map(p -> p.getService() != null ? p.getService().getName()
                        : "")
                .filter(s -> !s.isBlank())
                .distinct()
                .sorted()
                .toList();

        List<String> sites = tablePersonList.stream()
                .map(p -> p.getSite() != null ? p.getSite().getVille() : "")
                .filter(s -> !s.isBlank())
                .distinct()
                .sorted()
                .toList();

        selectedServices.retainAll(services);
        selectedSites.retainAll(sites);

        for (String serviceName : services) {
            CheckMenuItem item = new CheckMenuItem(serviceName);
            item.setSelected(selectedServices.contains(serviceName));
            item.selectedProperty()
                    .addListener((obs, wasSelected, isSelected) -> {
                        if (isSelected) {
                            selectedServices.add(serviceName);
                        } else {
                            selectedServices.remove(serviceName);
                        }
                        applyFilter.run();
                    });
            serviceFilterButton.getItems().add(item);
        }

        for (String siteName : sites) {
            CheckMenuItem item = new CheckMenuItem(siteName);
            item.setSelected(selectedSites.contains(siteName));
            item.selectedProperty()
                    .addListener((obs, wasSelected, isSelected) -> {
                        if (isSelected) {
                            selectedSites.add(siteName);
                        } else {
                            selectedSites.remove(siteName);
                        }
                        applyFilter.run();
                    });
            siteFilterButton.getItems().add(item);
        }

        applyFilter.run();
    }
}