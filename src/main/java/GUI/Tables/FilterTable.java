package GUI.Tables;

import API.FilterBlast.FilterBlast;
import API.FilterBlast.FilterBlastFilter;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 *
 */
public final class FilterTable
{
    private static final FilterTable INSTANCE = new FilterTable();
    private ObservableList<FilterTableRow> table = FXCollections.observableArrayList();
    private ArrayList<FilterBlastFilter> filterBlastFilters = new ArrayList<>();

    private FilterTable()
    {

    }

    public void initFilterBlast()
    {
        filterBlastFilters = FilterBlast.downloadListOfFilters();
    }

    public ObservableList<FilterTableRow> generate()
    {
        ObservableList<FilterTableRow> data = FXCollections.observableArrayList();
        for (FilterBlastFilter filter : filterBlastFilters)
        {
            for (FilterTableRow filterTableRow : filter.getPresetTables())
            {
                data.add(filterTableRow);
            }
        }
        return data;
    }

    public void addRow(FilterTableRow row)
    {
        table.add(row);
    }

    public void deleteRow(FilterTableRow row)
    {
        table.remove(row);
    }

    public ObservableList<FilterTableRow> getTable()
    {
        return table;
    }

    public static FilterTable getINSTANCE()
    {
        return INSTANCE;
    }

    public ArrayList<FilterBlastFilter> getFilterBlastFilters()
    {
        return filterBlastFilters;
    }

    public void setFilterBlastFilters(ArrayList<FilterBlastFilter> filterBlastFilters)
    {
        this.filterBlastFilters = filterBlastFilters;
    }

    public FilterBlastFilter getFilterByName(String in)
    {
        for (FilterBlastFilter f : filterBlastFilters)
        {
            if (f.getName().equals(in))
            {
                return f;
            }
        }
        return null;
    }

    public FilterBlastFilter getFilterByPresetName(String in)
    {
        for (FilterBlastFilter f: filterBlastFilters)
        {
            if (f.getPresets().contains(in))
            {
                return f;
            }
        }
        return null;
    }
}
