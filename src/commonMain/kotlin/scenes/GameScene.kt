package scenes

import World
import com.soywiz.klock.timesPerSecond
import com.soywiz.korge.resources.resourceBitmap
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.*
import com.soywiz.korge.view.camera.CameraContainer
import com.soywiz.korge.view.camera.cameraContainer
import com.soywiz.korim.atlas.readAtlas
import com.soywiz.korio.file.std.resourcesVfs
import com.soywiz.korio.resources.ResourcesContainer
import config.GameConfig.chunksSize
import entities.Enemy
import entities.Imp
import entities.Player
import entities.Soldier
import extensions.checkMoves
import extensions.generateWorld
import extensions.worldLoadingCheck
import module.MainModule
import skills.passive.LightningStrike
import utils.Animations
import utils.GameRandom.generateRand

val ResourcesContainer.background_texture by resourceBitmap("grass.png")

class GameScene(): Scene() {
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
            for(i in 1..10) generateImp(this, currentPlayer) //TODO: remove when tests on hits completed
        }
        movesUpdater(currentPlayer, camera)
    }

    private fun Container.attacksUpdater(currentPlayer: Player, ennemies: MutableList<Enemy>) {
        addFixedUpdater(currentPlayer.attackSpeed.timesPerSecond) {
            container {
                currentPlayer.processMainAttack(this, ennemies)
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

    private suspend fun generateImp(container: Container, currentPlayer: Player) {
        val imp = imp()
        imp.initDraw(container, currentPlayer.sprite!!.x+generateRand(-600,600), currentPlayer.sprite!!.y+ generateRand(-600,600))
        enemies.add(imp)
        container.addUpdater { imp.mayFollowPlayer(currentPlayer.sprite!!) }
    }

    private suspend fun animations() = Animations.build()
    suspend fun soldier() = Soldier(spriteAtlas = resourcesVfs["soldier.xml"].readAtlas(), animations = animations())
    //suspend fun marine() = Player(spriteAtlas = resourcesVfs["marine.xml"].readAtlas())
    //suspend fun mage() = Player(spriteAtlas = resourcesVfs["mage.xml"].readAtlas())

    suspend fun imp() = Imp(spriteAtlas = resourcesVfs["imp.xml"].readAtlas())
}
