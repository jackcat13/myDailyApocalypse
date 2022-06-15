package entities

import com.soywiz.korev.Key
import com.soywiz.korge.view.Container
import com.soywiz.korim.atlas.Atlas
import utils.AnimationTitle

/**
 * Imp class to create imp enemies
 * @property maxHp Maximum Health
 * @property hp Current health
 * @property range Range
 * @property spriteAtlas Imp sprite atlas
 * @property sprite Imp sprite
 * @property speed Speed
 * @property width Width
 * @property height Height
 * @property attackSpeed Attack speed
 * @property damage Damage
 * @property moveXDirection Left of Right direction
 * @property moveYDirection Up or Down direction
 * @property animations Imp animations
 */
class Imp(
    override var maxHp: Double = 100.0,
    override var hp: Double = maxHp,
    override var range: Double = 100.0,
    override var spriteAtlas: Atlas,
    override var sprite: Container? = null,
    override var speed: Double = 1.0,
    override var width: Int = 82,
    override var height: Int = 82,
    override var attackSpeed: Double = 1.0,
    override var damage: Double = 15.0,
    override var moveXDirection: Key? = null,
    override var moveYDirection: Key? = null,
    override var animations: Map<AnimationTitle, Atlas> = mapOf()
): Enemy(maxHp, hp, range, spriteAtlas, sprite, speed, width, height, attackSpeed, damage, moveXDirection, moveYDirection, animations) {


}