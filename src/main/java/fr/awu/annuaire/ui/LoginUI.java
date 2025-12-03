package fr.awu.annuaire.ui;

import fr.awu.annuaire.service.AuthService;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public class LoginUI {
    private AuthService authService;

    public LoginUI(AuthService authService) {
        this.authService = authService;
    }

    public Parent render() {
        GridPane root = new GridPane();
        
        return root;
    }
}
