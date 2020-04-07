package dipfx.common;

import javafx.scene.image.Image;

abstract public class PixelContextFilter extends BaseFilter<PixelContext> {
    public Image run(Image source) {
        return this.runWithContext(new PixelContext(source));
    }
}
