package Backend;

import Data.DaemonRequest;
import GUI.Core;
import GUI.DaemonRequests;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

/**
 *
 */
public class SystemTrayHandler
{
    /**
     * Returns a boolean if succesful or not.
     */
    public static void createSystemTray()
    {
        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is not supported");
            return;
        }
        final PopupMenu popup = new PopupMenu();

        URL url = System.class.getResource("/tray/baseTray.png");
        Image image = Toolkit.getDefaultToolkit().getImage(url);

        final TrayIcon trayIcon = new TrayIcon(image);

        final SystemTray tray = SystemTray.getSystemTray();

        // Create a pop-up menu components
        MenuItem showItem = new MenuItem("Show PAL");
        showItem.addActionListener(e -> DaemonRequests.addRequest(DaemonRequest.SHOW_UI));

        MenuItem settingsItem = new MenuItem("Settings");
        settingsItem.addActionListener(e ->
        {
            // Launch Settings
        });
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));

        //Add components to pop-up menu
        popup.add(showItem);
        popup.add(settingsItem);
        popup.add(exitItem);

        trayIcon.setPopupMenu(popup);

        trayIcon.setToolTip("PoE Addon Launcher\nmade by: twitch.tv/Rizlim");

        try
        {
            tray.add(trayIcon);
        } catch (AWTException e) {
            System.out.println("TrayIcon could not be added.");
        }
    }
}
