package fr.awu.annuaire.component;

import javafx.scene.Parent;
import javafx.scene.layout.HBox;

public class LoginButtonGroup {
    ButtonPrimary loginButton = new ButtonPrimary("Login");
    ButtonSecondary registerButton = new ButtonSecondary("Register");

    public LoginButtonGroup() {
        //Rien.
    }

    public Parent render() {
        HBox buttonBox = new HBox();
        buttonBox.getStyleClass().add("login-button-group-hbox");
        buttonBox.getChildren().addAll(loginButton.render(), registerButton.render());
        return buttonBox;
    }
}
