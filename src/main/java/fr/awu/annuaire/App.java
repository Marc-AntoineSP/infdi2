package fr.awu.annuaire;

import fr.awu.annuaire.model.Service;
import fr.awu.annuaire.model.Site;
import fr.awu.annuaire.repository.ServiceRepository;
import fr.awu.annuaire.service.SiteService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        var label = new Label("Hello, JavaFX " + javafxVersion
                + ", running on Java " + javaVersion + ".");
        var scene = new Scene(new StackPane(label), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Service service = new Service("Informatique");
        ServiceRepository serviceRepository = new ServiceRepository();
        serviceRepository.save(service);
        Site s = new Site("Lyon");
        SiteService siteService = new SiteService();
        siteService.save(s);
        siteService.getById(s.getId());
        launch();
    }

}