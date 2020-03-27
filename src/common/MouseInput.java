package common;

import javafx.scene.input.MouseEvent;

public class    MouseInput {
    private int axisX;
    private int axisY;
    private boolean raw = true;

    public MouseInput(MouseEvent event) {
        this(event.getX(), event.getY());
    }

    public MouseInput(double eventX, double eventY) {
        this.axisX = (int) eventX;
        this.axisY = (int) eventY;
    }

    public int getAxisX() {
        return axisX;
    }

    public int getAxisY() {
        return axisY;
    }

    public boolean isRaw() {
        return raw;
    }

    public boolean toPixel() {
        if (!this.isRaw()) {
            return false;
        }

        if (axisX > 0) {
            axisX -= 1;
        }

        if (axisY > 0) {
            axisY -= 1;
        }

        // future calls to this will be blocked
        this.raw = false;

        return true;
    }
}
