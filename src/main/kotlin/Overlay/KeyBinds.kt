package Overlay

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.io.File
import java.util.*

/**
 * Available Binds:
 * SHOW
 * HIDE
 * OPTIONS
 * CLOSE_CURRENT
 * CLOSE_ALL
 * CUSTOM_{NUM}
 *
 * CUSTOM:
 * E.g. send a certain byte to a socket of a program.
 * E.g. launch a certain program.
 */
data class KeyBind(var keyState: KeyState, var function: String)

data class KeyBinds(var binds: Array<KeyBind>)
{
    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as KeyBinds

        if (!Arrays.equals(binds, other.binds)) return false

        return true
    }

    override fun hashCode(): Int
    {
        return Arrays.hashCode(binds)
    }

    fun exporter(location: String)
    {
        val kb_file = File(location)

        if (kb_file.exists())
            kb_file.delete()

        val objectMapper = ObjectMapper()
        objectMapper.writeValue(kb_file, this)
    }

    companion object
    {
        fun importer(location: String): KeyBinds?
        {
            val kb_file = File(location)

            if (kb_file.exists())
            {
                val objectMapper = ObjectMapper()
                        .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                        .registerModule(KotlinModule())
                return objectMapper.readValue(kb_file, KeyBinds::class.java)

            }
            return null
        }
    }
}

