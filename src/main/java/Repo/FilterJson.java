package Repo;

import java.util.Objects;

/**
 *
 */
public class FilterJson
{
    private String name;
    private String version;
    private String creators;
    private String gh_username;
    private String gh_repository;
    private String download_link;
    private String poe_version;
    private String description;
    private String icon_url;

    private FilterJson()
    {

    }

    public FilterJson(String name, String version, String creators, String gh_username, String gh_repository, String download_link, String poe_version, String description, String icon_url)
    {
        this.name = name;
        this.version = version;
        this.creators = creators;
        this.gh_username = gh_username;
        this.gh_repository = gh_repository;
        this.download_link = download_link;
        this.poe_version = poe_version;
        this.description = description;
        this.icon_url = icon_url;
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

    public String getGh_repository()
    {
        return gh_repository;
    }

    public void setGh_repository(String gh_repository)
    {
        this.gh_repository = gh_repository;
    }

    public String getDownload_link()
    {
        return download_link;
    }

    public void setDownload_link(String download_link)
    {
        this.download_link = download_link;
    }

    public String getPoe_version()
    {
        return poe_version;
    }

    public void setPoe_version(String poe_version)
    {
        this.poe_version = poe_version;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getIcon_url()
    {
        return icon_url;
    }

    public void setIcon_url(String icon_url)
    {
        this.icon_url = icon_url;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilterJson that = (FilterJson) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(version, that.version) &&
                Objects.equals(creators, that.creators) &&
                Objects.equals(gh_username, that.gh_username) &&
                Objects.equals(gh_repository, that.gh_repository) &&
                Objects.equals(download_link, that.download_link) &&
                Objects.equals(poe_version, that.poe_version) &&
                Objects.equals(description, that.description) &&
                Objects.equals(icon_url, that.icon_url);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, version, creators, gh_username, gh_repository, download_link, poe_version, description, icon_url);
    }
}
