package GUI.Tables;

import IO.InstalledFilter;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.image.Image;

import java.util.Objects;

/**
 *
 */
public class InstalledTableRow
{
    private SimpleStringProperty type;
    private SimpleStringProperty name;
    private SimpleStringProperty status;
    private SimpleStringProperty version;
    private SimpleStringProperty latest_version;
    private SimpleStringProperty creators;
    private SimpleStringProperty source;

    public InstalledTableRow(SimpleStringProperty type, SimpleStringProperty name, SimpleStringProperty status, SimpleStringProperty version, SimpleStringProperty latest_version, SimpleStringProperty creators, SimpleStringProperty source)
    {
        this.type = type;
        this.name = name;
        this.status = status;
        this.version = version;
        this.latest_version = latest_version;
        this.creators = creators;
        this.source = source;
    }

    public InstalledFilter toFilter()
    {
        return new InstalledFilter(name.get(), version.get());
    }

    /*
    public InstalledTableRow(SimpleStringProperty name, SimpleStringProperty status, SimpleStringProperty version, SimpleStringProperty latest_version, SimpleStringProperty creators, SimpleStringProperty source)
    {
        this.name = name;
        this.status = status;
        this.version = version;
        this.latest_version = latest_version;
        this.creators = creators;
        this.source = source;
    }*/

    public String getName()
    {
        return name.get();
    }

    public SimpleStringProperty nameProperty()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name.set(name);
    }

    public String getStatus()
    {
        return status.get();
    }

    public SimpleStringProperty statusProperty()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status.set(status);
    }

    public String getVersion()
    {
        return version.get();
    }

    public SimpleStringProperty versionProperty()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version.set(version);
    }

    public String getLatest_version()
    {
        return latest_version.get();
    }

    public SimpleStringProperty latest_versionProperty()
    {
        return latest_version;
    }

    public void setLatest_version(String latest_version)
    {
        this.latest_version.set(latest_version);
    }

    public String getCreators()
    {
        return creators.get();
    }

    public SimpleStringProperty creatorsProperty()
    {
        return creators;
    }

    public void setCreators(String creators)
    {
        this.creators.set(creators);
    }

    public String getSource()
    {
        return source.get();
    }

    public SimpleStringProperty sourceProperty()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source.set(source);
    }

    public String getType()
    {
        return type.get();
    }

    public SimpleStringProperty typeProperty()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type.set(type);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstalledTableRow that = (InstalledTableRow) o;
        return Objects.equals(type, that.type) &&
                Objects.equals(name, that.name) &&
                Objects.equals(status, that.status) &&
                Objects.equals(version, that.version) &&
                Objects.equals(latest_version, that.latest_version) &&
                Objects.equals(creators, that.creators) &&
                Objects.equals(source, that.source);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(type, name, status, version, latest_version, creators, source);
    }
}
