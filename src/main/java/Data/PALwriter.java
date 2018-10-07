package Data;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.ObservableList;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.Properties;

/**
 *
 */
public final class PALwriter
{
    private static final PALwriter INSTANCE = new PALwriter();

    private PALwriter()
    {}

    public void saveSettings()
    {
        File core_settings_pal = new File(UserSettings.LOCAL_PAL_FOLDER + File.separator + "core_settings.pal");
        if (core_settings_pal.exists())
        {
            core_settings_pal.delete();
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try
        {
            objectMapper.writeValue(core_settings_pal, PALdata.settings);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void savePoEpaths()
    {
        File f = new File(UserSettings.LOCAL_PAL_FOLDER + File.separator + "poe_paths.pal");
        if (f.exists())
        {
            f.delete();
        }

        //TODO: Get list of PoE Paths.
        ObservableList<String> poe_paths = UserSettings.PoE_Paths;

        String[] array = new String[poe_paths.size()];
        for (int c = 0; c < array.length; c++)
        {
            array[c] = poe_paths.get(c);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        if (array.length > 0)
        {
            try
            {
                objectMapper.writeValue(f, array);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public boolean importDotSettings()
    {
        return readProperties();
    }

    public static boolean readProperties()
    {
        Properties pal_settings = new Properties();
        InputStream in = null;

        try
        {
            JFileChooser jFileChooser = new JFileChooser();
            jFileChooser.showOpenDialog(new JPanel());

            File selected = jFileChooser.getSelectedFile();
            if (selected == null)
                return true;

            if (!selected.getName().equals(".settings"))
                return false;

            in = new FileInputStream(selected);

            pal_settings.load(in);
            //UserSettings.setPathAddons(pal_settings.getProperty("addon_folder"));
            //UserSettings.setPathInstalldir(pal_settings.getProperty("version_folder"));
            //UserSettings.setPoeSteam(pal_settings.getProperty("steam"));
            //UserSettings.setPoePath(pal_settings.getProperty("standalone"));
            //UserSettings.setPoeBeta(pal_settings.getProperty("beta"));
            UserSettings.setLootFilter(pal_settings.getProperty("loot_filter"));
            UserSettings.setAhkPath(pal_settings.getProperty("AHK"));
            UserSettings.setGithub_API_Token(pal_settings.getProperty("github_token"));
            //UserSettings.setPoeVersionToLaunch(pal_settings.getProperty("pref_version"));

            // Booleans
            UserSettings.setFilterblastApiEnabled(Boolean.parseBoolean(pal_settings.getProperty("filterblast_api_enabled")));
            UserSettings.setGithubApiEnabled(Boolean.parseBoolean(pal_settings.getProperty("github_api_enabled")));
            UserSettings.setGithubApiTokenEnabled(Boolean.parseBoolean(pal_settings.getProperty("github_api_token_enabled")));
            UserSettings.setDownloadAllUpdatesOnPalLaunch(Boolean.parseBoolean(pal_settings.getProperty("down_on_launch")));
            //UserSettings.setLaunchPoeOnPalLaunch(Boolean.parseBoolean(pal_settings.getProperty("run_poe_on_launch")));
            UserSettings.setWaitForUpdatesAndLaunch(Boolean.parseBoolean(pal_settings.getProperty("wait_for_updates")));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
            return false;
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
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
        return true;
    }

    public static PALwriter getINSTANCE()
    {
        return INSTANCE;
    }
}
