package solo.Guzov.Ilya.Example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.InputStream;

public class Window extends Application implements Runnable{

    public void run(){
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/maket/scene.fxml"));

        Scene scene = new Scene(root);



        InputStream iconStream = getClass().getResourceAsStream("/images/myIcon.png");
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image);

        primaryStage.setTitle("Contester");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
