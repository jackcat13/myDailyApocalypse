package scenes

import World
import com.soywiz.korge.resources.resourceBitmap
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.camera
import com.soywiz.korge.view.camera.cameraContainer
import com.soywiz.korge.view.centerOn
import com.soywiz.korge.view.fixedSizeContainer
import com.soywiz.korge.view.position
import com.soywiz.korge.view.solidRect
import com.soywiz.korim.color.Colors
import com.soywiz.korio.resources.ResourcesContainer
import entities.Player
import extensions.checkMoves
import extensions.generateWorld
import module.MainModule

val ResourcesContainer.player_png by resourceBitmap("player.png")
val ResourcesContainer.background_texture by resourceBitmap("grass.png")
val world = World()

class GameScene: Scene() {
    private val player = Player()
    override suspend fun Container.sceneInit() {
        val camera = cameraContainer(MainModule.virtualWidth.toDouble(), MainModule.virtualHeight.toDouble()) {
            generateWorld(world, background_texture)
            solidRect(50, 50, Colors.RED).position(100, 100)
            player.initDraw(this, 0.0, 0.0, player_png.get(), Player::class.simpleName.toString())
        }
        addUpdater {
            checkMoves(player)
            player.mayMove()
            camera.follow(player.sprite, true)
        }
    }

}
