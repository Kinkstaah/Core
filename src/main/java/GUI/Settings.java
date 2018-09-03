package GUI;

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
public class Settings extends Application
{
    public static Stage stage;

    public void start(Stage primaryStage) throws Exception
    {
        primaryStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        Parent root = fxmlLoader.load(getClass().getResource("/Settings.fxml"));
        primaryStage.setTitle("PAL: Settings");
        primaryStage.getIcons().add(new Image(getClass().getResource("/witch.png").toString()));
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add("layout_settings.css");
        primaryStage.setScene(scene);
        stage = primaryStage;
        primaryStage.show();
    }

    public void launch_ui(String[] args)
    {
        launch(args);
    }
}
