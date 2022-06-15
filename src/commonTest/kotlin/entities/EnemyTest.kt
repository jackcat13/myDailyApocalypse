package entities

import com.soywiz.klock.blockingSleep
import com.soywiz.klock.seconds
import com.soywiz.korge.tests.ViewsForTesting
import com.soywiz.korge.view.collidesWith
import com.soywiz.korge.view.container
import com.soywiz.korge.view.position
import com.soywiz.korio.async.launchImmediately
import utils.EntitiesBuilder.generateImp
import utils.EntitiesBuilder.imp
import utils.EntitiesBuilder.soldier
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class EnemyTest: ViewsForTesting() {

    @Test
    fun `imp hit by player should take damage`() = viewsTest{
        val imp = imp()
        imp.hitBy(soldier())
        assertEquals(40.0, imp.hp)
    }

    @Test
    fun `imp should follow player`() = viewsTest {
        container {
            val soldier = soldier()
            soldier.initDraw(this, 0.0, 0.0)
            val imp = generateImp(this, soldier)
            imp.sprite!!.position(100.0, 100.0)
            imp.mayFollowPlayer(soldier)
            blockingSleep(0.1.seconds)
            assertTrue(imp.sprite!!.x < 100.0)
            assertTrue(imp.sprite!!.y < 100.0)
        }
    }
}