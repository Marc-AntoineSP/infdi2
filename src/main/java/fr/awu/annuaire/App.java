package fr.awu.annuaire;

import java.util.ArrayList;
import java.util.List;

import atlantafx.base.theme.Dracula;
import fr.awu.annuaire.model.Admin;
import fr.awu.annuaire.model.Employee;
import fr.awu.annuaire.model.Person;
import fr.awu.annuaire.service.AuthService;
import fr.awu.annuaire.service.PersonService;
import fr.awu.annuaire.service.ServiceService;
import fr.awu.annuaire.service.SiteService;
import fr.awu.annuaire.ui.LoginUI;
import fr.awu.annuaire.ui.MainUI;
import fr.awu.annuaire.utils.PopulateDB;
import javafx.application.Application;
import javafx.collections.ObservableList;
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

    ObservableList<Person> personObservableList = javafx.collections.FXCollections
            .observableArrayList();
    PersonService personService = new PersonService();
    AuthService authService = new AuthService(personService);
    List<Person> mockPersons = new ArrayList<>();
    ServiceService serviceService = new ServiceService();
    SiteService siteService = new SiteService();

    @Override
    public void start(Stage stage) {

        PopulateDB.populate();
        mockPersons.addAll(personService.getAll());

        Person testEmployee = new Employee("Test", "Employee",
                "test.employee@example.com", "0123456789", "0123456789",
                serviceService.getAll().get(0), siteService.getAll().get(0),
                "password123");
        personService.save(testEmployee);
        Person testAdmin = new Admin("Test", "Admin", "test.admin@example.com",
                "0123456789", "0123456789", serviceService.getAll().get(0),
                siteService.getAll().get(0), "adminpassword");
        personService.save(testAdmin);
        System.out.println("Personne 1: " + mockPersons.get(0).getEmail()
                + " - " + mockPersons.get(0).getHashedPassword());

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

        StackPane centerPane = new StackPane();
        final GridPane[] loginView = new GridPane[1];
        LoginUI loginUI = new LoginUI(authService, loggedPerson -> {
            System.out.println("User " + loggedPerson.getFirstName() + " "
                    + loggedPerson.getLastName() + " logged in.");
            this.personObservableList.setAll(personService.getAll());

            Runnable onLogout = () -> {
                authService.logout();
                centerPane.getChildren().setAll(loginView[0]);
            };
            MainUI mainUI = new MainUI(this.personObservableList, authService,
                    onLogout, personService, siteService, serviceService);
            Parent mainView = mainUI.render();
            centerPane.getChildren().setAll(mainView);
        });

        loginView[0] = loginUI.render();
        centerPane.getChildren().add(loginView[0]);

        root.setCenter(centerPane);
        Scene scene = new Scene(root, 640, 480);
        centerPane.setAlignment(Pos.CENTER);
        loginView[0].maxWidthProperty()
                .bind(centerPane.widthProperty().multiply(0.6));

        loginView[0].maxHeightProperty()
                .bind(centerPane.heightProperty().multiply(0.6));
        scene.getStylesheets()
                .add(getClass().getResource("/app.css").toExternalForm());
        scene.setUserAgentStylesheet(new Dracula().getUserAgentStylesheet());
        // scene.setUserAgentStylesheet(new
        // CupertinoDark().getUserAgentStylesheet());
        // scene.setUserAgentStylesheet(new
        // NordDark().getUserAgentStylesheet());
        // scene.setUserAgentStylesheet(new
        // NordLight().getUserAgentStylesheet());
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}