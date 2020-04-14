package dipfx.common;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class LabelAutoChangeSliderUnit extends SimpleSliderUnit {
    private Label label;

    public LabelAutoChangeSliderUnit(Slider slider, Label label) {
        super(slider, false);
        this.label = label;
        this.setValue(0);
    }

    public void setValue(int value, boolean updateSlider) {
        super.setValue(value, updateSlider);
        this.label.setText(String.valueOf(value));
    }
}
