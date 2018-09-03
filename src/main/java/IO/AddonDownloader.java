package IO;

import Data.InstalledAddons;
import Data.UserSettings;
import GUI.Tables.InstalledTableRow;
import Repo.AddonJson;
import Repo.Addons;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 */
public final class AddonDownloader
{
    private static final AddonDownloader INSTANCE = new AddonDownloader();

    private AddonDownloader()
    {

    }

    public static void downloadAddon(String name) throws MalformedURLException
    {
        ActiveDownload.add(name);
        InstalledTableRow installedTableRow = Addons.getINSTANCe().getAddonByName(name).convertToInstallTableRow();
        installedTableRow.setStatus("Downloading");
        InstalledAddons.getINSTANCE().updateAddonInfo(installedTableRow);


        AddonJson addon = Addons.getINSTANCe().getAddonByName(name);
        URL url = new URL(addon.getDownload_url());

        // If addon is already intalled make a backup of current.
        File addon_name_dir = new File(UserSettings.getPathAddons() + File.separator + name);

        if (addon_name_dir.exists())
        {
            File addon_dir_backup = new File(UserSettings.getPathAddons() + "/BK_" + name);

            // create backup
            try
            {
                FileUtils.copyDirectory(addon_name_dir, addon_dir_backup);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        addon_name_dir.mkdir();

        // Find out where to download it to.
        InstallHandler installHandler = new InstallHandler(addon);
        installHandler.handle();


        ActiveDownload.remove(name);
    }
}
