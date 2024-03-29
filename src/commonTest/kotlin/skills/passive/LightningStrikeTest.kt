package skills.passive

import com.soywiz.korim.atlas.Atlas
import entities.Imp
import entities.Soldier
import kotlin.test.Test
import kotlin.test.assertEquals

class LightningStrikeTest {

    @Test
    fun lightning_strike_for_new_soldier_should_deals_75(){
        val soldier = Soldier(animations = mapOf(), spriteAtlas = Atlas(listOf()), passiveSkills = mutableListOf(LightningStrike()))
        val imp = Imp(spriteAtlas = Atlas(listOf()))
        imp.hitBy(soldier)
        assertEquals(imp.hp, 25.0)
        assertEquals(soldier.processDamage(), 75.0)
    }
}