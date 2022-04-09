package entities

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.centerOn
import com.soywiz.korge.view.circle
import com.soywiz.korge.view.container
import com.soywiz.korge.view.name
import com.soywiz.korge.view.position
import com.soywiz.korge.view.solidRect
import com.soywiz.korge.view.sprite
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.BitmapSlice
import com.soywiz.korim.color.Colors
import com.soywiz.korim.vector.StrokeInfo

open class Entity (
        open var maxHp: Int = 100,
        open var hp: Int = maxHp,
        open var range: Double = 40.0,
        open var spriteBitmap: BitmapSlice<Bitmap>,
        open var sprite: Container? = null,
        open var speed: Double = 1.0,
        open var width: Int = 200,
        open var height: Int = 40,
) {
    fun initDraw(container: Container, x: Double, y: Double, bitmap: BitmapSlice<Bitmap>, name: String = "Entity") {
        sprite = container.container {
            val entitySprite = sprite(bitmap).name(name)
            if (this@Entity::class == Player::class) {
                circle(range, Colors["#ffffff00"], Colors["#DF52527f"], 2.0)
                        .centerOn(entitySprite)
            }
        }.position(x, y)
    }
}