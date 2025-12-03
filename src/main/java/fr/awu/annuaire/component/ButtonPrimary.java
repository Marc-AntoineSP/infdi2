package fr.awu.annuaire.component;

import javafx.scene.control.Button;
    
public class ButtonPrimary {
    private String label;

    public ButtonPrimary(String label) {
        this.label = label;
    }

    public Button render() {
        Button button = new Button();
        button.getStyleClass().add("button-primary");
        button.setText(label);
        return button;
    }
}
