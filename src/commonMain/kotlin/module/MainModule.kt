package module

import com.soywiz.korge.scene.Module
import com.soywiz.korim.color.Colors
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korma.geom.SizeInt
import config.ExcludeFromJacocoGeneratedReport
import extensions.mapPrototypes
import scenes.MainScene

/**
 * Main game module for korge engine.
 */
object MainModule : Module() {
	const val virtualWidth = 1920
	const val virtualHeight = 1080
	override val mainScene = MainScene::class
	override val size = SizeInt(virtualWidth, virtualHeight) // Virtual Size
	override val bgcolor = Colors["#2b2b2b"]

	@ExcludeFromJacocoGeneratedReport("Function used to map korge scenes. Unrelevant to test it.")
	override suspend fun AsyncInjector.configure() {
		mapPrototypes()
	}
}