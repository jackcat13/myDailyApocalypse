package scenes

import com.soywiz.korge.animate.launchAnimate
import com.soywiz.korge.input.onClick
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.uiButton
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.SContainer
import com.soywiz.korge.view.View
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.allDescendants
import com.soywiz.korge.view.container
import com.soywiz.korge.view.position
import com.soywiz.korge.view.text
import com.soywiz.korio.async.launchImmediately
import config.ExcludeFromJacocoGeneratedReport
import config.PlayableCharacter
import entities.Soldier
import entities.Wolf
import module.MainModule
import utils.EntitiesBuilder.soldier
import utils.EntitiesBuilder.wolf

const val PLAY_BUTTON_LABEL = "Play"

@ExcludeFromJacocoGeneratedReport("Won't test scenes, focus is on logic testing")
class LevelConfigurationScene(): Scene() {

    override suspend fun SContainer.sceneInit() {
        var selectedPlayer: PlayableCharacter = PlayableCharacter.Soldier
        container{
            soldier().initDraw(this, 20.0, 20.0)
            text(Soldier::class.simpleName.toString()).position(20.0, 100.0)
        }.onClick{
            reinitOtherAlpha(it.view)
            launchAnimate { it.view.alpha(0.5) }
            selectedPlayer = PlayableCharacter.Soldier
        }
        container{
            wolf().initDraw(this, 100.0, 20.0)
            text(Wolf::class.simpleName.toString()).position(100.0, 100.0)
        }.onClick{
            reinitOtherAlpha(it.view)
            launchAnimate { it.view.alpha(0.5) }
            selectedPlayer = PlayableCharacter.Wolf //TODO: change selectedPlayer to = wolf(), soldier(), etc to ease diplay details and pass the object directly in mainlevel
        }
        uiButton(PLAY_BUTTON_LABEL){
            position(MainModule.virtualWidth/2, 800)
        }.onPress{
            launchImmediately { sceneContainer.changeTo<MainLevelScene>(selectedPlayer) }
        }
    }
}

private suspend fun Container.reinitOtherAlpha(view: View) {
    containerRoot.allDescendants().filter { it != view }.forEach { launchAnimate { it.alpha(1) } }
}
