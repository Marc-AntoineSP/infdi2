package fr.awu.annuaire.component;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginTextInput {
    private String label;
    private String placeholder;

    public LoginTextInput(String label, String placeholder) {
        this.label = label;
        this.placeholder = placeholder;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
    }

    public Parent render() {
        VBox inputBox = new VBox();
        inputBox.getStyleClass().add("login-text-input-vbox");
        Label labelNode = new Label(label);
        labelNode.getStyleClass().add("login-text-input-label");
        TextField textField = new TextField();
        textField.getStyleClass().add("login-text-input-textfield");
        textField.setPromptText(placeholder);
        inputBox.getChildren().addAll(labelNode, textField);
        return inputBox;
    }
}
