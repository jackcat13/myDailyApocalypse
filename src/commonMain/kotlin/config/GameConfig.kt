package config

import entities.Player
import kotlin.reflect.KClass

/**
 * Global configuration of the game
 * @property chunksSize Used size for the 9 chunks of the game
 * @property keyMap keymap used in the game for actions. Can be configured.
 */
object GameConfig{
    const val chunksSize = 2500.0
    var keyMap = Keymap()
}