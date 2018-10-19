package Overlay

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import javafx.application.Application
import javafx.embed.swing.SwingFXUtils
import javafx.stage.Stage
import javafx.scene.layout.GridPane
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.stage.StageStyle
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import java.awt.Font
import java.awt.FontMetrics
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.geom.Rectangle2D
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.net.URL
import java.util.*
import javax.imageio.ImageIO
import javax.imageio.stream.ImageInputStream


/**
 *
 */
class SideBar : Application()
{
    override fun start(primaryStage: Stage?)
    {
        var primaryStage = primaryStage
        primaryStage = Stage()
        val fxmlLoader = FXMLLoader()
        val root = fxmlLoader.load<Parent>(javaClass.getResource("/overlay/GridDisplay.fxml").openStream())
        primaryStage.initStyle(StageStyle.UNDECORATED)
        primaryStage.icons.add(Image(javaClass.getResource("/witch.png").toString()))
        val scene = Scene(root, 200.0, 500.0)
        scene.stylesheets.add("layout_settings.css")
        primaryStage.scene = scene
        primaryStage.isAlwaysOnTop = true
        primaryStage.show()
    }
}

class SideBarController : Initializable
{
    val imageSize = 32

    @FXML
    lateinit var iconGridPane: GridPane

    override fun initialize(location: URL?, resources: ResourceBundle?)
    {
        val url = URL("https://raw.githubusercontent.com/POE-Addon-Launcher/Curated-Repo/master/websites.json")

        var objectMapper = ObjectMapper()
                .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                .registerModule(KotlinModule())
        var data = objectMapper.readValue(url, Array<WebAddonData>::class.java)

        for (c in 0 until data.size)
        {
            GlobalScope.launch(Dispatchers.Main) {
                when (true)
                {
                    c in 0..10 -> iconGridPane.add(setFavicon(data[c]), 0, c)
                    c in 11..20 -> iconGridPane.add(setFavicon(data[c]), 1, c-11)
                    c in 21..30 -> iconGridPane.add(setFavicon(data[c]), 2, c-21)
                }
            }
        }
    }

    fun setFavicon(data: WebAddonData): ImageView
    {
        try
        {
            var image: Image = SwingFXUtils.toFXImage(ImageIO.read(URL(data.url + "/favicon.ico")), null)
            var img = ImageView(image)
            img.fitHeight = imageSize.toDouble()
            img.fitWidth = imageSize.toDouble()
            return img
        }
        catch (e: Exception)
        {
            return createImageWithText(ImageCreator.nameSanitizer(data.name))
        }
        catch (e: IOException)
        {
            return createImageWithText(ImageCreator.nameSanitizer(data.name))
        }
    }

    fun createImageWithText(char: String): ImageView
    {
        val image = BufferedImage(imageSize, imageSize, BufferedImage.TYPE_INT_RGB)
        // Fill it with some color.
        val randomColour = ImageCreator.createRandomColour()
        var intArray: IntArray = IntArray(imageSize * imageSize)
        for (i in 0 until imageSize)
            intArray[i] = randomColour

        image.setRGB(0,0,imageSize,imageSize,intArray,0,0)

        var base = Font("Arial", Font.BOLD, 24)
        image.graphics.font = ImageCreator.pickOptimalFontSize(image.createGraphics(), char, 64, 64, base)
        ImageCreator.drawString(image.createGraphics(), char, imageSize/2.0, imageSize/2.0, "center", "center")


        var fxImage: Image = SwingFXUtils.toFXImage(image, null)
        return ImageView(fxImage)
    }
}

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

        fun getStringBoundsRectangle2D (g: Graphics, title: String, font: Font): Rectangle2D
        {
            g.setFont(font)
            val fontMetrics = g.getFontMetrics()
            val rect = fontMetrics.getStringBounds(title, g)
            return rect
        }

        fun createRandomColour(): Int
        {
            val rng = Random()
            lateinit var hexR: String
            lateinit var hexG: String
            lateinit var hexB: String
            return rng.nextInt(15777050 - 11963776 )
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