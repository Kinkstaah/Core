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
public class ImportPopup extends Application
{
    public static Stage stage;

    public void start(Stage primaryStage) throws Exception
    {
        primaryStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setAlwaysOnTop(true);
        Parent root = fxmlLoader.load(getClass().getResource("/popup_import.fxml"));
        primaryStage.setTitle("PAL: Importer");
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
