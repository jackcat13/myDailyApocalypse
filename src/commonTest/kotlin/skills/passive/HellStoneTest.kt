package skills.passive

import com.soywiz.korim.atlas.Atlas
import entities.Imp
import entities.Soldier
import kotlin.test.Test
import kotlin.test.assertEquals

class HellStoneTest {

    @Test
    fun `Hell stone for new soldier should deals 120`(){
        val soldier = Soldier(animations = mapOf(), spriteAtlas = Atlas(listOf()), passiveSkills = mutableListOf(HellStone()))
        val imp = Imp(spriteAtlas = Atlas(listOf()))
        imp.hitBy(soldier)
        assertEquals(imp.hp, -20.0)
        assertEquals(soldier.processDamage(), 120.0)
    }
}