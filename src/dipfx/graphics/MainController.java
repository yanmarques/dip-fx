package dipfx.graphics;

import dipfx.common.*;
import dipfx.common.multiImg.MixedContextFilter;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

abstract public class MainController {
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

    protected LabelAutoChangeSliderUnit redSliderUnit;
    protected LabelAutoChangeSliderUnit greenSliderUnit;
    protected LabelAutoChangeSliderUnit blueSliderUnit;
    protected LabelAutoChangeSliderUnit thresholdUnit;
    protected SimpleSliderUnit srcMultiImgUnit;
    protected SimpleSliderUnit dstMultiImgUnit;
    @FXML
    private TextField txtMouseX;
    @FXML
    private TextField txtMouseY;
    @FXML
    private TextField txtSourceGeometry;
    @FXML
    private TextField txtTargetGeometry;
    @FXML
    private TextField txtDestGeometry;
    @FXML
    private Slider redSlider;
    @FXML
    private Slider greenSlider;
    @FXML
    private Slider blueSlider;
    @FXML
    private Label lblRedScale;
    protected HashMap<String, PixelContextFilter> filters = new HashMap<>();
    protected HashMap<String, MixedContextFilter> multiImgFilters = new HashMap<>();

    protected static final Logger logger = LogUtil.getLogger(MainController.class.getName());
    protected ViewToOutput viewToOutput;
    @FXML
    private Slider srcMultiImgSlider;
    @FXML
    private Slider dstMultiImgSlider;
    private MouseEvent sourceMark;
    @FXML
    private Label lblGreenScale;
    @FXML
    private Label lblBlueScale;
    @FXML
    private Slider thresholdSlider;
    @FXML
    private Label lblThreshold;

    abstract public void setPixelColor(Image image, Color color);

    abstract public Color getImagePixelColor(Image image, int mouseX, int mouseY);

    abstract public int colorToDecimal(double color);

    abstract public Image onImageMark(MouseInput srcInput, MouseInput dstInput, Image image);

    public void initialize() {
        logger.info("initiliazing services...");

        this.setupServices();

        logger.log(Level.FINEST, "new log level: {0}", logger.getLevel());
        logger.info("starting window...");
    }

    @FXML
    public void onMousePointerAction() {
        try {
            if (this.sourceView.getImage() == null) {
                logger.warning("no source image set.");
            } else {
                int mouseX = Integer.parseInt(txtMouseX.getText());
                int mouseY = Integer.parseInt(txtMouseY.getText());
                MouseInput mouseInput = new MouseInput(mouseX, mouseY);
                this.handleDisplayPixelFromContext(this.sourceView.getImage(), mouseInput);
            }
        } catch (NumberFormatException numberExc) {
            logger.warning("Mouse pointer value is invalid or empty.");
            logger.finest(numberExc.getMessage());
        }
        catch (Exception exception) {
            logger.log(Level.SEVERE, exception.toString(), exception);
        }
    }

    @FXML
    public void onViewMouseMoved(MouseEvent event) {
        logger.finest("recv mouse event");
        ImageView imgView = (ImageView)event.getTarget();
        Image target = imgView.getImage();
        if (target != null) {
            this.handleDisplayPixelFromContext(target, new MouseInput(event));
        }
    }

    @FXML
    public void registerMousePressed(MouseEvent event) {
        logger.fine("source view: mouse pressed");
        this.sourceMark = event;
    }

    @FXML void registerMouseReleased(MouseEvent event) {
        logger.fine("source view: mouse released");
        this.handleImageMark(event);
    }

    @FXML
    public void openSourceImage() {
        this.updateViewFromFilesystem(sourceView, txtSourceGeometry);
    }

    @FXML
    public void openTargetImage() {
        this.updateViewFromFilesystem(targetView, txtTargetGeometry);
    }

    @FXML
    public void arithmeticGrayScale() {
        this.withFilter("arithmetic-gray-scale");
    }

    @FXML
    public void weightedGrayScale() {
        this.withFilter("weighted-gray-scale");
    }

    @FXML
    public void negativeScale() {
        this.withFilter("negative-scale");
    }

    @FXML
    public void noiseInCross() {
        this.withFilter("cross-noise");
    }

    @FXML
    public void noiseInX() {
        this.withFilter("in-x-noise");
    }

    @FXML
    public void noiseInSquare() {
        this.withFilter("square-noise");
    }

    @FXML
    public void addMultiImages() {
        this.withMultiImgFilter("add-multi-image");
    }

    @FXML
    public void subMultiImages() {
        this.withMultiImgFilter("sub-multi-image");
    }

    public void thresholding(Unit thresholdUnit) {
        this.withFilter("threshold");
    }

    @FXML
    public void save() {
        try {
            File toSave = this.chooseImage(true);

            if (toSave == null) {
                logger.warning("aborting save operation due to user input error");
            } else {
                boolean result = this.viewToOutput.write(toSave);
                logger.info("save image operation result: " + result);
            }
        } catch (RuntimeException exception) {
            logger.log(Level.SEVERE, exception.toString(), exception);
        }
    }

    @FXML
    public void resetPixelColors() {
        txtRed.setText("");
        txtGreen.setText("");
        txtBlue.setText("");
    }

    public void setRed(double color) {
        this.setColorText(txtRed, color);
    }

    public void setGreen(double color) {
        this.setColorText(txtGreen, color);
    }

    public void setBlue(double color) {
        this.setColorText(txtBlue, color);
    }

    protected void setupServices() {     // called on JavaFx initialize
        this.viewToOutput = new ViewToOutput(this.sourceView, this.destView);

        this.registerChannelSliders();
        this.registerThresholdSliders();
        this.registerMultiImageSliders();
    }

