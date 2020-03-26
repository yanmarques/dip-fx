package sample.common;

import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class PixelContext {
    private int axisX;
    private int axisY;
    private PixelReader pixelReader;
    private WritableImage image;
    private PixelWriter pixelWriter;

    public PixelContext(int axisX, int axisY, PixelReader pixelReader, WritableImage image, PixelWriter pixelWriter) {
        this.axisX = axisX;
        this.axisY = axisY;
        this.pixelReader = pixelReader;
        this.image = image;
        this.pixelWriter = pixelWriter;
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
