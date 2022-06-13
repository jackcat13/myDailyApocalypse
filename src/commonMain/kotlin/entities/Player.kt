package entities

import com.soywiz.klock.timesPerSecond
import com.soywiz.korev.Key
import com.soywiz.korev.Key.*
import com.soywiz.korge.animate.animator
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addFixedUpdater
import com.soywiz.korge.view.position
import com.soywiz.korim.atlas.Atlas
import com.soywiz.korim.bitmap.Bitmap
import entities.PlayerStatus.*
import exceptions.UninitializedSpriteException
import utils.AnimationTitle
import utils.Animations

abstract class Player(
    override var maxHp: Int = 100,
    override var hp: Int = maxHp,
    override var range: Double = 100.0,
    override var spriteAtlas: Atlas,
    override var sprite: Container? = null,
    override var speed: Double = 3.0,
    override var width: Int = 82,
    override var height: Int = 82,
    override var attackSpeed: Double = 1.0,
    open var playerStatus: PlayerStatus = STAY,
    open var moveXDirection: Key? = null,
    open var moveYDirection: Key? = null,
    open var animations: Map<AnimationTitle, Atlas> = mapOf()
): Entity(maxHp, hp, range, spriteAtlas, sprite, speed, width, height, attackSpeed) {

    fun mayMove() {
        sprite?.animator {
            parallel {
                processMoveCoordinates()?.let { (px, py) ->
                    if (playerStatus == RUN) {
                        sprite!!.position(px, py) //TODO: Find a way to animate smoothly start of run
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

    abstract fun processMainAttack(container: Container)
}