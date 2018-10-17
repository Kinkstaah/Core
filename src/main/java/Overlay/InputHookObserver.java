package Overlay;

import javafx.application.Application;

import java.io.File;

/**
 * Read User Inputs (Uses Kotlin)
 */
public class InputHookObserver
{
    public static void main(String[] args) throws InterruptedException
    {
        Runnable r2 = () -> Application.launch(WebAddon.class, args);
        Thread t = new Thread(r2);
        t.setDaemon(true);
        t.start();

        Runnable r = InputHook.Companion::main;
        Thread inputHook_thread = new Thread(r);
        inputHook_thread.setDaemon(true);
        inputHook_thread.start();

        KeyState prev = InputHook.Companion.getKeyState();


        while (true)
        {
            Thread.sleep(10L);
            if (prev.getKey() != InputHook.Companion.getKeyState().getKey())
            {
                prev = InputHook.Companion.getKeyState();
                if (prev.getKey() == 45)
                    System.exit(0);
            }
        }
    }

    public static void handle(KeyState keyState)
    {
        // Check against user defined keystrokes.
    }
}
