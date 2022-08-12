package entities

import com.soywiz.kds.FastArrayList
import com.soywiz.korge.view.*
import com.soywiz.korim.atlas.Atlas
import config.ExcludeFromJacocoGeneratedReport
import skills.active.ActiveSkill
import skills.passive.PassiveSkill
import utils.AnimationTitle
import utils.AnimationTitle.SLASH

/**
 * Soldier class to create soldier playable character
 * @property maxHp Maximum health
 * @property hp Current health
 * @property range Range
 * @property spriteAtlas Soldier sprite atlas
 * @property sprite Soldier sprite
 * @property speed Speed
 * @property width Width
 * @property height Height
 * @property attackSpeed AttackSpeed
 * @property damage Damage
 * @property playerStatus Current soldier status
 * @property moveXDirection Left of Right direction
 * @property moveYDirection Up or Down direction
 * @property animations Soldier animations
 * @property passiveSkills Soldier passive skills
 * @property activeSkills Soldier active skills
 */
class Soldier (
        override var maxHp: Double = 120.0,
        override var hp: Double = maxHp,
        override var range: Double = 205.0,
        override var spriteAtlas: Atlas,
        override var sprite: Sprite? = null,
        override var speed: Double = 3.0,
        override var width: Int = 20,
        override var height: Int = 40,
        override var attackSpeed: Double = 1.0,
        override var damage: Double = 60.0,
        override var playerStatus: PlayerStatus = PlayerStatus.STAY,
        override var moveXDirection: Char? = null,
        override var previousXDirection: Char? = null,
        override var moveYDirection: Char? = null,
        override var animations: Map<AnimationTitle, Atlas>,
        override var passiveSkills: MutableList<PassiveSkill> = mutableListOf(),
        override var activeSkills: MutableList<ActiveSkill> = mutableListOf(),
): Player(maxHp, hp, range, spriteAtlas, sprite, speed, width, height, attackSpeed, damage, playerStatus, moveXDirection, previousXDirection, moveYDirection, animations, passiveSkills) {

    /**
     * Handles the soldier main attack
     */
    override fun processMainAttack(container: Container, enemies: FastArrayList<Enemy>) {
        val slash = container.sprite(animations[SLASH]!!.getSpriteAnimation(prefix = "slash"))
                .position(sprite!!.x + sprite!!.width/2, sprite!!.y)
        slash.scaledWidth = range
        slash.playAnimation()
        slash.onAnimationCompleted.once {
            slash.maySlashEnnemies(enemies)
        }
    }

    private fun Sprite.maySlashEnnemies(enemies: FastArrayList<Enemy>) {
        val enemiesToKill = mutableListOf<Enemy>()
        enemies.forEach {
            val enemySprite = it.sprite!!
            if (collidesWith(enemySprite)) {
                enemySprite.hitAnimation()
                if (it.hitBy(this@Soldier)) {
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

    @ExcludeFromJacocoGeneratedReport("Won't test equals method")
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Soldier

        if (maxHp != other.maxHp) return false
        if (hp != other.hp) return false
        if (range != other.range) return false
        if (speed != other.speed) return false
        if (width != other.width) return false
        if (height != other.height) return false
        if (attackSpeed != other.attackSpeed) return false
        if (damage != other.damage) return false

        return true
    }

    @ExcludeFromJacocoGeneratedReport("Won't test hashcode method")
    override fun hashCode(): Int {
        var result = maxHp.hashCode()
        result = 31 * result + hp.hashCode()
        result = 31 * result + range.hashCode()
        result = 31 * result + speed.hashCode()
        result = 31 * result + width
        result = 31 * result + height
        result = 31 * result + attackSpeed.hashCode()
        result = 31 * result + damage.hashCode()
        return result
    }


}