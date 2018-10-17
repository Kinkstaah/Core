package Overlay

import Data.PALdata
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.runBlocking
import java.io.File

/**
 * Uses Kotlin Coroutines
 */
class KeyHandler
{
    companion object
    {
        fun check(keyState: KeyState, location: String)
        {
            // Load Keybinds
            println(location)
            val keyBinds = KeyBinds.importer(location)
            when (keyBinds)
            {
                null -> throw Error("keybinds.pal doesn't exist or can't be parsed!")
                else -> checkIfKeyBindIsValid(keyBinds, keyState)
            }
        }

        private fun checkIfKeyBindIsValid(keyBinds: KeyBinds, keyState: KeyState) = runBlocking {
            launch {
                for (key in keyBinds.binds)
                {
                    if (keyState == key.keyState)
                    {
                        useFunction(key.function)
                        // Execute Function
                        println("Found this")
                        break
                    }
                }
            }
        }

        private fun useFunction(function: String)
        {
            when (function)
            {
                "SHOW" -> SHOW()
                "HIDE" -> HIDE()
                "OPTIONS" -> OPTIONS()
                "CLOSE_CURRENT" -> CLOSE_CURRENT()
                "CLOSE_ALL" -> CLOSE_ALL()
                else -> customFunction(function)
            }
        }

        private fun customFunction(function: String)
        {
            println(function)
            // Parse custom function somehow?
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        private fun CLOSE_ALL()
        {
            println("CLOSE_ALL")
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        private fun CLOSE_CURRENT()
        {
            println("CLOSE_CURRENT")
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        private fun OPTIONS()
        {
            println("OPTIONS")
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        private fun HIDE()
        {
            println("HIDE")
            if (WebAddon.visible)
                WebAddon.hideUI()
            else
                WebAddon.showUI()
        }

        private fun SHOW()
        {
            println("SHOW")
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}