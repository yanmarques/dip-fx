package dipfx.graphics;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import jdk.jfr.Description;

import java.io.IOException;

public class AppLoader {
    public static Parent loadFromHere(Object controller, String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(AppLoader.class.getResource(fxml));
        loader.setController(controller);
        return loader.load();
    }

    @Description("Use AppLoader.loadBase() instead")
    public static Parent load(MainController controller) throws IOException {
        return loadBase(controller);
    }

    public static Parent loadBase(MainController controller) throws IOException {
        return loadFromHere(controller, "base.fxml");
    }

    public static Parent loadHistogram(BaseHistogramController controller) throws IOException {
        return loadFromHere(controller, "histogram.fxml");
    }
}
