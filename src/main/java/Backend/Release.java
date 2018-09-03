package Backend;

import java.util.Objects;

/**
 * Object that holds relevant info from the github api.
 */
public class Release
{
    private String name = "";
    private String version = "";
    private String download_url = "";
    private int num = 0;
    private String creators = "";

    public Release(String name, String version, String download_url, int num)
    {
        this.name = name;
        this.version = version;
        this.download_url = download_url;
        this.num = num;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getDownload_url()
    {
        return download_url;
    }

    public void setDownload_url(String download_url)
    {
        this.download_url = download_url;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(name);
        stringBuilder.append(" | ");
        stringBuilder.append(version);
        stringBuilder.append(" | ");
        stringBuilder.append(download_url);
        return stringBuilder.toString();
    }

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Release release = (Release) o;
        return num == release.num &&
                Objects.equals(name, release.name) &&
                Objects.equals(version, release.version) &&
                Objects.equals(download_url, release.download_url);
    }

    public String getCreators()
    {
        return creators;
    }

    public void setCreators(String creators)
    {
        this.creators = creators;
    }
}
