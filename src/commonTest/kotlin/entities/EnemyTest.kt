package entities

import com.soywiz.korge.tests.ViewsForTesting
import utils.EntitiesBuilder.imp
import utils.EntitiesBuilder.soldier
import kotlin.test.Test
import kotlin.test.assertEquals

class EnemyTest: ViewsForTesting() {

    @Test
    fun `imp hit by player should take damage`() = viewsTest{
        val imp = imp()
        imp.hitBy(soldier())
        assertEquals(40.0, imp.hp)
    }
}