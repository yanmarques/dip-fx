package sample.filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import sample.logging.LogUtil;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GrayScaleFilter {
    private static final Logger logger = LogUtil.getLogger(GrayScaleFilter.class.getName());

    private Image image;

    public GrayScaleFilter(Image image) {
        this.image = image;
    }

    public Image run() {
        try {
            logger.info("initializing gray scale filter");
            return this.doRun();
        } catch (Exception exc) {
            logger.log(Level.SEVERE, exc.toString(), exc);
        }

        return null;
    }

    protected Image doRun() {
        int height = (int)this.image.getHeight();
        int width = (int)this.image.getWidth();

        logger.log(Level.FINE, "got image resolution: {0}x{1}", new Object[]{width, height});

        PixelReader pR = this.image.getPixelReader();
        WritableImage wI = new WritableImage(width, height);
        PixelWriter pW = wI.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color oldColor = pR.getColor(i, j);
                double average = this.calcScale(oldColor);
                Color newColor = new Color(average, average, average, oldColor.getOpacity());
                pW.setColor(i, j, newColor);
            }
        }

        return wI;
    }

    protected double calcScale(Color oldColor) {
        return (oldColor.getRed() + oldColor.getGreen() + oldColor.getBlue()) / 3;
    }
}
