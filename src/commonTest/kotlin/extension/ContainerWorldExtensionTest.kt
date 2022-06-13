package extension

import World
import com.soywiz.korge.tests.ViewsForTesting
import extensions.generateWorld
import scenes.background_texture
import kotlin.test.Test
import kotlin.test.assertEquals

class ContainerWorldExtensionTest(): ViewsForTesting() {

    @Test
    fun `world should generate properly`() = viewsTest {
        var world = World()
        generateWorld(world, background_texture)
        assertEquals(9, world.chunks.size)
        assertEquals(-5000.0 to -5000.0, world.chunks.first().beginPosition)
        assertEquals(0.0 to 0.0, world.chunks.last().beginPosition)
    }
}