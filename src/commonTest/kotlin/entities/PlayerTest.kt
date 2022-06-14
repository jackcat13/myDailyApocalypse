package entities

import com.soywiz.klock.blockingSleep
import com.soywiz.klock.seconds
import com.soywiz.korge.tests.ViewsForTesting
import com.soywiz.korim.atlas.readAtlas
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korma.geom.Point
import config.GameConfig
import entities.PlayerStatus.RUN
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerTest: ViewsForTesting() {

    @Test
    fun `may move should not change player position when player is not running`() = viewsTest {
        val soldier = Soldier(animations = mapOf(), spriteAtlas = resourcesVfs["soldier.xml"].readAtlas())
        soldier.initDraw(this, 0.0, 0.0)
        soldier.mayMove()
        blockingSleep(0.6.seconds)
        assertEquals(Point(0.0, 0.0), soldier.sprite!!.pos)
    }

    @Test
    fun `may move should change player x position`() = viewsTest {
        val soldier = Soldier(animations = mapOf(), spriteAtlas = resourcesVfs["soldier.xml"].readAtlas())
        soldier.initDraw(this, 0.0, 0.0)
        soldier.moveXDirection = GameConfig.keyMap.right
        soldier.playerStatus = RUN
        soldier.mayMove()
        blockingSleep(0.6.seconds)
        assertEquals(1.5, soldier.sprite!!.x)
        assertEquals(0.0, soldier.sprite!!.y)
        soldier.mayMove()
        assertEquals(4.5, soldier.sprite!!.x)
        assertEquals(0.0, soldier.sprite!!.y)
        soldier.moveXDirection = GameConfig.keyMap.left
        soldier.mayMove()
        assertEquals(1.5, soldier.sprite!!.x)
        assertEquals(0.0, soldier.sprite!!.y)
    }

    @Test
    fun `may move should change player y position`() = viewsTest {
        val soldier = Soldier(animations = mapOf(), spriteAtlas = resourcesVfs["soldier.xml"].readAtlas())
        soldier.initDraw(this, 0.0, 0.0)
        soldier.moveYDirection = GameConfig.keyMap.down
        soldier.playerStatus = RUN
        soldier.mayMove()
        blockingSleep(0.6.seconds)
        assertEquals(0.0, soldier.sprite!!.x)
        assertEquals(1.5, soldier.sprite!!.y)
        soldier.mayMove()
        assertEquals(0.0, soldier.sprite!!.x)
        assertEquals(4.5, soldier.sprite!!.y)
        soldier.moveYDirection = GameConfig.keyMap.up
        soldier.mayMove()
        assertEquals(0.0, soldier.sprite!!.x)
        assertEquals(1.5, soldier.sprite!!.y)
    }
}