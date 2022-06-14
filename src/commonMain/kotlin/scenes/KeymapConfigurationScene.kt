package scenes

import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.uiButton
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.name
import com.soywiz.korio.async.launchImmediately
import config.ExcludeFromJacocoGeneratedReport
import extensions.keymapTextInputs

@ExcludeFromJacocoGeneratedReport("Won't test scenes, focus is on logic testing")
class KeymapConfigurationScene : Scene() {
    companion object{
        const val BACK_BUTTON = "BackButton"
        const val BACK_LABEL = "Back"
    }

    override suspend fun Container.sceneInit() {
        keymapTextInputs()
        uiButton(BACK_LABEL).onClick { launchImmediately { sceneContainer.changeTo<MainScene>() } }!!.name(BACK_BUTTON)
    }
}
