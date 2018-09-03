package API.Github;

import Data.UserSettings;
import Repo.AddonJson;
import Repo.NewestVersion;
import org.kohsuke.github.GHRateLimit;
import org.kohsuke.github.GHRelease;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
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
            System.out.println("NEW VERSION " + version);
            addon.setVersion(version);
            addon.setSource("Github API");
            addon.setDownload_url(latest.getAssets().get(0).getBrowserDownloadUrl());
            return true;
        }
        return false;
    }

    public static GitHub connect() throws IOException
    {
        GitHub gitHub;
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
        System.out.println("Remaining requests: " + github.getRateLimit().remaining);
        System.out.println("Resets at: " + d);
        return github.getRateLimit();
    }

}
