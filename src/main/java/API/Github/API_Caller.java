package API.Github;

import Data.PALdata;
import Data.PALreader;
import Data.UserSettings;
import IO.ActiveDownload;
import IO.InstallHandler;
import Repo.AddonJson;
import Repo.NewestVersion;
import Repo.Repository;
import org.apache.commons.io.FileUtils;
import org.kohsuke.github.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

/**
 *
 */
public class API_Caller
{
    public static boolean updateAvailable(AddonJson addon) throws IOException
    {
        GitHub gitHub = connect();
        GHRepository repo = gitHub.getRepository(addon.getGh_username() + "/" + addon.getGh_reponame());
        GHRelease latest = repo.getLatestRelease();
        String version = latest.getTagName().replaceAll("[^\\.0123456789]","");

        if (NewestVersion.isNewer(addon.getVersion(), version))
        {
            addon.setVersion(version);
            addon.setSource("Github API");
            addon.setDownload_url(latest.getAssets().get(0).getBrowserDownloadUrl());
            return true;
        }
        return false;
    }

    public static GitHub connect() throws IOException
    {
        if (UserSettings.isGithubApiTokenEnabled())
        {
            return GitHub.connectUsingOAuth(UserSettings.getGithub_API_Token());
        }
        else
        {
            return GitHub.connectAnonymously();
        }
    }

    public static GHRateLimit requestsLeft() throws IOException
    {
        GitHub github = connect();
        Date d = new Date(github.getRateLimit().reset.getTime() * 1000);
        //System.out.println("Remaining requests: " + github.getRateLimit().remaining);
        //System.out.println("Resets at: " + d);
        return github.getRateLimit();
    }

    //TODO: Further Testing.
    public static void PAL_Launcher_Update_Checker()
    {
        try
        {
            GitHub gitHub = API_Caller.connect();
            if (gitHub.getRateLimit().remaining > 0)
            {
                GHRepository ghRepository = gitHub.getRepository("POE-Addon-Launcher/PoE-Addon-Launcher");

                String version = PALdata.launcher_data.getVersion().replaceAll("[^\\.0123456789]","");
                GHRelease latest = ghRepository.getLatestRelease();
                String gh_version = latest.getTagName().replaceAll("[^\\.0123456789]","");
                Double v = Double.parseDouble(version);
                Double gh_v = Double.parseDouble(gh_version);
                PALreader.readLauncherData();
                if (gh_v > v)
                {

                    // Download update.
                    File f = new File(PALdata.launcher_data.getLauncher_location() + "PoE.Addon.Launcher.v" + gh_v + ".jar");
                    if (f.exists())
                    {
                        f.delete();
                        URL url = new URL(latest.getAssets().get(0).getBrowserDownloadUrl());
                        InputStream in = url.openStream();
                        Files.copy(in, Paths.get(f.getPath()));
                    }

                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
