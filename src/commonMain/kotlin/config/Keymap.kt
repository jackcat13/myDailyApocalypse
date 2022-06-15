package config

import com.soywiz.korge.service.storage.NativeStorage

/**
 * Handles keymap for the game actions. Can be configured.
 * @property up To move player in up direction
 * @property down To move player in down direction
 * @property left To move player in left direction
 * @property right To move player in right direction
 * @property powers To open powers menu
 */
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

    //Note: Can't do reflection of members of class because of native development

    /**
     * Load keymap from local storage to retrieve previous configuration saved.
     */
    fun loadKeyMap(){
        keymapFile.getOrNull(Keymap::up.name)?.let { up = it.toCharArray()[0] }
        keymapFile.getOrNull(Keymap::down.name)?.let { down = it.toCharArray()[0] }
        keymapFile.getOrNull(Keymap::left.name)?.let { left = it.toCharArray()[0] }
        keymapFile.getOrNull(Keymap::right.name)?.let { right = it.toCharArray()[0] }
        keymapFile.getOrNull(Keymap::powers.name)?.let { powers = it.toCharArray()[0] }
    }

    /**
     * Save up key in local storage to persist configuration
     * @param char key to save
     */
    fun saveUp(char: Char) {
        up = char
        println(up.toString())
        keymapFile[Keymap::up.name] = up.toString()
    }

    /**
     * Save down key in local storage to persist configuration
     * @param char key to save
     */
    fun saveDown(char: Char) {
        down = char
        keymapFile[Keymap::down.name] = down.toString()
    }

    /**
     * Save right key in local storage to persist configuration
     * @param char key to save
     */
    fun saveRight(char: Char) {
        right = char
        keymapFile[Keymap::right.name] = right.toString()
    }

    /**
     * Save left key in local storage to persist configuration
     * @param char key to save
     */
    fun saveLeft(char: Char) {
        left = char
        keymapFile[Keymap::left.name] = left.toString()
    }

    /**
     * Save powers key in local storage to persist configuration
     * @param char key to save
     */
    fun savePowers(char: Char) {
        powers = char
        keymapFile[Keymap::powers.name] = powers.toString()
    }
}
