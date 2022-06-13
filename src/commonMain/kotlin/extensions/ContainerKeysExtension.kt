package extensions

import com.soywiz.korev.KeyEvent
import com.soywiz.korge.input.keys
import com.soywiz.korge.view.Container
import config.GameConfig
import entities.Player
import entities.PlayerStatus

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