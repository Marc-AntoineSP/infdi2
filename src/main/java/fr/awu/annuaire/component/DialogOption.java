package fr.awu.annuaire.component;

import java.util.List;
import java.util.function.Function;

import javafx.collections.FXCollections;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;

public class DialogOption<T> {
    private final String label;
    private final boolean isEditable;

    private final Label labelNode;
    private final Label valueLabel;
    private final ComboBox<T> comboBox;
    private final HBox root;

    private final Function<T, String> labelProvider;

    public DialogOption(String label,
                        T initialValue,
                        boolean isEditable,
                        List<T> options,
                        Function<T, String> labelProvider) {
        this.label = label;
        this.isEditable = isEditable;
        this.labelProvider = labelProvider;

        this.labelNode = new Label(label + " : ");

        this.valueLabel = new Label(toText(initialValue));

        this.comboBox = new ComboBox<>();
        this.comboBox.setItems(FXCollections.observableArrayList(options));

        this.comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(T object) {
                return toText(object);
            }

            @Override
            public T fromString(String string) {
                return comboBox.getItems().stream()
                        .filter(o -> toText(o).equals(string))
                        .findFirst()
                        .orElse(null);
            }
        });

        if (initialValue != null) {
            this.comboBox.setValue(initialValue);
        } else if (!options.isEmpty()) {
            this.comboBox.setValue(options.get(0));
        }

        this.root = new HBox(10, labelNode, valueLabel, comboBox);

        if (isEditable) {
            valueLabel.setVisible(false);
            valueLabel.setManaged(false);

            comboBox.setVisible(true);
            comboBox.setManaged(true);
        } else {
            valueLabel.setVisible(true);
            valueLabel.setManaged(true);

            comboBox.setVisible(false);
            comboBox.setManaged(false);
        }
    }

    public Parent render() {
        return root;
    }

    public T getSelectedValue() {
        if (isEditable) {
            return comboBox.getValue();
        }
        String text = valueLabel.getText();
        return comboBox.getItems().stream()
                .filter(o -> toText(o).equals(text))
                .findFirst()
                .orElse(null);
    }

    public String getCurrentLabel() {
        return isEditable ? toText(comboBox.getValue()) : valueLabel.getText();
    }

    public void setSelectedValue(T value) {
        if (value == null) return;
        if (isEditable) {
            comboBox.setValue(value);
        } else {
            valueLabel.setText(toText(value));
        }
    }

    private String toText(T value) {
        if (value == null) return "";
        return labelProvider.apply(value);
    }
}
