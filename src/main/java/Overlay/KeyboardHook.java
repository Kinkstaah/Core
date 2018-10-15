package Overlay;

/**
 * Read User Inputs (Uses Kotlin)
 */
public class KeyboardHook
{
    public static void main(String[] args) throws InterruptedException
    {
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
                System.out.println(prev.toString());
            }
        }
    }
}
