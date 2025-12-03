package fr.awu.annuaire.ui;

import java.util.List;

import fr.awu.annuaire.model.Person;
import javafx.scene.Parent;
import javafx.scene.control.TableView;

public class MainUI {
    private List<Person> tablePersonList;

    public MainUI(List<Person> tablePersonList) {
        this.tablePersonList = tablePersonList;
    }

    public Parent render(){
        TableView<Person> tableView = new TableView<>();

        return tableView;
    }
}
