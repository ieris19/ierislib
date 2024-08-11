package com.ieris19.lib.ui.components.input;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class FloatInputField extends TextField {
    private FloatProperty value;

    public FloatInputField() {
        super();
    }

    public FloatInputField(float initialValue) {
        super(String.valueOf(initialValue));
        this.value = new SimpleFloatProperty(initialValue);

        FloatInputField self = this;

        this.value.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldValue, Number newValue) {
                if (newValue == null) {
                    self.textProperty().set("0");
                } else {
                    self.textProperty().set(newValue.toString());
                }
            }
        });

        this.addEventFilter(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (!"-0123456789".contains(keyEvent.getCharacter())) {
                    keyEvent.consume();
                }
            }
        });

        this.textProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (newValue == null || newValue.isEmpty()) {
                    value.setValue(0);
                    textProperty().set("0");
                    return;
                }

                final float floatValue = Float.parseFloat(newValue);
                textProperty().setValue(oldValue);

                value.set(Float.parseFloat(textProperty().get()));
            }
        });
    }

    public float getValue() {
        return value.getValue();
    }

    public void setValue(float newValue) {
        value.setValue(newValue);
    }

    public FloatProperty integerProperty() {
        return value;
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[0-9]") || text.isEmpty()) {
            super.replaceText(start, end, text);
        } else {
            throw new NumberFormatException("Only floateger values are allowed");
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (text.matches("[0-9]") || text.isEmpty()) {
            super.replaceSelection(text);
        } else {
            throw new NumberFormatException("Only floateger values are allowed");
        }
    }
}
