package entities

import com.soywiz.kds.FastArrayList
import com.soywiz.korge.view.*
import com.soywiz.korim.atlas.Atlas
import skills.active.ActiveSkill
import skills.passive.PassiveSkill
import utils.AnimationTitle
import utils.AnimationTitle.BITE

const val WOLF_NAME = "Wolf"

/**
 * Wolf class to create wolf playable character
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
class Wolf (
        override var maxHp: Double = 80.0,
        override var hp: Double = maxHp,
        override var range: Double = 105.0,
        override var spriteAtlas: Atlas,
        override var sprite: Sprite? = null,
        override var speed: Double = 6.0,
        override var width: Int = 20,
        override var height: Int = 40,
        override var attackSpeed: Double = 2.0,
        override var damage: Double = 30.0,
        override var playerStatus: PlayerStatus = PlayerStatus.STAY,
        override var moveXDirection: Char? = null,
        override var previousXDirection: Char? = null,
        override var moveYDirection: Char? = null,
        override var animations: Map<AnimationTitle, Atlas>,
        override var passiveSkills: MutableList<PassiveSkill> = mutableListOf(),
        override var activeSkills: MutableList<ActiveSkill> = mutableListOf(),
): Player(maxHp, hp, range, spriteAtlas, sprite, speed, width, height, attackSpeed, damage, playerStatus, moveXDirection, previousXDirection, moveYDirection, animations, passiveSkills) {

    /**
     * Handles the wolf main attack
     */
    override fun processMainAttack(container: Container, enemies: FastArrayList<Enemy>) {
        val bite = container.sprite(animations[BITE]!!.getSpriteAnimation(prefix = "bite"))
        bite.addUpdater { position(sprite!!.x + sprite!!.width + 20, sprite!!.y + sprite!!.height/2) }
        bite.scaledWidth = range
        bite.playAnimation()
        bite.onAnimationCompleted.once {
            bite.maySlashEnnemies(enemies)
        }
    }

    override fun characterName() = WOLF_NAME
}