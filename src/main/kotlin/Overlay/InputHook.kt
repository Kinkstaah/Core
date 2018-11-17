package Overlay

import org.jnativehook.GlobalScreen
import org.jnativehook.NativeHookException
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import java.io.File
import java.util.logging.Level
import java.util.logging.Logger

/**
 * First attempt at using Kotlin.
 */
data class KeyState(var shiftHeld: Boolean,
                    var ctrlHeld: Boolean,
                    var altHeld: Boolean,
                    var key: Int)

data class Modifiers(var shiftHeld: Boolean,
                     var ctrlHeld: Boolean,
                     var altHeld: Boolean
                    )
{
    fun isHeld(): String
    {
        val stringBuilder = StringBuilder()
        stringBuilder.append(when {ctrlHeld -> "CTRL + "; else -> ""})
        stringBuilder.append(when {shiftHeld -> "SHIFT + "; else -> ""})
        stringBuilder.append(when {altHeld -> "ALT + "; else -> ""})
        return stringBuilder.toString()
    }
}

class InputHook
{
    companion object
    {
        var keyState = KeyState(false, false, false, 0)
        var modifiers = Modifiers(false, false, false)

        fun main()
        {
            val logger = Logger.getLogger(GlobalScreen::class.java.getPackage().name)
            logger.level = Level.OFF
            try
            {
                GlobalScreen.registerNativeHook()
                GlobalScreen.addNativeKeyListener(object : NativeKeyListener
                {

                    override fun nativeKeyTyped(nativeEvent: NativeKeyEvent) {}

                    override fun nativeKeyReleased(nativeEvent: NativeKeyEvent)
                    {
                        when (nativeEvent.keyCode)
                        {
                            NativeKeyEvent.VC_SHIFT -> modifiers.shiftHeld = false
                            NativeKeyEvent.VC_CONTROL -> modifiers.ctrlHeld = false
                            NativeKeyEvent.VC_ALT -> modifiers.altHeld = false
                            else                      -> handleKey(nativeEvent.keyCode)
                        }
                    }

                    private fun handleKey(keyCode: Int)
                    {
                        val keyText = NativeKeyEvent.getKeyText(keyCode)
                        keyState = KeyState(modifiers.shiftHeld, modifiers.ctrlHeld, modifiers.altHeld, keyCode)
                        KeyHandler.check(keyState, System.getenv("LOCALAPPDATA") + File.separator + "PAL" + File.separator + "keybinds.pal")

                    }

                    override fun nativeKeyPressed(nativeEvent: NativeKeyEvent)
                    {
                        when (nativeEvent.keyCode)
                        {
                            NativeKeyEvent.VC_SHIFT -> modifiers.shiftHeld = true
                            NativeKeyEvent.VC_CONTROL -> modifiers.ctrlHeld = true
                            NativeKeyEvent.VC_ALT -> modifiers.altHeld = true
                        }
                    }
                })
            }
            catch (e: NativeHookException)
            {
                e.printStackTrace()
            }

        }
    }
}

