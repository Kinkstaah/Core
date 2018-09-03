package Repo;

import GUI.Tables.AddonTableRow;
import GUI.Tables.InstalledTableRow;
import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

/**
 *
 */
public class AddonJson
{
    private String name;
    private String version;
    private String creators;
    private String gh_username;
    private String gh_reponame;
    private String download_url;
    private String icon_url;
    private String description;
    private String file_launch;
    private String programming_language;
    private String source = "Local";
    private Long timestamp = 0L;

    private AddonJson()
    {

    }

    public AddonJson isNewerVersion(AddonJson other)
    {
        if (NewestVersion.isNewer(this.version, other.getVersion()))
        {
            return other;
        }
        return this;
    }

    @Deprecated
    public int sanitizeVersion(String in)
    {
        if (in.contains("-"))
        {
            in = in.split("-")[0];
        }
        in = in.replaceAll("[^\\d.]", "");
        return Integer.parseInt(in.replaceAll("\\.", ""));
    }

    public AddonTableRow convertToTableRow()
    {
        String last_update = "Today";
        return new AddonTableRow(new SimpleStringProperty(name),
                                 new SimpleStringProperty(version),
                                 new SimpleStringProperty(last_update),
                                 new SimpleStringProperty(source),
                                 new SimpleStringProperty(creators),
                                 new SimpleStringProperty(programming_language)
                );
    }

    public InstalledTableRow convertToInstallTableRow()
    {
        return new InstalledTableRow(new SimpleStringProperty("A"),
                new SimpleStringProperty(name),
                new SimpleStringProperty("Up To Date"),
                new SimpleStringProperty(version),
                new SimpleStringProperty(version),
                new SimpleStringProperty(creators),
                new SimpleStringProperty(source)
                );
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public Long getTimestamp()
    {
        return timestamp;
    }

    public void setTimestamp()
    {
        timestamp = System.currentTimeMillis();
    }

    public AddonJson(String name, String version, String creators, String gh_username, String gh_reponame, String download_url, String icon_url, String description, String file_launch, String programming_language)
    {
        this.name = name;
        this.version = version;
        this.creators = creators;
        this.gh_username = gh_username;
        this.gh_reponame = gh_reponame;
        this.download_url = download_url;
        this.icon_url = icon_url;
        this.description = description;
        this.file_launch = file_launch;
        this.programming_language = programming_language;
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

    public String getCreators()
    {
        return creators;
    }

    public void setCreators(String creators)
    {
        this.creators = creators;
    }

    public String getGh_username()
    {
        return gh_username;
    }

    public void setGh_username(String gh_username)
    {
        this.gh_username = gh_username;
    }

    public String getGh_reponame()
    {
        return gh_reponame;
    }

    public void setGh_reponame(String gh_reponame)
    {
        this.gh_reponame = gh_reponame;
    }

    public String getDownload_url()
    {
        return download_url;
    }

    public void setDownload_url(String download_url)
    {
        this.download_url = download_url;
    }

    public String getIcon_url()
    {
        return icon_url;
    }

    public void setIcon_url(String icon_url)
    {
        this.icon_url = icon_url;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getFile_launch()
    {
        return file_launch;
    }

    public void setFile_launch(String file_launch)
    {
        this.file_launch = file_launch;
    }

    public String getProgramming_language()
    {
        return programming_language;
    }

    public void setProgramming_language(String programming_language)
    {
        this.programming_language = programming_language;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AddonJson addonJson = (AddonJson) o;
        return Objects.equals(name, addonJson.name) &&
                Objects.equals(version, addonJson.version) &&
                Objects.equals(creators, addonJson.creators) &&
                Objects.equals(gh_username, addonJson.gh_username) &&
                Objects.equals(gh_reponame, addonJson.gh_reponame) &&
                Objects.equals(download_url, addonJson.download_url) &&
                Objects.equals(icon_url, addonJson.icon_url) &&
                Objects.equals(description, addonJson.description) &&
                Objects.equals(file_launch, addonJson.file_launch) &&
                Objects.equals(programming_language, addonJson.programming_language) &&
                Objects.equals(source, addonJson.source) &&
                Objects.equals(timestamp, addonJson.timestamp);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, version, creators, gh_username, gh_reponame, download_url, icon_url, description, file_launch, programming_language);
    }
}
