package entities

import com.soywiz.klock.seconds
import com.soywiz.korge.animate.launchAnimate
import com.soywiz.korge.view.*
import com.soywiz.korim.atlas.Atlas
import com.soywiz.korio.async.runBlockingNoSuspensions
import entities.SpritesAnimationConstants.DEATH
import entities.SpritesAnimationConstants.STAND

/**
 * Entity class to centralize common behavior of all entities in the game.
 * @property maxHp Maximum health
 * @property hp Current health
 * @property range Range
 * @property spriteAtlas Entity sprite atlas
 * @property sprite Entity sprite
 * @property speed Speed
 * @property width Width
 * @property height Height
 * @property attackSpeed AttackSpeed
 * @property damage Damage
 */
abstract class Entity (
    open var maxHp: Double = 100.0,
    open var hp: Double = maxHp,
    open var range: Double = 40.0,
    open var spriteAtlas: Atlas,
    open var sprite: Sprite? = null,
    open var speed: Double = 1.0,
    open var width: Int = 200,
    open var height: Int = 40,
    open var attackSpeed: Double = 1.0,
    open var damage: Double = 1.0,
) {
    companion object{
        const val ENTITY_SPRITE_NAME = "Entity"
    }

    /**
     * Initializes entity sprites
     */
    open fun initDraw(container: Container, x: Double, y: Double, name: String = ENTITY_SPRITE_NAME) {
        draw(container, name, x, y)
        container.parent!!.sendChildToFront(sprite!!)
    }

    /**
     * Draw entity sprites
     * @param container The container in which the sprites are created
     * @param name Name of the container of sprites
     * @param x X position of sprites
     * @param y Y position of sprites
     */
    fun draw(container: Container, name: String = ENTITY_SPRITE_NAME, x: Double, y: Double, animationType: String = STAND) {
        sprite = container.sprite(spriteAtlas.getSpriteAnimation(prefix = animationType)).name(name).let {
            it.playAnimationLooped()
            it.position(x, y)
        }
    }

    /**
     * Hit animation of an entity
     */
    fun View.hitAnimation() {
        runBlockingNoSuspensions {
            launchAnimate {
                alpha(0.5, 0.1.seconds)
                alpha(1.0)
            }
        }
    }

    /**
     * Responsible of death animation.
     */
    fun launchDeathAnimation(){
        sprite!!.playAnimation(1, spriteAtlas.getSpriteAnimation(DEATH), spriteDisplayTime = 0.2.seconds)
    }
}