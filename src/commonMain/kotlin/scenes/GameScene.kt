package scenes

import World
import com.soywiz.korge.resources.resourceBitmap
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.camera.cameraContainer
import com.soywiz.korio.resources.ResourcesContainer
import config.GameConfig.chunksSize
import entities.Player
import extensions.checkMoves
import extensions.generateWorld
import extensions.worldLoadingCheck
import module.MainModule

val ResourcesContainer.player_png by resourceBitmap("player.png")
val ResourcesContainer.background_texture by resourceBitmap("grass.png")
var world = World()

class GameScene: Scene() {
    private val player = Player()
    override suspend fun Container.sceneInit() {
        val camera = cameraContainer(MainModule.virtualWidth.toDouble(), MainModule.virtualHeight.toDouble()) {
            generateWorld(world, background_texture)
            player.initDraw(this, -chunksSize, -chunksSize, player_png.get(), Player::class.simpleName.toString())
            player.speed = 50.0 //TODO: for test only, TO REMOVE
            addUpdater {
                world = worldLoadingCheck(world, player, background_texture)
            }
        }
        addUpdater {
            checkMoves(player)
            player.mayMove()
            camera.follow(player.sprite, true)
        }
    }

}