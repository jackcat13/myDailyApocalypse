package entities

import com.soywiz.korev.Key
import com.soywiz.korge.view.Container
import com.soywiz.korim.atlas.Atlas
import utils.AnimationTitle

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