package dipfx.common;

import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;

import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleSliderUnit implements Unit {
    private static Logger logger = LogUtil.getLogger(LabelAutoChangeSliderUnit.class.getName());

    private Slider slider;
    private int value;
    private Consumer<Unit> unitConsumer;

    public SimpleSliderUnit(Slider slider, boolean initToZero) {
        this.slider = slider;
        this.slider.setOnMouseReleased(this::onValueChanged);
        if (initToZero) {
            this.setValue(0);
        }
    }

    public SimpleSliderUnit(Slider slider) {
        this(slider, true);
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
        logger.log(Level.FINE, "recv new value: {0}", value);
        this.value = value;
        if (updateSlider) {
            this.slider.adjustValue(value);
        }
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
}
