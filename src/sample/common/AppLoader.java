package sample.common;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import sample.DipController;
import sample.MainController;

import java.io.IOException;

public class AppLoader {
    public static Parent load(MainController controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(AppLoader.class.getResource("../base.fxml"));
        loader.setController(new DipController());
        return loader.load();
    }
}
