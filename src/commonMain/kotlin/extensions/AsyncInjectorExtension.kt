package extensions

import com.soywiz.korinject.AsyncInjector
import scenes.KeymapConfigurationScene
import scenes.MainLevelScene
import scenes.MainScene

fun AsyncInjector.mapPrototypes() {
    mapPrototype { MainScene() }
    mapPrototype { KeymapConfigurationScene() }
    mapPrototype { MainLevelScene() }
}