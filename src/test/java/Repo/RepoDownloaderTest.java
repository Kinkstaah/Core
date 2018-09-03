package Repo;

import Data.IniHandler;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

/**
 *
 */
public class RepoDownloaderTest
{
    @Test
    public void testDownload() throws IOException
    {
        IniHandler.readProperties();
        Repository r = new Repository("Official Curated PAL Repository",
                new URL("https://raw.githubusercontent.com/POE-Addon-Launcher/Curated-Repo/master/addons.json"),
                new URL("https://raw.githubusercontent.com/POE-Addon-Launcher/Curated-Repo/master/filters.json"));
        Repository r2 = new Repository("Community PAL Repository",
                new URL("https://raw.githubusercontent.com/POE-Addon-Launcher/Community-Repo/master/addons.json"),
                new URL("https://raw.githubusercontent.com/POE-Addon-Launcher/Community-Repo/master/filters.json"));
        //RepoDownloader.downloadRepo(r);
    }
}