package fr.awu.annuaire.component;

import javafx.scene.control.Button;

public class ButtonSecondary {
    private String label;

    public ButtonSecondary(String label) {
        this.label = label;
    }

    public Button render() {
        Button button = new Button();
        button.getStyleClass().add("button-secondary");
        button.setText(label);
        return button;
    }
}
