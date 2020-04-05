package dipfx.common;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.logging.Level;
import java.util.logging.Logger;

abstract public class BaseFilter {
    protected static final Logger logger = LogUtil.getLogger(BaseFilter.class.getName());

    private PixelToolkit pixelToolkit = new PixelToolkit(this::filter);

    abstract public Color filter(PixelContext context);

    public Image run(Image source) {
        try {
            logger.info("initializing filter: " + super.getClass().getName());
            return this.getPixelToolkit().apply(source);
        } catch (Exception exc) {
            logger.log(Level.SEVERE, exc.toString(), exc);
        }

        return null;
    }

    public PixelToolkit getPixelToolkit() {
        return pixelToolkit;
    }

    public void setPixelToolkit(PixelToolkit pixelToolkit) {
        this.pixelToolkit = pixelToolkit;
    }
}
