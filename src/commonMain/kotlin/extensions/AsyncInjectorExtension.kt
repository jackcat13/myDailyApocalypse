package extensions

import com.soywiz.korinject.AsyncInjector
import scenes.GameScene
import scenes.MainScene

fun AsyncInjector.mapPrototypes() {
    mapPrototype { MainScene() }
    mapPrototype { GameScene() }
}