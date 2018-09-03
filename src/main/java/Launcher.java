import Backend.SystemTrayHandler;
import Data.IniHandler;
import GUI.Core;


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
        IniHandler iniParser = new IniHandler();
        iniParser.readProperties();

        SystemTrayHandler.createSystemTray();
        Core core = new Core();
        core.launch_ui(args);
    }
}
