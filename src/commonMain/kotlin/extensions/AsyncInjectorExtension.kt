package extensions

import com.soywiz.korinject.AsyncInjector
import config.ExcludeFromJacocoGeneratedReport
import entities.Player
import scenes.KeymapConfigurationScene
import scenes.LevelConfigurationScene
import scenes.MainLevelScene
import scenes.MainScene

/**
 * Map scenes for korge engine.
 */
@ExcludeFromJacocoGeneratedReport("Function used to map korge scenes. Unrelevant to test it.")
fun AsyncInjector.mapPrototypes() {
    mapPrototype { MainScene() }
    mapPrototype { LevelConfigurationScene() }
    mapPrototype { KeymapConfigurationScene() }
    mapPrototype { MainLevelScene(get()) }
}
