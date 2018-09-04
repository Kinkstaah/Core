package IO;

import API.FilterBlast.FilterBlast;
import API.FilterBlast.FilterBlastFilter;
import Data.InstalledAddons;
import Data.UserSettings;
import GUI.PopUp.PopupFactory;
import GUI.Tables.FilterTable;
import GUI.Tables.InstalledTableRow;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 */
public final class FilterDownloader
{
    public static void downloadPreset(String preset) throws IOException
    {
        ActiveDownload.add(preset);
        FilterBlastFilter f = FilterTable.getINSTANCE().getFilterByPresetName(preset);

        // Download every preset.
        String str = "http://filterblast.oversoul.xyz/api/FilterFile/?filter=%k&preset=%p";
        str = str.replace("%k", f.getKey());
        str = str.replace("%p", preset);
        str = str.replace(" ", "%20");

        //System.out.println(str);

        StringBuilder filter_filename = new StringBuilder();
        filter_filename.append(preset);
        filter_filename.append(".filter");

        fileHandler(filter_filename.toString());

        URL url = new URL(str);

        HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
        httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");

        InputStream inputStream = httpcon.getInputStream();
        Files.copy(inputStream, Paths.get(UserSettings.getLootFilter() + File.separator + filter_filename.toString()));
        InstalledTableRow i = f.convertToInstallTableRow();
        i.setName(preset);
        InstalledAddons.getINSTANCE().updateAddonInfo(i);
        ActiveDownload.remove(preset);
    }

    /**
     * Downloads all presets from given name.
     * @param name
     * @throws IOException
     */
    public static void download(String name) throws IOException
    {
        ActiveDownload.add(name);
        FilterBlastFilter f = FilterTable.getINSTANCE().getFilterByName(name);

        // Download every preset.
        for (String preset : f.getPresets())
        {
            String str = "http://filterblast.oversoul.xyz/api/FilterFile/?filter=%k&preset=%p";
            str = str.replace("%k", f.getKey());
            str = str.replace("%p", preset);
            str = str.replace(" ", "%20");

            //System.out.println(str);

            StringBuilder filter_filename = new StringBuilder();
            filter_filename.append(preset);
            filter_filename.append(".filter");

            fileHandler(filter_filename.toString());

            URL url = new URL(str);

            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            httpcon.addRequestProperty("User-Agent", "Mozilla/4.0");

            InputStream inputStream = httpcon.getInputStream();
            Files.copy(inputStream, Paths.get(UserSettings.getLootFilter() + File.separator + filter_filename.toString()));
            InstalledAddons.getINSTANCE().updateAddonInfo(f.convertToInstallTableRow());
            ActiveDownload.remove(name);
        }
    }

    /**
     * Removes existing filter.
     * @param filename
     */
    public static void fileHandler(String filename)
    {
        File f = new File(UserSettings.getLootFilter() + File.separator + filename);
        if (f.exists())
        {
            f.delete();
        }
    }
}
