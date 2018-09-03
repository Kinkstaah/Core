package Data;

import Repo.AddonJson;
import Repo.Addons;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * TODO: Seperate Repositories file.
 */
public class IniHandler
{
    public static void writeProperties()
    {
        Properties pal_settings = new Properties();
        OutputStream out = null;

        try
        {
            out = new FileOutputStream(".settings");
            pal_settings.setProperty("addon_folder", UserSettings.getPathAddons());
            pal_settings.setProperty("version_folder", UserSettings.getPathInstalldir());
            pal_settings.setProperty("steam", UserSettings.getPoeSteam());
            pal_settings.setProperty("standalone", UserSettings.getPoePath());
            pal_settings.setProperty("beta", UserSettings.getPoeBeta());
            pal_settings.setProperty("loot_filter", UserSettings.getLootFilter());
            pal_settings.setProperty("filterblast_api_enabled", UserSettings.isFilterblastApiEnabled() + "");
            pal_settings.setProperty("github_api_enabled", UserSettings.isGithubApiEnabled() + "");
            pal_settings.setProperty("github_api_token_enabled", UserSettings.isGithubApiTokenEnabled() + "");
            pal_settings.setProperty("github_token", UserSettings.getGithub_API_Token());
            if (UserSettings.getAhkPath() != null)
            {
                pal_settings.setProperty("AHK", UserSettings.getAhkPath());
            }
            pal_settings.setProperty("down_on_launch", UserSettings.isDownloadAllUpdatesOnPalLaunch() + "");
            pal_settings.setProperty("run_poe_on_launch", UserSettings.isLaunchPoeOnPalLaunch() + "");
            pal_settings.setProperty("wait_for_updates", UserSettings.isWaitForUpdatesAndLaunch() + "");
            pal_settings.setProperty("pref_version", UserSettings.getPoeVersionToLaunch());
            pal_settings.store(out, "Only change this if you know what you are doing!");

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            if (out != null)
            {
                try
                {
                    out.close();
                }
                catch (IOException ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void readProperties()
    {
        if (!checkProperties())
        {
            return;
        }
        Properties pal_settings = new Properties();
        InputStream in = null;

        try
        {
            in = new FileInputStream(".settings");
            pal_settings.load(in);
            UserSettings.setPathAddons(pal_settings.getProperty("addon_folder"));
            UserSettings.setPathInstalldir(pal_settings.getProperty("version_folder"));
            UserSettings.setPoeSteam(pal_settings.getProperty("steam"));
            UserSettings.setPoePath(pal_settings.getProperty("standalone"));
            UserSettings.setPoeBeta(pal_settings.getProperty("beta"));
            UserSettings.setLootFilter(pal_settings.getProperty("loot_filter"));
            UserSettings.setAhkPath(pal_settings.getProperty("AHK"));
            UserSettings.setGithub_API_Token(pal_settings.getProperty("github_token"));
            UserSettings.setPoeVersionToLaunch(pal_settings.getProperty("pref_version"));

            // Booleans
            UserSettings.setFilterblastApiEnabled(Boolean.parseBoolean(pal_settings.getProperty("filterblast_api_enabled")));
            UserSettings.setGithubApiEnabled(Boolean.parseBoolean(pal_settings.getProperty("github_api_enabled")));
            UserSettings.setGithubApiTokenEnabled(Boolean.parseBoolean(pal_settings.getProperty("github_api_token_enabled")));
            UserSettings.setDownloadAllUpdatesOnPalLaunch(Boolean.parseBoolean(pal_settings.getProperty("down_on_launch")));
            UserSettings.setLaunchPoeOnPalLaunch(Boolean.parseBoolean(pal_settings.getProperty("run_poe_on_launch")));
            UserSettings.setWaitForUpdatesAndLaunch(Boolean.parseBoolean(pal_settings.getProperty("wait_for_updates")));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }

    public static AddonJson getInstallInfo(File f)
    {
        Properties installed_info = new Properties();
        InputStream in = null;

        try
        {
            in = new FileInputStream(f);
            installed_info.load(in);
            AddonJson addonJson = new AddonJson
                    (
                            installed_info.getProperty("name"),
                            installed_info.getProperty("version"),
                            installed_info.getProperty("creators"),
                            installed_info.getProperty("gh_username"),
                            installed_info.getProperty("gh_repo"),
                            installed_info.getProperty("download_url"),
                            installed_info.getProperty("description"),
                            installed_info.getProperty("file_launch"),
                            installed_info.getProperty("language"),
                            installed_info.getProperty("source")
                    );
            return addonJson;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static boolean checkProperties()
    {
        File f = new File(".settings");
        return f.exists();
    }
}
