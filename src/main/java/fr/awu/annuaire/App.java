package fr.awu.annuaire;

import atlantafx.base.theme.CupertinoDark;
import atlantafx.base.theme.Dracula;
import atlantafx.base.theme.NordDark;
import atlantafx.base.theme.NordLight;
import atlantafx.base.theme.PrimerDark;
import atlantafx.base.theme.PrimerLight;
import fr.awu.annuaire.service.AuthService;
import fr.awu.annuaire.ui.LoginUI;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
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
        
        GridPane loginView = loginUI.render();
        StackPane centerPane = new StackPane(loginView);

        root.setCenter(centerPane);
        Scene scene = new Scene(root, 640, 480);
        centerPane.setAlignment(Pos.CENTER);
        loginView.maxWidthProperty().bind(centerPane.widthProperty().multiply(0.6));

        loginView.maxHeightProperty().bind(centerPane.heightProperty().multiply(0.6));
        scene.getStylesheets().add(getClass().getResource("/app.css").toExternalForm());
        scene.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
        // scene.setUserAgentStylesheet(new CupertinoDark().getUserAgentStylesheet());
        // scene.setUserAgentStylesheet(new NordDark().getUserAgentStylesheet());
        // scene.setUserAgentStylesheet(new NordLight().getUserAgentStylesheet());
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