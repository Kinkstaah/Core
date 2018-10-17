package Overlay

import org.junit.Before
import org.junit.Test
import java.io.File

/**
 *
 */
class KeyHandlerTest
{
    private val keyBinds = KeyBinds(arrayOf(KeyBind(KeyState(false, false, false, 13), "HIDE"), KeyBind(KeyState(false, false, false, 13), "SHOW"), KeyBind(KeyState(true, true, false, 24), "OPTIONS"), KeyBind(KeyState(false, false, false, 1), "CLOSE_CURRENT"), KeyBind(KeyState(true, false, false, 1), "CLOSE_ALL"), KeyBind(KeyState(false, false, false, 59), "CUSTOM1")))
    private val TEST_LOCATION = System.getenv("LOCALAPPDATA") + File.separator + "TEST" + File.separator + "keybinds"
    private val detected_key_bind = KeyState(false, false, false, 1)

    @Before
    fun before()
    {
        val f = File(System.getenv("LOCALAPPDATA") + File.separator + "TEST")
        if (!f.exists())
            f.mkdir()

        // Create a keybinds file to read from.
        keyBinds.exporter(TEST_LOCATION)
    }

    @Test
    fun testBasicKeyBind()
    {
        // This shouldn't throw an error.
        KeyHandler.check(detected_key_bind, TEST_LOCATION)
    }

    @Test(expected = Error::class)
    fun triggerError()
    {
        KeyHandler.check(detected_key_bind, "foo")
    }
}
