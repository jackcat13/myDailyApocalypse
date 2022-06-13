package scenes

import World
import com.soywiz.korge.resources.resourceBitmap
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.camera.cameraContainer
import com.soywiz.korio.resources.ResourcesContainer
import config.GameConfig.chunksSize
import entities.EntityType
import entities.EntityType.MAGE
import entities.EntityType.SOLDIER
import entities.Player
import extensions.checkMoves
import extensions.generateWorld
import extensions.worldLoadingCheck
import module.MainModule

val ResourcesContainer.soldier_png by resourceBitmap("player.png")
val ResourcesContainer.mage_png by resourceBitmap("mage.png")
val ResourcesContainer.background_texture by resourceBitmap("grass.png")

class GameScene(): Scene() {
    var world = World()
    override suspend fun Container.sceneInit() {
        val currentPlayer = soldier()
        val camera = cameraContainer(MainModule.virtualWidth.toDouble(), MainModule.virtualHeight.toDouble()) {
            generateWorld(world, background_texture)
            currentPlayer.initDraw(this, -chunksSize, -chunksSize, currentPlayer.spriteBitmap, Player::class.simpleName.toString())
            currentPlayer.speed = 50.0 //TODO: for test only, TO REMOVE
            addUpdater {
                world = worldLoadingCheck(world, currentPlayer, background_texture)
            }
        }
        addUpdater {
            checkMoves(currentPlayer)
            currentPlayer.mayMove()
            camera.follow(currentPlayer.sprite, true)
        }
    }

    suspend fun soldier() = Player(maxHp = 120, range = 105.0, spriteBitmap = soldier_png.get(), type = SOLDIER)
    suspend fun mage() = Player(maxHp = 80, spriteBitmap = mage_png.get(), type = MAGE)
}