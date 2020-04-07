package dipfx.common;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.util.logging.Level;
import java.util.logging.Logger;

public class SimplePixelToolkit<T extends PixelContext> {
    private static final Logger logger = LogUtil.getLogger(SimplePixelToolkit.class.getName());

    private Callback<T, Color> runner;
    private int initialPixel;
    private int untilWidth;
    private int untilHeight;

    public SimplePixelToolkit(Callback<T, Color> runner, int initialPixel, int untilWidth, int untilHeight) {
        this.runner = runner;
        this.initialPixel = initialPixel;
        this.untilWidth = untilWidth;
        this.untilHeight = untilHeight;
    }

    public SimplePixelToolkit(Callback<T, Color> runner) {
        this(runner, 0, 0, 0);
    }

    public Image apply(T context) {
        logger.log(Level.FINE, "got image resolution: {0}x{1}", context.getGeometry());

        int width = context.getGeometry()[0] + this.untilWidth;
        int height = context.getGeometry()[1] + this.untilHeight;

        for (int i = this.initialPixel; i < width; i++) {
            for (int j = this.initialPixel; j < height; j++) {
                context.setAxisX(i);
                context.setAxisY(j);
                Color newColor = this.runner.call(context);
                context.getPixelWriter().setColor(i, j, newColor);
            }
        }

        return context.getImage();
    }
}
