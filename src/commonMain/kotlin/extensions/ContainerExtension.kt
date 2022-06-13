package extensions

import World
import com.soywiz.korev.Key.DOWN
import com.soywiz.korev.Key.LEFT
import com.soywiz.korev.Key.RIGHT
import com.soywiz.korev.Key.UP
import com.soywiz.korev.KeyEvent
import com.soywiz.korge.input.keys
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.container
import com.soywiz.korge.view.image
import com.soywiz.korge.view.position
import com.soywiz.korge.view.size
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.BitmapSlice
import com.soywiz.korio.resources.Resource
import config.GameConfig.chunksSize
import entities.Player
import entities.PlayerStatus.RUN
import entities.PlayerStatus.RUN_FULL_SPEED
import entities.PlayerStatus.STAY

fun Container.checkMoves(player: Player) {
    keys {
        down {
            if (it.isMoveKey()) {
                if (player.playerStatus != RUN_FULL_SPEED) player.playerStatus = RUN
                if (it.isXDirection()) player.moveXDirection = it.key
                if (it.isYDirection()) player.moveYDirection = it.key
            }
        }
        up {
            if (it.isMoveKey()) {
                if (it.isXDirection()) player.moveXDirection = null
                if (it.isYDirection()) player.moveYDirection = null
                if (player.moveXDirection == null && player.moveYDirection == null) player.playerStatus = STAY
            }
        }
    }
}

private fun KeyEvent.isMoveKey(): Boolean {
    return key == DOWN || key == UP || key == LEFT || key == RIGHT
}

private fun KeyEvent.isXDirection(): Boolean {
    return key == LEFT || key == RIGHT
}

private fun KeyEvent.isYDirection(): Boolean {
    return key == DOWN || key == UP
}

fun Container.generateWorld(world: World, background_texture: Resource<BitmapSlice<Bitmap>>) {
    world.chunks.onEach {
        it.beginPosition.let { (bx, by) ->
            it.container = container {
                image(background_texture)
            }.position(bx, by)
        }
    }
}