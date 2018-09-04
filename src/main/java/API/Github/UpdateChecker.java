package API.Github;

import Data.InstalledAddons;
import Data.UserSettings;
import GUI.Tables.InstalledTableRow;
import Repo.AddonJson;
import Repo.Addons;
import org.kohsuke.github.GHRateLimit;

import java.io.IOException;
import java.util.ArrayList;

/**
 *
 */
public class UpdateChecker
{
    // Check every minute for updates.
    public static void startChecking()
    {
        Runnable r = () ->
        {
            boolean firstrun = true;
            while (true)
            {
                if (!firstrun)
                {
                    try
                    {
                        // Check every 30 seconds.
                        Thread.sleep(30000l);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
                firstrun = false;
                // Update whenever we have requests available.
                try
                {
                    GHRateLimit requests = API_Caller.requestsLeft();
                    if (requests.remaining != 0)
                    {
                        boolean found_update = false;
                        ArrayList<String> update_available_addons = new ArrayList<>();
                        ArrayList<AddonJson> addons = Addons.getINSTANCe().getAddons();
                        for (AddonJson a : addons)
                        {
                            try
                            {
                                if (API_Caller.updateAvailable(a))
                                {
                                    found_update = true;
                                    update_available_addons.add(a.getName());
                                }
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }
                        }
                        if (found_update)
                        {
                            // Update Installed Table that there is an update available.
                            for (String s : update_available_addons)
                            {
                                AddonJson addon_info = Addons.getINSTANCe().getAddonByName(s);
                                InstalledTableRow i = InstalledAddons.getINSTANCE().getByName(s);
                                if (i == null)
                                    break;
                                i.setStatus("New Update");
                                assert addon_info != null;
                                i.setLatest_version(addon_info.getVersion());
                                i.setSource("Github API");
                            }
                        }
                    }
                    else
                    {
                        // Wait until we can run again.
                        long remaining_mili = (requests.reset.getTime() * 1000) - System.currentTimeMillis();
                        Thread.sleep(remaining_mili);
                    }
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }
}
