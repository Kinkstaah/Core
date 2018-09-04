package Repo;

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
        if (UserSettings.getPoeVersionToLaunch() == null)
        {
            PopupFactory.showError(1);
            return;
        }

        pre_launch_addons();
        // Get PoE version prefered.
        switch (UserSettings.getPoeVersionToLaunch())
        {
            case "Steam":
                launch_steam_poe();
                break;
            case "Stand Alone":
                launch_poe(UserSettings.getPoePath() + File.separator + "Client.exe");
                break;
            case "Beta":
                launch_poe(UserSettings.getPoeBeta() + File.separator + "Client.exe");
                break;
            default:
                // Show Popup of no PoE Version set.
                break;
        }
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
        try
        {
            Runtime.getRuntime().exec(exe);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void pre_launch_addons()
    {
        // Get list of addons to launch from JSON.
        File f = new File(UserSettings.getPathInstalldir() + File.separator + "l_addons.pal");

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
