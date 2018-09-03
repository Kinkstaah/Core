package IO;

import java.util.ArrayList;

/**
 *
 */
public class ActiveDownload
{
    public static ArrayList<String> activeDownload = new ArrayList<>();

    public static void add(String download)
    {
        activeDownload.add(download);
    }

    public static void remove(String download)
    {
        activeDownload.remove(download);
    }

    public static ArrayList<String> getActiveDownload()
    {
        return activeDownload;
    }
}
