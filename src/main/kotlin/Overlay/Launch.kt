package Overlay

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import javafx.application.Application
import javafx.stage.Stage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.net.URL

/**
 *
 */
fun main(args: Array<String>)
{
    GlobalScope.launch {
        InputHook.main()
    }

    OverlayData.init()

    val url = URL("https://raw.githubusercontent.com/POE-Addon-Launcher/Curated-Repo/master/websites.json")

    var objectMapper = ObjectMapper()
            .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
            .registerModule(KotlinModule())
    GridDisplay.data = objectMapper.readValue(url, Array<OverlayAddonData>::class.java)

    GridDisplay.data.forEach { GridDisplay.dataMap[it.name] = it }

    //Application.launch(WebAddon::class.java, *args)
    //Application.launch(SideBar::class.java, *args)
    var sideBar = SideBar()
    sideBar.start(Stage())
}