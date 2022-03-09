package scenes

import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.anchor
import com.soywiz.korge.view.image
import com.soywiz.korge.view.position
import com.soywiz.korge.view.scale
import com.soywiz.korim.format.readBitmap
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.file.std.resourcesVfs
import module.MainModule

class MainScene: Scene(){
	override suspend fun Container.sceneInit() {
		val image = image(resourcesVfs["korge.png"].readBitmap()) {
			anchor(.5, .5)
			scale(.8)
			position(MainModule.virtualWidth/2, MainModule.virtualHeight/2)
		}
		image.onClick { launchImmediately { sceneContainer.changeTo<GameScene>() } }
	}
}