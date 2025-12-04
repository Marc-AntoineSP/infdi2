package fr.awu.annuaire.component;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class DialogField {
    private String label;
    private String value;
    private boolean isEditable;
    private boolean isEditMode;
    
    public DialogField(String label, String value, boolean isEditable) {
        this.label = label;
        this.value = value;
        this.isEditable = isEditable;
        this.isEditMode = false;
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
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public Parent render(){
        Label labelNode = new Label(this.label + " : ");
        Node textField;
        if(this.isEditable && this.isEditMode){
            textField = new TextField(this.value);
        }else{
            textField = new Label(this.value);
        }

        return new HBox(10, labelNode, textField);
    }

}
