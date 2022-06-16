package scenes

import World
import com.soywiz.kds.FastArrayList
import com.soywiz.kds.fastArrayListOf
import com.soywiz.korge.input.onClick
import com.soywiz.korge.resources.resourceBitmap
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.uiButton
import com.soywiz.korge.view.*
import com.soywiz.korge.view.camera.cameraContainer
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.resources.ResourcesContainer
import config.ExcludeFromJacocoGeneratedReport
import config.GameConfig.chunksSize
import config.GameStatus
import entities.Enemy
import entities.Player
import entities.PlayerStatus
import extensions.*
import extensions.attacksUpdater
import extensions.movesUpdater
import module.MainModule
import utils.EntitiesBuilder.generateImp
import utils.EntitiesBuilder.soldier

const val EXIT_BUTTON_TEXT = "Exit"
const val EXIT_BUTTON = "ExitButton"
const val DEATH_MESSAGE = "DEAD"
val ResourcesContainer.backgroundTexture by resourceBitmap("grass.png")

/**
 * Main level scene when the game is started from the menu.
 * @param world The game world object used for procedural generation
 * @param enemies List of enemies that appear during the game
 */
@ExcludeFromJacocoGeneratedReport("Won't test scenes, focus is on logic testing")
class MainLevelScene(): Scene() {
    var world = World()
    val enemies: FastArrayList<Enemy> = fastArrayListOf()
    override suspend fun Container.sceneInit() {
        world = World()
        val currentPlayer: Player = soldier()
        val camera = cameraContainer(MainModule.virtualWidth.toDouble(), MainModule.virtualHeight.toDouble()) {
            generateWorld(world, backgroundTexture)
            currentPlayer.initDraw(this, -chunksSize, -chunksSize)
            addUpdaterWithPause {
                world = worldLoadingCheck(world, currentPlayer, backgroundTexture)
            }
            for(i in 1..10) enemies.add(generateImp(this, currentPlayer)) //TODO: remove when tests on hits completed
            attacksUpdater(currentPlayer, enemies)
        }
        checkPowersInput()
        movesUpdater(currentPlayer, camera)
        checkPlayerDeath(currentPlayer)
    }

    private fun Container.checkPlayerDeath(currentPlayer: Player) {
        addUpdater {
            if (currentPlayer.playerStatus == PlayerStatus.DEAD) {
                GameStatus.pause = true
                currentPlayer.launchDeathAnimation()
                uiButton(EXIT_BUTTON_TEXT).onClick { launchImmediately {
                    sceneContainer.changeTo<MainScene>()
                    world = World()
                    enemies.clear()
                    GameStatus.pause = false
                } }!!.name(EXIT_BUTTON).position(10.0, 200.0)
            }
        }
    }
}
