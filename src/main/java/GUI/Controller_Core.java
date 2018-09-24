package GUI;

import API.Github.UpdateChecker;
import Backend.SystemTrayHandler;
import Data.InstalledAddons;
import Data.PALdata;
import Data.UserSettings;
import GUI.PopUp.PopupFactory;
import GUI.PopUp.UpdatedPopup;
import GUI.Tables.*;
import IO.*;
import Repo.Addons;
import Repo.PoELauncher;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.controlsfx.control.CheckComboBox;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Controller_Core implements Initializable
{
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        Platform.runLater(() -> shouldPopupShow());

        installed_tableView.setOnMouseClicked( event ->
        {
            if (event.getClickCount() == 2)
            {
                // Prevent double clicking on a tab for sorting to do something we don't want.
                if (event.getY() >= 23)
                {
                    InstalledTableRow i = installed_tableView.getSelectionModel().getSelectedItem();
                    assert i != null;
                    if (i.getType().equals("A"))
                    {
                        ProgramLauncher.launch(i.getName());
                    }
                    else if (i.getType().equals("F"))
                    {
                        if (UserSettings.getLootFilter().equals(""))
                        {
                            Platform.runLater(() -> PopupFactory.showError(2));
                            return;
                        }
                        Uninstaller.removeFilter(i);
                    }
                }
            }
        });

        addons_tableView.setOnMouseClicked( event ->
        {
            if (event.getClickCount() == 2)
            {
                addon_download_onMouseClicked();
            }
        });

        filters_tableView.setOnMouseClicked( event ->
        {
            if (event.getClickCount() == 2)
            {
                // Download Filter.
                filter_download_onMouseClicked();
            }
        });

        FilterTable.getINSTANCE().initFilterBlast();

        Platform.runLater(this::setAddonTableData);
        Platform.runLater(this::setInstallTableData);
        Platform.runLater(this::setFilterTableData);
        daemonWarningText();
        daemonDownloadNotifier();
        daemonTabPaneWatcher();
        daemonTableViewSelections();
        daemonSystemTrayHandler();

        // Create Repo objects.
        try
        {
            UserSettings.addDefaultRepos();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        // Add more repos?
        UserSettings.downloadAllRepos();
        InstalledAddons.getINSTANCE().scanForInstalledAddons();

        //System.out.println(Addons.getINSTANCe().getAddons().size() + " ADDONS");
        UpdateChecker.startChecking();
        UserSettings.parseCustomAHK();
        // Create a table with the addons for the Browse
        //setAddonTableData();
        //Handle Specials Settings
        handle_launch_request();
        /*
            Longer Term non v1.0
            TODO: In game overlay for browser related addons.
            TODO: User Repo Adding
            TODO: User API Handling
         */

    }

    private void daemonSystemTrayHandler()
    {
        Runnable r = ()  ->
        {
            while (true)
            {
                try
                {
                    Thread.sleep(300L);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                boolean showSettings = SystemTrayHandler.openSettings;
                boolean showUI = SystemTrayHandler.showUI;
                if (showSettings)
                {
                    SystemTrayHandler.openSettings = false;
                    Platform.runLater(() -> topbar_settings_onMouseClicked());
                }

                if (showUI)
                {
                    SystemTrayHandler.showUI = false;
                    Platform.runLater(() -> Core.stage.setIconified(false));
                }
            }
        };
        Thread t_tray = new Thread(r);
        t_tray.setDaemon(true);
        t_tray.start();
    }

    private void handle_launch_request()
    {
        if (PALdata.settings.isRun_poe_on_launch())
        {
            launchPoE();
        }
    }

    private void filter_download_onMouseClicked()
    {
        Runnable r = () ->
        {
            FilterTableRow f = filters_tableView.getSelectionModel().getSelectedItem();
            if (f != null)
            {
                try
                {
                    if (UserSettings.getLootFilter().equals(""))
                    {
                        Platform.runLater(() -> PopupFactory.showError(2));
                        return;
                    }
                    FilterDownloader.downloadPreset(f.getName());
                    InstalledAddons.getINSTANCE().saveInstalledFiltersJSON();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }

    private void daemonTableViewSelections()
    {
        Runnable r = () ->
        {
            while (true)
            {
                try
                {
                    Thread.sleep(300L);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                // If table > 15, resize Creator(s) to 85px otherwise resize to 98px
                Platform.runLater(() ->
                {
                    resizeTable(installed_tableView, installed_tc_creators);
                    resizeTable(addons_tableView, addon_tc_creators);
                    resizeTable(filters_tableView, filters_tc_creators);

                });
            }
        };
        Thread thread = new Thread(r);
        thread.setDaemon(true);
        thread.start();
    }

    private void resizeTable(TableView tableView, TableColumn<?, ?> tc_creators)
    {
        if (tableView.getItems().size() >= 15)
        {
            if (tc_creators.getWidth() != 85)
            {
                Platform.runLater(() ->
                {
                    tc_creators.setMaxWidth(85);
                    tc_creators.setPrefWidth(85);
                    tc_creators.setMinWidth(85);
                });
            }
        }
        else
        {
            if (tc_creators.getWidth() != 98)
            {
                Platform.runLater(() ->
                {
                    tc_creators.setMaxWidth(98);
                    tc_creators.setPrefWidth(98);
                    tc_creators.setMinWidth(98);
                });
            }
        }
    }

    private void daemonTabPaneWatcher()
    {
        Runnable r = () ->
        {
            while (true)
            {
                Platform.runLater(() -> updateTables(core_tabpane.getSelectionModel().getSelectedIndex()));

                if (UserSettings.update_request)
                {
                    UserSettings.update_request = false;
                    updateTables(-1);
                }

                try
                {
                    Thread.sleep(300L);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }

    public void updateTables(int n)
    {
        if (n != 0)
        {
            setInstallTableData();
        }

        if (n != 1)
        {
            setAddonTableData();
        }

        if (n != 2)
        {
            // Replace with threaded version (set)
            filterTableData();
        }
    }

    private void daemonDownloadNotifier()
    {
        Runnable r = () ->
        {
            String cached = "";
            while (true)
            {
                try
                {
                    Thread.sleep(300L);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                ArrayList<String> downloads = ActiveDownload.getActiveDownload();
                if (downloads.size() == 0)
                {
                    String str = "Launch Path of Exile";
                    if (!cached.equals(str))
                    {
                        Platform.runLater(() -> launch_text.setText(str));
                        cached = str;
                        Platform.runLater(() -> show_while_downloading.setVisible(false));
                    }
                }
                else
                {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Downloading: ");
                    stringBuilder.append(downloads.size());
                    stringBuilder.append(" addon(s)...");
                    Platform.runLater(() -> launch_text.setText(stringBuilder.toString()));
                    Platform.runLater(() -> show_while_downloading.setVisible(true));
                    cached = stringBuilder.toString();
                }


            }
        };
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }

    private boolean dismissLootFilterWarning = false;
    private void daemonWarningText()
    {
        Runnable r = () ->
        {
            while (true)
            {
                try
                {
                    Thread.sleep(300L);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                if (UserSettings.getLootFilter() == null)
                    UserSettings.setLootFilter("");
                if (UserSettings.getLootFilter().equals("") && !dismissLootFilterWarning)
                {
                    Platform.runLater(() -> displayLootFilterFolderWarning());
                }
            }
        };
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }

    public void displayLootFilterFolderWarning()
    {
        txtWarning.setText("WARNING: LOOTFILTER FOLDER HAS NOT BEEN SET!");
        txtWarning.setVisible(true);
    }

    //region @FXML References
    @FXML
    private Text installed_refresh_text_hl;

    @FXML
    private Text installed_update_text_hl;

    @FXML
    private Text installed_updateAll_text_hl;

    @FXML
    private Text installed_remove_text_hl;

    @FXML
    private Text filters_refresh_text_hl;

    @FXML
    private Text filters_download_text_hl;

    @FXML
    private Text addon_refresh_text_hl;

    @FXML
    private ProgressIndicator show_while_downloading;

    @FXML
    private Text txtWarning;

    @FXML
    private ImageView topbar_image_closeWindow;

    @FXML
    private ImageView topbar_image_minimizeWindow;

    @FXML
    private ImageView topbar_image_settings;

    @FXML
    private TabPane core_tabpane;

    @FXML
    private Tab core_tab_installed;

    @FXML
    private AnchorPane installed_anchorpane;

    @FXML
    private TableView<InstalledTableRow> installed_tableView;

    @FXML
    private TableColumn<InstalledTableRow, String> installed_tc_source;

    @FXML
    private TableColumn<InstalledTableRow, String> installed_tc_type;

    @FXML
    private TableColumn<InstalledTableRow, String> installed_tc_name;

    @FXML
    private TableColumn<InstalledTableRow, String> installed_tc_status;

    @FXML
    private TableColumn<InstalledTableRow, String> installed_tc_version;

    @FXML
    private TableColumn<InstalledTableRow, String> installed_tc_latestVersion;

    @FXML
    private TableColumn<InstalledTableRow, String> installed_tc_creators;

    @FXML
    private ImageView installed_refresh_img;

    @FXML
    private Text installed_refresh_text;

    @FXML
    private ImageView installed_update_img;

    @FXML
    private Text installed_update_text;

    @FXML
    private ImageView installed_updateAll_img;

    @FXML
    private Text installed_updateAll_text;

    @FXML
    private ImageView installed_remove_img;

    @FXML
    private Text installed_remove_text;

    @FXML
    private TextField installed_searchField;

    @FXML
    private Tab core_tab_addons;

    @FXML
    private TableView<AddonTableRow> addons_tableView;

    @FXML
    private TableColumn<AddonTableRow, String> addons_tc_name;

    @FXML
    private TableColumn<AddonTableRow, String> addon_tc_version;

    @FXML
    private TableColumn<AddonTableRow, String> addon_tc_lastUpdate;

    @FXML
    private TableColumn<AddonTableRow, String> addon_tc_source;

    @FXML
    private TableColumn<AddonTableRow, String> addon_tc_creators;

    @FXML
    private TableColumn<AddonTableRow, String> addon_tc_code;

    @FXML
    private ImageView addon_refresh_img;

    @FXML
    private Text addon_refresh_text;

    @FXML
    private ImageView addon_download_img;

    @FXML
    private Text addon_download_text;

    @FXML
    private Text addon_download_text_hl;

    @FXML
    private CheckComboBox<String> addon_repo;

    @FXML
    private TextField addon_search;

    @FXML
    private Tab core_tab_filters;

    @FXML
    private TableView<FilterTableRow> filters_tableView;

    @FXML
    private TableColumn<FilterTableRow, String> filters_tc_name;

    @FXML
    private TableColumn<FilterTableRow, String> filters_tc_version;

    @FXML
    private TableColumn<FilterTableRow, String> filters_tc_lastUpdate;

    @FXML
    private TableColumn<FilterTableRow, String> filters_tc_PoEVersion;

    @FXML
    private TableColumn<FilterTableRow, String> filters_tc_source;

    @FXML
    private TableColumn<FilterTableRow, String> filters_tc_creators;

    @FXML
    private ImageView filters_refresh_img;

    @FXML
    private Text filters_refresh_text;

    @FXML
    private ImageView filters_download_img;

    @FXML
    private Text filters_download_text;

    @FXML
    private CheckComboBox<String> filters_repo;

    @FXML
    private TextField filters_search;

    @FXML
    private AnchorPane desc_anchorpane;

    @FXML
    private ImageView desc_image_closeWindow;

    @FXML
    private Text desc_like_dislike_percentage;

    @FXML
    private Text desc_downloads_text;

    @FXML
    private ProgressBar desc_like_dislike_bar;

    @FXML
    private ImageView desc_like_image;

    @FXML
    private ImageView desc_dislike_image;

    @FXML
    private Text desc_like_count;

    @FXML
    private Text desc_dislike_count;

    @FXML
    private ImageView desc_Addon_Image;

    @FXML
    private Text desc_Title;

    @FXML
    private Text desc_long_text;

    @FXML
    private ImageView desc_download_icon;

    @FXML
    private AnchorPane launch_anchorpane;

    @FXML
    private Text launch_text;
    //endregion;

    /**
     * Change the image of an image view.
     * @param imageView ImageView to be changed.
     * @param s String location of the image. [(resources)/{your_folder}/your_file.ext]
     */
    private void changeImage(ImageView imageView, String s)
    {
        Platform.runLater(() -> imageView.setImage(new Image(getClass().getResource(s).toString())));
    }

    //region Topbar
    private double xOffset = 0;
    private double yOffset = 0;

    public void onMouseDragged(MouseEvent mouseEvent)
    {
        Core.stage.setX(mouseEvent.getScreenX() + xOffset);
        Core.stage.setY(mouseEvent.getScreenY() + yOffset);
    }
    public void onMousePressed(MouseEvent mouseEvent)
    {
        xOffset = Core.stage.getX() - mouseEvent.getScreenX();
        yOffset = Core.stage.getY() - mouseEvent.getScreenY();
    }

    public void topbar_closeWIndow_onMouseClicked()
    {
        System.exit(0);
    }

    public void topbar_closeWindow_onMouseEntered()
    {
        changeImage(topbar_image_closeWindow, "/icons/cancel.png");
    }

    public void topbar_closeWindow_onMouseExited()
    {
        changeImage(topbar_image_closeWindow, "/icons/cancel0.png");
    }

    public void topbar_minimizeWindow_onMouseEntered()
    {
        changeImage(topbar_image_minimizeWindow, "/icons/minimize_hl.png");
    }

    public void topbar_minimizeWindow_onMouseClicked()
    {
        Core.stage.setIconified(true);
    }

    public void topbar_minimizeWindow_onMouseExited()
    {
        changeImage(topbar_image_minimizeWindow, "/icons/minimize.png");
    }

    public void topbar_settings_onMouseClicked()
    {
        Settings settings = new Settings();
        try
        {
            settings.start(new Stage());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void topbar_settings_onMouseExited()
    {
        changeImage(topbar_image_settings, "/icons/settings.png");
    }

    public void topbar_settings_onMouseEntered()
    {
        changeImage(topbar_image_settings, "/icons/settings_hl.png");
    }

    //TODO: Other Tables
    public void setAddonTableData()
    {
        Runnable r = () -> Platform.runLater(this::addonTableData);
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }

    public void setInstallTableData()
    {
        Runnable r = () -> Platform.runLater(this::installedTableData);
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }

    private void setFilterTableData()
    {
        Runnable r = () -> Platform.runLater(this::filterTableData);
        Thread t = new Thread(r);
        t.setDaemon(true);
        t.start();
    }

    public void addonTableData()
    {
        ObservableList<AddonTableRow> data = AddonTable.getINSTANCE().createAddonTableData();

        addons_tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        addon_tc_version.setCellValueFactory(new PropertyValueFactory<>("version"));
        addon_tc_lastUpdate.setCellValueFactory(new PropertyValueFactory<>("last_update"));
        addon_tc_source.setCellValueFactory(new PropertyValueFactory<>("source"));
        addon_tc_creators.setCellValueFactory(new PropertyValueFactory<>("creators"));
        addon_tc_code.setCellValueFactory(new PropertyValueFactory<>("extra"));

        //TODO: Make Creators table 85px when the table reaches a certain amount of items.

        addons_tableView.setItems(data);
    }

    public void installedTableData()
    {
        ObservableList<InstalledTableRow> data = InstalledTable.getINSTANCE().generateData();

        installed_tc_type.setCellValueFactory(new PropertyValueFactory<>("type"));
        installed_tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        installed_tc_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        installed_tc_version.setCellValueFactory(new PropertyValueFactory<>("version"));
        installed_tc_latestVersion.setCellValueFactory(new PropertyValueFactory<>("latest_version"));
        installed_tc_creators.setCellValueFactory(new PropertyValueFactory<>("creators"));
        installed_tc_source.setCellValueFactory(new PropertyValueFactory<>("source"));

        //TODO: Make Creators table 85px when the table reaches a certain amount of items.

        installed_tableView.setItems(data);
    }


    public void filterTableData()
    {
        ObservableList<FilterTableRow> data = FilterTable.getINSTANCE().generate();

        filters_tc_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        filters_tc_version.setCellValueFactory(new PropertyValueFactory<>("version"));
        filters_tc_lastUpdate.setCellValueFactory(new PropertyValueFactory<>("last_update"));

        filters_tc_PoEVersion.setCellValueFactory(new PropertyValueFactory<>("poe_version"));
        filters_tc_source.setCellValueFactory(new PropertyValueFactory<>("source"));
        filters_tc_creators.setCellValueFactory(new PropertyValueFactory<>("creators"));

        filters_tableView.setItems(data);
    }

    public void hideTxtWarning()
    {
        dismissLootFilterWarning = true;
        Platform.runLater(() -> txtWarning.setVisible(false));
    }

    public void desc_image_close_onMouseClicked()
    {
        Platform.runLater(() -> desc_anchorpane.setVisible(false));
    }

    public void desc_image_close_onMouseEntered()
    {
        changeImage(desc_image_closeWindow, "/icons/cancel.png");
    }

    public void desc_image_close_onMouseExit()
    {
        changeImage(desc_image_closeWindow, "/icons/cancel0.png");
    }

    public void addon_download_onMouseClicked()
    {
        Runnable r = () ->
        {
            AddonTableRow addonTableRow = addons_tableView.getSelectionModel().getSelectedItem();

            if (addonTableRow == null)
                return;

            if (addonTableRow.getExtra().equals("AHK"))
            {
                if (PALdata.settings.getAHK_Folder() == null)
                {
                    Platform.runLater(() -> PopupFactory.showError(3));
                    return;
                }
                if (PALdata.settings.getAHK_Folder().equals(""))
                {
                    Platform.runLater(() -> PopupFactory.showError(3));
                    return;
                }
            }

            try
            {
                AddonDownloader.downloadAddon(addonTableRow.getName());
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
        };
        Thread t_download_thread = new Thread(r);
        t_download_thread.setDaemon(true);
        t_download_thread.start();
    }

    public void addon_download_onMouseExit()
    {
        Platform.runLater(() ->
        {
            addon_download_text.setVisible(true);
            addon_download_text_hl.setVisible(false);
        });
    }

    public void addon_download_onMouseOver()
    {
        Platform.runLater(() ->
                {
                    addon_download_text.setVisible(false);
                    addon_download_text_hl.setVisible(true);
                });
    }

    public void launchPoE()
    {
        // Launch PoE
        if (!launch_text.getText().equals("Launch Path of Exile"))
        {
            return;
        }
        Platform.runLater(() -> launch_text.setText("Launching Path of Exile..."));
        Platform.runLater(() ->
        {
            try
            {
                PoELauncher.run();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch (URISyntaxException e)
            {
                e.printStackTrace();
            }
        });
        Platform.runLater(() -> launch_text.setText("Launch Path of Exile"));
    }

    public void launchPoEExit()
    {
        Platform.runLater(() ->
        {
            launch_anchorpane.setStyle("");
            launch_anchorpane.setStyle("-fx-background-color: #3C3F41");
        });
    }

    public void launchPoEEnter()
    {
        Platform.runLater(() ->
        {
            launch_anchorpane.setStyle("");
            launch_anchorpane.setStyle("-fx-background-color: #515658");
        });
    }

    public void refreshOnMouseEnter()
    {
        Platform.runLater(() ->
        {
            addon_refresh_text_hl.setVisible(true);
            installed_refresh_text_hl.setVisible(true);
            filters_refresh_text_hl.setVisible(true);

            addon_refresh_text.setVisible(false);
            installed_refresh_text.setVisible(false);
            filters_refresh_text.setVisible(false);
        });
    }

    public void refreshOnMouseExit()
    {
        Platform.runLater(() ->
        {
            addon_refresh_text_hl.setVisible(false);
            installed_refresh_text_hl.setVisible(false);
            filters_refresh_text_hl.setVisible(false);

            addon_refresh_text.setVisible(true);
            installed_refresh_text.setVisible(true);
            filters_refresh_text.setVisible(true);
        });
    }

    public void refreshOnClick()
    {
        Platform.runLater(() ->
        {
            filterTableData();
            installedTableData();
            addonTableData();
        });
    }

    public void FiltersDownloadMouseEntered()
    {
        Platform.runLater(() ->
        {
            filters_download_text_hl.setVisible(true);
            filters_download_text.setVisible(false);
        });
    }

    public void FiltersDownloadMouseExited()
    {
        Platform.runLater(() ->
        {
            filters_download_text_hl.setVisible(false);
            filters_download_text.setVisible(true);
        });
    }

    public void FiltersDownloadMouseClicked()
    {
        filter_download_onMouseClicked();
    }

    public void installUpdateMouseExit()
    {
        installed_update_text.setVisible(true);
        installed_update_text_hl.setVisible(false);
    }

    public void installUpdateMouseEnter()
    {
        installed_update_text.setVisible(false);
        installed_update_text_hl.setVisible(true);
    }

    public void installUpdateClick()
    {
        Runnable r = () ->
        {
            InstalledTableRow row = installed_tableView.getSelectionModel().getSelectedItem();
            try
            {
                if (row == null)
                {
                    return;
                }

                AddonDownloader.downloadAddon(row.getName());
            }
            catch (MalformedURLException e)
            {
                e.printStackTrace();
            }
        };
        Thread t_download_thread = new Thread(r);
        t_download_thread.setDaemon(true);
        t_download_thread.start();
    }

    public void shouldPopupShow()
    {
        File blocker = new File(PALdata.LOCAL_PAL_FOLDER + File.separator + "b10.v");
        if (!blocker.exists())
        {
            try
            {
                blocker.createNewFile();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            UpdatedPopup updatedPopup = new UpdatedPopup();
            try
            {
                updatedPopup.start(new Stage());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    private boolean updateAll = false;
    public void updateAllClick()
    {
        if (updateAll)
            return;

        Runnable r = () ->
        {
            updateAll = true;
            ExecutorService executorService = Executors.newCachedThreadPool();

            // Collect list of addons to download.
            ObservableList<InstalledTableRow> list = installed_tableView.getItems();

            for (InstalledTableRow i : list)
            {
                if (i.getStatus().equals("New Update"))
                {
                    executorService.execute(() ->
                    {
                        try
                        {
                            AddonDownloader.downloadAddon(i.getName());
                        }
                        catch (MalformedURLException e)
                        {
                            e.printStackTrace();
                        }
                    });
                }
            }
            try
            {
                executorService.shutdown();
                while (!executorService.awaitTermination(2L, TimeUnit.MINUTES))
                {

                }
            }
            catch (InterruptedException ex)
            {

            }
            updateAll = false;
        };
        Thread t_download_all_main_thread = new Thread(r);
        t_download_all_main_thread.setDaemon(true);
        t_download_all_main_thread.start();
    }

    public void updateAllEnter()
    {
        installed_updateAll_text.setVisible(false);
        installed_updateAll_text_hl.setVisible(true);
    }

    public void updateAllExit()
    {
        installed_updateAll_text.setVisible(true);
        installed_updateAll_text_hl.setVisible(false);
    }

    public void removeExit()
    {
        installed_remove_text.setVisible(true);
        installed_remove_text_hl.setVisible(false);
    }

    public void removeEnter()
    {
        installed_remove_text.setVisible(false);
        installed_remove_text_hl.setVisible(true);
    }

    /**
     * WARNING: RECURSIVE DELETION, MAKE A MISTAKE AND IT'S IRREVERSIBLE.
     */
    public void removeClick()
    {
        InstalledTableRow installedTableRow = installed_tableView.getSelectionModel().getSelectedItem();
        if (installedTableRow != null)
        {
            // Show Confirmation Box
            if (installedTableRow.getType().equals("A"))
            {
                Uninstaller.unninstall(installedTableRow.getName());
            }
            else if (installedTableRow.getType().equals("F"))
            {
                // Delete Filter.
                if (UserSettings.getLootFilter().equals(""))
                {
                    Platform.runLater(() -> PopupFactory.showError(2));
                    return;
                }
                Uninstaller.removeFilter(installedTableRow);
            }

        }
    }


    //endregion;
}
