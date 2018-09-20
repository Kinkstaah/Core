package GUI;

import Data.IniHandler;
import Data.InstalledAddons;
import Data.UserSettings;
import GUI.Tables.FilterTable;
import GUI.Tables.InstalledTable;
import GUI.Tables.InstalledTableRow;
import IO.CustomAHK;
import IO.InstalledFilter;
import Repo.Repository;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller_Settings implements Initializable
{

    @FXML
    private ListView<String> settings_listView;

    @FXML
    private AnchorPane main_settings_anchorpane;

    @FXML
    private AnchorPane settings_a_repo;

    @FXML
    private AnchorPane settings_a_api;

    @FXML
    private CheckBox github_api_checkbox;

    @FXML
    private CheckBox filterblastCheckBox;

    @FXML
    private TextField github_api_token;

    @FXML
    private CheckBox enableAPItoken;

    @FXML
    private Button bGetGitHubAPIToken;

    @FXML
    private AnchorPane settings_a_paths;

    @FXML
    private TextField ADDONS_FOLDER;

    @FXML
    private Button bBrowseAddons;

    @FXML
    private TextField LOOTFILTER_FOLDER;

    @FXML
    private Button bBrowseLootFilter;

    @FXML
    private TextField POE_STEAM;

    @FXML
    private Button bBrowseSteam;

    @FXML
    private TextField POE_STANDALONE;

    @FXML
    private Button bBrowseSA;

    @FXML
    private TextField POE_BETA;

    @FXML
    private Button bBrowseBeta;

    @FXML
    private AnchorPane settings_a_launchoptions;

    @FXML
    private ListView<String> settings_dont_launch_list;

    @FXML
    private ListView<String> settings_launch_list;

    @FXML
    private Button bLaunchSelectedItem;

    @FXML
    private Button bDontLaunchSelectedItem;

    @FXML
    private CheckBox checkbox_launch_poe_when_pal_launches;

    @FXML
    private CheckBox checkbox_download_updates_upon_launch;

    @FXML
    private CheckBox checkbox_wait_for_updates_to_download;

    @FXML
    private ComboBox<String> combobox_PoE_Version;

    @FXML
    private AnchorPane settings_a_AHK;

    @FXML
    private TextField AHK_LOCATION;

    @FXML
    private Button bBrowseAHK;

    @FXML
    private Hyperlink hyperlink_AHK_WEBSITE;

    @FXML
    private AnchorPane settings_a_d;

    @FXML
    private AnchorPane settings_a_e;

    @FXML
    private AnchorPane settings_a_f;

    @FXML
    private AnchorPane settings_a_about;

    @FXML
    private ListView<CustomAHK> customAHKlist;

    @FXML
    private Button addScript;

    @FXML
    private CheckBox box_launch_with_PoE;

    @FXML
    private Button removeScript;



    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(() -> syncUserSettings());
        Platform.runLater(() -> populateListView());
        settingsSelectorDaemon();
    }

    private void initLaunchOptions()
    {
        ObservableList<String> data = FXCollections.observableArrayList();

        if (!UserSettings.getPoeSteam().equals(""))
        {
            data.add("Steam");
        }

        if (!UserSettings.getPoePath().equals(""))
        {
            data.add("Stand Alone");
        }

        if (!UserSettings.getPoeBeta().equals(""))
        {
            data.add("Beta");
        }

        combobox_PoE_Version.setItems(data);

        // Get JSON list of installed addons.


    }

    private void syncUserSettings()
    {
        POE_BETA.setText(UserSettings.getPoeBeta());
        POE_STANDALONE.setText(UserSettings.getPoePath());
        POE_STEAM.setText(UserSettings.getPoeSteam());
        ADDONS_FOLDER.setText(UserSettings.getPathAddons());
        LOOTFILTER_FOLDER.setText(UserSettings.getLootFilter());
        github_api_checkbox.setSelected(UserSettings.isGithubApiTokenEnabled());
        filterblastCheckBox.setSelected(UserSettings.isFilterblastApiEnabled());
        github_api_token.setText(UserSettings.getGithub_API_Token());
        enableAPItoken.setSelected(UserSettings.isGithubApiTokenEnabled());
        AHK_LOCATION.setText(UserSettings.getAhkPath());
        checkbox_download_updates_upon_launch.setSelected(UserSettings.isDownloadAllUpdatesOnPalLaunch());
        checkbox_launch_poe_when_pal_launches.setSelected(UserSettings.isLaunchPoeOnPalLaunch());
        checkbox_wait_for_updates_to_download.setSelected(UserSettings.isWaitForUpdatesAndLaunch());
        initLaunchOptions();
        String poe_version = UserSettings.getPoeVersionToLaunch();
        if (poe_version == null)
        {
            combobox_PoE_Version.getSelectionModel().select(0);
            return;
        }
        if (poe_version.equals("Steam"))
        {
            combobox_PoE_Version.getSelectionModel().select(poe_version);
        }
        else if (poe_version.equals("Stand Alone"))
        {
            combobox_PoE_Version.getSelectionModel().select(poe_version);
        }
        else if (poe_version.equals("Beta"))
        {
            combobox_PoE_Version.getSelectionModel().select(poe_version);
        }
        else
        {
            combobox_PoE_Version.getSelectionModel().select(0);
        }
    }

    private void settingsSelectorDaemon()
    {
        Runnable r = () ->
        {
            String cache = "";
            while (true)
            {
                try
                {
                    Thread.sleep(300);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                String selected = settings_listView.getSelectionModel().getSelectedItem();
                if (selected != null)
                {
                    if (!selected.equals(cache))
                    {
                        cache = selected;
                        Platform.runLater(() -> showCorrectSetting(selected));
                    }
                }
            }
        };
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }

    private void showCorrectSetting(String selected)
    {
        if (selected.equals("API"))
        {
            hideAll();
            settings_a_api.setVisible(true);
        }
        else if (selected.equals("Repository"))
        {
            hideAll();
            settings_a_repo.setVisible(true);
        }
        else if (selected.equals("About"))
        {
            hideAll();
            settings_a_about.setVisible(true);
        }
        else if (selected.equals("System Paths"))
        {
            hideAll();
            settings_a_paths.setVisible(true);
        }
        else if (selected.equals("Launch Options"))
        {
            hideAll();
            settings_a_launchoptions.setVisible(true);
        }
        else if (selected.equals("AutoHotKey"))
        {
            hideAll();
            settings_a_AHK.setVisible(true);
        }
        else
            hideAll();
    }

    public void populateListView()
    {
        ObservableList<String> list = FXCollections.observableArrayList("System Paths", "AutoHotKey", "Launch Options", "Repository", "API", "About");
        settings_listView.setItems(list);

        ObservableList<String> installed_list = FXCollections.observableArrayList();
        ObservableList<String> installed_list_launch = FXCollections.observableArrayList();
        ArrayList<InstalledTableRow> data = InstalledAddons.getINSTANCE().getList_of_installed_addons();
        for (InstalledTableRow i : data)
        {
            if (i.getType().equals("A"))
                installed_list.add(i.getName());
        }

        // Parse JSON, remove from list if they are in the json.
        File f = new File("l_addons.pal");

        if (f.exists())
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try
            {
               String[] array = objectMapper.readValue(f, String[].class);
               for (String s : array)
               {
                   installed_list_launch.add(s);
                   if (installed_list.contains(s))
                   {
                       installed_list.remove(s);
                   }
               }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        settings_dont_launch_list.setItems(installed_list);
        settings_launch_list.setItems(installed_list_launch);
        parseCustomAHK();
    }

    private void hideAll()
    {
        settings_a_repo.setVisible(false);
        settings_a_api.setVisible(false);
        settings_a_about.setVisible(false);
        settings_a_paths.setVisible(false);
        settings_a_launchoptions.setVisible(false);
        settings_a_AHK.setVisible(false);
    }

    public void SaveAndExit()
    {
        setUserSettings();
        IniHandler.writeProperties();
        Settings.stage.close();
    }

    private void setUserSettings()
    {
        // TODO: Save Repositories
        UserSettings.setLootFilter(LOOTFILTER_FOLDER.getText());
        UserSettings.setPoeBeta(POE_BETA.getText());
        UserSettings.setPoePath(POE_STANDALONE.getText());
        UserSettings.setPoeSteam(POE_STEAM.getText());
        UserSettings.setPathAddons(ADDONS_FOLDER.getText());
        UserSettings.setGithub_API_Token(github_api_token.getText());
        UserSettings.setGithubApiTokenEnabled(enableAPItoken.isSelected());
        UserSettings.setGithubApiEnabled(github_api_checkbox.isSelected());
        UserSettings.setFilterblastApiEnabled(filterblastCheckBox.isSelected());
        UserSettings.setAhkPath(AHK_LOCATION.getText());
        UserSettings.setWaitForUpdatesAndLaunch(checkbox_wait_for_updates_to_download.isSelected());
        UserSettings.setLaunchPoeOnPalLaunch(checkbox_launch_poe_when_pal_launches.isSelected());
        UserSettings.setDownloadAllUpdatesOnPalLaunch(checkbox_download_updates_upon_launch.isSelected());
        UserSettings.setPoeVersionToLaunch(combobox_PoE_Version.getSelectionModel().getSelectedItem());
        UserSettings.setCustomAHKS(customAHKlist.getItems());
        // Save JSON
        saveAddonLaunch();
        saveCustomAHK();
    }

    private void saveAddonLaunch()
    {
            File f = new File("l_addons.pal");
            if (f.exists())
            {
                f.delete();
            }

            ObservableList<String> data = settings_launch_list.getItems();
            String[] array = new String[data.size()];
            for (int c = 0; c < array.length; c++)
            {
                array[c] = data.get(c);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            if (array.length > 0)
            {
                try
                {
                    objectMapper.writeValue(f, array);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
    }

    public void BrowseAddons()
    {
        File f = browse("Browse for a folder to download addons to:");
        if (f==null)
            return;
        Platform.runLater(() -> ADDONS_FOLDER.setText(f.getPath()));
    }


    public void BrowseLootFilter()
    {
        File f = browse("Browse to your Loot Filter folder (Document/My Games/Path of Exile/):");
        if (f==null)
            return;
        Platform.runLater(() -> LOOTFILTER_FOLDER.setText(f.getPath()));
    }

    public void BrowseSteam()
    {
        File f = browse("Browse for a beta version of Path of Exile");
        if (f==null)
            return;
        Platform.runLater(() -> POE_STEAM.setText(f.getPath()));
    }

    public void BrowseSA()
    {
        File f = browse("Browse for your stand-alone version of Path of Exile");
        if (f==null)
            return;
        Platform.runLater(() -> POE_STANDALONE.setText(f.getPath()));
    }

    public void BrowseBeta()
    {
        File f = browse("Browse for your steam version of Path of Exile");
        if (f==null)
            return;
        Platform.runLater(() -> POE_BETA.setText(f.getPath()));
    }

    /**
     * Method for Opening the DirectoryChooser.
     */
    public File browse(String title)
    {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(title);
        return directoryChooser.showDialog(Settings.stage);
    }

    public void downloadAHK() throws IOException
    {
        InputStream inputStream = URI.create("https://github.com/Lexikos/AutoHotkey_L/releases/download/v1.1.30.00/AutoHotkey_1.1.30.00_setup.exe").toURL().openStream();

        File temp_dir = new File(UserSettings.getPathAddons() + File.separator + "temp");

        if (!temp_dir.exists())
            temp_dir.mkdir();

        String location = UserSettings.getPathAddons() + File.separator + "temp" + File.separator + "AutoHotkey_1.1.30.00_setup.exe";

        File installer = new File(location);
        if (installer.exists())
            installer.delete();

        Files.copy(inputStream, Paths.get(location));
        Runtime.getRuntime().exec("cmd /c " + location);
    }

    public void browseForAHK()
    {
        File f = browse("Browse for your AutoHotKey installation folder");
        if (f==null)
            return;
        Platform.runLater(() -> AHK_LOCATION.setText(f.getPath()));
    }

    public void moveFromListToList(ListView<String> from, ListView<String> to)
    {
        Platform.runLater(() ->
        {
            String selected = from.getSelectionModel().getSelectedItem();
            if (selected == null)
                return;
            ObservableList<String> data = from.getItems();
            data.remove(selected);
            from.setItems(data);

            ObservableList<String> _data = to.getItems();
            _data.add(selected);
            to.setItems(_data);
        });
    }


    public void removeFromLaunchList()
    {
        Platform.runLater(() ->
                moveFromListToList(settings_launch_list, settings_dont_launch_list));
    }

    public void addToLaunchList()
    {
        Platform.runLater(() ->
                moveFromListToList(settings_dont_launch_list, settings_launch_list));
    }

    public void removeAHKscript()
    {
        CustomAHK selectedItem = customAHKlist.getSelectionModel().getSelectedItem();
        if (selectedItem != null)
        {
            customAHKlist.getItems().remove(selectedItem);
        }
    }

    public void addAHKscript()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Browse for your .AHK script");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("AutoHotKey Scripts", "*.ahk"));
        File selected = fileChooser.showOpenDialog(Settings.stage);

        if (selected != null)
        {
            Platform.runLater(() -> customAHKlist.getItems().add(new CustomAHK(selected.getPath(), box_launch_with_PoE.isSelected())));

        }
    }

    private void saveCustomAHK()
    {
        File f = new File(UserSettings.getPathInstalldir() + File.separator + "c_ahk.pal");
        if (f.exists())
        {
            f.delete();
        }

        ObservableList<CustomAHK> data = customAHKlist.getItems();
        CustomAHK[] array = new CustomAHK[data.size()];
        for (int c = 0; c < array.length; c++)
        {
            array[c] = data.get(c);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        if (array.length > 0)
        {
            try
            {
                objectMapper.writeValue(f, array);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void parseCustomAHK()
    {
        // Parse JSON, remove from list if they are in the json.
        File f = new File(UserSettings.getPathInstalldir() + File.separator + "c_ahk.pal");

        if (f.exists())
        {
            ObjectMapper objectMapper = new ObjectMapper();
            try
            {
                CustomAHK[] array = objectMapper.readValue(f, CustomAHK[].class);
                ObservableList<CustomAHK> items = customAHKlist.getItems();
                for (CustomAHK c : array)
                {
                    items.add(c);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
