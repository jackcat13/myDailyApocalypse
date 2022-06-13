package extensions

import com.soywiz.klock.timesPerSecond
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addFixedUpdater
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.camera.CameraContainer
import com.soywiz.korge.view.container
import entities.Enemy
import entities.Player

internal fun Container.attacksUpdater(currentPlayer: Player, ennemies: MutableList<Enemy>) {
    addFixedUpdater(currentPlayer.attackSpeed.timesPerSecond) {
        container {
            currentPlayer.processMainAttack(this, ennemies)
        }
    }
}

internal fun Container.movesUpdater(currentPlayer: Player, camera: CameraContainer) {
    addUpdater {
        checkMoves(currentPlayer)
        currentPlayer.mayMove()
        camera.follow(currentPlayer.sprite, true)
    }
}