package entities

import com.soywiz.klock.seconds
import com.soywiz.klock.timesPerSecond
import com.soywiz.korev.Key
import com.soywiz.korge.view.*
import com.soywiz.korim.atlas.Atlas
import com.soywiz.korim.bitmap.Bitmap
import utils.AnimationTitle
import utils.AnimationTitle.SLASH

class Soldier (
    override var maxHp: Int = 120,
    override var hp: Int = maxHp,
    override var range: Double = 205.0,
    override var spriteAtlas: Atlas,
    override var sprite: Container? = null,
    override var speed: Double = 3.0,
    override var width: Int = 20,
    override var height: Int = 40,
    override var attackSpeed: Double = 1.0,
    override var playerStatus: PlayerStatus = PlayerStatus.STAY,
    override var moveXDirection: Key? = null,
    override var moveYDirection: Key? = null,
    override var animations: Map<AnimationTitle, Atlas>
): Player(maxHp, hp, range, spriteAtlas, sprite, speed, width, height, attackSpeed, playerStatus, moveXDirection, moveYDirection) {

    override fun processMainAttack(container: Container) {
        container.container {
            val slash = sprite(animations[SLASH]!!.getSpriteAnimation(prefix = "slash"))
                    .position(sprite!!.x + spriteAtlas.entries.first().info.sourceSize.width, sprite!!.y)
            slash.scaledWidth = range
            slash.playAnimation()
        }
    }

}