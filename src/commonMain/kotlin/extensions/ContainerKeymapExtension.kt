package extensions

import com.soywiz.korge.input.onClick
import com.soywiz.korge.ui.uiTextInput
import com.soywiz.korge.view.*
import config.GameConfig

const val FIRST_COLUMNS_X = 150.0
const val UP_INPUT = "upInput"
const val UP_INPUT_TEXT = "Up : "
const val DOWN_INPUT = "downInput"
const val DOWN_INPUT_TEXT = "Down : "
const val LEFT_INPUT = "leftInput"
const val LEFT_INPUT_TEXT = "Left : "
const val RIGHT_INPUT = "rightInput"
const val RIGHT_INPUT_TEXT = "Right : "
const val POWER_INPUT = "powerInput"
const val POWER_INPUT_TEXT = "Power : "
private const val TEXT_INPUT_X = 70.0

/**
 * Handles keymap graphical interface and its events.
 * It allows to change keys configuration for actions of the game.
 */
fun Container.keymapTextInputs() {
    container { saveUpKeyInput() }.position(FIRST_COLUMNS_X, 10.0).name(UP_INPUT)
    container { saveDownKeyInput() }.position(FIRST_COLUMNS_X, 40.0).name(DOWN_INPUT)
    container { saveLeftKeyInput() }.position(FIRST_COLUMNS_X, 70.0).name(LEFT_INPUT)
    container { saveRightKeyInput() }.position(FIRST_COLUMNS_X, 100.0).name(RIGHT_INPUT)
    container { savePowersKeyInput() }.position(FIRST_COLUMNS_X, 130.0).name(POWER_INPUT)
}

private fun Container.saveUpKeyInput(){
    text(UP_INPUT_TEXT)
    val upTextInput = uiTextInput(GameConfig.keyMap.up.toString())
    upTextInput.x = TEXT_INPUT_X
    upTextInput.onClick { upTextInput.text = "" }
    upTextInput.onFocusLost { (upTextInput.text.asChar())?.let{ GameConfig.keyMap.saveUp(it) } }
}

private fun Container.saveDownKeyInput(){
    text(DOWN_INPUT_TEXT)
    val downTextInput = uiTextInput(GameConfig.keyMap.down.toString())
    downTextInput.x = TEXT_INPUT_X
    downTextInput.onClick { downTextInput.text = "" }
    downTextInput.onFocusLost { (downTextInput.text.asChar())?.let{ GameConfig.keyMap.saveDown(it) } }
}

private fun Container.saveLeftKeyInput(){
    text(LEFT_INPUT_TEXT)
    val leftTextInput = uiTextInput(GameConfig.keyMap.left.toString())
    leftTextInput.x = TEXT_INPUT_X
    leftTextInput.onClick { leftTextInput.text = "" }
    leftTextInput.onFocusLost { (leftTextInput.text.asChar())?.let{ GameConfig.keyMap.saveLeft(it) } }
}

private fun Container.saveRightKeyInput(){
    text(RIGHT_INPUT_TEXT)
    val rightTextInput = uiTextInput(GameConfig.keyMap.right.toString())
    rightTextInput.x = TEXT_INPUT_X
    rightTextInput.onClick { rightTextInput.text = "" }
    rightTextInput.onFocusLost { (rightTextInput.text.asChar())?.let{ GameConfig.keyMap.saveRight(it) } }
}

private fun Container.savePowersKeyInput(){
    text(POWER_INPUT_TEXT)
    val powersTextInput = uiTextInput(GameConfig.keyMap.powers.toString())
    powersTextInput.x = TEXT_INPUT_X
    powersTextInput.onClick { powersTextInput.text = "" }
    powersTextInput.onFocusLost { (powersTextInput.text.asChar())?.let{ GameConfig.keyMap.savePowers(it) } }
}