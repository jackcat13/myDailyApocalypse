package extension

import World
import com.soywiz.korge.tests.ViewsForTesting
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.position
import com.soywiz.korim.atlas.readAtlas
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.file.std.resourcesVfs
import config.GameConfig
import config.GameConfig.chunksSize
import entities.Player
import entities.Soldier
import extensions.generateWorld
import extensions.worldLoadingCheck
import scenes.backgroundTexture
import utils.EntitiesBuilder.soldier
import kotlin.test.Test
import kotlin.test.assertEquals

class ContainerWorldExtensionTest(): ViewsForTesting() {

    @Test
    fun `world should generate properly`() = viewsTest {
        var world = World()
        generateWorld(world, backgroundTexture)
        assertEquals(9, world.chunks.size)
        assertEquals(-5000.0 to -5000.0, world.chunks.first().beginPosition)
        assertEquals(0.0 to 0.0, world.chunks.last().beginPosition)
    }

    @Test
    fun `world should regenerate left chunks when player is at its right limits`() = viewsTest {
        var world = World()
        generateWorld(world, backgroundTexture)
        val soldier = Soldier(animations = mapOf(), spriteAtlas = resourcesVfs["soldier.xml"].readAtlas())
        soldier.initDraw(this, -chunksSize, -chunksSize)
        world = worldLoadingCheck(world, soldier, backgroundTexture)
        assertEquals(-5000.0 to -5000.0, world.chunks.first().beginPosition)
        soldier.sprite!!.position(500.0, -chunksSize)
        world = worldLoadingCheck(world, soldier, backgroundTexture)
        assertEquals(-chunksSize to -5000.0, world.chunks.first().beginPosition)
    }

    @Test
    fun `world should regenerate right chunks when player is at its left limits`() = viewsTest {
        var world = World()
        generateWorld(world, backgroundTexture)
        val soldier = Soldier(animations = mapOf(), spriteAtlas = resourcesVfs["soldier.xml"].readAtlas())
        soldier.initDraw(this, -chunksSize, -chunksSize)
        world = worldLoadingCheck(world, soldier, backgroundTexture)
        assertEquals(-5000.0 to -5000.0, world.chunks.first().beginPosition)
        soldier.sprite!!.position(-4500.0, -chunksSize)
        world = worldLoadingCheck(world, soldier, backgroundTexture)
        assertEquals(-chunksSize to 0.0, world.chunks.last().beginPosition)
    }

    @Test
    fun `world should regenerate top chunks when player is at its bottom limits`() = viewsTest {
        var world = World()
        generateWorld(world, backgroundTexture)
        val soldier = Soldier(animations = mapOf(), spriteAtlas = resourcesVfs["soldier.xml"].readAtlas())
        soldier.initDraw(this, -chunksSize, -chunksSize)
        world = worldLoadingCheck(world, soldier, backgroundTexture)
        assertEquals(-5000.0 to -5000.0, world.chunks.first().beginPosition)
        soldier.sprite!!.position(-chunksSize, 500.0)
        world = worldLoadingCheck(world, soldier, backgroundTexture)
        assertEquals(-5000.0 to -chunksSize, world.chunks.first().beginPosition)
    }

    @Test
    fun `world should regenerate bottom chunks when player is at its top limits`() = viewsTest {
        var world = World()
        generateWorld(world, backgroundTexture)
        val soldier = Soldier(animations = mapOf(), spriteAtlas = resourcesVfs["soldier.xml"].readAtlas())
        soldier.initDraw(this, -chunksSize, -chunksSize)
        world = worldLoadingCheck(world, soldier, backgroundTexture)
        assertEquals(-5000.0 to -5000.0, world.chunks.first().beginPosition)
        soldier.sprite!!.position(-chunksSize, -4500.0)
        world = worldLoadingCheck(world, soldier, backgroundTexture)
        assertEquals(0.0 to -chunksSize, world.chunks.last().beginPosition)
    }
}