package scenes

import World
import com.soywiz.korge.resources.resourceBitmap
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.camera.cameraContainer
import com.soywiz.korio.resources.ResourcesContainer
import config.ExcludeFromJacocoGeneratedReport
import config.GameConfig.chunksSize
import entities.Enemy
import entities.Player
import extensions.*
import extensions.attacksUpdater
import extensions.movesUpdater
import module.MainModule
import utils.EntitiesBuilder.generateImp
import utils.EntitiesBuilder.soldier

val ResourcesContainer.backgroundTexture by resourceBitmap("grass.png")

@ExcludeFromJacocoGeneratedReport("Won't test scenes, focus is on logic testing")
class MainLevelScene(): Scene() {
    var world = World()
    val enemies: MutableList<Enemy> = mutableListOf()
    override suspend fun Container.sceneInit() {
        val currentPlayer: Player = soldier()
        val camera = cameraContainer(MainModule.virtualWidth.toDouble(), MainModule.virtualHeight.toDouble()) {
            generateWorld(world, backgroundTexture)
            currentPlayer.initDraw(this, -chunksSize, -chunksSize)
            addUpdaterWithPause {
                world = worldLoadingCheck(world, currentPlayer, backgroundTexture)
            }
            for(i in 1..10) enemies.add(generateImp(this, currentPlayer)) //TODO: remove when tests on hits completed
            attacksUpdater(currentPlayer, enemies)
        }
        checkPowersInput()
        movesUpdater(currentPlayer, camera)
    }
}