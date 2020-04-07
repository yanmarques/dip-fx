package dipfx.common;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.logging.Level;
import java.util.logging.Logger;

abstract public class BaseFilter<T extends PixelContext> {
    protected static final Logger logger = LogUtil.getLogger(BaseFilter.class.getName());

    private SimplePixelToolkit<T> simplePixelToolkit = new SimplePixelToolkit<>(this::filter);

    abstract public Color filter(T context);

    protected Image runWithContext(T context) {
        try {
            logger.info("initializing filter: " + super.getClass().getName());
            return this.getPixelToolkit().apply(context);
        } catch (Exception exc) {
            logger.log(Level.SEVERE, exc.toString(), exc);
        }

        return null;
    }

    public SimplePixelToolkit<T> getPixelToolkit() {
        return simplePixelToolkit;
    }

    public void setPixelToolkit(SimplePixelToolkit<T> simplePixelToolkit) {
        this.simplePixelToolkit = simplePixelToolkit;
    }
}
