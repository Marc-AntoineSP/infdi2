package fr.awu.annuaire.ui;

import java.util.List;
import java.util.Observable;

import fr.awu.annuaire.model.Person;
import fr.awu.annuaire.service.AuthService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class MainUI {
    private ObservableList<Person> tablePersonList;
    private AuthService authService;
    
    public MainUI(ObservableList<Person> tablePersonList, AuthService authService) {
        this.tablePersonList = tablePersonList;
        this.authService = authService;
    }
    
    public Parent render(){
        TableView<Person> tableView = new TableView<>();
        
        TableColumn<Person, String> firstNameCol = new TableColumn<>("Prénom");
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        TableColumn<Person, String> lastNameCol = new TableColumn<>("Nom");
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        TableColumn<Person, String> serviceCol = new TableColumn<>("Service");
        serviceCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getService().getName()));
        TableColumn<Person, String> siteCol = new TableColumn<>("Site");
        siteCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSite().getVille()));

        tableView.getColumns().addAll(firstNameCol, lastNameCol, serviceCol, siteCol);

        FilteredList<Person> filteredPerson = new FilteredList<>(tablePersonList, p -> true);
        TextField searchField = new TextField();
        searchField.setPromptText("Recherche...");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String query = newValue == null ? "" : newValue.trim().toLowerCase();
            filteredPerson.setPredicate(
                (Person p) -> {
                    if(query.isEmpty()) {
                        return true;
                    }
                    return p.getFirstName().toLowerCase().contains(query) ||
                            p.getLastName().toLowerCase().contains(query) ||
                            p.getService().getName().toLowerCase().contains(query) ||
                            p.getSite().getVille().toLowerCase().contains(query);
                }
            );
        });
        SortedList<Person> sortedPerson = new SortedList<>(filteredPerson);
        sortedPerson.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedPerson);

        VBox root = new VBox(10);
        root.getChildren().addAll(searchField, tableView);
        VBox.setVgrow(tableView, Priority.ALWAYS);
        return root;
    }
}
