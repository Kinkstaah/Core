package GUI.PopUp;

import Data.PALreader;
import Data.PALwriter;
import GUI.Core;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Importer_Controller {

    @FXML
    private Text bold_text;

    @FXML
    private Text small_text;

    @FXML
    private Text topbar_text;

    @FXML
    private Button bBrowse;

    @FXML
    private Button bContinue;

    @FXML
    void browse(ActionEvent event)
    {
        if (!PALwriter.getINSTANCE().importDotSettings())
            browse(event);
        else
            continue_with_run(null);
    }

    @FXML
    void continue_with_run(ActionEvent event)
    {
        ImportPopup.stage.close();
        Core core = new Core();
        try
        {
            core.start(new Stage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    public void onMouseDragged(MouseEvent mouseEvent)
    {
        ImportPopup.stage.setX(mouseEvent.getScreenX() + xOffset);
        ImportPopup.stage.setY(mouseEvent.getScreenY() + yOffset);
    }

    @FXML
    public void onMousePressed(MouseEvent mouseEvent)
    {
        xOffset = ImportPopup.stage.getX() - mouseEvent.getScreenX();
        yOffset = ImportPopup.stage.getY() - mouseEvent.getScreenY();
    }

}
