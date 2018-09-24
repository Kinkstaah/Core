package GUI.OOD;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 */
public class updatepls extends Application
{
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("/updatepls.fxml"));
        primaryStage.setTitle("PAL: New Launcher Required!");
        primaryStage.getIcons().add(new Image(getClass().getResource("/witch.png").toString()));
        Scene scene = new Scene(root, 265, 149);
        scene.getStylesheets().add("text.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void launch_ui(String[] args)
    {
        launch(args);
    }
}
