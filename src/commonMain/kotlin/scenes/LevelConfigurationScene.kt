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
import com.soywiz.korge.view.alpha
import com.soywiz.korge.view.container
import com.soywiz.korge.view.position
import com.soywiz.korge.view.setText
import com.soywiz.korge.view.text
import com.soywiz.korio.async.launchImmediately
import config.ExcludeFromJacocoGeneratedReport
import entities.Player
import entities.Soldier
import entities.Wolf
import module.MainModule
import utils.EntitiesBuilder.soldier
import utils.EntitiesBuilder.wolf

const val PLAY_BUTTON_LABEL = "Play"

@ExcludeFromJacocoGeneratedReport("Won't test scenes, focus is on logic testing")
class LevelConfigurationScene(): Scene() {

    override suspend fun SContainer.sceneInit() {
        var selectedPlayer: Player = soldier()
        container{
            soldier().initDraw(this, 20.0, 20.0).apply { alpha(0.5) }
            text(Soldier::class.simpleName.toString()).position(20.0, 100.0)
        }.onClick{
            reinitOtherAlpha(it.view)
            launchAnimate { it.view.alpha(0.5) }
            selectedPlayer = soldier()
        }
        container{
            wolf().initDraw(this, 100.0, 20.0)
            text(Wolf::class.simpleName.toString()).position(100.0, 100.0)
        }.onClick{
            reinitOtherAlpha(it.view)
            launchAnimate { it.view.alpha(0.5) }
            selectedPlayer = wolf()
        }
        uiButton(PLAY_BUTTON_LABEL){
            position(MainModule.virtualWidth/2, 800)
        }.onPress{
            launchImmediately { sceneContainer.changeTo<MainLevelScene>(selectedPlayer.characterName()) }
        }

        //Selected character details
        val selectedPlayerText = text("").position(MainModule.virtualWidth/2.0, 300.0).apply { fontSize = 26.0 }
        addUpdater {
            selectedPlayerText.setText(selectedPlayer.toString())
        }
    }
}

private suspend fun Container.reinitOtherAlpha(view: View) {
    containerRoot.allDescendants().filter { it != view }.forEach { launchAnimate { it.alpha(1) } }
}
