package entities

import com.soywiz.klock.seconds
import com.soywiz.korge.view.*
import com.soywiz.korim.atlas.Atlas
import com.soywiz.korim.color.Colors
import entities.SpritesAnimationConstants.STAND

abstract class Entity (
    open var maxHp: Double = 100.0,
    open var hp: Double = maxHp,
    open var range: Double = 40.0,
    open var spriteAtlas: Atlas,
    open var sprite: Container? = null,
    open var speed: Double = 1.0,
    open var width: Int = 200,
    open var height: Int = 40,
    open var attackSpeed: Double = 1.0,
    open var damage: Double = 1.0,
) {
    companion object{
        const val ENTITY_SPRITE_NAME = "Entity"
    }

    fun initDraw(container: Container, x: Double, y: Double, name: String = ENTITY_SPRITE_NAME) {
        draw(container, name, x, y)
    }

    fun draw(container: Container, name: String = ENTITY_SPRITE_NAME, x: Double, y: Double, animationType: String = STAND) {
        sprite = container.container {
            val entitySprite = sprite(spriteAtlas.getSpriteAnimation(prefix = animationType)).name(name)
            entitySprite.playAnimationLooped(spriteDisplayTime = 0.2.seconds)
            (this@Entity as? Player)?.let {
                circle(range, Colors["#ffffff00"], Colors["#DF52527f"], 2.0)
                    .centerOn(entitySprite)
            }
        }.position(x, y)
    }
}