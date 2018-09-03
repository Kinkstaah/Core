package GUI.PopUp;

import javafx.stage.Stage;

/**
 *
 */
public final class PopupFactory
{
    private static void showPopup(String top, String bold, String bottom)
    {
        PopUp.bold_text = bold;
        PopUp.top_text = top;
        PopUp.small_text = bottom;
        PopUp popUp = new PopUp();
        try
        {
            popUp.start(new Stage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void showError(int n)
    {
        switch (n)
        {
            case 1 : showPopup("PAL: Warning", "ERROR #1",
                    "You're trying to launch Path of Exile but PAL has no idea how to do so. In the main program window click on the 3 dots in the top left!");
            break;
            case 2 : showPopup("PAL: Warning", "ERROR #2",
                    "You're trying to do something with a loot filter, but you do not have your Filter Location set. In the main program window click on the 3 dots in the top left!");
            break;
            case 3 : showPopup("PAL Warning", "ERROR #3",
                    "You're trying, or an addon is trying to use AutoHotKey but you either haven't installed AutoHotKey or you haven't told PAL where it is installed. To do so: In the main program window click on the 3 dots in the top left!");
        }
    }
}
