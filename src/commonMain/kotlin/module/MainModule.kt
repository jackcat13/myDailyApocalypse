package module

import com.soywiz.korge.scene.Module
import com.soywiz.korim.color.Colors
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.SizeInt
import extensions.mapPrototypes
import scenes.MainScene

object MainModule : Module() {
	const val virtualWidth = 1280
	const val virtualHeight = 720
	override val mainScene = MainScene::class
	override val size = SizeInt(virtualWidth, virtualHeight) // Virtual Size
	override val bgcolor = Colors["#2b2b2b"]
	override val fullscreen = true

	override suspend fun AsyncInjector.configure() {
		mapPrototypes()
	}
}