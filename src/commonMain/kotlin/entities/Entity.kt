package entities

import com.soywiz.korge.view.*
import com.soywiz.korim.atlas.Atlas
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.BitmapSlice
import com.soywiz.korim.color.Colors
import entities.SpritesAnimationConstants.STAND

open class Entity (
    open var maxHp: Int = 100,
    open var hp: Int = maxHp,
    open var range: Double = 40.0,
    open var spriteAtlas: Atlas,
    open var sprite: Container? = null,
    open var speed: Double = 1.0,
    open var width: Int = 200,
    open var height: Int = 40,
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
            entitySprite.playAnimationLooped()
            if (this@Entity::class == Player::class) {
                circle(range, Colors["#ffffff00"], Colors["#DF52527f"], 2.0)
                    .centerOn(entitySprite)
            }
        }.position(x, y)
    }
}