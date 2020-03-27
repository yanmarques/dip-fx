package common;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.ImageView;
import logging.LogUtil;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewToOutput {
    private static final Logger logger = LogUtil.getLogger(ViewToOutput.class.getName());
    public static String DEFAULT_FORMAT = "jpeg";
    private ImageView sourceView;
    private ImageView destView;

    public ViewToOutput(ImageView sourceView, ImageView destView) {
        this.sourceView = sourceView;
        this.destView = destView;
    }

    public boolean write(File toSave) throws RuntimeException {
        if (this.destView.getImage() == null) {
            throw new RuntimeException("Destination view to save is missing.");
        }

        logger.finest("trying to save image at: " + toSave.getPath());
        logger.finest("image exists: " + toSave.exists());

        try {
            // maybe user should get a popup when source guessed format is different from original source
            String formatName = this.guessFormatFromName();
            logger.info("image format: " + formatName);
            RenderedImage fxImage = SwingFXUtils.fromFXImage(this.destView.getImage(), null);
            return ImageIO.write(fxImage, formatName, toSave);
        } catch (IOException ioExc) {
            logger.log(Level.SEVERE, ioExc.toString(), ioExc);
        }

        return false;
    }

    protected String guessFormatFromName() throws RuntimeException {
        if (this.sourceView.getImage() == null) {
            throw new RuntimeException("Source image view is missing.");
        }

        // TODO is there any better other way
        String[] nameFrags = this.sourceView.getImage().getUrl().split("\\.");

        if (nameFrags.length == 0) {
            return ViewToOutput.DEFAULT_FORMAT;
        }

        return nameFrags[nameFrags.length - 1];
    }
}
