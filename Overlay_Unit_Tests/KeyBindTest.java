package Overlay;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 *
 */
public class KeyBindTest
{
    private KeyBinds keyBinds = new KeyBinds(new KeyBind[]
    {
            new KeyBind(new KeyState(false, false, false, 13), "HIDE"),
            new KeyBind(new KeyState(false, false, false, 13), "SHOW"),
            new KeyBind(new KeyState(true, true, false, 24), "OPTIONS"),
            new KeyBind(new KeyState(false, false, false, 1), "CLOSE_CURRENT"),
            new KeyBind(new KeyState(true, false, false, 1), "CLOSE_ALL"),
            new KeyBind(new KeyState(false, false, false, 59), "CUSTOM1")
    });
    private static final String TEST_LOCATION = System.getenv("LOCALAPPDATA") + File.separator + "TEST" + File.separator + "keybinds";

    @Before
    public void before()
    {
        // Create Test Dir if it doesn't exist.
        File f = new File(System.getenv("LOCALAPPDATA") + File.separator + "TEST");
        if (!f.exists())
            f.mkdir();
    }

    @Test
    public void exporter()
    {
        keyBinds.exporter(TEST_LOCATION);

        // Verify file was created
        File f = new File(TEST_LOCATION);
        assertTrue(f.exists());
    }

    @Test
    public void importer()
    {
        assertEquals(keyBinds, KeyBinds.Companion.importer(TEST_LOCATION));
    }

    @Test
    public void importFileNotReal()
    {
        assertNull(KeyBinds.Companion.importer(TEST_LOCATION + File.separator + "somethingthatisntreal"));
    }
}