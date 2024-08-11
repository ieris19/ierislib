package com.ieris19.lib.ui.components.input;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class IntegerInputField extends TextField {
    private IntegerProperty value;

    public IntegerInputField() {
        super();
    }

    public IntegerInputField(int initialValue) {
        super(String.valueOf(initialValue));
        this.value = new SimpleIntegerProperty(initialValue);

        IntegerInputField self = this;

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
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                if (newValue == null || newValue.isEmpty()) {
                    value.setValue(0);
                    textProperty().set("0");
                    return;
                }

                final int intValue = Integer.parseInt(newValue);
                textProperty().setValue(oldValue);

                value.set(Integer.parseInt(textProperty().get()));
            }
        });
    }

    public int getValue() {
        return value.getValue();
    }

    public void setValue(int newValue) {
        value.setValue(newValue);
    }

    public IntegerProperty integerProperty() {
        return value;
    }

    @Override
    public void replaceText(int start, int end, String text) {
        if (text.matches("[0-9]") || text.isEmpty()) {
            super.replaceText(start, end, text);
        } else {
            throw new NumberFormatException("Only integer values are allowed");
        }
    }

    @Override
    public void replaceSelection(String text) {
        if (text.matches("[0-9]") || text.isEmpty()) {
            super.replaceSelection(text);
        } else {
            throw new NumberFormatException("Only integer values are allowed");
        }
    }
}
