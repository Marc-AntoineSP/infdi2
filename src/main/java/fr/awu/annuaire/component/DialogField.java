package fr.awu.annuaire.component;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class DialogField {
    private String label;
    private String value;
    private Label labelNode;
    private Label valueLabel;
    private TextField valueTF;
    private boolean isEditable;
    private HBox root;
    private BooleanProperty editMode = new SimpleBooleanProperty(false);
    private Button editButton;
    
    public DialogField(String label, String value, boolean isEditable) {
        this.label = label;
        this.value = value;
        this.isEditable = isEditable;
        this.labelNode = new Label(this.label + " : ");
        this.valueLabel = new Label(this.value != null ? this.value : "");
        this.valueTF = new TextField(this.value != null ? this.value : "");
        this.editButton = new Button("Edit");
        this.root = new HBox(10, labelNode, valueLabel, valueTF, editButton);

        if(this.isEditable){
            valueLabel.visibleProperty().bind(editMode.not());
            valueLabel.managedProperty().bind(editMode.not());

            valueTF.visibleProperty().bind(editMode);
            valueTF.managedProperty().bind(editMode);

            editButton.setOnAction(e -> {
                editMode.set(!editMode.get());
                editButton.setText(editMode.get() ? "Annuler" : "Modifier");
            });

            editMode.set(false);
            editButton.setText("Modifier");
        }else{
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
        if(this.isEditable){
            this.editMode.set(editMode);
        }
    }

    public Parent render(){
        return this.root;
    }

    public boolean getIsEditMode(){
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

}
