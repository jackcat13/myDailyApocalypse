package extensions

import com.soywiz.klock.timesPerSecond
import com.soywiz.korge.input.keys
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addUpdater
import com.soywiz.korge.view.camera.CameraContainer
import com.soywiz.korge.view.container
import config.GameStatus
import entities.Enemy
import entities.Player

/**
 * Performs main attacks of players based on player attacks speed as frequency
 * @receiver The container on which the updater is applied
 * @param currentPlayer The player that needs to attack
 * @param enemies The enemies that may be hit by the player
 */
internal fun Container.attacksUpdater(currentPlayer: Player, enemies: MutableList<Enemy>) {
    addFixedUpdaterWithPause(currentPlayer.attackSpeed.timesPerSecond) {
        container {
            currentPlayer.processMainAttack(this, enemies)
        }
    }
}

/**
 * Performs players moves if any move key is pressed
 * @receiver The container on which the updater is applied
 * @param currentPlayer The player that needs to move
 * @param camera The camera that follows the player to move
 */
internal fun Container.movesUpdater(currentPlayer: Player, camera: CameraContainer) {
    addUpdaterWithPause {
        checkMoves(currentPlayer)
        currentPlayer.mayMove()
        camera.follow(currentPlayer.sprite, true)
    }
}