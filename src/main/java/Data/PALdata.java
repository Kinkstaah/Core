package Data;

import java.io.File;

/**
 *
 */
public final class PALdata
{
    public static PALsettings settings = new PALsettings(false, "", true, "", true, false, "", "", false, false);;
    public static Launcher_Data launcher_data;
    public static final String LOCAL_PAL_FOLDER = System.getenv("LOCALAPPDATA") + File.separator + "PAL";


    public PALdata()
    {}
}
