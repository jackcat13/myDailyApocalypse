package entities

import com.soywiz.korev.Key
import com.soywiz.korge.view.Container
import com.soywiz.korim.atlas.Atlas
import utils.AnimationTitle

/**
 * Abstract Enemy class. Centralize common behaviors of all enemies of the game.
 * @property maxHp Maximum health
 * @property hp Current health
 * @property range Range
 * @property spriteAtlas Enemy sprite atlas
 * @property sprite Enemy sprite
 * @property speed Speed
 * @property width Width
 * @property height Height
 * @property attackSpeed Attack speed
 * @property damage Damage
 * @property moveXDirection Left of Right direction
 * @property moveYDirection Up or Down direction
 * @property animations Enemy animations
 */
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

    /**
     * May follow the player if he's too far away from the enemy
     * @param playerSprite The player to follow
     */
    fun mayFollowPlayer(playerSprite: Container) {
        sprite?.let {
            if (it.x < playerSprite.x) it.x += speed
            if (it.x > playerSprite.x) it.x -= speed
            if (it.y < playerSprite.y) it.y += speed
            if (it.y > playerSprite.y) it.y -= speed
        }
    }

    /**
     * Process hit received by the player.
     * @param player The players that hits the enemy
     */
    fun hitBy(player: Player): Boolean{
        val processedDamage = player.processDamage()
        hp -= processedDamage
        println("HIT: $processedDamage. HP: $hp")
        return hp <= 0
    }
}