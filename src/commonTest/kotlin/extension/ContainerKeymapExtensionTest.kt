package extension

import com.soywiz.korge.service.storage.storage
import com.soywiz.korge.tests.ViewsForTesting
import com.soywiz.korge.ui.UITextInput
import com.soywiz.korge.ui.focus
import com.soywiz.korge.view.Container
import com.soywiz.korio.async.launchImmediately
import config.GameConfig
import config.Keymap
import extensions.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ContainerKeymapExtensionTest: ViewsForTesting() {

    init {
        Keymap.keymapFile = views.storage
    }

    @Test
    fun `Up text input should update keymaps up input`() = viewsTest {
        assertEquals('z', GameConfig.keyMap.up)
        keymapTextInputs()
        val upInput = (getChildByName(UP_INPUT)!! as Container).children.first { it::class == UITextInput::class } as UITextInput
        upInput.focus()
        assertEquals("", upInput.text)
        launchImmediately { keyType('a') }
        assertEquals("a", upInput.text)
        assertEquals('a', GameConfig.keyMap.up)
    }

    @Test
    fun `Down text input should update keymaps down input`() = viewsTest {
        assertEquals('s', GameConfig.keyMap.down)
        keymapTextInputs()
        val downInput = (getChildByName(DOWN_INPUT)!! as Container).children.first { it::class == UITextInput::class } as UITextInput
        downInput.focus()
        assertEquals("", downInput.text)
        launchImmediately { keyType('w') }
        assertEquals("w", downInput.text)
        assertEquals('w', GameConfig.keyMap.down)
    }

    @Test
    fun `Left text input should update keymaps left input`() = viewsTest {
        assertEquals('q', GameConfig.keyMap.left)
        keymapTextInputs()
        val leftInput = (getChildByName(LEFT_INPUT)!! as Container).children.first { it::class == UITextInput::class } as UITextInput
        leftInput.focus()
        assertEquals("", leftInput.text)
        launchImmediately { keyType('t') }
        assertEquals("t", leftInput.text)
        assertEquals('t', GameConfig.keyMap.left)
    }

    @Test
    fun `Right text input should update keymaps right input`() = viewsTest {
        assertEquals('d', GameConfig.keyMap.right)
        keymapTextInputs()
        val rightInput = (getChildByName(RIGHT_INPUT)!! as Container).children.first { it::class == UITextInput::class } as UITextInput
        rightInput.focus()
        assertEquals("", rightInput.text)
        launchImmediately { keyType('g') }
        assertEquals("g", rightInput.text)
        assertEquals('g', GameConfig.keyMap.right)
    }

    @Test
    fun `Power text input should update keymaps power input`() = viewsTest {
        assertEquals('p', GameConfig.keyMap.powers)
        keymapTextInputs()
        val powerInput = (getChildByName(POWER_INPUT)!! as Container).children.first { it::class == UITextInput::class } as UITextInput
        powerInput.focus()
        assertEquals("", powerInput.text)
        launchImmediately { keyType('b') }
        assertEquals("b", powerInput.text)
        assertEquals('b', GameConfig.keyMap.powers)
    }
}