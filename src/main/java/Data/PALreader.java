package Data;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;

import static Data.PALdata.LOCAL_PAL_FOLDER;


/**
 * Reads various PAL files.
 */
public final class PALreader
{
    private static final PALreader INSTANCE = new PALreader();

    private PALreader()
    {}

    public ObservableList<String> readPoePaths()
    {
        ObservableList<String> poe_paths = FXCollections.observableArrayList();
        String[] poe_paths_array;

        File poe_paths_pal = new File(LOCAL_PAL_FOLDER + File.separator + "poe_paths.pal");
        if (poe_paths_pal.exists())
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try
            {
                poe_paths_array = objectMapper.readValue(poe_paths_pal, String[].class);
                for (String s : poe_paths_array)
                {
                    poe_paths.add(s);
                }
                return poe_paths;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        return poe_paths;
    }

    public static boolean readCoreSettings()
    {
        File core_settings_pal = new File(LOCAL_PAL_FOLDER + File.separator + "core_settings.pal");

        PALsettings palsettings = new PALsettings(false, "", true, "", true, false, "", "", false, false);

        if (core_settings_pal.exists())
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try
            {
                palsettings = objectMapper.readValue(core_settings_pal, PALsettings.class);
                PALdata.settings = palsettings;
                UserSettings.sync();
                return true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        PALdata.settings = palsettings;
        UserSettings.sync();
        return false;
    }

    /**
     * Read launcher data.
     */
    public static boolean readLauncherData()
    {
        File f = new File(LOCAL_PAL_FOLDER + File.separator + "launcher_data.pal");
        if (f.exists())
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try
            {
                PALdata.launcher_data = objectMapper.readValue(f, Launcher_Data.class);
                return true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static PALreader getINSTANCE()
    {
        return INSTANCE;
    }
}
