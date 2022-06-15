package extension

import KeysUtilsTest.charDown
import KeysUtilsTest.charUp
import com.soywiz.klock.seconds
import com.soywiz.korge.animate.animator
import com.soywiz.korge.tests.ViewsForTesting
import com.soywiz.korge.view.camera.cameraContainer
import com.soywiz.korge.view.container
import com.soywiz.korge.view.position
import config.GameConfig
import entities.Enemy
import entities.Player
import extensions.attacksUpdater
import extensions.movesUpdater
import module.MainModule
import utils.EntitiesBuilder
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ContainerUpdaterExtensionTest(): ViewsForTesting() {

    @Test
    fun `player should kill imp when using main attack updater`() = viewsTest {
        val enemies: MutableList<Enemy> = mutableListOf()
        val currentPlayer: Player = EntitiesBuilder.soldier().apply { damage = 100.0 }
        currentPlayer.initDraw(this, 0.0, 0.0)
        enemies.add(EntitiesBuilder.generateImp(this, currentPlayer).apply { position(30.0, 0.0) })
        assertEquals(1, enemies.size)
        attacksUpdater(currentPlayer, enemies)
        animator {
            block {
                wait(1.seconds) //wait for user attack
                assertEquals(0, enemies.size)
            }
        }
    }

    @Test
    fun `player should move when using move keys`() = viewsTest {
        container {
            val currentPlayer: Player = EntitiesBuilder.soldier()
            currentPlayer.initDraw(this, 0.0, 0.0)
            movesUpdater(currentPlayer, cameraContainer(MainModule.virtualWidth.toDouble(), MainModule.virtualHeight.toDouble()))
            charDown(gameWindow, GameConfig.keyMap.right)
            delayFrame()
            charUp(gameWindow, GameConfig.keyMap.right)
            assertTrue(currentPlayer.sprite!!.x > 0.0)
            charDown(gameWindow, GameConfig.keyMap.down)
            delayFrame()
            charUp(gameWindow, GameConfig.keyMap.down)
            assertTrue(currentPlayer.sprite!!.y > 0.0)
            currentPlayer.sprite!!.position(0.0, 0.0)
            charDown(gameWindow, GameConfig.keyMap.left)
            delayFrame()
            charUp(gameWindow, GameConfig.keyMap.left)
            assertTrue(currentPlayer.sprite!!.x < 0.0)
            charDown(gameWindow, GameConfig.keyMap.up)
            delayFrame()
            charUp(gameWindow, GameConfig.keyMap.up)
            assertTrue(currentPlayer.sprite!!.y < 0.0)
        }
    }
}