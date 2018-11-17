package Overlay

import kotlinx.coroutines.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.awt.Robot
import java.awt.event.KeyEvent

/**
 *
 */
class InputHookTest
{
    @Before
    fun before()
    {
        GlobalScope.launch {
            InputHook.main()
        }
    }

    val captureOnDemand = sequence {
        // Start capturing outputs.
        var prev = InputHook.keyState

        while (true)
        {
            println(prev)
            yield(prev)
            if (prev.key != InputHook.keyState.key)
            {
                prev = InputHook.keyState

            }
        }

    }

    @Test
    fun testShift()
    {
        captureOnDemand.iterator().next()
        val r = Robot()
        r.keyPress(KeyEvent.VK_SHIFT)
        r.keyPress(KeyEvent.VK_CAPS_LOCK)
        r.keyRelease(KeyEvent.VK_CAPS_LOCK)
        // Allow time for it to register a keypress.
        Thread.sleep(100L)
        // Iterate for the next keypress to be read.
        var result = captureOnDemand.iterator().next()

        // Release Key and Reset Capslock
        r.keyRelease(KeyEvent.VK_SHIFT)
        r.keyPress(KeyEvent.VK_CAPS_LOCK)
        r.keyRelease(KeyEvent.VK_CAPS_LOCK)

        assert(result.shiftHeld)
    }

    @Test
    fun testAlt()
    {
        captureOnDemand.iterator().next()
        val r = Robot()
        r.keyPress(KeyEvent.VK_ALT)
        r.keyPress(KeyEvent.VK_CAPS_LOCK)
        r.keyRelease(KeyEvent.VK_CAPS_LOCK)
        // Allow time for it to register a keypress.
        Thread.sleep(100L)
        // Iterate for the next keypress to be read.
        var result = captureOnDemand.iterator().next()

        // Release Key and Reset Capslock
        r.keyRelease(KeyEvent.VK_ALT)
        r.keyPress(KeyEvent.VK_CAPS_LOCK)
        r.keyRelease(KeyEvent.VK_CAPS_LOCK)

        assert(result.altHeld)
    }

    @Test
    fun tesCtrl()
    {
        captureOnDemand.iterator().next()
        val r = Robot()
        r.keyPress(KeyEvent.VK_CONTROL)
        r.keyPress(KeyEvent.VK_CAPS_LOCK)
        r.keyRelease(KeyEvent.VK_CAPS_LOCK)
        // Allow time for it to register a keypress.
        Thread.sleep(100L)
        // Iterate for the next keypress to be read.
        var result = captureOnDemand.iterator().next()

        // Release Key and Reset Capslock
        r.keyRelease(KeyEvent.VK_CONTROL)
        r.keyPress(KeyEvent.VK_CAPS_LOCK)
        r.keyRelease(KeyEvent.VK_CAPS_LOCK)

        assert(result.ctrlHeld)
    }

    @Test
    fun testKeyStroke()
    {
        captureOnDemand.iterator().next()
        val r = Robot()
        r.keyPress(KeyEvent.VK_CAPS_LOCK)
        r.keyRelease(KeyEvent.VK_CAPS_LOCK)
        // Allow time for it to register a keypress.
        Thread.sleep(100L)
        // Iterate for the next keypress to be read.
        var result = captureOnDemand.iterator().next()

        // Release Key and Reset Capslock
        r.keyPress(KeyEvent.VK_CAPS_LOCK)
        r.keyRelease(KeyEvent.VK_CAPS_LOCK)

        assertEquals(58, result.key)
    }
}