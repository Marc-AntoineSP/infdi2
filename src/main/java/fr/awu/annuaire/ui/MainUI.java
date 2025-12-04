package fr.awu.annuaire.ui;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fr.awu.annuaire.component.ButtonPrimary;
import fr.awu.annuaire.component.ButtonSecondary;
import fr.awu.annuaire.component.DialogComponent;
import fr.awu.annuaire.component.DialogOption;
import fr.awu.annuaire.errors.PersonValidationException;
import fr.awu.annuaire.model.Employee;
import fr.awu.annuaire.model.Person;
import fr.awu.annuaire.model.Service;
import fr.awu.annuaire.model.Site;
import fr.awu.annuaire.service.AuthService;
import fr.awu.annuaire.service.PersonService;
import fr.awu.annuaire.service.ServiceService;
import fr.awu.annuaire.service.SiteService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Parent;
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

public class MainUI {
    
    private final ObservableList<Person> tablePersonList;
    private final AuthService authService;
    private Runnable onLogout;
    private PersonService personService;
    private SiteService siteService;
    private ServiceService serviceService;
    
    public MainUI(ObservableList<Person> tablePersonList, AuthService authService, Runnable onLogout, PersonService personService, SiteService siteService, ServiceService serviceService) {
        this.tablePersonList = tablePersonList;
        this.authService = authService;
        this.onLogout = onLogout;
        this.personService = personService;
        this.siteService = siteService;
        this.serviceService = serviceService;
    }
    
    public Parent render() {
        
        ButtonPrimary addButton = new ButtonPrimary("Ajouter");
        addButton.getButton().setOnAction(e -> openCreatePersonDialog());
        
        TextField searchField = new TextField();
        searchField.setPromptText("Recherche (nom, prénom");
        
        Set<String> selectedServices = new HashSet<>();
        Set<String> selectedSites = new HashSet<>();
        
        List<String> services = tablePersonList.stream()
        .map(p -> p.getService() != null ? p.getService().getName() : "")
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
        
        MenuButton siteFilterButton = new MenuButton("Site");
        MenuButton serviceFilterButton = new MenuButton("Service");
        
        TableView<Person> tableView = new TableView<>();
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);
        
        TableColumn<Person, String> firstNameCol = new TableColumn<>("Prénom");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        
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
        
        tableView.getColumns().addAll(firstNameCol, lastNameCol, serviceCol, siteCol);
        
        FilteredList<Person> filteredPerson = new FilteredList<>(tablePersonList, p -> true);
        
        Runnable applyFilter = () -> {
            String query = normalize(searchField.getText());
            
            filteredPerson.setPredicate(person -> {
                if (person == null) return false;
                
                boolean textOk;
                if (query.isEmpty()) {
                    textOk = true;
                } else {
                    String firstName = normalize(person.getFirstName());
                    String lastName = normalize(person.getLastName());
                    
                    textOk = firstName.contains(query)
                    || lastName.contains(query);
                }
                
                String personSite = person.getSite() != null ? person.getSite().getVille() : null;
                boolean siteOk = selectedSites.isEmpty()
                || (personSite != null && selectedSites.contains(personSite));
                
                String personService = person.getService() != null ? person.getService().getName() : null;
                boolean serviceOk = selectedServices.isEmpty()
                || (personService != null && selectedServices.contains(personService));
                
                return textOk && siteOk && serviceOk;
            });
        };
        
        searchField.textProperty().addListener((observable, oldValue, newValue) -> applyFilter.run());
        
        for (String serviceName : services) {
            CheckMenuItem item = new CheckMenuItem(serviceName);
            item.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                if (isSelected != null) {
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
            item.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                if (isSelected != null) {
                    selectedSites.add(siteName);
                } else {
                    selectedSites.remove(siteName);
                }
                applyFilter.run();
            });
            siteFilterButton.getItems().add(item);
        }
        
        applyFilter.run();
        
        SortedList<Person> sortedPerson = new SortedList<>(filteredPerson);
        sortedPerson.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedPerson);
        
        ButtonSecondary logoutButton = new ButtonSecondary("Logout");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        
        HBox horizontalBoxLogout = new HBox(8, addButton.getButton(), spacer, logoutButton.getButton());
        logoutButton.getButton().setOnAction(e -> onLogout.run());
        HBox searchAndFilters = new HBox(8, searchField, siteFilterButton, serviceFilterButton);
        HBox.setHgrow(searchField, Priority.ALWAYS);
        
        VBox root = new VBox(10, horizontalBoxLogout, searchAndFilters, tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        
        tableView.setRowFactory(t -> {
            TableRow<Person> row = new TableRow<>();
            row.setOnMouseClicked(e -> {
                if(e.getClickCount() == 2){
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
                sitesAllowed
            );
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
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);
        
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
            Service::getName
        );

        DialogOption<Site> siteOption = new DialogOption<>(
            "Site",
            sites.isEmpty() ? null : sites.get(0),
            true,
            sites,
            Site::getVille
        );
        
        VBox content = new VBox(10,
            makeRow("Prénom", firstNameField),
            makeRow("Nom", lastNameField),
            makeRow("Email", emailField),
            makeRow("GSM", mobileField),
            makeRow("Socotel", homePhoneField),
            serviceOption.render(),
            siteOption.render(),
            makeRow("Mot de passe", passwordField)
        );
        
        dialog.getDialogPane().setContent(content);
        
        var result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String email = emailField.getText().trim();
            String mobile = mobileField.getText().trim();
            String home = homePhoneField.getText().trim();
            String password = passwordField.getText();
            
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                System.out.println("Champs obligatoires manquants");
                return;
            }
            
            Service service = serviceOption.getSelectedValue();
            Site site = siteOption.getSelectedValue();

            if (service == null || site == null) {
                System.out.println("Service/Site manquant");
                return;
            }

            siteService.save(site);
            serviceService.save(service);

            Employee newEmployee = new Employee(
                firstName,
                lastName,
                email,
                home,
                mobile,
                service,
                site,
                password
            );
            
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
}