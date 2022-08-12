package scenes

import com.soywiz.korge.animate.launchAnimate
import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.uiButton
import com.soywiz.korge.view.SContainer
import com.soywiz.korge.view.container
import com.soywiz.korge.view.position
import com.soywiz.korio.async.launchImmediately
import config.ExcludeFromJacocoGeneratedReport
import entities.Soldier
import module.MainModule
import utils.EntitiesBuilder.soldier

const val PLAY_BUTTON_LABEL = "Play"

@ExcludeFromJacocoGeneratedReport("Won't test scenes, focus is on logic testing")
class LevelConfigurationScene(): Scene() {

    override suspend fun SContainer.sceneInit() {
        var selectedPlayer = Soldier::class
        container{

            soldier().initDraw(this, 20.0, 20.0)
        }.onClick{
            launchAnimate { it.view.alpha(0.5) }
            selectedPlayer = Soldier::class
        }
        uiButton(PLAY_BUTTON_LABEL){
            position(MainModule.virtualWidth/2, 800)
        }.onPress{
            launchImmediately { sceneContainer.changeTo<MainLevelScene>(selectedPlayer) }
        }
    }
}