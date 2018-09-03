package GUI.Tables;

import Data.IniHandler;
import Data.InstalledAddons;
import Repo.AddonJson;
import Repo.Addons;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.util.ArrayList;

/**
 *
 */
public final class InstalledTable
{
    private static final InstalledTable INSTANCE = new InstalledTable();
    private ObservableList<InstalledTableRow> table = FXCollections.observableArrayList();

    private InstalledTable()
    {

    }

    public ObservableList<InstalledTableRow> generateData()
    {
        ObservableList<InstalledTableRow> data = FXCollections.observableArrayList();

        ArrayList<InstalledTableRow> installed_addons = InstalledAddons.getINSTANCE().getList_of_installed_addons();

        for (InstalledTableRow itr : installed_addons)
        {
            data.add(itr);
        }
        return data;

    }

    public static InstalledTable getINSTANCE()
    {
        return INSTANCE;
    }

    public void addToTable(InstalledTableRow row)
    {
        table.add(row);
    }

    public void removeFromTable(InstalledTableRow row)
    {
        table.remove(row);
    }

    public ObservableList<InstalledTableRow> getTable()
    {
        return table;
    }
}
