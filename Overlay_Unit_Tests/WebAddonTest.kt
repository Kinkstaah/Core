package Overlay

import javafx.application.Application
import org.junit.Assert.*
import org.junit.Test

/**
 *
 */
class WebAddonTest
{
    @Test
    fun testToggle()
    {
        val r2 = { Application.launch(WebAddon::class.java, null) }
        val t = Thread(r2)
        t.isDaemon = true
        t.start()

        Thread.sleep(1000L)

        WebAddon.hideUI()
        WebAddon.showUI()
        assertTrue(WebAddon.stage.isShowing)
    }
}