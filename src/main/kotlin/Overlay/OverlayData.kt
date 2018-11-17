package Overlay

import com.sun.jna.Native
import com.sun.jna.platform.win32.User32
import javafx.stage.Stage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 *
 */
class OverlayData
{
    companion object
    {
        lateinit var windows: ArrayList<Window>

        fun init()
        {
            windows = ArrayList()
        }

        fun getWindow(name: String): Window?
        {
            windows.forEach {if (it.name == name) return it }
            return null
        }

        fun addWindow(window: Window)
        {
            windows.add(window)
        }

        fun hideAll()
        {
            windows.forEach { GlobalScope.launch(Dispatchers.Main) { it.stage.opacity = 0.0 } }
        }

        fun showAll()
        {
            windows.forEach { GlobalScope.launch(Dispatchers.Main) { it.stage.opacity = 1.0 } }
        }

        fun closeAll()
        {
            windows.forEach { GlobalScope.launch(Dispatchers.Main) { it.stage.close(); windows.remove(it) } }
        }

        fun closeCurrentActiveWindow()
        {
            val activ_win = getActiveWindow()
            if (activ_win.contains("PAL: "))
            {
                var addonName = activ_win.replace("PAL: ", "")
                var window = getWindow(addonName)
                if (window != null)
                {
                    println("Attempting to close: ${window.name}")
                    GlobalScope.launch(Dispatchers.Main) { window.stage.close(); windows.remove(window) }
                }
            }
        }

        fun getActiveWindow(): String
        {
            val MAX_TITLE_LENGTH = 1024
            val buffer = CharArray(MAX_TITLE_LENGTH * 2)
            val hwnd = User32.INSTANCE.GetForegroundWindow()
            User32.INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH)
            return Native.toString(buffer)
        }
    }
}

data class Window(var stage: Stage, val name: String)