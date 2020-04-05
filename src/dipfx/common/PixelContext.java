package dipfx.common;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class PixelContext {
    private int axisX;
    private int axisY;
    private Image originalImage;
    private Integer[] geometry;
    private PixelReader pixelReader;
    private WritableImage image;
    private PixelWriter pixelWriter;

    public PixelContext(Image image, int axisX, int axisY) {
        this.axisX = axisX;
        this.axisY = axisY;
        this.originalImage = image;

        int height = (int) image.getHeight();
        int width = (int) image.getWidth();

        this.geometry = new Integer[]{width, height};
        this.pixelReader = image.getPixelReader();
        this.image = new WritableImage(width, height);
        this.pixelWriter = this.image.getPixelWriter();
    }

    public PixelContext(Image image) {
        this(image, -1, -1);
    }

    public Image getOriginalImage() {
        return originalImage;
    }

    public Integer[] getGeometry() {
        return geometry;
    }

    public int getAxisX() {
        return axisX;
    }

    public void setAxisX(int axisX) {
        this.axisX = axisX;
    }

    public int getAxisY() {
        return axisY;
    }

    public void setAxisY(int axisY) {
        this.axisY = axisY;
    }

    public Color getCurrentColor() {
        return this.getPixelReader().getColor(this.getAxisX(), this.getAxisY());
    }

    public PixelReader getPixelReader() {
        return pixelReader;
    }

    public WritableImage getImage() {
        return image;
    }

    public PixelWriter getPixelWriter() {
        return pixelWriter;
    }
}
