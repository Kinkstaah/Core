package API.FilterBlast;

import GUI.Tables.FilterTableRow;
import GUI.Tables.InstalledTableRow;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;

/**
 *
 */
public class FilterBlastFilter
{
    private String name;
    private String version;
    private String LastUpdate;
    private String PoEVersion;
    private String ForumThread;
    private ArrayList<String> presets;
    private String key;

    public FilterBlastFilter(String name, String version, String lastUpdate, String poEVersion, String forumThread, ArrayList<String> presets)
    {
        this.name = name.replace("\"", "");
        this.version = version.replace("\"", "");
        LastUpdate = lastUpdate.replace("\"", "");
        LastUpdate = threeLetterMonths();

        PoEVersion = poEVersion.replace("\"", "");
        ForumThread = forumThread.replace("\"", "");
        this.presets = presets;
        key = findKey();
    }

    public InstalledTableRow convertToInstallTableRow()
    {
        return  new InstalledTableRow(new SimpleStringProperty("F"),
                new SimpleStringProperty(name),
                new SimpleStringProperty("Installed"),
                new SimpleStringProperty(version),
                new SimpleStringProperty(version),
                new SimpleStringProperty(key),
                new SimpleStringProperty("FilterBlast API"));
    }

    public FilterTableRow[] getPresetTables()
    {
        FilterTableRow[] data = new FilterTableRow[presets.size()];

        for (int c = 0; c < presets.size(); c++)
        {
            data[c] = new FilterTableRow(new SimpleStringProperty(presets.get(c)),
                    new SimpleStringProperty("Installed"),
                    new SimpleStringProperty(version),
                    new SimpleStringProperty(version),
                    new SimpleStringProperty("FilterBlast API"),
                    new SimpleStringProperty(key));
        }
        return data;
    }

    private String threeLetterMonths()
    {
        if (LastUpdate.contains("January"))
        {
            return LastUpdate.replace("January", "Jan");
        }
        else if (LastUpdate.contains("February"))
        {
            return LastUpdate.replace("February", "Feb");
        }
        else if (LastUpdate.contains("March"))
        {
            return LastUpdate.replace("March", "Mar");
        }
        else if (LastUpdate.contains("April"))
        {
            return LastUpdate.replace("April", "Apr");
        }
        else if (LastUpdate.contains("August"))
        {
            return LastUpdate.replace("August", "Aug");
        }
        else if (LastUpdate.contains("September"))
        {
            return LastUpdate.replace("September", "Sep");
        }
        else if (LastUpdate.contains("October"))
        {
            return LastUpdate.replace("October", "Oct");
        }
        else if (LastUpdate.contains("November"))
        {
            return LastUpdate.replace("November", "Nov");
        }
        else if (LastUpdate.contains("December"))
        {
            return LastUpdate.replace("December", "Dec");
        }
        return LastUpdate;
    }

    private String findKey()
    {
        if (name.split("'")[0].equals("Highwind"))
            return "ffhighwind";
        else if (name.split("'")[0].equals("Lumpa"))
            return "Lumpaa";
        else if (name.split("'")[0].equals("Dsgreat"))
            return "Dsgreat3";
        else if (name.split("'")[0].equals("Ment"))
            return "ment2008";
        else if (name.split("'")[0].equals("Vexi"))
            return "Vexivian";
        else if (name.equals("Sayk Loot Filters"))
            return "Sayk";
        else
            return name.split("'")[0];
    }

    public FilterTableRow converToTableRow()
    {
        return new FilterTableRow(new SimpleStringProperty(name),
                new SimpleStringProperty(version),
                new SimpleStringProperty(LastUpdate),
                new SimpleStringProperty(PoEVersion),
                new SimpleStringProperty("FilterBlast API"),
                new SimpleStringProperty(key));
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getLastUpdate()
    {
        return LastUpdate;
    }

    public void setLastUpdate(String lastUpdate)
    {
        LastUpdate = lastUpdate;
    }

    public String getPoEVersion()
    {
        return PoEVersion;
    }

    public void setPoEVersion(String poEVersion)
    {
        PoEVersion = poEVersion;
    }

    public String getForumThread()
    {
        return ForumThread;
    }

    public void setForumThread(String forumThread)
    {
        ForumThread = forumThread;
    }

    public ArrayList<String> getPresets()
    {
        return presets;
    }

    public void setPresets(ArrayList<String> presets)
    {
        this.presets = presets;
    }

    @Override
    public String toString()
    {
        return "FilterBlastFilter{" +
                "name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", LastUpdate='" + LastUpdate + '\'' +
                ", PoEVersion='" + PoEVersion + '\'' +
                ", ForumThread='" + ForumThread + '\'' +
                ", presets=" + presets +
                '}';
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }
}
