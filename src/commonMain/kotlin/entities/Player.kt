package entities

import com.soywiz.klock.seconds
import com.soywiz.korev.Key
import com.soywiz.korev.Key.*
import com.soywiz.korge.animate.animator
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import com.soywiz.korim.atlas.Atlas
import com.soywiz.korio.async.runBlockingNoSuspensions
import entities.PlayerStatus.*
import exceptions.UninitializedSpriteException
import skills.passive.PassiveSkill
import utils.AnimationTitle

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
    open var moveXDirection: Key? = null,
    open var moveYDirection: Key? = null,
    open var animations: Map<AnimationTitle, Atlas> = mapOf(),
    open var passiveSkills: MutableList<PassiveSkill> = mutableListOf()
): Entity(maxHp, hp, range, spriteAtlas, sprite, speed, width, height, attackSpeed, damage) {

    fun mayMove() {
        sprite?.animator {
            parallel {
                processMoveCoordinates()?.let { (px, py) ->
                    if (playerStatus == RUN) {
                        runBlockingNoSuspensions { sprite!!.moveTo(px, py, time = 0.5.seconds) }
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
                    if (it == RIGHT) coordinates = coordinates!!.first.plus(speed) to coordinates!!.second
                    else if (it == LEFT) coordinates = coordinates!!.first.minus(speed) to coordinates!!.second
                }
                moveYDirection?.let {
                    if (it == UP) coordinates = coordinates!!.first to coordinates!!.second.minus(speed)
                    else if (it == DOWN) coordinates = coordinates!!.first to coordinates!!.second.plus(speed)
                }
            }
        }
        return coordinates
    }

    fun processAdditionalDamage(): Double{
        var processedDamage = damage
        passiveSkills.forEach {
            processedDamage += it.additionalDamageFlat()
            processedDamage *= it.additionalDamagePercent()
        }
        return processedDamage
    }
    abstract fun processMainAttack(container: Container, enemies: MutableList<Enemy>)
}