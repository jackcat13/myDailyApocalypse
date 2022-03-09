package scenes

import com.soywiz.korge.resources.resourceBitmap
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.centerOn
import com.soywiz.korge.view.solidRect
import com.soywiz.korim.color.Colors
import com.soywiz.korio.resources.ResourcesContainer
import entities.Player
import extensions.checkMoves
import module.MainModule

val ResourcesContainer.player_png by resourceBitmap("player.png")

class GameScene: Scene() {
    private val player = Player()
    override suspend fun Container.sceneInit() {
        player.initDraw(this, MainModule.virtualWidth/2.0, MainModule.virtualHeight/2.0, player_png)
        addUpdater {
            checkMoves(player)
            player.mayMove()
            centerOn(player.sprite!!.root)
        }
    }

}