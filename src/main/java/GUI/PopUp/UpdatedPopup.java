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

    public static final String updateText = "IMPORTANT!\n" +
            "Double check your settings, this version changed a lot to how settings work.\n" +
            "\n" +
            "IMPORTANT!!\n" +
            "You will have to redownload your Addons due to changes in how the program works.\n" +
            "Sorry for the inconvenience, but due to this change\n " +
            "this won't have to happen in the future!\n" +
            "\n" +
            "Changes:\n" +
            "- Changed Launcher and how file selection is handled.\n" +
            "- Complete overhaul of how settings are stored and handled.\n" +
            "- Added More Addons to Repo.\n" +
            "- PoE Trade Macro will now run without asking for Admin Privelege.\n" +
            "- Change PoE Stand Alone launching.\n" +
            "- Fix Github API enabled checkbox bug.\n" +
            "- Upon first launch a little box will notify you\n of new changes made in this version.";
}
