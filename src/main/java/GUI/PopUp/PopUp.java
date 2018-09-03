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
public class PopUp extends Application
{
    public static Stage stage;
    public static String top_text;
    public static String bold_text;
    public static String small_text;

    public void start(Stage primaryStage) throws Exception
    {
        primaryStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        Parent root = fxmlLoader.load(getClass().getResource("/popup.fxml"));
        primaryStage.setTitle("PAL: Message");
        primaryStage.getIcons().add(new Image(getClass().getResource("/witch.png").toString()));
        Scene scene = new Scene(root, 350, 150);
        primaryStage.setScene(scene);
        stage = primaryStage;
        primaryStage.show();
    }

    public void activate(String[] args)
    {
        launch(args);
    }
}
