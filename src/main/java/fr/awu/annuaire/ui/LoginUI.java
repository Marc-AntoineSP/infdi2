package fr.awu.annuaire.ui;

import java.util.function.Consumer;

import fr.awu.annuaire.component.LoginButtonGroup;
import fr.awu.annuaire.component.LoginTextInput;
import fr.awu.annuaire.model.Person;
import fr.awu.annuaire.service.AuthService;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class LoginUI {
    private AuthService authService;
    private final Consumer<Person> onLoginSuccess;

    public LoginUI(AuthService authService, Consumer<Person> onLoginSuccess) {
        this.authService = authService;
        this.onLoginSuccess = onLoginSuccess;
    }

    public GridPane render() {
        GridPane root = new GridPane();
        root.getStyleClass().add("login-ui-gridpane");
        root.setMaxWidth(Double.MAX_VALUE);
        
        ColumnConstraints column = new ColumnConstraints();
        column.setHgrow(Priority.ALWAYS);
        column.setFillWidth(true);
        root.getColumnConstraints().add(column);
        
        LoginTextInput emailInput = new LoginTextInput("Email", "Enter your email");
        LoginTextInput passwordInput = new LoginTextInput("Password", "Enter your password");

        root.add(emailInput.render(), 0, 0, 1, 1);
        root.add(passwordInput.render(), 0, 1, 1, 1);

        LoginButtonGroup buttonGroup = new LoginButtonGroup();
        root.add(buttonGroup.render(), 0, 2, 1, 1);

        Button loginButton = buttonGroup.getLoginButton().getButton();
        Button registerButton = buttonGroup.getRegisterButton().getButton();

        return root;
    }
}
