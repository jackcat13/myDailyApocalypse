package entities

import com.soywiz.korev.Key
import com.soywiz.korge.animate.animate
import com.soywiz.korge.view.*
import com.soywiz.korim.atlas.Atlas
import com.soywiz.korim.color.ColorAdd
import com.soywiz.korim.color.Colors.RED
import com.soywiz.korma.geom.shape.Shape2d
import com.soywiz.korma.geom.vector.rect
import extensions.testDamage
import utils.AnimationTitle
import utils.AnimationTitle.SLASH

class Soldier (
    override var maxHp: Double = 120.0,
    override var hp: Double = maxHp,
    override var range: Double = 205.0,
    override var spriteAtlas: Atlas,
    override var sprite: Container? = null,
    override var speed: Double = 3.0,
    override var width: Int = 20,
    override var height: Int = 40,
    override var attackSpeed: Double = 1.0,
    override var damage: Double = 60.0,
    override var playerStatus: PlayerStatus = PlayerStatus.STAY,
    override var moveXDirection: Key? = null,
    override var moveYDirection: Key? = null,
    override var animations: Map<AnimationTitle, Atlas>
): Player(maxHp, hp, range, spriteAtlas, sprite, speed, width, height, attackSpeed, damage, playerStatus, moveXDirection, moveYDirection) {

    override fun processMainAttack(container: Container, enemies: MutableList<Enemy>) {
        val playerSprite = sprite[ENTITY_SPRITE_NAME].first as Sprite
        val slash = container.sprite(animations[SLASH]!!.getSpriteAnimation(prefix = "slash"))
                .position(sprite!!.x + playerSprite.width/2, sprite!!.y)
        slash.scaledWidth = range
        slash.playAnimation()
        slash.onAnimationCompleted.once {
            slash.maySlashEnnemies(enemies)
        }
    }

    private fun Sprite.maySlashEnnemies(enemies: MutableList<Enemy>) {
        val enemiesToKill = mutableListOf<Enemy>()
        enemies.forEach {
            val enemySprite = it.sprite[ENTITY_SPRITE_NAME].first
            if (collidesWith(enemySprite)) {
                if (it.hit(damage)) {
                    enemiesToKill.add(it)
                }
            }
        }
        removeFromParent()
        enemiesToKill.forEach {
            it.sprite!!.removeFromParent()
            enemies.remove(it)
        }
    }

}