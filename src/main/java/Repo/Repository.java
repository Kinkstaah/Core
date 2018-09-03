package Repo;

import java.net.URL;
import java.util.Arrays;

/**
 *
 */
public class Repository
{
    private String name;
    private URL ADDON_JSON;
    private URL FILTER_JSON;

    private AddonJson[] addons;
    private FilterJson[] filters;

    public Repository(String name, URL ADDON_JSON, URL FILTER_JSON)
    {
        this.name = name;

        if (RepoDownloader.isValidRepoURL(ADDON_JSON, true))
        {
            this.ADDON_JSON = ADDON_JSON;
        }
        if (RepoDownloader.isValidRepoURL(FILTER_JSON, false))
        {
            this.FILTER_JSON = FILTER_JSON;
        }
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public URL getADDON_JSON()
    {
        return ADDON_JSON;
    }

    public void setADDON_JSON(URL ADDON_JSON)
    {
        this.ADDON_JSON = ADDON_JSON;
    }

    public URL getFILTER_JSON()
    {
        return FILTER_JSON;
    }

    public void setFILTER_JSON(URL FILTER_JSON)
    {
        this.FILTER_JSON = FILTER_JSON;
    }

    @Override
    public String toString()
    {
        return "Repository{" +
                "name='" + name + '\'' +
                ", ADDON_JSON=" + ADDON_JSON +
                ", FILTER_JSON=" + FILTER_JSON +
                ", addons=" + Arrays.toString(addons) +
                ", filters=" + Arrays.toString(filters) +
                '}';
    }
}
