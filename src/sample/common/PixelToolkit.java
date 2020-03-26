package sample.common;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import sample.logging.LogUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PixelToolkit {
    private static final Logger logger = LogUtil.getLogger(PixelToolkit.class.getName());

    private Callback<PixelContext, Color> runner;

    public PixelToolkit(Callback<PixelContext, Color> runner) {
        this.runner = runner;
    }

    public Image apply(Image image) {
        int height = (int) image.getHeight();
        int width = (int) image.getWidth();

        logger.log(Level.FINE, "got image resolution: {0}x{1}", new Object[]{width, height});

        PixelReader pR = image.getPixelReader();
        WritableImage wI = new WritableImage(width, height);
        PixelWriter pW = wI.getPixelWriter();

        PixelContext context = new PixelContext(-1, -1, pR, wI, pW);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                context.setAxisX(i);
                context.setAxisY(j);
                Color newColor = this.runner.call(context);
                pW.setColor(i, j, newColor);
            }
        }

        return wI;
    }
}
