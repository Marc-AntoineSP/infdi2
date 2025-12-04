package fr.awu.annuaire.component;

import javafx.scene.control.Button;
    
public class ButtonPrimary {
    private String label;
    private Button button;

    public ButtonPrimary(String label) {
        this.label = label;
        this.button = new Button();
        this.button.getStyleClass().add("button-primary");
        this.button.setText(label);
    }

    public Button getButton() {
        return button;
    }
    public Button render() {
        return button;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        this.button.setText(label);
    }

}
