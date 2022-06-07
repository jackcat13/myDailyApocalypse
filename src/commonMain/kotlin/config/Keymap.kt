package config

import com.soywiz.korge.service.storage.NativeStorage

class Keymap(
        var up: Char = 'z',
        var down: Char = 's',
        var right: Char = 'd',
        var left: Char = 'q',
        var powers: Char = 'p'
){
    companion object{
        lateinit var keymapFile: NativeStorage
    }

    //Can't do reflection of members of class because of native development

    fun loadKeyMap(){
        keymapFile.getOrNull(Keymap::up.name)?.let { up = it.toCharArray()[0] }
        keymapFile.getOrNull(Keymap::down.name)?.let { down = it.toCharArray()[0] }
        keymapFile.getOrNull(Keymap::left.name)?.let { left = it.toCharArray()[0] }
        keymapFile.getOrNull(Keymap::right.name)?.let { right = it.toCharArray()[0] }
        keymapFile.getOrNull(Keymap::powers.name)?.let { powers = it.toCharArray()[0] }
    }

    fun saveUp(it: Char) {
        up = it
        println(up.toString())
        keymapFile[Keymap::up.name] = up.toString()
    }

    fun saveDown(it: Char) {
        down = it
        keymapFile[Keymap::down.name] = down.toString()
    }

    fun saveRight(it: Char) {
        right = it
        keymapFile[Keymap::right.name] = right.toString()
    }

    fun saveLeft(it: Char) {
        left = it
        keymapFile[Keymap::left.name] = left.toString()
    }

    fun savePowers(it: Char) {
        powers = it
        keymapFile[Keymap::powers.name] = powers.toString()
    }
}
