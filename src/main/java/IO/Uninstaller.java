package IO;

import Data.InstalledAddons;
import Data.UserSettings;
import GUI.PopUp.PopupFactory;
import GUI.Tables.InstalledTable;
import GUI.Tables.InstalledTableRow;
import Repo.Addons;

import java.io.File;
import java.util.Objects;

/**
 *
 */
public class Uninstaller
{
    public static void unninstall(String name)
    {
        InstalledTableRow i = Addons.getINSTANCe().getAddonByName(name).convertToInstallTableRow();
        i.setStatus("Removing");
        InstalledAddons.getINSTANCE().updateAddonInfo(i);
        UserSettings.update_request = true;
        if (name.equals("CurrencyCop"))
        {
            //Local folder
        }
        recursiveDelete(new File(UserSettings.getPathAddons() + File.separator + name));

        InstalledAddons.getINSTANCE().removeAddon(i);
        UserSettings.update_request = true;
    }

    public static void recursiveDelete(File f)
    {
        if (f.isDirectory())
        {
            if (Objects.requireNonNull(f.list()).length == 0)
            {
                f.delete();
                System.out.println("[DELETED] " + f.getPath());
            }
            else
            {
                File[] files = f.listFiles();

                for (File _f : files)
                {
                    recursiveDelete(_f);
                }

                if (Objects.requireNonNull(f.list()).length == 0)
                {
                    f.delete();
                    System.out.println("[DELETED] " + f.getPath());
                }
            }
        }
        else
        {
            f.delete();
            System.out.println("[DELETED] " + f.getPath());
        }
    }

    public static void removeFilter(InstalledTableRow i)
    {
        File f = new File(UserSettings.getLootFilter() + File.separator + i.getName() + ".filter");
        f.delete();

        InstalledAddons.getINSTANCE().removeAddon(i);
        UserSettings.update_request = true;
    }
}
