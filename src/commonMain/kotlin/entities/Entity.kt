package entities

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.Image
import com.soywiz.korge.view.image
import com.soywiz.korge.view.position
import com.soywiz.korge.view.scale
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.BitmapSlice
import com.soywiz.korio.resources.Resource

open class Entity (
        open var maxHp: Int = 100,
        open var hp: Int = maxHp,
        open var range: Int = 40,
        open var sprite: Image? = null,
        open var speed: Double = 1.0,
        open var width: Int = 20,
        open var height: Int = 40,
) {
    fun initDraw(container: Container, x: Double, y: Double, korge_png: Resource<BitmapSlice<Bitmap>>) {
        sprite = container.image(korge_png)
                .scale(0.1)
                .position(x, y)
    }
}