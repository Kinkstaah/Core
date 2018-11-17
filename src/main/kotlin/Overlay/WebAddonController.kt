package Overlay

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import javafx.application.Application
import javafx.application.Platform
import javafx.beans.value.ChangeListener
import javafx.beans.value.ObservableValue
import javafx.scene.web.WebView
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.AnchorPane
import javafx.scene.paint.Color
import javafx.stage.Stage
import javafx.stage.StageStyle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import kotlinx.coroutines.javafx.JavaFx as Main
import java.net.URL
import java.util.*
import javax.imageio.ImageIO
import javax.imageio.ImageReader


/**
 *
 */
class WebAddonController(var data: OverlayAddonData) : Initializable
{
    override fun initialize(location: URL?, resources: ResourceBundle?)
    {
        Platform.runLater { webView.engine.load(data.url) }
    }

    @FXML
    lateinit var webView: WebView
}

data class OverlayAddonData(var url: String,
                            var x: Int,
                            var y: Int,
                            var width: Double,
                            var height: Double,
                            var name: String,
                            var favicon: String,
                            var description: String,
                            var abbreviation: String
                           )
{
    override fun toString(): String
    {
        return name;
    }
}

//TODO: Make Generic for any HTML
class WebAddon : Application()
{
    companion object
    {
        lateinit var stage: Stage
        var visible: Boolean = false


        fun hideUI()
        {
            GlobalScope.launch(Dispatchers.Main) {
                stage.opacity = 0.0
                visible = false
            }
        }

        fun showUI()
        {
            GlobalScope.launch(Dispatchers.Main) {
                stage.opacity = 1.0
                visible = true
            }
        }

        fun createUI(primaryStage: Stage, data: OverlayAddonData)
        {
            var webView = WebView()
            webView.engine.load(data.url)

            var primaryStage = primaryStage
            primaryStage = Stage()
            //val fxmlLoader = FXMLLoader()
            //fxmlLoader.setController(WebAddonController(data))
            //val root = fxmlLoader.load<Parent>(WebAddon::class.java.getResource("/Overlay/WebAddonUI.fxml").openStream())
            primaryStage.title = "PAL: ${data.name}"
            primaryStage.initStyle(StageStyle.UTILITY)
            primaryStage.icons.add(Image(WebAddon::class.java.getResource("/witch.png").toString()))
            val scene = Scene(webView, data.width, data.height)
            scene.stylesheets.add("layout_settings.css")
            primaryStage.scene = scene
            primaryStage.isAlwaysOnTop = true
            stage = primaryStage
            stage.show()
            OverlayData.addWindow(Window(stage, data.name))
            visible = true
        }
    }

    // JavaFX init
    @Throws(Exception::class)
    override fun start(primaryStage: Stage)
    {
        val url = URL("https://raw.githubusercontent.com/POE-Addon-Launcher/Curated-Repo/master/websites.json")

        var objectMapper = ObjectMapper()
                .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                .registerModule(KotlinModule())
        var data = objectMapper.readValue(url, Array<OverlayAddonData>::class.java)

        data.forEach { WebAddon.createUI(Stage(), it) }

    }

}