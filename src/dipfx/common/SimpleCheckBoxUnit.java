package dipfx.common;

import javafx.scene.control.CheckBox;

import java.util.function.Consumer;

public class SimpleCheckBoxUnit implements Unit<Boolean> {
    private CheckBox checkBox;

    public SimpleCheckBoxUnit(CheckBox checkBox, boolean initialValue) {
        this.checkBox = checkBox;
        this.setValue(initialValue);
    }

    @Override
    public Boolean getValue() {
        return this.checkBox.isSelected();
    }

    @Override
    public void setValue(Boolean value) {
        this.checkBox.setSelected(value);
    }

    @Override
    public void setOnValueChanged(Consumer<Unit<Boolean>> unitConsumer) {
        throw new RuntimeException("setOnValueChanged is not implemented on " + this.getClass().getName());
    }
}
