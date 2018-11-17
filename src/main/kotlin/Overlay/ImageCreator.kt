package Overlay

import java.awt.Font
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import java.util.*

/**
 * https://stackoverflow.com/questions/19693710/drawing-a-character-with-a-specific-size-in-java
 */
class ImageCreator
{
    companion object
    {
        fun drawString(g: Graphics, str: String, x: Double, y: Double, hAlign: String, vAlign: String)
        {

            var metrics = g.getFontMetrics();
            var dX = x.toInt()
            var dY = y.toInt()

            when (hAlign.toLowerCase())
            {
                "center" -> dX -= (metrics.getStringBounds(str, g).width /2.0).toInt()
                "right" -> dX -= (metrics.getStringBounds(str, g).width).toInt()
            }

            when (vAlign.toLowerCase())
            {
                "center" -> dY += (metrics.ascent / 2.0).toInt()
                "top" -> dY += metrics.ascent
            }
            g.drawString(str, dX, dY)
        }

        fun pickOptimalFontSize(g: Graphics2D, title: String, width: Int, height: Int, baseFont: Font): Font
        {
            lateinit var rect: Rectangle2D

            var fontSize = 32 //initial value
            var font: Font
            do
            {
                fontSize-=1;
                font = baseFont.deriveFont(fontSize);
                rect = getStringBoundsRectangle2D(g, title, font);
            }
            while (rect.getWidth() >= width || rect.getHeight() >= height)
            return font;
        }

        fun getStringBoundsRectangle2D(g: Graphics, title: String, font: Font): Rectangle2D
        {
            g.setFont(font)
            val fontMetrics = g.getFontMetrics()
            val rect = fontMetrics.getStringBounds(title, g)
            return rect
        }

        fun createRandomColour(): Int
        {
            val rng = Random()
            return rng.nextInt(16751359 - 16711935 )
        }

        fun nameSanitizer(text: String): String
        {
            var str = text
            str = str.replace("PoE", "")
            str = str.replace("POE", "")
            str = str.replace("poe", "")
            str = str.replace(",", "")
            str = str.replace(" ", "")
            str = str.replace(Regex("([a-z])"), "")
            str = str.replace("//", "/r/")
            println("New: $str Old: $text")

            when (true)
            {
                str.length <= 3 -> return str;
                else -> return str.substring(0,3)
            }
        }
    }

}