package Overlay

import Overlay.GridDisplay.Companion.gridDisplay
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import javafx.application.Application
import javafx.embed.swing.SwingFXUtils
import javafx.event.EventHandler
import javafx.fxml.FXMLLoader
import javafx.scene.Group
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Label
import javafx.scene.control.TextField
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.input.MouseEvent
import javafx.scene.layout.BorderPane
import javafx.scene.layout.HBox
import javafx.stage.Stage
import javafx.scene.paint.Color.STEELBLUE
import javafx.scene.layout.TilePane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle
import javafx.stage.StageStyle
import java.awt.Font
import java.awt.image.BufferedImage
import java.io.IOException
import java.lang.Exception
import java.net.URL
import javax.imageio.ImageIO


/**
 * https://stackoverflow.com/questions/23272924/dynamically-add-elements-to-a-fixed-size-gridpane-in-javafx#23320691
 */
class GridDisplay(nRows: Int, nCols: Int)
{
    companion object
    {
        var gridDisplay: GridDisplay? = null
        val ELEMENT_SIZE = 32.0
        val GAP = ELEMENT_SIZE / 10
        lateinit var data: Array<OverlayAddonData>
        var dataMap = HashMap<String, OverlayAddonData>()
    }

    private val tilePane = TilePane()
    val display = Group(tilePane)
    private var nRows: Int = 0
    private var nCols: Int = 0
    var xOffset = 0.0
    var yOffset = 0.0

    init
    {
        setListners()
        tilePane.style = "-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 10;"
        tilePane.hgap = GAP
        tilePane.vgap = GAP
        setColumns(nCols)
        setRows(nRows)
    }

    fun setListners()
    {
        tilePane.onMouseDragged = EventHandler()
        {
            mouseEvent: MouseEvent ->
            SideBar.stage.x = mouseEvent.screenX + xOffset
            SideBar.stage.y = mouseEvent.screenY + yOffset
        }

        tilePane.onMousePressed = EventHandler()
        {
            mouseEvent: MouseEvent ->
            xOffset = SideBar.stage.x - mouseEvent.screenX
            yOffset = SideBar.stage.y - mouseEvent.screenY
        }
        tilePane.onMouseExited = EventHandler { SideBar.stage.opacity = 0.01 }
        tilePane.onMouseEntered = EventHandler { SideBar.stage.opacity = 1.0 }
    }

    fun setColumns(newColumns: Int)
    {
        nCols = newColumns
        tilePane.prefColumns = nCols
        createElements()
    }

    fun setRows(newRows: Int)
    {
        nRows = newRows
        tilePane.prefRows = nRows
        createElements()
    }


    private fun createElements()
    {
        tilePane.children.clear()
        for (i in 0 until nCols)
        {
            for (j in 0 until nRows)
            {
                var loop_num = i*nRows +j
                println("$loop_num: ${data.size} I: $i J: $j")
                if (loop_num < data.size)
                {
                    var img = setFavicon( data[loop_num].favicon,  data[loop_num].abbreviation)
                    img.id = data[loop_num].name
                    img.setOnMouseClicked {
                        val addon = getWebAddonByName(img.id)
                        if (addon != null)
                        {
                            WebAddon.createUI(Stage(), addon)
                        }
                    }
                    tilePane.children.add(img)
                }
            }
        }
    }

    private fun getWebAddonByName(id: String): OverlayAddonData?
    {
        println("$id: ${dataMap[id]}")
        return dataMap[id]
    }

    fun setFavicon(favicon: String, name: String): ImageView
    {
        try
        {
            var image: Image = SwingFXUtils.toFXImage(ImageIO.read(URL(favicon)), null)
            var img = ImageView(image)
            img.fitHeight = ELEMENT_SIZE
            img.fitWidth = ELEMENT_SIZE
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
        val image = BufferedImage(ELEMENT_SIZE.toInt(), ELEMENT_SIZE.toInt(), BufferedImage.TYPE_INT_RGB)
        // Fill it with some color.
        val randomColour = ImageCreator.createRandomColour()
        var intArray: IntArray = IntArray((ELEMENT_SIZE * ELEMENT_SIZE).toInt())
        for (i in 0 until ELEMENT_SIZE.toInt())
            intArray[i] = randomColour

        image.setRGB(0,0, ELEMENT_SIZE.toInt(), ELEMENT_SIZE.toInt(),intArray,0,0)

        var base = Font("Arial", Font.BOLD, 24)
        image.graphics.font = ImageCreator.pickOptimalFontSize(image.createGraphics(), char, 64, 64, base)
        ImageCreator.drawString(image.createGraphics(), char, ELEMENT_SIZE/2.0, ELEMENT_SIZE/2.0, "center", "center")


        var fxImage: Image = SwingFXUtils.toFXImage(image, null)
        return ImageView(fxImage)
    }
}