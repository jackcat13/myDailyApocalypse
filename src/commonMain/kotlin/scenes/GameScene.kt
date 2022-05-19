package scenes

import World
import com.soywiz.klock.timesPerSecond
import com.soywiz.korge.resources.resourceBitmap
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addFixedUpdater
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.camera.CameraContainer
import com.soywiz.korge.view.camera.cameraContainer
import com.soywiz.korge.view.container
import com.soywiz.korim.atlas.readAtlas
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.resources.ResourcesContainer
import config.GameConfig.chunksSize
import entities.Player
import entities.Soldier
import extensions.checkMoves
import extensions.generateWorld
import extensions.worldLoadingCheck
import module.MainModule
import utils.Animations
import kotlin.jvm.JvmStatic

val ResourcesContainer.background_texture by resourceBitmap("grass.png")

class GameScene(): Scene() {
    var world = World()
    override suspend fun Container.sceneInit() {
        val currentPlayer: Player = soldier()
        val camera = cameraContainer(MainModule.virtualWidth.toDouble(), MainModule.virtualHeight.toDouble()) {
            generateWorld(world, background_texture)
            currentPlayer.initDraw(this, -chunksSize, -chunksSize, Player::class.simpleName.toString())
            addUpdater {
                world = worldLoadingCheck(world, currentPlayer, background_texture)
            }
            attacksUpdater(currentPlayer)
        }
        movesUpdater(currentPlayer, camera)
    }

    private fun Container.attacksUpdater(currentPlayer: Player) {
        addFixedUpdater(currentPlayer.attackSpeed.timesPerSecond) {
            container {
                removeChildren()
                currentPlayer.processMainAttack(this)
            }
        }
    }

    private fun Container.movesUpdater(currentPlayer: Player, camera: CameraContainer) {
        addUpdater {
            checkMoves(currentPlayer)
            currentPlayer.mayMove()
            camera.follow(currentPlayer.sprite, true)
        }
    }

    private suspend fun animations() = Animations.build()
    suspend fun soldier() = Soldier(spriteAtlas = resourcesVfs["soldier.xml"].readAtlas(), animations = animations())
    //suspend fun mage() = Player(spriteAtlas = resourcesVfs["mage.xml"].readAtlas())
    //suspend fun marine() = Player(spriteAtlas = resourcesVfs["marine.xml"].readAtlas())
}