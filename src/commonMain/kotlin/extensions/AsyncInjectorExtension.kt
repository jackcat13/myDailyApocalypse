package extensions

import com.soywiz.korinject.AsyncInjector
import scenes.MainLevelScene
import scenes.MainScene

fun AsyncInjector.mapPrototypes() {
    mapPrototype { MainScene() }
    mapPrototype { MainLevelScene() }
}