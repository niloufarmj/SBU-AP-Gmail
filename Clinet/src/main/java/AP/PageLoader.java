package AP;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.nio.file.Paths;


public class PageLoader {
    private static Stage stage;
    private static Scene scene;

    public static final int WIDTH = 530;
    public static final int HEIGHT = 695;

    public static void initStage(Stage primaryStage) {

        stage = primaryStage;
        stage.setTitle("Gmail");
        stage.setResizable(true);
        stage.setWidth(WIDTH);
        stage.setHeight(HEIGHT);
        stage.initStyle(StageStyle.DECORATED);
        stage.getIcons().add(new Image(Paths.get("Images/7328930.png").toUri().toString()));
    }

    public void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    public static Stage getPrimaryStage() {
        return stage;
    }

    public Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public void load(String url) throws IOException {
        scene = new Scene(new PageLoader().loadFXML(url));
        stage.setScene(scene);
        stage.show();
    }

    public void load(String fxml, Object controller) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        fxmlLoader.setController(controller);
        fxmlLoader.load();
    }
}
