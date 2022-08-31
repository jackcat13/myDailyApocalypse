package scenes

import com.soywiz.korge.animate.launchAnimate
import com.soywiz.korge.input.onClick
import com.soywiz.korge.input.onOut
import com.soywiz.korge.input.onOver
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.uiButton
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.SContainer
import com.soywiz.korge.view.Text
import com.soywiz.korge.view.View
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.allDescendants
import com.soywiz.korge.view.alpha
import com.soywiz.korge.view.container
import com.soywiz.korge.view.position
import com.soywiz.korge.view.setText
import com.soywiz.korge.view.text
import com.soywiz.korgw.openFileDialog
import com.soywiz.korim.color.Colors.RED
import com.soywiz.korim.color.Colors.WHITE
import com.soywiz.korio.async.launchImmediately
import config.ExcludeFromJacocoGeneratedReport
import entities.Player
import entities.Soldier
import entities.Wolf
import module.MainModule
import utils.EntitiesBuilder.soldier
import utils.EntitiesBuilder.wolf

const val SOLDIER_X = 20.0
const val WOLF_X = 200.0
const val FIRST_LINE_SPRITES_Y = 20.0
const val FIRST_LINE_TEXT_Y = 100.0
const val FIRST_LINE_SELECT_Y = 150.0

const val PLAY_BUTTON_LABEL = "Play"
const val BACK_BUTTON_LABEL = "Back"
const val SELECT_BUTTON_LABEL = "Select"

@ExcludeFromJacocoGeneratedReport("Won't test scenes, focus is on logic testing")
class LevelConfigurationScene(): Scene() {

    override suspend fun SContainer.sceneInit() {
        var selectedPlayer: Player = soldier()
        container{
            soldier().initDraw(this, SOLDIER_X, FIRST_LINE_SPRITES_Y).apply { alpha(0.5) }
            text(Soldier::class.simpleName.toString()).position(SOLDIER_X, FIRST_LINE_TEXT_Y)
            uiButton(SELECT_BUTTON_LABEL).position(SOLDIER_X, FIRST_LINE_SELECT_Y).onPress {
                launchImmediately {
                    reinitOtherAlpha(this)
                    this.alpha(0.5)
                    selectedPlayer = soldier()
                }
            }
        }
        container{
            wolf().initDraw(this, WOLF_X, FIRST_LINE_SPRITES_Y)
            text(Wolf::class.simpleName.toString()).position(WOLF_X, FIRST_LINE_TEXT_Y)
            uiButton(SELECT_BUTTON_LABEL).position(WOLF_X, FIRST_LINE_SELECT_Y).onPress {
                launchImmediately {
                    reinitOtherAlpha(this)
                    this.alpha(0.5)
                    selectedPlayer = wolf()
                }
            }
        }

        uiButton(PLAY_BUTTON_LABEL){
            position(MainModule.virtualWidth/2, 800)
        }.onPress{
            launchImmediately { sceneContainer.changeTo<MainLevelScene>(selectedPlayer.characterName()) }
        }
        uiButton(BACK_BUTTON_LABEL){
            position(MainModule.virtualWidth/2, 900)
        }.onPress{
            launchImmediately { sceneContainer.changeTo<MainScene>() }
        }

        //Selected character details
        val selectedPlayerText = text("").position(MainModule.virtualWidth/2.0, 300.0).apply { fontSize = 26.0 }
        addUpdater {
            selectedPlayerText.setText(selectedPlayer.toString())
        }
    }
}

private suspend fun Container.reinitOtherAlpha(view: View) {
    parent.allDescendants().filter { it != view }.forEach { launchAnimate { it.alpha(1) } }
}
