package fr.awu.annuaire.component;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.function.Predicate;

public class DialogField {
    private String label;
    private String value;
    private Label labelNode;
    private Label valueLabel;
    private TextField valueTF;
    private Label errorLabel;
    private boolean isEditable;
    private VBox root;
    private BooleanProperty editMode = new SimpleBooleanProperty(false);
    private Button editButton;
    private Predicate<String> validator;
    private String errorMessage;

    public DialogField(String label, String value, boolean isEditable) {
        this(label, value, isEditable, null, null);
    }

    public DialogField(String label, String value, boolean isEditable,
            Predicate<String> validator, String errorMessage) {
        this.label = label;
        this.value = value;
        this.isEditable = isEditable;
        this.validator = validator;
        this.errorMessage = errorMessage;

        this.labelNode = new Label(this.label + " : ");
        this.valueLabel = new Label(this.value != null ? this.value : "");
        this.valueTF = new TextField(this.value != null ? this.value : "");
        this.editButton = new Button("Edit");
        this.errorLabel = new Label();
        this.errorLabel.setTextFill(Color.RED);
        this.errorLabel.setVisible(false);
        this.errorLabel.setManaged(false);
        this.errorLabel.setStyle("-fx-font-size: 11px;");

        HBox fieldRow = new HBox(10, labelNode, valueLabel, valueTF,
                editButton);
        this.root = new VBox(5, fieldRow, errorLabel);

        if (this.isEditable) {
            valueLabel.visibleProperty().bind(editMode.not());
            valueLabel.managedProperty().bind(editMode.not());

            valueTF.visibleProperty().bind(editMode);
            valueTF.managedProperty().bind(editMode);

            editButton.setOnAction(e -> {
                editMode.set(!editMode.get());
                editButton.setText(editMode.get() ? "Annuler" : "Modifier");
                if (!editMode.get()) {
                    hideError();
                }
            });

            valueTF.textProperty().addListener((obs, oldVal, newVal) -> {
                if (editMode.get() && validator != null) {
                    validate();
                }
            });

            editMode.set(false);
            editButton.setText("Modifier");
        } else {
            valueLabel.setVisible(true);
            valueLabel.setManaged(true);

            valueTF.setVisible(false);
            valueTF.setManaged(false);

            editButton.setVisible(false);
            editButton.setManaged(false);
        }
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public Label getLabelNode() {
        return labelNode;
    }

    public void setLabelNode(Label labelNode) {
        this.labelNode = labelNode;
    }

    public Label getValueLabel() {
        return valueLabel;
    }

    public void setValueLabel(Label valueLabel) {
        this.valueLabel = valueLabel;
    }

    public TextField getValueTF() {
        return valueTF;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public Boolean getEditMode() {
        return editMode.get();
    }

    public void setEditMode(Boolean editMode) {
        if (this.isEditable) {
            this.editMode.set(editMode);
        }
    }

    public Parent render() {
        return this.root;
    }

    public boolean getIsEditMode() {
        return this.editMode.get();
    }

    public void setEditMode(boolean editable) {
        if (isEditable) {
            editMode.set(editable);
            editButton.setText(editable ? "Annuler" : "Modifier");
        }
    }

    public boolean isEditMode() {
        return editMode.get();
    }

    public String getCurrentValue() {
        if (isEditable && editMode.get()) {
            return valueTF.getText();
        }
        return valueLabel.getText();
    }

    public void setValue(String value) {
        String checkString = value != null ? value : "";
        valueLabel.setText(checkString);
        valueTF.setText(checkString);
    }

    public boolean validate() {
        if (validator == null || !isEditable || !editMode.get()) {
            hideError();
            return true;
        }

        String currentValue = valueTF.getText();
        boolean isValid = validator.test(currentValue);

        if (!isValid) {
            showError(errorMessage != null ? errorMessage : "Valeur invalide");
        } else {
            hideError();
        }

        return isValid;
    }

    private void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
        valueTF.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
    }

    private void hideError() {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
        valueTF.setStyle("");
    }

}
