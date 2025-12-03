package fr.awu.annuaire;

import fr.awu.annuaire.service.AuthService;
import fr.awu.annuaire.ui.LoginUI;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) {

        BorderPane root = new BorderPane();
        ToolBar topBar = new ToolBar();
        topBar.getStyleClass().add("top-bar");
        Region spacer = new Region();
        Region spacer2 = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        Label label = new Label("Annuaire Application");
        label.getStyleClass().add("top-bar-label");
        topBar.getItems().addAll(spacer, label, spacer2);
        root.setTop(topBar);
        LoginUI loginUI = new LoginUI(new AuthService());
        root.setCenter(loginUI.render());
        Scene scene = new Scene(root, 640, 480);
        scene.getStylesheets().add(getClass().getResource("/app.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        // System.out.println("App started.");
        // List<Person> persons = new ArrayList<>();
        // startController = new StartController(persons, personService);
        // System.out.println(personService.getAll().size() + " persons in database.");
        // System.out.println("Login time");
        // loginConsole.creds();
        
    }

}