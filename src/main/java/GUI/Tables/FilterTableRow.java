package GUI.Tables;

import javafx.beans.property.SimpleStringProperty;

import java.util.Objects;

/**
 *
 */
public class FilterTableRow
{
    private SimpleStringProperty name;
    private SimpleStringProperty version;
    private SimpleStringProperty last_update;
    private SimpleStringProperty poe_version;
    private SimpleStringProperty source;
    private SimpleStringProperty creators;

    public FilterTableRow(SimpleStringProperty name, SimpleStringProperty version, SimpleStringProperty last_update, SimpleStringProperty poe_version, SimpleStringProperty source, SimpleStringProperty creators)
    {
        this.name = name;
        this.version = version;
        this.last_update = last_update;
        this.poe_version = poe_version;
        this.source = source;
        this.creators = creators;
    }

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

    public String getLast_update()
    {
        return last_update.get();
    }

    public SimpleStringProperty last_updateProperty()
    {
        return last_update;
    }

    public void setLast_update(String last_update)
    {
        this.last_update.set(last_update);
    }

    public String getPoe_version()
    {
        return poe_version.get();
    }

    public SimpleStringProperty poe_versionProperty()
    {
        return poe_version;
    }

    public void setPoe_version(String poe_version)
    {
        this.poe_version.set(poe_version);
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

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilterTableRow that = (FilterTableRow) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(version, that.version) &&
                Objects.equals(last_update, that.last_update) &&
                Objects.equals(poe_version, that.poe_version) &&
                Objects.equals(source, that.source) &&
                Objects.equals(creators, that.creators);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(name, version, last_update, poe_version, source, creators);
    }
}
