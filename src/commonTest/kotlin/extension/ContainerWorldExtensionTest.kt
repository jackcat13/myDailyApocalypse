package extension

import World
import com.soywiz.korge.tests.ViewsForTesting
import com.soywiz.korge.view.container
import com.soywiz.korge.view.position
import com.soywiz.korim.atlas.readAtlas
import com.soywiz.korio.file.std.resourcesVfs
import config.GameConfig.chunksSize
import entities.Soldier
import extensions.generateWorld
import extensions.initFastSpriteContainers
import extensions.worldLoadingCheck
import kotlin.test.Test
import kotlin.test.assertEquals

class ContainerWorldExtensionTest(): ViewsForTesting() {

    @Test
    fun world_should_generate_properly() = viewsTest {
        var world = World()
        generateWorld(world)
        assertEquals(9, world.chunks.size)
        assertEquals(-5000.0 to -5000.0, world.chunks.first().beginPosition)
        assertEquals(0.0 to 0.0, world.chunks.last().beginPosition)
    }

    @Test
    fun world_should_regenerate_left_chunks_when_player_is_at_its_right_limits() = viewsTest {
        container {
            var world = World()
            initFastSpriteContainers()
            val soldier = Soldier(animations = mapOf(), spriteAtlas = resourcesVfs["soldier.xml"].readAtlas())
            soldier.initDraw(this, -chunksSize, -chunksSize)
            generateWorld(world)
            world = worldLoadingCheck(world, soldier)
            assertEquals(-5000.0 to -5000.0, world.chunks.first().beginPosition)
            soldier.sprite!!.position(500.0, -chunksSize)
            world = worldLoadingCheck(world, soldier)
            assertEquals(-chunksSize to -5000.0, world.chunks.first().beginPosition)
        }
    }

    @Test
    fun world_should_regenerate_right_chunks_when_player_is_at_its_left_limits() = viewsTest {
        container {
            var world = World()
            initFastSpriteContainers()
            val soldier = Soldier(animations = mapOf(), spriteAtlas = resourcesVfs["soldier.xml"].readAtlas())
            soldier.initDraw(this, -chunksSize, -chunksSize)
            generateWorld(world)
            world = worldLoadingCheck(world, soldier)
            assertEquals(-5000.0 to -5000.0, world.chunks.first().beginPosition)
            soldier.sprite!!.position(-4500.0, -chunksSize)
            world = worldLoadingCheck(world, soldier)
            assertEquals(-chunksSize to 0.0, world.chunks.last().beginPosition)
        }
    }

    @Test
    fun world_should_regenerate_top_chunks_when_player_is_at_its_bottom_limits() = viewsTest {
        container {
            var world = World()
            initFastSpriteContainers()
            val soldier = Soldier(animations = mapOf(), spriteAtlas = resourcesVfs["soldier.xml"].readAtlas())
            soldier.initDraw(this, -chunksSize, -chunksSize)
            generateWorld(world)
            world = worldLoadingCheck(world, soldier)
            assertEquals(-5000.0 to -5000.0, world.chunks.first().beginPosition)
            soldier.sprite!!.position(-chunksSize, 500.0)
            world = worldLoadingCheck(world, soldier)
            assertEquals(-5000.0 to -chunksSize, world.chunks.first().beginPosition)
        }
    }

    @Test
    fun world_should_regenerate_bottom_chunks_when_player_is_at_its_top_limits() = viewsTest {
        container {
            var world = World()
            initFastSpriteContainers()
            val soldier = Soldier(animations = mapOf(), spriteAtlas = resourcesVfs["soldier.xml"].readAtlas())
            soldier.initDraw(this, -chunksSize, -chunksSize)
            generateWorld(world)
            world = worldLoadingCheck(world, soldier)
            assertEquals(-5000.0 to -5000.0, world.chunks.first().beginPosition)
            soldier.sprite!!.position(-chunksSize, -4500.0)
            world = worldLoadingCheck(world, soldier)
            assertEquals(0.0 to -chunksSize, world.chunks.last().beginPosition)
        }
    }
}