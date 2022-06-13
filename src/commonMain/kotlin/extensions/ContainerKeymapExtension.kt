package extensions

import com.soywiz.korge.ui.uiTextInput
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.container
import com.soywiz.korge.view.position
import com.soywiz.korge.view.text
import config.GameConfig

private const val FIRST_COLUMNS_X = 150.0
private const val TEXT_INPUT_X = 70.0

fun Container.keymapTextInputs() {
    container { saveUpKeyInput() }.position(FIRST_COLUMNS_X, 10.0)
    container { saveDownKeyInput() }.position(FIRST_COLUMNS_X, 40.0)
    container { saveLeftKeyInput() }.position(FIRST_COLUMNS_X, 70.0)
    container { saveRightKeyInput() }.position(FIRST_COLUMNS_X, 100.0)
    container { savePowersKeyInput() }.position(FIRST_COLUMNS_X, 130.0)
}

private fun Container.saveUpKeyInput(){
    text("Up : ")
    val upTextInput = uiTextInput(GameConfig.keyMap.up.toString())
    upTextInput.x = TEXT_INPUT_X
    upTextInput.onFocused { it.text = "" }
    upTextInput.onTextUpdated { (upTextInput.text.asChar())?.let{ GameConfig.keyMap.saveUp(it) } }
}

private fun Container.saveDownKeyInput(){
    text("Down : ")
    val downTextInput = uiTextInput(GameConfig.keyMap.down.toString())
    downTextInput.x = TEXT_INPUT_X
    downTextInput.onFocused { it.text = "" }
    downTextInput.onTextUpdated { (downTextInput.text.asChar())?.let{ GameConfig.keyMap.saveDown(it) } }
}

private fun Container.saveLeftKeyInput(){
    text("Left : ")
    val leftTextInput = uiTextInput(GameConfig.keyMap.left.toString())
    leftTextInput.x = TEXT_INPUT_X
    leftTextInput.onFocused { it.text = "" }
    leftTextInput.onTextUpdated { (leftTextInput.text.asChar())?.let{ GameConfig.keyMap.saveLeft(it) } }
}

private fun Container.saveRightKeyInput(){
    text("Right : ")
    val rightTextInput = uiTextInput(GameConfig.keyMap.right.toString())
    rightTextInput.x = TEXT_INPUT_X
    rightTextInput.onFocused { it.text = "" }
    rightTextInput.onTextUpdated { (rightTextInput.text.asChar())?.let{ GameConfig.keyMap.saveRight(it) } }
}

private fun Container.savePowersKeyInput(){
    text("Powers : ")
    val powersTextInput = uiTextInput(GameConfig.keyMap.powers.toString())
    powersTextInput.x = TEXT_INPUT_X
    powersTextInput.onFocused { it.text = "" }
    powersTextInput.onTextUpdated { (powersTextInput.text.asChar())?.let{ GameConfig.keyMap.savePowers(it) } }
}