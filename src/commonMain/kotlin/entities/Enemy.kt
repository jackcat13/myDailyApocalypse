package entities

import com.soywiz.korev.Key
import com.soywiz.korge.view.Container
import com.soywiz.korim.atlas.Atlas
import utils.AnimationTitle

abstract class Enemy(
    override var maxHp: Double = 100.0,
    override var hp: Double = maxHp,
    override var range: Double = 100.0,
    override var spriteAtlas: Atlas,
    override var sprite: Container? = null,
    override var speed: Double = 3.0,
    override var width: Int = 82,
    override var height: Int = 82,
    override var attackSpeed: Double = 1.0,
    override var damage: Double = 15.0,
    open var moveXDirection: Key? = null,
    open var moveYDirection: Key? = null,
    open var animations: Map<AnimationTitle, Atlas> = mapOf()
): Entity(maxHp, hp, range, spriteAtlas, sprite, speed, width, height, attackSpeed, damage) {

    fun mayFollowPlayer(playerSprite: Container) {
        sprite?.let {
            if (it.x < playerSprite.x) it.x += speed
            if (it.x > playerSprite.x) it.x -= speed
            if (it.y < playerSprite.y) it.y += speed
            if (it.y > playerSprite.y) it.y -= speed
        }
    }

    fun hit(incommingDamage: Double): Boolean{
        hp -= incommingDamage
        println("HIT: $incommingDamage. HP: $hp")
        return hp <= 0
    }
}