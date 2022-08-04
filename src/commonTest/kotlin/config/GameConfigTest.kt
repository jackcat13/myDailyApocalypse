package config

import com.soywiz.korge.service.storage.get
import com.soywiz.korge.service.storage.storage
import com.soywiz.korge.tests.ViewsForTesting
import extensions.asChar
import kotlin.test.Test
import kotlin.test.assertEquals

class GameConfigTest: ViewsForTesting() {

    @Test
    fun changing_key_mapping_should_be_stored_properly() = viewsTest {
        Keymap.keymapFile = views.storage
        GameConfig.keyMap = Keymap()
        GameConfig.keyMap.saveUp('t')
        GameConfig.keyMap.saveDown('y')
        GameConfig.keyMap.saveLeft('g')
        GameConfig.keyMap.saveRight('a')
        GameConfig.keyMap.savePowers('l')
        assertEquals('t', Keymap.keymapFile[Keymap::up.name].asChar())
        assertEquals('y', Keymap.keymapFile[Keymap::down.name].asChar())
        assertEquals('g', Keymap.keymapFile[Keymap::left.name].asChar())
        assertEquals('a', Keymap.keymapFile[Keymap::right.name].asChar())
        assertEquals('l', Keymap.keymapFile[Keymap::powers.name].asChar())
    }
}