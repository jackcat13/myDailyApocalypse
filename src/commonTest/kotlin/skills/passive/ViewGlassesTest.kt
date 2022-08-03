package skills.passive

import com.soywiz.korge.tests.ViewsForTesting
import com.soywiz.korge.view.container
import com.soywiz.korim.atlas.readAtlas
import com.soywiz.korio.file.std.resourcesVfs
import entities.Soldier
import kotlin.test.Test
import kotlin.test.assertEquals

class ViewGlassesTest(): ViewsForTesting() {

    @Test
    fun new_soldier_should_have_range_of_305_when_using_view_glasses() = viewsTest {
        container {
            val soldier = Soldier(
                animations = mapOf(),
                spriteAtlas = resourcesVfs["soldier.xml"].readAtlas(),
                passiveSkills = mutableListOf(ViewGlasses())
            )
            soldier.initDraw(this, 0.0, 0.0)
            assertEquals(305.0, soldier.processRange())
        }
    }
}