package fr.awu.annuaire;

import fr.awu.annuaire.service.PersonService;
import fr.awu.annuaire.utils.PopulateDB;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    private static PersonService personService = new PersonService();
    // @Override
    // public void start(Stage stage) {
    //     var javaVersion = SystemInfo.javaVersion();
    //     var javafxVersion = SystemInfo.javafxVersion();

    //     var label = new Label("Hello, JavaFX " + javafxVersion
    //             + ", running on Java " + javaVersion + ".");
    //     var scene = new Scene(new StackPane(label), 640, 480);
    //     stage.setScene(scene);
    //     stage.show();
    // }

    public static void main(String[] args) {
        // launch();
        System.out.println("App started.");
        PopulateDB.populate();
        System.out.println(personService.getAll().size() + " persons in database.");
        System.out.println("Login time");

    }

}