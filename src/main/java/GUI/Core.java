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
public class Core extends Application
{
    public static Stage stage;

    public void start(Stage primaryStage) throws Exception
    {
        primaryStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Parent root = fxmlLoader.load(getClass().getResource("/CoreUI.fxml"));
        primaryStage.setTitle("PAL: Core");
        primaryStage.getIcons().add(new Image(getClass().getResource("/witch.png").toString()));
        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add("text.css");
        primaryStage.setScene(scene);
        stage = primaryStage;
        primaryStage.show();
    }

    public void launch_ui(String[] args)
    {
        launch(args);
    }
}
