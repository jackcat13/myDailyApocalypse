package entities

import com.soywiz.korev.Key
import com.soywiz.korev.Key.DOWN
import com.soywiz.korev.Key.LEFT
import com.soywiz.korev.Key.RIGHT
import com.soywiz.korev.Key.UP
import com.soywiz.korge.animate.animator
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.position
import com.soywiz.korim.atlas.Atlas
import entities.PlayerStatus.RUN
import entities.PlayerStatus.RUN_FULL_SPEED
import entities.PlayerStatus.STAY
import exceptions.UninitializedSpriteException

open class Player(
    override var maxHp: Int = 100,
    override var hp: Int = maxHp,
    override var range: Double = 100.0,
    override var spriteAtlas: Atlas,
    override var sprite: Container? = null,
    override var speed: Double = 3.0,
    override var width: Int = 20,
    override var height: Int = 40,
    var playerStatus: PlayerStatus = STAY,
    var moveXDirection: Key? = null,
    var moveYDirection: Key? = null,
    val type: EntityType
): Entity(spriteAtlas = spriteAtlas) {

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
}