package dipfx.common;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PixelToolkit {
    private static final Logger logger = LogUtil.getLogger(PixelToolkit.class.getName());

    private Callback<PixelContext, Color> runner;
    private int initialPixel;
    private int untilWidth;
    private int untilHeight;

    public PixelToolkit(Callback<PixelContext, Color> runner, int initialPixel, int untilWidth, int untilHeight) {
        this.runner = runner;
        this.initialPixel = initialPixel;
        this.untilWidth = untilWidth;
        this.untilHeight = untilHeight;
    }

    public PixelToolkit(Callback<PixelContext, Color> runner) {
        this(runner, 0, 0, 0);
    }

    public Image apply(Image image) {
        PixelContext context = new PixelContext(image);

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
