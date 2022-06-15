import com.soywiz.korev.Key
import com.soywiz.korev.KeyEvent
import com.soywiz.korev.dispatch
import com.soywiz.korgw.GameWindow

object KeysUtilsTest {

    fun charDown(gameWindow: GameWindow, char: Char) {
        gameWindow.dispatch(
                KeyEvent(
                        type = KeyEvent.Type.DOWN,
                        id = 0, key = Key.NONE, keyCode = char.toInt(), character = char.toInt().toChar(),
                        shift = false, ctrl = false, alt = false, meta = false
                )
        )
    }

    fun charUp(gameWindow: GameWindow, char: Char) {
        gameWindow.dispatch(
                KeyEvent(
                        type = KeyEvent.Type.UP,
                        id = 0, key = Key.NONE, keyCode = char.toInt(), character = char.toInt().toChar(),
                        shift = false, ctrl = false, alt = false, meta = false
                )
        )
    }

    fun charDownAndUp(gameWindow: GameWindow, char: Char){
        gameWindow.dispatch(
                KeyEvent(
                        type = KeyEvent.Type.DOWN,
                        id = 0, key = Key.NONE, keyCode = char.toInt(), character = char.toInt().toChar(),
                        shift = false, ctrl = false, alt = false, meta = false
                )
        )
        gameWindow.dispatch(
                KeyEvent(
                        type = KeyEvent.Type.UP,
                        id = 0, key = Key.NONE, keyCode = char.toInt(), character = char.toInt().toChar(),
                        shift = false, ctrl = false, alt = false, meta = false
                )
        )
    }
}