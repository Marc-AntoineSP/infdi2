package fr.awu.annuaire.component;

import java.util.List;

import fr.awu.annuaire.model.Person;
import javafx.scene.control.TableView;

public class EmployeeTable {
    TableView<Person> tableView = new TableView<>();
    private List<Person> persons;

    public EmployeeTable(List<Person> persons) {
        this.persons = persons;
    }
}