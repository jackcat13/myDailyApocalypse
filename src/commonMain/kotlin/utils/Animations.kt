package utils

import com.soywiz.korim.atlas.Atlas
import com.soywiz.korim.atlas.readAtlas
import com.soywiz.korio.file.std.resourcesVfs
import utils.AnimationTitle.BITE
import utils.AnimationTitle.SLASH

/**
 * Object to build collection of animations of the game
 */
object Animations{

    /**
     * Builds the game animations
     */
    suspend fun build() = mapOf<AnimationTitle, Atlas>(
            SLASH to resourcesVfs["slash.xml"].readAtlas(),
            BITE to resourcesVfs["bite.xml"].readAtlas(),
    )
}

enum class AnimationTitle{
    SLASH, BITE
}