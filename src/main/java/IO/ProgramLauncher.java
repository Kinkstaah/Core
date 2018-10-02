package IO;

import Data.InstalledAddons;
import Data.PALdata;
import Data.UserSettings;
import GUI.Tables.InstalledTable;
import Repo.AddonJson;
import Repo.Addons;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 *
 */
public final class ProgramLauncher
{
    private static final ProgramLauncher INSTANCE = new ProgramLauncher();

    public static void launch(String addon_name)
    {
        AddonJson addon = Addons.getINSTANCe().getAddonByName(addon_name);

        if (addon_name.equals("CurrencyCop"))
        {
            currencyCopHandler(addon);
            return;
        }

        if (addon_name.equals("POE TradeMacro"))
        {
            tradeMacro();
            return;
        }

        String launch_command = addon.getFile_launch();
        assert launch_command != null;

        launch_command = launch_command.replace("%d", addon_name);
        launch_command = launch_command.replace("Addons", UserSettings.getPathAddons());
        launch_command = launch_command.replace("/", File.separator);
        // Execute Launch Command:
        String[] filename = getFileName(launch_command);

        System.out.println("COMMNAND: " + launch_command);
        if (addon_name.equals("Path of Maps"))
        {
            pathOfMaps(launch_command);
            return;
        }

        if (filename[1].equals("exe"))
        {
            Runtime runtime = Runtime.getRuntime();
            try
            {
                runtime.exec("\"" + launch_command + "\"");
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else if (filename[1].equals("jar"))
        {
            Runtime runtime = Runtime.getRuntime();
            try
            {
                runtime.exec("java -jar " + launch_command);
            } catch (IOException e)
            {
                e.printStackTrace();
                InstalledAddons.getINSTANCE().scanForInstalledAddons();
                // TODO Show anchorpane with error
            }
        }
        else if (filename[1].equals("ahk"))
        {
            File autohotkeyEXE = new File(PALdata.settings.getAHK_Folder() + File.separator + "autohotkey.exe");
            if (autohotkeyEXE.exists())
            {
                String ahk_path = autohotkeyEXE.getPath();
                String scriptPath = launch_command;

                Runtime runtime = Runtime.getRuntime();
                try
                {
                    runtime.exec(new String[] { ahk_path, scriptPath} );
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                    InstalledAddons.getINSTANCE().scanForInstalledAddons();
                    //TODO: Show window with error
                }
            }
        }
        else
        {
            // TODO: Show window to create issue on github.
            System.out.println("ASK FOR SUPPORT FOR THIS EXTENSION: " + filename[1]);
        }
    }

    private static void pathOfMaps(String launch_command)
    {
        System.out.println("Attempting to launch Path of Maps.");
        Runtime runtime = Runtime.getRuntime();
        try
        {
            runtime.exec("rundll32 url.dll,FileProtocolHandler \"" + launch_command + "\"");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void tradeMacro()
    {
        File autohotkeyExe = new File(PALdata.settings.getAHK_Folder() + File.separator + "AutoHotkey.exe ");
        String launch_command = autohotkeyExe.getPath() + "\"" + UserSettings.getPathAddons()
                + File.separator + "POE TradeMacro" + File.separator + "Run_TradeMacro.ahk\"" + " -noelevation";
        System.out.println(launch_command);
        try
        {
            Runtime.getRuntime().exec(launch_command);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * If the program is installed in Local/Programs we move and delete it.
     * @param addon
     */
    private static void currencyCopHandler(AddonJson addon)
    {
        //TODO: NFO.PAL | Installed Table to show installed.
        String local = System.getenv("LOCALAPPDATA");
        local = addon.getFile_launch().replace("LOCALAPPDATA",local);
        Runtime runtime = Runtime.getRuntime();
        try
        {
            runtime.exec(local);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static String[] getFileName(String str)
    {
        String[] parts = str.split("\\\\");
        // Need final 2 parts.
        String filename = parts[parts.length-2];
        String file_ext = parts[parts.length-1].split("\\.")[1];
        return new String[]{filename, file_ext};
    }

    public static void launchAHKscript(String location)
    {
        File autohotkeyEXE = new File(UserSettings.getAhkPath() + File.separator + "autohotkey.exe");
        if (autohotkeyEXE.exists())
        {
            String ahk_path = autohotkeyEXE.getPath();
            String scriptPath = location;

            Runtime runtime = Runtime.getRuntime();
            try
            {
                runtime.exec(new String[] { ahk_path, scriptPath} );
            }
            catch (IOException e)
            {
                e.printStackTrace();
                InstalledAddons.getINSTANCE().scanForInstalledAddons();
                //TODO: Show window with error
            }
        }
    }
}
