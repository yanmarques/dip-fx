package sample.filters;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import sample.Logger;

public class GrayScaleFilter {
    private Image image;
    private Logger logger;

    public GrayScaleFilter(Logger logger, Image image) {
        this.image = image;
        this.logger = logger;
    }

    public Image run() {
        try {
            this.logger.info("initializing gray scale filter");
            return this.doRun();
        } catch (Exception exc) {
            this.logger.error(exc);
        }

        return null;
    }

    protected Image doRun() {
        int height = (int)this.image.getHeight();
        int width = (int)this.image.getWidth();

        this.logger.info("got image info. Height: " + height + " / Width: " + width);

        PixelReader pR = this.image.getPixelReader();
        WritableImage wI = new WritableImage(width, height);
        PixelWriter pW = wI.getPixelWriter();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                Color oldColor = pR.getColor(i, j);
                double average = this.calScale(oldColor);
                Color newColor = new Color(average, average, average, oldColor.getOpacity());
                pW.setColor(i, j, newColor);
            }
        }

        return wI;
    }

    protected double calScale(Color oldColor) {
        return (oldColor.getRed() + oldColor.getGreen() + oldColor.getBlue()) / 3;
    }
}
