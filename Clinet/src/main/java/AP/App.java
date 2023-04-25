package AP;

import AP.Model.Thread.Listener;
import same.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static User currentUser = new User("" , "");
    public static Socket client;
    public static ObjectOutputStream out;
    public static ObjectInputStream in;
    public static int port = 1379;

    @Override
    public void start(Stage stage) throws IOException {
        PageLoader.initStage(stage);
        new PageLoader().load("IPPage");
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}