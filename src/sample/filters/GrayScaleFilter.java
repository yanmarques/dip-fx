package sample.filters;

import javafx.scene.paint.Color;
import sample.common.BaseFilter;
import sample.common.PixelContext;

public class GrayScaleFilter extends BaseFilter {
    public Color filter(PixelContext context) {
        Color oldColor = context.getCurrentColor();
        double average = this.calcScale(oldColor);
        return new Color(average, average, average, oldColor.getOpacity());
    }

    protected double calcScale(Color oldColor) {
        return (oldColor.getRed() + oldColor.getGreen() + oldColor.getBlue()) / 3;
    }
}
