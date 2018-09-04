package IO;

import Data.InstalledAddons;
import Data.UserSettings;
import GUI.Tables.InstalledTableRow;
import Repo.AddonJson;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 *
 */
public class InstallHandler
{
    private AddonJson addon;

    public InstallHandler(AddonJson addon)
    {
        this.addon = addon;
    }

    public AddonJson getAddon()
    {
        return addon;
    }

    public void setAddon(AddonJson addon)
    {
        this.addon = addon;
    }

    public void installFilter()
    {

    }

    public void handle()
    {
        try
        {
            genericDownloader();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        /*
        String name = addon.getName();
        switch (name)
        {
            case "MercuryTrade" : MT(); break;
            case "POE TradeMacro" : PT(); break;
            case "Path of Building" : PB(); break;
            case "LabCompass" : LC(); break;
            case "CurrencyCop" : CC(); break;
            default: genericDownloader(); break;
        }*/
    }

    /**
     * Unfortunatelly Currency Cop installs to your appdata by default.
     */
    private void installCurrencyCop(String installer_location)
    {
        // Run installer
        Runtime runtime = Runtime.getRuntime();
        try
        {
            runtime.exec(installer_location);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Generic Downloader will attempt to handle the downloads of any other types.
     * - if file is a .zip it will unzip it and place it and try to find an .exe / .jar / .ahk to launch.
     *   > It will then attempt to launch using the launch_command.
     * - if file is an .exe / .jar / .ahk it will attempt to launch it.
     */
    private void genericDownloader() throws IOException
    {
        InstalledTableRow installedTableRow = addon.convertToInstallTableRow();
        installedTableRow.setStatus("Installing");
        InstalledAddons.getINSTANCE().updateAddonInfo(installedTableRow);
        InputStream inputStream = URI.create(addon.getDownload_url()).toURL().openStream();

        String[] url_parts = addon.getDownload_url().split("/");
        String file_name = url_parts[url_parts.length-1];
        // Create a temp_download_folder
        File f = new File(UserSettings.getPathAddons() + "/temp");
        if (!f.exists())
        {
            f.mkdir();
        }

        File download_file = new File(f.getPath() + File.separator + file_name);

        if (download_file.exists())
        {
            download_file.delete();
        }

        Files.copy(inputStream, Paths.get(f.getPath() + File.separator + file_name));

        String[] file_path_parts = file_name.split("\\.");
        String ext = file_path_parts[file_path_parts.length-1];

        if (addon.getName().equals("CurrencyCop"))
        {
            installCurrencyCop(f.getPath() + File.separator + file_name);
        }
        else if (ext.equals("zip") || ext.equals("rar") || ext.equals("7z"))
        {
            unzip(f.getPath() + File.separator + file_name, UserSettings.getPathAddons() + File.separator + addon.getName());
        }
        else
        {
            executableHandler(file_name, ext);
        }
        installedTableRow = addon.convertToInstallTableRow();
        installedTableRow.setStatus("Finalizing");
        InstalledAddons.getINSTANCE().updateAddonInfo(installedTableRow);
        createInfoFile();


        /*
        // Run the program once to ensure it's working. EXCEPT for CurrencyCop.
        if (!addon.getName().equals("CurrencyCop"))
        {
            ProgramLauncher.launch(addon.getName());
        }*/// TODO: Decided to not launch after downloading.
        installedTableRow = addon.convertToInstallTableRow();
        installedTableRow.setStatus("Up To Date");
        InstalledAddons.getINSTANCE().updateAddonInfo(installedTableRow);
        UserSettings.update_request = true;
    }

    /**
     * Move file and it's installed... probably...
     * Sometimes it includes an installer.
     */
    private void executableHandler(String filename, String ext) throws IOException
    {
        //Move to correct Dir.
        File install_file = new File(UserSettings.getPathAddons() + File.separator + addon.getName() + File.separator + filename);
        File temp_file = new File(UserSettings.getPathAddons() + "/temp/" + filename);
        FileUtils.copyFile(temp_file, install_file);
    }

    private void runInstaller()
    {

    }

    private void createInfoFile()
    {
        Properties pal_settings = new Properties();
        OutputStream out = null;

        try
        {
            out = new FileOutputStream(UserSettings.getPathAddons() + File.separator + addon.getName() + File.separator + "nfo.pal");
            pal_settings.setProperty("name", addon.getName());
            pal_settings.setProperty("version", addon.getVersion());
            pal_settings.setProperty("creators", addon.getCreators());
            pal_settings.setProperty("gh_username", addon.getGh_username());
            pal_settings.setProperty("gh_repo", addon.getGh_reponame());
            pal_settings.setProperty("download_url", addon.getDownload_url());
            pal_settings.setProperty("description", addon.getDescription());
            pal_settings.setProperty("file_launch", addon.getFile_launch());
            pal_settings.setProperty("language", addon.getProgramming_language());
            pal_settings.setProperty("source", addon.getSource());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            pal_settings.setProperty("date", dateFormat.format(date));
            pal_settings.store(out, "DO NOT CHANGE THIS FILE");

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


    private static void unzip(String zipFilePath, String destDir)
    {
        try
        {
            ZipFile zipFile = new ZipFile(zipFilePath);
            zipFile.extractAll(destDir);
        }
        catch (ZipException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Handles PoE Trade Macro
     */
    private void PT()
    {
    }

    /**
     * Handles Mercury Trade
     */
    private void MT()
    {
    }

    /**
     * Handles Path of Building.
     */
    private void PB()
    {
    }

    /**
     * Lab Compass Handler
     */
    private void LC()
    {
    }

    /**
     * CurrencyCop
     */
    private void CC()
    {

    }
}
