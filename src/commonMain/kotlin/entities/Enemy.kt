package entities

import com.soywiz.klock.timesPerSecond
import com.soywiz.korev.Key
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Sprite
import com.soywiz.korge.view.collidesWith
import com.soywiz.korge.view.getSpriteAnimation
import com.soywiz.korim.atlas.Atlas
import com.soywiz.korim.bitmap.flippedX
import config.GameConfig
import extensions.addFixedUpdaterWithPause
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
        override var sprite: Sprite? = null,
        override var speed: Double = 3.0,
        override var width: Int = 82,
        override var height: Int = 82,
        override var attackSpeed: Double = 1.0,
        override var damage: Double = 15.0,
        open var moveXDirection: Key? = null,
        open var moveYDirection: Key? = null,
        open var animations: Map<AnimationTitle, Atlas> = mapOf()
): Entity(maxHp, hp, range, spriteAtlas, sprite, speed, width, height, attackSpeed, damage) {

    override fun initDraw(container: Container, x: Double, y: Double, name: String) {
        super.initDraw(container, x, y, name)
        sprite!!.setSizeScaled(36.0, 62.0)
    }

    /**
     * May follow the player if he's too far away from the enemy
     * @param container The container in which the player is followed
     * @param player The player to follow
     */
    fun mayFollowPlayer(player: Player) {
        sprite?.let { player?.sprite?.let { playerSprite ->
            if (it.x < playerSprite.x) {
                it.x += speed
                playRightRunAnimation()
            }
            if (it.x > playerSprite.x) {
                it.x -= speed
                playLeftRunAnimation()
            }
            if (it.y < playerSprite.y) it.y += speed
            if (it.y > playerSprite.y) it.y -= speed
        } }
    }

    private fun playRightRunAnimation() {
        sprite!!.playAnimationLooped(spriteAtlas.getSpriteAnimation(SpritesAnimationConstants.RUN))
    }

    private fun playLeftRunAnimation() {
        sprite!!.playAnimationLooped(spriteAtlas.getSpriteAnimation(SpritesAnimationConstants.RUN))
        sprite!!.bitmap = sprite!!.bitmap.flippedX()
    }

    /**
     * May hit the player in case of collision
     * @param container The container where entities are created
     * @param player The player to hit
     */
    fun mayHitPlayer(container: Container, player: Player){
        container.addFixedUpdaterWithPause(attackSpeed.timesPerSecond) {
            if (sprite!!.collidesWith(player.sprite!!)) {
                player.hitBy(this)
            }
        }
    }

    /**
     * Process hit received by the player.
     * @param player The players that hits the enemy
     * @return boolean to indicate if the current enemy is dead (true) or not (false).
     */
    fun hitBy(player: Player): Boolean{
        val processedDamage = player.processDamage()
        hp -= processedDamage
        println("HIT: $processedDamage. HP: $hp")
        return hp <= 0
    }
}
