package dipfx.graphics;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class AppLoader {
    public static Parent load(MainController controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(AppLoader.class.getResource("base.fxml"));
        loader.setController(controller);
        return loader.load();
    }
}
