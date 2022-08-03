package extension

import com.soywiz.korge.service.storage.storage
import com.soywiz.korge.tests.ViewsForTesting
import com.soywiz.korge.ui.UITextInput
import com.soywiz.korge.view.Container
import com.soywiz.korio.async.launchImmediately
import config.GameConfig
import config.Keymap
import extensions.*
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertEquals

//FIXME: keymap update broken by upgrade to 3.0.0 of korge.
@Ignore
class ContainerKeymapExtensionTest: ViewsForTesting() {

    init {
        Keymap.keymapFile = views.storage
    }

    @Test
    fun up_text_input_should_update_keymaps_up_input() = viewsTest {
        assertEquals('z', GameConfig.keyMap.up)
        keymapTextInputs()
        val upInput = (getChildByName(UP_INPUT)!! as Container)[1]
        upInput.simulateClick()
        assertEquals("", upInput.getProp("text"))
        launchImmediately { keyType('a') }.invokeOnCompletion {
            assertEquals("a", upInput.getProp("text"))
            assertEquals('a', GameConfig.keyMap.up)
        }
    }

    @Test
    fun down_text_input_should_update_keymaps_down_input() = viewsTest {
        assertEquals('s', GameConfig.keyMap.down)
        keymapTextInputs()
        val downInput = (getChildByName(DOWN_INPUT)!! as Container).children.first { it::class == UITextInput::class } as UITextInput
        launchImmediately { downInput.focus() }.invokeOnCompletion {
            assertEquals("", downInput.text)
            launchImmediately { keyType('w') }.invokeOnCompletion {
                assertEquals("w", downInput.text)
                assertEquals('w', GameConfig.keyMap.down)
            }
        }
    }

    @Test
    fun left_text_input_should_update_keymaps_left_input() = viewsTest {
        assertEquals('q', GameConfig.keyMap.left)
        keymapTextInputs()
        val leftInput = (getChildByName(LEFT_INPUT)!! as Container).children.first { it::class == UITextInput::class } as UITextInput
        launchImmediately { leftInput.focus() }.invokeOnCompletion {
            assertEquals("", leftInput.text)
            launchImmediately { keyType('t') }.invokeOnCompletion {
                assertEquals("t", leftInput.text)
                assertEquals('t', GameConfig.keyMap.left)
            }
        }
    }

    @Test
    fun right_text_input_should_update_keymaps_right_input() = viewsTest {
        assertEquals('d', GameConfig.keyMap.right)
        keymapTextInputs()
        val rightInput = (getChildByName(RIGHT_INPUT)!! as Container).children.first { it::class == UITextInput::class } as UITextInput
        launchImmediately { rightInput.focus() }.invokeOnCompletion {
            assertEquals("", rightInput.text)
            launchImmediately { keyType('g') }.invokeOnCompletion {
                assertEquals("g", rightInput.text)
                assertEquals('g', GameConfig.keyMap.right)
            }
        }
    }

    @Test
    fun power_text_input_should_update_keymaps_power_input() = viewsTest {
        assertEquals('p', GameConfig.keyMap.powers)
        keymapTextInputs()
        val powerInput = (getChildByName(POWER_INPUT)!! as Container).children.first { it::class == UITextInput::class } as UITextInput
        launchImmediately { powerInput.focus() }.invokeOnCompletion {
            assertEquals("", powerInput.text)
            launchImmediately { keyType('b') }.invokeOnCompletion {
                assertEquals("b", powerInput.text)
                assertEquals('b', GameConfig.keyMap.powers)
            }
        }
    }
}