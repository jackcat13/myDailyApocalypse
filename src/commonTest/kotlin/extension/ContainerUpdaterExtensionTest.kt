package extension

import com.soywiz.klock.seconds
import com.soywiz.korev.Key
import com.soywiz.korev.Key.DOWN
import com.soywiz.korev.Key.LEFT
import com.soywiz.korev.Key.RIGHT
import com.soywiz.korev.Key.UP
import com.soywiz.korge.animate.animator
import com.soywiz.korge.tests.ViewsForTesting
import com.soywiz.korge.view.camera.cameraContainer
import com.soywiz.korge.view.position
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
        val currentPlayer: Player = EntitiesBuilder.soldier()
        currentPlayer.initDraw(this, 0.0, 0.0)
        movesUpdater(currentPlayer, cameraContainer(MainModule.virtualWidth.toDouble(), MainModule.virtualHeight.toDouble()))
        keyDownThenUp(RIGHT)
        assertTrue(currentPlayer.sprite!!.x > 0.0)
        keyDownThenUp(DOWN)
        assertTrue(currentPlayer.sprite!!.y > 0.0)
        currentPlayer.sprite!!.position(0.0, 0.0)
        keyDownThenUp(LEFT)
        assertTrue(currentPlayer.sprite!!.x < 0.0)
        keyDownThenUp(UP)
        assertTrue(currentPlayer.sprite!!.y < 0.0)
    }
}