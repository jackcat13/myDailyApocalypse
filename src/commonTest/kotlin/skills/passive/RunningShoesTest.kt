package skills.passive

import com.soywiz.korim.atlas.Atlas
import entities.PlayerStatus.RUN_FULL_SPEED
import entities.Soldier
import kotlin.test.Test
import kotlin.test.assertEquals

class RunningShoesTest {

    @Test
    fun new_soldier_with_running_shoes_should_have_a_speed_of_6(){
        val soldier = Soldier(animations = mapOf(), spriteAtlas = Atlas(listOf()), passiveSkills = mutableListOf(RunningShoes()))
        soldier.playerStatus = RUN_FULL_SPEED
        assertEquals(soldier.processSpeed(), 6.0)
    }
}