    protected void handleDisplayPixelFromContext(Image image, MouseInput mouseInput) {
        try {
            Color color = this.handleColorFromContext(image, mouseInput);
            logger.log(Level.FINEST, "setting pixel color: {0}", color.toString());
            this.setPixelColor(image, color);
        } catch (Exception exc) {
            logger.log(Level.SEVERE, exc.toString(), exc);
            this.resetPixelColors();
        }
    }

    protected Color handleColorFromContext(Image image, MouseInput mouseInput) {
        mouseInput.toPixel();
        txtMouseX.setText(Integer.toString(mouseInput.getAxisX()));
        txtMouseY.setText(Integer.toString(mouseInput.getAxisY()));

        return this.getImagePixelColor(image, mouseInput.getAxisX(), mouseInput.getAxisY());
    }

    protected void handleImageMark(MouseEvent event) {
        if (this.sourceMark != null) {
            MouseInput srcInput = new MouseInput(this.sourceMark);
            srcInput.toPixel();

            MouseInput dstInput = new MouseInput(event);
            dstInput.toPixel();

            Image markedImg = this.onImageMark(srcInput, dstInput, this.sourceView.getImage());
            if (markedImg == null) {
                logger.warning("image mark: failed to get marked image");
            } else {
                this.sourceView.setImage(markedImg);
            }
        }
    }

    protected void setColorText(TextField field, double color) {
        int value = this.colorToDecimal(color);
        field.setText(Integer.toString(value));
    }

    protected void updateViewFromFilesystem(ImageView view, TextField geometryText) {
        File file = chooseImage(false);
        if (file != null) {
            URI fileUri = file.toURI();
            logger.log(Level.FINE, "file chosen: {0}", fileUri.getPath());
            Image source = new Image(fileUri.toString());

            // TODO add better handling of images with error
            if (source.isError()) {
                logger.warning("chosen image not seems right");
            }

            this.updateView(view, source, geometryText);
        }
    }

    protected void withFilter(String filterName) {
        if (! this.filters.containsKey(filterName)) {
            logger.log(Level.SEVERE, "Filter is not registered: {0}", filterName);
            logger.severe("Use filters HashMap to register");
            return;
        }

        if (sourceView.getImage() == null) {
            logger.log(Level.SEVERE, "{0} : source image is empty", filterName);
            return;
        }

        PixelContextFilter filter = this.filters.get(filterName);
        Image resultImg = filter.run(this.sourceView.getImage());
        if (resultImg == null) {
            logger.warning(filterName + ": failed to filter image");
        } else {
            this.updateView(destView, resultImg, txtDestGeometry);
        }
    }

    protected void withMultiImgFilter(String filterName) {
        if (! this.multiImgFilters.containsKey(filterName)) {
            logger.log(Level.SEVERE, "Multi image filter is not registered: {0}", filterName);
            logger.severe("Use multiImgFilters HashMap to register");
            return;
        }

        boolean failed = false;

        if (sourceView.getImage() == null) {
            logger.log(Level.SEVERE, "{0} : source image is empty", filterName);
            failed = true;
        }

        if (targetView.getImage() == null) {
            logger.log(Level.SEVERE, "{0} : target image is empty", filterName);
            failed = true;
        }

        if (! failed) {
            MixedContextFilter filter = this.multiImgFilters.get(filterName);
            Image resultImg = filter.run(this.sourceView.getImage(), this.targetView.getImage());
            if (resultImg == null) {
                logger.warning(filterName + ": failed to filter image");
            } else {
                this.updateView(destView, resultImg, txtDestGeometry);
            }
        }
    }

    protected File chooseImage(boolean isSaving) {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("images", "*.jpg", "*.png", "*.bmp", "*.PNG", "*.JPG", "*.BMP"));

        try {
            logger.info("opening file chooser...");
            if (isSaving) {
                return chooser.showSaveDialog(null);
            }
            return chooser.showOpenDialog(null);
        } catch (Exception exc) {
            logger.log(Level.SEVERE, exc.toString(), exc);
        }

        return null;
    }

    protected void updateView(ImageView view, Image source, TextField geometryText) {
        double height = source.getHeight();
        double width = source.getWidth();

        view.setImage(source);
        view.setFitHeight(height);
        view.setFitWidth(width);

        geometryText.setText((int) width + "x" + (int) height);
    }

    protected void registerChannelSliders() {
        this.redSliderUnit = new LabelAutoChangeSliderUnit(this.redSlider, this.lblRedScale);
        this.greenSliderUnit = new LabelAutoChangeSliderUnit(this.greenSlider, this.lblGreenScale);
        this.blueSliderUnit = new LabelAutoChangeSliderUnit(this.blueSlider, this.lblBlueScale);

        ArrayList<Unit> sliders = new ArrayList<>();
        sliders.add(this.redSliderUnit);
        sliders.add(this.greenSliderUnit);
        sliders.add(this.blueSliderUnit);

        // no need to keep it, Units will take care of auto-checking
        new MaxValueDistribution(100, sliders);
    }

    protected void registerThresholdSliders() {
        this.thresholdSlider.setMax(255);

        this.thresholdUnit = new LabelAutoChangeSliderUnit(this.thresholdSlider, this.lblThreshold);
        this.thresholdUnit.setOnValueChanged(this::thresholding);
    }

    protected void registerMultiImageSliders() {
        this.srcMultiImgUnit = new SimpleSliderUnit(this.srcMultiImgSlider);
        this.dstMultiImgUnit = new SimpleSliderUnit(this.dstMultiImgSlider);

        ArrayList<Unit> sliders = new ArrayList<>();
        sliders.add(this.srcMultiImgUnit);
        sliders.add(this.dstMultiImgUnit);

        new MaxValueDistribution(100, sliders);
    }
}
