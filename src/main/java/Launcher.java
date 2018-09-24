import Backend.SystemTrayHandler;
import Data.IniHandler;
import Data.PALdata;
import Data.PALreader;
import Data.UserSettings;
import GUI.Core;
import GUI.OOD.updatepls;
import GUI.PopUp.ImportPopup;

import java.io.File;

/**
 *
 */
public class Launcher
{
    /**
     * Args should be in the order of: Addons, Folders, PoE Steam, PoE Stand Alone, PoE Beta.
     * @param args
     */
    public static void main(String[] args)
    {
        PALdata paldata = new PALdata();
        SystemTrayHandler.createSystemTray();
        // Check if user is using old launcher
        String local = System.getenv("LOCALAPPDATA");
        File new_launcher = new File(local + File.separator + "PAL" + File.separator + "launcher_data.pal");

        if (!new_launcher.exists())
        {
            updatepls updatepls = new updatepls();
            updatepls.launch_ui(null);
            return;
        }

        PALreader.readLauncherData();

        File core_settings = new File(PALdata.LOCAL_PAL_FOLDER + File.separator + "core_settings.pal");
        if (!core_settings.exists())
        {
            // First Launch / import .settings.
            ImportPopup importPopup = new ImportPopup();
            importPopup.activate(null);
            return;
        }


        //IniHandler iniParser = new IniHandler();
        //iniParser.readProperties();
        PALreader.readCoreSettings();

        Core core = new Core();
        core.launch_ui(args);
    }
}
