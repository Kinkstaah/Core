package GUI.Tables;

import Repo.AddonJson;
import Repo.Addons;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 *
 */
public final class AddonTable
{
    private static final AddonTable INSTANCE = new AddonTable();

    private AddonTable()
    {}

    /*
    public void testData()
    {
        table.add(new AddonTableRow(new SimpleStringProperty("POE Trade Macro"),
                new SimpleStringProperty("v2.5.0"),
                new SimpleStringProperty("v2.6.0"),
                new SimpleStringProperty("Cache"),
                new SimpleStringProperty("Eruyome"),
                new SimpleStringProperty("AHK") ));
        table.add(new AddonTableRow(new SimpleStringProperty("Mercury Trade"),
                new SimpleStringProperty("v2.0"),
                new SimpleStringProperty("v2.5"),
                new SimpleStringProperty("Github API"),
                new SimpleStringProperty("Exslims"),
                new SimpleStringProperty("Java") ));
    }*/

    public ObservableList<AddonTableRow> createAddonTableData()
    {
        ObservableList<AddonTableRow> data = FXCollections.observableArrayList();
        ArrayList<AddonJson> list = Addons.getINSTANCe().getAddons();
        for (AddonJson a : list)
        {
            data.add(a.convertToTableRow());
        }
        return data;
    }

    public static AddonTable getINSTANCE()
    {
        return INSTANCE;
    }
}
