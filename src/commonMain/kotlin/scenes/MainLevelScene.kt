package scenes

import World
import com.soywiz.korge.resources.resourceBitmap
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.camera.cameraContainer
import com.soywiz.korio.resources.ResourcesContainer
import config.GameConfig.chunksSize
import entities.Enemy
import entities.Player
import extensions.attacksUpdater
import extensions.generateWorld
import extensions.movesUpdater
import extensions.worldLoadingCheck
import module.MainModule
import utils.EntitiesBuilder.generateImp
import utils.EntitiesBuilder.soldier

val ResourcesContainer.background_texture by resourceBitmap("grass.png")

class MainLevelScene(): Scene() {
    var world = World()
    val enemies: MutableList<Enemy> = mutableListOf()
    override suspend fun Container.sceneInit() {
        val currentPlayer: Player = soldier()
        val camera = cameraContainer(MainModule.virtualWidth.toDouble(), MainModule.virtualHeight.toDouble()) {
            generateWorld(world, background_texture)
            currentPlayer.initDraw(this, -chunksSize, -chunksSize)
            addUpdater {
                world = worldLoadingCheck(world, currentPlayer, background_texture)
            }
            attacksUpdater(currentPlayer, enemies)
            for(i in 1..10) enemies.add(generateImp(this, currentPlayer)) //TODO: remove when tests on hits completed
        }
        movesUpdater(currentPlayer, camera)
    }
}
