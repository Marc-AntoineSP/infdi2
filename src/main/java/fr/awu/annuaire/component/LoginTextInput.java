package fr.awu.annuaire.component;

import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LoginTextInput {
    private String label;
    private Label labelNode;
    private String placeholder;
    private TextField textField;
    private VBox loginBox;

    public LoginTextInput(String label, String placeholder) {
        this.label = label;
        this.placeholder = placeholder;
        this.loginBox = new VBox();
        this.loginBox.getStyleClass().add("login-text-input-vbox");
        this.loginBox.setMaxWidth(Double.MAX_VALUE);
        this.loginBox.setFillWidth(true);

        this.textField = new TextField();
        this.textField.setPromptText(placeholder);
        this.textField.getStyleClass().add("login-text-input-textfield");
        this.textField.setMaxWidth(Double.MAX_VALUE);
        this.textField.setPrefWidth(400);

        this.labelNode = new Label(label);
        this.labelNode.setMaxWidth(Double.MAX_VALUE);
        this.labelNode.getStyleClass().add("login-text-input-label");

        this.loginBox.getChildren().addAll(labelNode, this.textField);

    }

    public String getLabel() {
        return label;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public Parent render() {
        return this.loginBox;
    }

    public Label getLabelNode() {
        return labelNode;
    }

    public TextField getTextField() {
        return textField;
    }

    public VBox getLoginBox() {
        return loginBox;
    }

    public String getText() {
        return this.textField.getText();
    }

    public void setText(String text) {
        this.textField.setText(text);
    }

}
