package Start;/**
 * Created by Ranga Rathnayake on 2020-12-28.
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Start extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            primaryStage = stage;
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
