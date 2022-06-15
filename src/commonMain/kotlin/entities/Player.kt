package entities

import com.soywiz.korge.animate.animator
import com.soywiz.korge.view.Circle
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import com.soywiz.korim.atlas.Atlas
import com.soywiz.korio.async.runBlockingNoSuspensions
import config.GameConfig
import entities.PlayerStatus.RUN
import entities.PlayerStatus.RUN_FULL_SPEED
import entities.PlayerStatus.STAY
import exceptions.UninitializedSpriteException
import skills.active.ActiveSkill
import skills.passive.PassiveSkill
import utils.AnimationTitle

/**
 * Player class to centralize common behavior of all playable characters
 * @property maxHp Maximum health
 * @property hp Current health
 * @property range Range
 * @property spriteAtlas Entity sprite atlas
 * @property sprite Entity sprite
 * @property speed Speed
 * @property width Width
 * @property height Height
 * @property attackSpeed AttackSpeed
 * @property damage Damage
 * @property playerStatus Current player status
 * @property moveXDirection Left of Right direction
 * @property moveYDirection Up or Down direction
 * @property animations Player animations
 * @property passiveSkills Player passive skills
 * @property activeSkills Player active skills
 */
abstract class Player(
        override var maxHp: Double = 100.0,
        override var hp: Double = maxHp,
        override var range: Double = 100.0,
        override var spriteAtlas: Atlas,
        override var sprite: Container? = null,
        override var speed: Double = 3.0,
        override var width: Int = 82,
        override var height: Int = 82,
        override var attackSpeed: Double = 1.0,
        override var damage: Double = 50.0,
        open var playerStatus: PlayerStatus = STAY,
        open var moveXDirection: Char? = null,
        open var moveYDirection: Char? = null,
        open var animations: Map<AnimationTitle, Atlas> = mapOf(),
        open var passiveSkills: MutableList<PassiveSkill> = mutableListOf(),
        open var activeSkills: MutableList<ActiveSkill> = mutableListOf(),
): Entity(maxHp, hp, range, spriteAtlas, sprite, speed, width, height, attackSpeed, damage) {

    /**
     * Handles player possible movements.
     */
    fun mayMove() {
        sprite?.animator {
            parallel {
                processMoveCoordinates()?.let { (px, py) ->
                    if (playerStatus == RUN) {
                        runBlockingNoSuspensions { sprite!!.position(px, py) }
                        playerStatus = RUN_FULL_SPEED
                    } else if (playerStatus == RUN_FULL_SPEED) {
                        sprite!!.position(px, py)
                    }
                    println("X: $px, Y: $py")
                }
            }
        } ?: throw UninitializedSpriteException("Player sprite has not been initialized")
    }

    private fun processMoveCoordinates(): Pair<Double, Double>? {
        var coordinates: Pair<Double, Double>? = null
        if (playerStatus in setOf(RUN, RUN_FULL_SPEED)){
            sprite?.let { s ->
                coordinates = s.x to s.y
                moveXDirection?.let {
                    if (it == GameConfig.keyMap.right) coordinates = coordinates!!.first.plus(processSpeed()) to coordinates!!.second
                    else if (it == GameConfig.keyMap.left) coordinates = coordinates!!.first.minus(processSpeed()) to coordinates!!.second
                }
                moveYDirection?.let {
                    if (it == GameConfig.keyMap.up) coordinates = coordinates!!.first to coordinates!!.second.minus(processSpeed())
                    else if (it == GameConfig.keyMap.down) coordinates = coordinates!!.first to coordinates!!.second.plus(processSpeed())
                }
            }
        }
        return coordinates
    }

    /**
     * Process player damage based on player stats plus his power ups
     */
    fun processDamage(): Double{
        var processedDamage = damage
        passiveSkills.forEach {
            processedDamage += it.additionalDamageFlat()
            processedDamage *= it.additionalDamageMultiplier()
        }
        return processedDamage
    }

    /**
     * Process player speed based on player stats plus his power ups
     */
    fun processSpeed(): Double{
        var processedSpeed = if (playerStatus == RUN_FULL_SPEED) speed else speed/2
        passiveSkills.forEach { processedSpeed += it.additionalSpeed() }
        return processedSpeed
    }

    /**
     * Process player range based on player stats plus his power ups
     */
    fun processRange(): Double{
        var processedRange = range
        passiveSkills.forEach { processedRange += it.additionalRange() }
        (sprite!!.getChildByName(RANGE_CIRCLE_NAME) as Circle).radius = processedRange
        return processedRange
    }

    /**
     * Process player main attack. To override for each playable character.
     */
    abstract fun processMainAttack(container: Container, enemies: MutableList<Enemy>)
}