package fr.awu.annuaire.ui;

import fr.awu.annuaire.component.ButtonPrimary;
import fr.awu.annuaire.component.ButtonSecondary;
import fr.awu.annuaire.component.LoginButtonGroup;
import fr.awu.annuaire.component.LoginTextInput;
import fr.awu.annuaire.service.AuthService;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;

public class LoginUI {
    private AuthService authService;

    public LoginUI(AuthService authService) {
        this.authService = authService;
    }

    public Parent render() {
        GridPane root = new GridPane();
        root.getStyleClass().add("login-ui-gridpane");
        LoginTextInput emailInput = new LoginTextInput("Email", "Enter your email");
        LoginTextInput passwordInput = new LoginTextInput("Password", "Enter your password");
        root.add(emailInput.render(), 0, 0, 1, 1);
        root.add(passwordInput.render(), 0, 1, 1, 1);
        LoginButtonGroup buttonGroup = new LoginButtonGroup();
        root.add(buttonGroup.render(), 0, 2, 1, 1);

        return root;
    }
}
