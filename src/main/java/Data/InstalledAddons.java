package Data;

import API.FilterBlast.FilterBlastFilter;
import GUI.Tables.FilterTable;
import GUI.Tables.InstalledTable;
import GUI.Tables.InstalledTableRow;
import IO.InstalledFilter;
import Repo.AddonJson;
import Repo.Addons;
import Repo.NewestVersion;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleStringProperty;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 */
public final class InstalledAddons
{
    private static final InstalledAddons INSTANCE = new InstalledAddons();
    private ArrayList<InstalledTableRow> list_of_installed_addons = new ArrayList<>();

    private InstalledAddons()
    {}

    public void scanForInstalledAddons()
    {
        File addon_dir = new File(UserSettings.getPathAddons());
        if (addon_dir.exists())
        {
            File[] listFiles = addon_dir.listFiles();
            assert listFiles != null;
            for (File f : listFiles)
            {
                String addon_name = f.getName();
                if (!addon_name.startsWith("BK_"))
                {
                    if (!addon_name.equals("temp"))
                    {
                        //Valid addon
                        AddonJson local_addon = IniHandler.getInstallInfo(new File(f.getPath() + File.separator + "nfo.pal"));
                        AddonJson up_to_date_addon = Addons.getINSTANCe().getAddonByName(f.getName());

                        String status;
                        if (NewestVersion.isNewer(local_addon.getVersion(), up_to_date_addon.getVersion()))
                        {
                            status = "New Update";
                        }
                        else
                        {
                            status = "Up To Date";
                        }

                        list_of_installed_addons.add(
                                new InstalledTableRow(new SimpleStringProperty("A"),
                                        new SimpleStringProperty(f.getName()),
                                new SimpleStringProperty(status),
                                new SimpleStringProperty(local_addon.getVersion()),
                                new SimpleStringProperty(up_to_date_addon.getVersion()),
                                new SimpleStringProperty(local_addon.getCreators()),
                                new SimpleStringProperty(local_addon.getSource())));
                    }
                }
            }
        }
        else
        {
            System.err.println("NO ADDON DIRECTORY HAS BEEN SET!");
        }
        checkForInstalledFilters();
    }

    public void updateAddonInfo(InstalledTableRow installedTableRow)
    {
        InstalledTableRow outdated_itr = getByName(installedTableRow.getName());
        assert outdated_itr != null;
        list_of_installed_addons.remove(outdated_itr);
        list_of_installed_addons.add(installedTableRow);
    }

    public void removeAddon(InstalledTableRow row)
    {
        list_of_installed_addons.remove(row);
    }

    public void checkForInstalledFilters()
    {
        File f = new File(UserSettings.getLootFilter() + File.separator + "filters.pal");

        if (f.exists())
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try
            {
                InstalledFilter[] array = objectMapper.readValue(f, InstalledFilter[].class);
                for (InstalledFilter i : array)
                {
                    //System.out.println(i.getVersion());
                    //System.out.println(i.getKey());
                    try
                    {
                        InstalledTableRow installedtableRow = FilterTable.getINSTANCE().getFilterByPresetName(i.getKey()).convertToInstallTableRow();
                        installedtableRow.setName(i.getKey());
                        installedtableRow.setVersion(i.getVersion());
                        list_of_installed_addons.add(installedtableRow);
                    }
                    catch (NullPointerException ex)
                    {
                        ex.printStackTrace();
                    }

                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Deprecated
    public void _checkForInstalledFilters()
    {
        File PoE_Filter_Dir = new File(UserSettings.getLootFilter());
        if (PoE_Filter_Dir.exists())
        {
            File[] filters = PoE_Filter_Dir.listFiles();

            ArrayList<String> presets_list = presetsList();
            for (File f : filters)
            {
                if (!f.isDirectory())
                {
                    l:for (String s : presets_list)
                    {
                        if (f.getName().contains(s))
                        {

                            InstalledTableRow installedtableRow = FilterTable.getINSTANCE().getFilterByPresetName(s).convertToInstallTableRow();
                            installedtableRow.setName(s);
                            presets_list.remove(s);
                            list_of_installed_addons.add(installedtableRow);
                            break l;
                        }
                    }
                }
            }
        }
    }

    public void saveInstalledFiltersJSON()
    {
        File f = new File(UserSettings.getLootFilter() + File.separator + "filters.pal");
        if (f.exists())
        {
            f.delete();
        }

        ArrayList<InstalledFilter> list = new ArrayList<>();
        for (InstalledTableRow i : list_of_installed_addons)
        {
            if (i.getType().equals("F"))
            {
                list.add(i.toFilter());
            }
        }

        InstalledFilter[] array = new InstalledFilter[list.size()];
        for (int c = 0; c < array.length; c++)
        {
            array[c] = list.get(c);
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

    public ArrayList<String> presetsList()
    {
        ArrayList<FilterBlastFilter> filterBlastFilterArrayList = FilterTable.getINSTANCE().getFilterBlastFilters();
        ArrayList<String> data = new ArrayList<>();

        for (FilterBlastFilter f : filterBlastFilterArrayList)
        {
            data.addAll(f.getPresets());
        }
        return data;
    }

    public InstalledTableRow getByName(String name)
    {
        for (InstalledTableRow i : list_of_installed_addons)
        {
            if (i.getName().equals(name))
            {
                return i;
            }
        }
        return null;
    }

    public static InstalledAddons getINSTANCE()
    {
        return INSTANCE;
    }

    public ArrayList<InstalledTableRow> getList_of_installed_addons()
    {
        return list_of_installed_addons;
    }
}
