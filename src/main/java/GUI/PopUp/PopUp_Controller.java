package GUI.PopUp;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 */
public class PopUp_Controller implements Initializable
{
    @FXML
    private Text bold_text;

    @FXML
    private Text small_text;

    @FXML
    private Text topbar_text;

    @FXML
    private ImageView topbar_image_closeWindow;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(() ->
        {
            bold_text.setText(PopUp.bold_text);
            small_text.setText(PopUp.small_text);
            topbar_text.setText(PopUp.top_text);
        });
    }

    private double xOffset = 0;
    private double yOffset = 0;

    public void onMouseDragged(MouseEvent mouseEvent)
    {
        PopUp.stage.setX(mouseEvent.getScreenX() + xOffset);
        PopUp.stage.setY(mouseEvent.getScreenY() + yOffset);
    }
    public void onMousePressed(MouseEvent mouseEvent)
    {
        xOffset = PopUp.stage.getX() - mouseEvent.getScreenX();
        yOffset = PopUp.stage.getY() - mouseEvent.getScreenY();
    }

    public void topbar_closeWIndow_onMouseClicked()
    {
        PopUp.stage.close();
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
}
