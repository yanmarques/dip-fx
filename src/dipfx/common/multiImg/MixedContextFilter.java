package dipfx.common.multiImg;

import dipfx.common.BaseFilter;
import javafx.scene.image.Image;

abstract public class MixedContextFilter extends BaseFilter<MixedPixelContext> {
    public Image run(Image source, Image target) {
        return this.runWithContext(new MixedPixelContext(source, target));
    }
}
