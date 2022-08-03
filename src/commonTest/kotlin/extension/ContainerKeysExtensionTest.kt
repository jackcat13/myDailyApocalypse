package extension

import KeysUtilsTest.charDownAndUp
import com.soywiz.korge.tests.ViewsForTesting
import com.soywiz.korge.view.container
import config.GameConfig
import config.GameStatus
import extensions.checkPowersInput
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ContainerKeysExtensionTest: ViewsForTesting() {

    @Test
    fun check_powers_input_should_pause_the_game() = viewsTest {
        container {
            checkPowersInput()
            charDownAndUp(gameWindow, GameConfig.keyMap.powers)
            assertTrue(GameStatus.pause)
            assertEquals(0.5, alpha)
            charDownAndUp(gameWindow, GameConfig.keyMap.powers)
            assertFalse(GameStatus.pause)
            assertEquals(1.0, alpha)
        }
    }
}