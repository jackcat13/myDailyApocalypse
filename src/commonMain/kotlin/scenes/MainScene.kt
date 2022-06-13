package scenes

import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.service.storage.storage
import com.soywiz.korge.ui.uiButton
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import com.soywiz.korio.async.launchImmediately
import config.GameConfig
import config.Keymap
import module.MainModule

class MainScene: Scene(){

	companion object{
		const val START_GAME_LABEL = "Start game"
		const val CHANGE_KEYMAP_LABEL = "Change keys mapping"
	}

	override suspend fun Container.sceneInit() {
		initKeymap()
		val image = uiButton(START_GAME_LABEL) {
			position(MainModule.virtualWidth/2, 20)
		}
		image.onClick { launchImmediately { sceneContainer.changeTo<MainLevelScene>() } }
		uiButton(CHANGE_KEYMAP_LABEL) {
			position(MainModule.virtualWidth/2, 80)
			scaledWidth = 200.0
		}.onClick { launchImmediately { sceneContainer.changeTo<KeymapConfigurationScene>() } }
	}

	private fun initKeymap(){
		Keymap.keymapFile = views.storage
		GameConfig.keyMap.loadKeyMap()
	}
}