package GUI.PopUp;

import Data.IniHandler;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdatedPopup_Controller implements Initializable
{

    @FXML
    private Text bold_text;

    @FXML
    private ImageView topbar_image_closeWindow;

    @FXML
    private Text topbar_text;

    @FXML
    private Text updateText;

    private double xOffset = 0;
    private double yOffset = 0;

    public void onMouseDragged(MouseEvent mouseEvent)
    {
        UpdatedPopup.stage.setX(mouseEvent.getScreenX() + xOffset);
        UpdatedPopup.stage.setY(mouseEvent.getScreenY() + yOffset);
    }
    public void onMousePressed(MouseEvent mouseEvent)
    {
        xOffset = UpdatedPopup.stage.getX() - mouseEvent.getScreenX();
        yOffset = UpdatedPopup.stage.getY() - mouseEvent.getScreenY();
    }

    public void topbar_closeWIndow_onMouseClicked()
    {
        UpdatedPopup.stage.close();
    }

    public void topbar_closeWindow_onMouseEntered()
    {
        changeImage(topbar_image_closeWindow, "/icons/cancel.png");
    }

    public void topbar_closeWindow_onMouseExited()
    {
        changeImage(topbar_image_closeWindow, "/icons/cancel0.png");
    }

    private void changeImage(ImageView imageView, String s)
    {
        Platform.runLater(() -> imageView.setImage(new Image(getClass().getResource(s).toString())));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(() -> updateText.setText(UpdatedPopup.updateText));


    }
}
