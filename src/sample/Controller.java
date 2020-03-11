package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import sample.filters.GrayScaleFilter;
import sample.logging.LogUtil;

import java.io.File;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    @FXML
    private ImageView sourceView;

    @FXML
    private ImageView targetView;

    @FXML
    private ImageView destView;

    @FXML
    private TextField txtRed;

    @FXML
    private TextField txtGreen;

    @FXML
    private TextField txtBlue;

    private static final Logger logger = LogUtil.getLogger(Controller.class.getName());

    public void initialize() {
        logger.log(Level.FINEST, "new log level: {0}", logger.getLevel());
        logger.info("starting window...");
    }

    @FXML
    public void onViewMouseMoved(MouseEvent event) {
        logger.finest("recv mouse event");
        ImageView imgView = (ImageView)event.getTarget();
        Image target = imgView.getImage();
        if (target != null) {
            setPixelColor(target, (int)event.getX(), (int) event.getY());
        }
    }

    @FXML
    public void openSourceImage() {
        this.updateViewFromFilesystem(sourceView);
    }

    @FXML
    public void openTargetImage() {
        this.updateViewFromFilesystem(targetView);
    }

    @FXML
    public void grayScale() {
        if (this.sourceView.getImage() == null) {
            logger.warning("gray-scale: no source image found");
        } else {
            destView.setImage(new GrayScaleFilter(this.sourceView.getImage()).run());
        }
    }

    @FXML
    public void save() {
        //
    }

    @FXML
    public void resetPixelColors() {
        txtRed.setText("");
        txtGreen.setText("");
        txtBlue.setText("");
    }

    public void setPixelColor(Image image, int mouseX, int mouseY) {
        try {
            Color color = image.getPixelReader().getColor(mouseX - 1, mouseY - 1);
            logger.log(Level.FINEST, "setting pixel color: {0}", color.toString());
            setColorText(txtRed, color.getRed());
            setColorText(txtGreen, color.getGreen());
            setColorText(txtBlue, color.getBlue());
        } catch (Exception exc) {
            logger.log(Level.SEVERE, exc.toString(), exc);
        }
    }

    protected void setColorText(TextField field, double color) {
        int value = (int)(color * 255);
        field.setText(Integer.toString(value));
    }

    public void updateViewFromFilesystem(ImageView view) {
        File file = chooseImage();
        if (file != null) {
            URI fileUri = file.toURI();
            logger.log(Level.FINE, "file chosen: {0}", fileUri.getPath());
            Image source = new Image(fileUri.toString());
            view.setImage(source);
            view.setFitHeight(source.getHeight());
            view.setFitWidth(source.getWidth());
        }
    }

    public File chooseImage() {
        FileChooser chooser = new FileChooser();
//        chooser.getExtensionFilters().add(
//                new FileChooser.ExtensionFilter("images", ".jpg", "png", ".bmp", ".PNG", ".JPG", ".BMP"));

        try {
            logger.info("opening file chooser...");
            return chooser.showOpenDialog(null);
        } catch (Exception exc) {
            logger.log(Level.SEVERE, exc.toString(), exc);
        }

        return null;
    }
}
