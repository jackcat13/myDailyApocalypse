package utils

import com.soywiz.korim.atlas.Atlas
import com.soywiz.korim.atlas.readAtlas
import com.soywiz.korio.file.std.resourcesVfs
import utils.AnimationTitle.SLASH

object Animations{
    suspend fun build() = mapOf<AnimationTitle, Atlas>(SLASH to resourcesVfs["slash.xml"].readAtlas())
}

enum class AnimationTitle{
    SLASH
}