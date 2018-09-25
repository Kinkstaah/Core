package Repo;

import Data.PALdata;
import Data.UserSettings;
import GUI.PopUp.PopupFactory;
import IO.CustomAHK;
import IO.ProgramLauncher;
import IO.Uninstaller;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static java.awt.Desktop.getDesktop;

/**
 *
 */
public class PoELauncher
{
    public static void run() throws IOException, URISyntaxException
    {
        if (PALdata.settings.getPref_version().equals(""))
        {
            PopupFactory.showError(1);
            return;
        }

        pre_launch_addons();
        launch_poe(PALdata.settings.getPref_version());
        cleanUp();
    }

    private static void launch_steam_poe() throws IOException, URISyntaxException
    {
        Desktop desktop = getDesktop();
        URI steamProtocol = new URI("steam://run/238960");
        desktop.browse(steamProtocol);
    }

    private static void launch_poe(String exe)
    {
        if (exe.contains("PathOfExileSteam.exe") || exe.contains("PathOfExile_x64Steam.exe"))
        {
            try
            {
                launch_steam_poe();
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
        else if (exe.contains("PathOfExile.exe") || exe.contains("PathOfExile_x64.exe"))
        {
            String dir;
            String executable;
            if (exe.contains("PathOfExile_x64.exe"))
            {
                executable = "PathOfExile_x64.exe";
                dir = exe.replace(executable, "");
            }
            else
            {
                executable = "PathOfExile.exe";
                dir = exe.replace(executable, "");
            }
            try
            {
                Runtime.getRuntime().exec(exe, null, new File(dir));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
    }

    private static void pre_launch_addons()
    {
        // Get list of addons to launch from JSON.
        String local = System.getenv("LOCALAPPDATA");
        File f = new File(local + File.separator + "PAL" + File.separator + "l_addons.pal");
        if (f.exists())
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try
            {
                String[] array = objectMapper.readValue(f, String[].class);
                for (String s : array)
                {
                    ProgramLauncher.launch(s);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        // Launch Custom AHK Scripts.
        ObservableList<CustomAHK> customAHKS = UserSettings.getCustomAHKS();
        for (CustomAHK c : customAHKS)
        {
            ProgramLauncher.launchAHKscript(c.getLocation());
        }
    }

    public static void cleanUp()
    {
        Uninstaller.recursiveDelete(new File(UserSettings.getPathAddons() + File.separator + "temp"));
    }

}
