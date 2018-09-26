package GUI.PopUp;

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
public class UpdatedPopup extends Application
{
    public static Stage stage;

    public void start(Stage primaryStage) throws Exception
    {
        primaryStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setAlwaysOnTop(true);
        Parent root = fxmlLoader.load(getClass().getResource("/new_in_this_release.fxml"));
        primaryStage.setTitle("PAL: NEW!");
        primaryStage.getIcons().add(new Image(getClass().getResource("/witch.png").toString()));
        Scene scene = new Scene(root, 500, (65 + (updateText.split("\n").length * 20)));
        primaryStage.setScene(scene);
        stage = primaryStage;
        primaryStage.show();
    }

    public void activate(String[] args)
    {
        launch(args);
    }

    public static final String updateText = "Build #12\n JRE10 should now be able to use PAL.\n(If you're seeing this it worked!).";
}
