package Overlay

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import javafx.application.Application
import javafx.application.Platform
import javafx.scene.web.WebView
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import javafx.stage.StageStyle
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import java.io.File
import kotlinx.coroutines.experimental.javafx.JavaFx as Main
import java.net.URL
import java.util.*
import javax.imageio.ImageIO
import javax.imageio.ImageReader




/**
 *
 */
class WebAddonController(var data: WebAddonData) : Initializable
{
    override fun initialize(location: URL?, resources: ResourceBundle?)
    {
        Platform.runLater { webView.engine.load(data.url) }
    }

    @FXML
    lateinit var webView: WebView
}

//TODO: Add Favicon Location
data class WebAddonData(var url: String,
                        var x_dimension: Double,
                        var y_dimension: Double,
                        var width: Double,
                        var height: Double,
                        var name: String)

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

        fun createUI(primaryStage: Stage, data: WebAddonData)
        {
            var primaryStage = primaryStage
            primaryStage = Stage()
            val fxmlLoader = FXMLLoader()
            fxmlLoader.setController(WebAddonController(data))
            val root = fxmlLoader.load<Parent>(javaClass.getResource("/overlay/WebAddonUI.fxml").openStream())
            primaryStage.title = "PAL: ${data.name}"
            primaryStage.initStyle(StageStyle.UTILITY)
            primaryStage.icons.add(Image(javaClass.getResource("/witch.png").toString()))
            val scene = Scene(root, data.width, data.height)
            scene.stylesheets.add("layout_settings.css")
            primaryStage.scene = scene
            primaryStage.isAlwaysOnTop = true
            stage = primaryStage
            stage.show()
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
        var data = objectMapper.readValue(url, Array<WebAddonData>::class.java)

        data.forEach { WebAddon.createUI(Stage(), it) }

    }

}

fun main(args: Array<String>)
{
    GlobalScope.launch {
        InputHook.main()
    }
    //Application.launch(WebAddon::class.java, *args)
    Application.launch(SideBar::class.java, *args)
}

