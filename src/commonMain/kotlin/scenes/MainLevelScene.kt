package scenes

import World
import com.soywiz.kds.FastArrayList
import com.soywiz.kds.fastArrayListOf
import com.soywiz.klock.timesPerSecond
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.ui.uiButton
import com.soywiz.korge.view.*
import com.soywiz.korge.view.camera.cameraContainer
import com.soywiz.korio.async.launchImmediately
import config.ExcludeFromJacocoGeneratedReport
import config.GameConfig.chunksSize
import config.GameStatus
import entities.Enemy
import entities.Player
import entities.PlayerStatus.DEAD
import entities.PlayerStatus.DEAD_MENU
import extensions.*
import module.MainModule
import utils.EntitiesBuilder.generateImp
import utils.EntitiesBuilder.soldier

const val EXIT_BUTTON_TEXT = "Exit"
const val EXIT_BUTTON = "ExitButton"

/**
 * Main level scene when the game is started from the menu.
 * @param world The game world object used for procedural generation
 * @param enemies List of enemies that appear during the game
 */
@ExcludeFromJacocoGeneratedReport("Won't test scenes, focus is on logic testing")
class MainLevelScene(): Scene() {
    var world = World()
    val enemies: FastArrayList<Enemy> = fastArrayListOf()
    override suspend fun SContainer.sceneInit() {
        world = World()
        stage?.hitTestEnabled = false
        val currentPlayer: Player = soldier()
        val camera = cameraContainer(MainModule.virtualWidth.toDouble(), MainModule.virtualHeight.toDouble()) {
            initFastSpriteContainers()
            currentPlayer.initDraw(this, -chunksSize, -chunksSize)
            val worldContainer = container { generateWorld(world) }.also { parent!!.sendChildToBack(it) }
            addUpdaterWithPause { world = worldContainer.worldLoadingCheck(world, currentPlayer) }
            addFixedUpdaterWithPause(0.1.timesPerSecond) {
                launchImmediately { enemies.add(generateImp(this@cameraContainer, currentPlayer)) }
            }
            containerRoot.text("${enemies.size}").apply { fontSize = 32.0 }.let { addUpdaterWithPause { it.text = "${enemies.size}" } }
            attacksUpdater(currentPlayer, enemies)
            sendChildToFront(currentPlayer.sprite as View)
        }
        checkPowersInput()
        movesUpdater(currentPlayer, camera)
        checkPlayerDeath(currentPlayer)
    }

    private fun Container.checkPlayerDeath(currentPlayer: Player) {
        addUpdaterWithPause {
            if (currentPlayer.playerStatus == DEAD) {
                currentPlayer.playerStatus = DEAD_MENU
                currentPlayer.launchDeathAnimation()
                GameStatus.pause = true
                uiButton(EXIT_BUTTON_TEXT).name(EXIT_BUTTON).position(10.0, 200.0).onPress {
                    launchImmediately { sceneContainer.changeTo<MainScene>() }
                    world = World()
                    enemies.clear()
                    GameStatus.pause = false
                }
            }
        }
    }
}
