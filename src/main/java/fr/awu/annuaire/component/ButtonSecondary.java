package fr.awu.annuaire.component;

import javafx.scene.control.Button;

public class ButtonSecondary {
    private String label;
    private Button button;

    public ButtonSecondary(String label) {
        this.label = label;
        this.button = new Button();
        this.button.getStyleClass().add("button-secondary");
        this.button.setText(label);
    }

    public Button getButton() {
        return button;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        this.button.setText(label);
    }
    
    public Button render() {
        return button;
    }
}
