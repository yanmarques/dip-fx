package dipfx.common;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

import java.util.function.Consumer;
import java.util.logging.Logger;

public class SliderUnit implements Unit {
    private static Logger logger = LogUtil.getLogger(SliderUnit.class.getName());

    private Slider slider;
    private Label label;
    private int value;
    private Consumer<Unit> unitConsumer;

    public SliderUnit(Slider slider, Label label) {
        this.slider = slider;
        this.label = label;
        this.slider.setOnMouseReleased(this::onValueChanged);
        this.setValue(0);
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.setValue(value, true);
    }

    public void setValue(int value, boolean updateSlider) {
        logger.fine("recv new value: " + value);
        this.value = value;
        this.update(updateSlider);
    }

    @Override
    public void setOnValueChanged(Consumer<Unit> unitConsumer) {
        this.unitConsumer = unitConsumer;
    }

    protected void onValueChanged(MouseEvent event) {
        this.setValue((int) this.slider.getValue(), false);
        if (unitConsumer == null) {
            logger.warning("no consumer available, ignoring...");
        } else {
            this.unitConsumer.accept(this);
        }
    }

    protected void update(boolean withSlider) {
        if (withSlider) {
            this.slider.adjustValue(this.getValue());
        }
        this.label.setText(String.valueOf(this.value));
    }
}
