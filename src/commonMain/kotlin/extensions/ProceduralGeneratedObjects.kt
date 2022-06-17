package extensions

import com.soywiz.kds.fastArrayListOf
import com.soywiz.korge.view.fast.FastSprite
import com.soywiz.korge.view.fast.FastSpriteContainer

object ProceduralGeneratedObjects {
    val obstacles = fastArrayListOf<FastSprite>()
    lateinit var backgroundFastSpriteContainer: FastSpriteContainer
}