package extensions

import com.soywiz.korev.KeyEvent
import com.soywiz.korge.input.keys
import com.soywiz.korge.view.*
import config.GameConfig
import config.GameStatus
import entities.Player
import entities.PlayerStatus

const val POWERS_CONTAINER_NAME = "PowersContainer"

/**
 * Checks if the powers' menu key is pressed.
 * @receiver The container in which the menu os opened
 */
fun Container.checkPowersInput() {
    keys{
        down {
            if (it.isPowersKey()){
                if (GameStatus.pause.not()) openPowersMenu()
                else closePowersMenu()
            }
        }
    }
}

private fun Container.openPowersMenu() {
    GameStatus.pause = true
    alpha(0.5)
    container{
        //TODO: Powers tree to generate
    }.name(POWERS_CONTAINER_NAME)
}

private fun Container.closePowersMenu() {
    val powersContainer = getChildByName(POWERS_CONTAINER_NAME)
    powersContainer?.removeFromParent()
    powersContainer?.invalidate()
    GameStatus.pause = false
    alpha(1)
}

private fun KeyEvent.isPowersKey() = character == GameConfig.keyMap.powers

/**
 * Checks if any move key is pressed.
 * @receiver The container in which the player may move
 * @param player The player to possibly move
 */
fun Container.checkMoves(player: Player) {
    keys {
        down {
            if (it.isMoveKey()) {
                if (player.playerStatus != PlayerStatus.RUN_FULL_SPEED) player.playerStatus = PlayerStatus.RUN
                if (it.isXDirection()) player.moveXDirection = it.character
                if (it.isYDirection()) player.moveYDirection = it.character
            }
        }
        up {
            if (it.isMoveKey()) {
                if (it.isXDirection()) player.moveXDirection = null
                if (it.isYDirection()) player.moveYDirection = null
                if (player.moveXDirection == null && player.moveYDirection == null) player.playerStatus =
                    PlayerStatus.STAY
            }
        }
    }
}

private fun KeyEvent.isMoveKey(): Boolean {
    return character == GameConfig.keyMap.down || character == GameConfig.keyMap.up || character == GameConfig.keyMap.left || character == GameConfig.keyMap.right
}

private fun KeyEvent.isXDirection(): Boolean {
    return character == GameConfig.keyMap.left || character == GameConfig.keyMap.right
}

private fun KeyEvent.isYDirection(): Boolean {
    return character == GameConfig.keyMap.down || character == GameConfig.keyMap.up
}