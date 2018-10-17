package Overlay

import javafx.application.Application
import javafx.application.Platform
import javafx.scene.web.WebView
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage
import javafx.stage.StageStyle
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.GlobalScope
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.javafx.JavaFx as Main
import java.net.URL
import java.util.*


/**
 *
 */
class WebAddonController : Initializable
{
    override fun initialize(location: URL?, resources: ResourceBundle?)
    {
        Platform.runLater { webView.engine.load("https://siveran.github.io/calc.html") }
    }

    @FXML
    lateinit var webView: WebView

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
    }

    @Throws(Exception::class)
    override fun start(primaryStage: Stage)
    {
        var primaryStage = primaryStage
        primaryStage = Stage()
        val fxmlLoader = FXMLLoader()
        val root = fxmlLoader.load<Parent>(javaClass.getResource("/overlay/WebAddonUI.fxml").openStream())
        primaryStage.title = "PAL Addon"
        primaryStage.initStyle(StageStyle.UTILITY)
        primaryStage.icons.add(Image(javaClass.getResource("/witch.png").toString()))
        val scene = Scene(root, 855.0, 825.0)
        scene.stylesheets.add("layout_settings.css")
        primaryStage.scene = scene
        primaryStage.isAlwaysOnTop = true
        stage = primaryStage
        stage.show()
        visible = true
    }

}

fun main(args: Array<String>)
{
    GlobalScope.launch {
        InputHook.main()
    }



    Application.launch(WebAddon::class.java, *args)
}

