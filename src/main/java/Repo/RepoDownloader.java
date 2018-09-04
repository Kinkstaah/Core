package Repo;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;

/**
 * Get Json arrays.
 */
public class RepoDownloader
{
    public static AddonJson[] downloadAddonRepo(URL url) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(url, AddonJson[].class);
    }

    public static FilterJson[] downloadFilterRepo(URL url) throws IOException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(url, FilterJson[].class);
    }

    public static boolean isValidRepoURL(URL url, boolean isAddonJson)
    {
        try
        {
            ObjectMapper objectMapper = new ObjectMapper();
            if (isAddonJson)
            {
                objectMapper.readValue(url, AddonJson[].class);
            }
            else
            {
                objectMapper.readValue(url, FilterJson[].class);
            }
        }
        catch (Exception ex)
        {
            return false;
        }

        return true;
    }
}
