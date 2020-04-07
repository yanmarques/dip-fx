package dipfx.common.multiImg;

import dipfx.common.PixelContext;
import javafx.scene.image.Image;

public class MixedPixelContext extends PixelContext {
    private PixelContext targetContext;

    public MixedPixelContext(Image source, Image target) {
        super(source);
        this.targetContext = new PixelContext(target);

        int minWidth = Math.min((int) source.getWidth(), (int) target.getWidth());
        int minHeight = Math.min((int) source.getHeight(), (int) target.getHeight());

        this.getGeometry()[0] = minWidth;
        this.getGeometry()[1] = minHeight;

        // force class to re-create WritableImage with new geometry
        this.setPixelHelpers();
    }

    @Override
    public void setAxisX(int axisX) {
        super.setAxisX(axisX);
        this.getTargetContext().setAxisX(axisX);
    }

    @Override
    public void setAxisY(int axisY) {
        super.setAxisY(axisY);
        this.getTargetContext().setAxisY(axisY);
    }

    public PixelContext getTargetContext() {
        return targetContext;
    }
}
