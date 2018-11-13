package Overlay

import GUI.PopUp.UpdatedPopup
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
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Background
import javafx.scene.paint.Color
import javafx.stage.StageStyle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.awt.*
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
    companion object
    {
        lateinit var stage: Stage
    }

    override fun start(primaryStage: Stage?)
    {
        GridDisplay.gridDisplay = GridDisplay(10, 4)
        stage = Stage()
        //val fxmlLoader = FXMLLoader()
        //val root = fxmlLoader.load<Parent>(javaClass.getResource("/overlay/GridDisplay.fxml").openStream())
        stage.initStyle(StageStyle.UNDECORATED)
        stage.icons.add(Image(javaClass.getResource("/witch.png").toString()))
        val scene = Scene(GridDisplay.gridDisplay!!.display, 200.0, 500.0)
        scene.stylesheets.add("layout_settings.css")
        stage.initStyle(StageStyle.TRANSPARENT)
        scene.fill = Color.TRANSPARENT
        stage.scene = scene
        stage.isAlwaysOnTop = true
        stage.x = 0.0
        stage.show()
    }
}




class SideBarController : Initializable
{
    val imageSize = 32

    @FXML
    lateinit var iconGridPane: GridPane

    override fun initialize(location: URL?, resources: ResourceBundle?)
    {
        iconGridPane.background = Background.EMPTY
        iconGridPane.style = "-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10;"

        val url = URL("https://raw.githubusercontent.com/POE-Addon-Launcher/Curated-Repo/master/websites.json")

        var objectMapper = ObjectMapper()
                .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                .registerModule(KotlinModule())
        var data = objectMapper.readValue(url, Array<OverlayAddonData>::class.java)


        GlobalScope.launch {
            for (c in 0 until data.size)
            {
                GlobalScope.launch(Dispatchers.Main) {
                    iconGridPane.add(setFavicon(data[c].favicon, data[c].abbreviation), data[c].y, data[c].x)
                }
            }
        }
    }


    fun setFavicon(favicon: String, name: String): ImageView
    {
        try
        {
            var image: Image = SwingFXUtils.toFXImage(ImageIO.read(URL(favicon)), null)
            var img = ImageView(image)
            img.fitHeight = imageSize.toDouble()
            img.fitWidth = imageSize.toDouble()
            return img
        }
        catch (e: Exception)
        {
            return createImageWithText(name)
        }
        catch (e: IOException)
        {
            return createImageWithText(name)
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

    var xOffset = 0.0
    var yOffset = 0.0

    fun onMouseDragged(mouseEvent: MouseEvent)
    {
        SideBar.stage.x = mouseEvent.screenX + xOffset
        SideBar.stage.y = mouseEvent.screenY + yOffset
    }

    fun onMousePressed(mouseEvent: MouseEvent)
    {
        xOffset = SideBar.stage.x - mouseEvent.screenX
        yOffset = SideBar.stage.y - mouseEvent.screenY
    }

    fun onMouseExit(mouseEvent: MouseEvent)
    {
        SideBar.stage.opacity = 0.01
    }

    fun onMouseEnter(mouseEvent: MouseEvent)
    {
        SideBar.stage.opacity = 1.0
    }

}