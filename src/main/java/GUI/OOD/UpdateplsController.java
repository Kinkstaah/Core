package GUI.OOD;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class UpdateplsController {

    @FXML
    void openBrowser(ActionEvent event)
    {
            if (Desktop.isDesktopSupported())
            {
                try
                {
                    Hyperlink hyperlink = (Hyperlink) event.getSource();
                    Desktop.getDesktop().browse(new URI(hyperlink.getText()));
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                catch (URISyntaxException e)
                {
                    e.printStackTrace();
                }
            }
    }

}
