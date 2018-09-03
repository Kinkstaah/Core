package IO;

/**
 *
 */
public class InstalledFilter
{
    private String key;
    private String version;

    private InstalledFilter()
    {}

    public InstalledFilter(String key, String version)
    {
        this.key = key;
        this.version = version;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }
}
