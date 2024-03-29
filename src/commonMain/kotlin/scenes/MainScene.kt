package scenes

import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.service.storage.storage
import com.soywiz.korge.ui.uiButton
import com.soywiz.korge.view.SContainer
import com.soywiz.korge.view.position
import com.soywiz.korio.async.launchImmediately
import config.ExcludeFromJacocoGeneratedReport
import config.GameConfig
import config.Keymap
import module.MainModule

/**
 * Main menu scene to start the game or to configure it or to buy power ups.
 */
@ExcludeFromJacocoGeneratedReport("Won't test scenes, focus is on logic testing")
class MainScene: Scene(){

	companion object{
		const val START_GAME_LABEL = "Start game"
		const val CHANGE_KEYMAP_LABEL = "Change keys mapping"
	}

	override suspend fun SContainer.sceneInit() {
		initKeymap()
		uiButton(START_GAME_LABEL) {
			position(MainModule.virtualWidth/2, 20)
		}.onPress { launchImmediately { sceneContainer.changeTo<LevelConfigurationScene>() } }
		uiButton(CHANGE_KEYMAP_LABEL) {
			position(MainModule.virtualWidth/2, 80)
			scaledWidth = 200.0
		}.onPress { launchImmediately { sceneContainer.changeTo<KeymapConfigurationScene>() } }
	}

	private fun initKeymap(){
		Keymap.keymapFile = views.storage
		GameConfig.keyMap.loadKeyMap()
	}
}