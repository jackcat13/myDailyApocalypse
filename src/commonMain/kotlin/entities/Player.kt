package entities

import com.soywiz.kds.FastArrayList
import com.soywiz.korge.animate.animator
import com.soywiz.korge.view.*
import com.soywiz.korim.atlas.Atlas
import com.soywiz.korim.bitmap.flippedX
import com.soywiz.korio.async.runBlockingNoSuspensions
import config.ExcludeFromJacocoGeneratedReport
import config.GameConfig
import config.GameStatus
import entities.PlayerStatus.*
import entities.SpritesAnimationConstants.STAND
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
        override var sprite: Sprite? = null,
        override var speed: Double = 3.0,
        override var width: Int = 82,
        override var height: Int = 82,
        override var attackSpeed: Double = 1.0,
        override var damage: Double = 50.0,
        open var playerStatus: PlayerStatus = STAY,
        open var moveXDirection: Char? = null,
        open var previousXDirection: Char? = null,
        open var moveYDirection: Char? = null,
        open var animations: Map<AnimationTitle, Atlas> = mapOf(),
        open var passiveSkills: MutableList<PassiveSkill> = mutableListOf(),
        open var activeSkills: MutableList<ActiveSkill> = mutableListOf(),
): Entity(maxHp, hp, range, spriteAtlas, sprite, speed, width, height, attackSpeed, damage) {

    override fun initDraw(container: Container, x: Double, y: Double, name: String) {
        super.initDraw(container, x, y, name)
        sprite!!.setSizeScaled(48.0, 62.0)
    }

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
     * Process hit received by an enemy.
     * @param player The players that hits the enemy
     * @return boolean to indicate if the current enemy is dead (true) or not (false).
     */
    fun hitBy(enemy: Enemy): Boolean{
        hp -= enemy.damage
        println("HIT: ${enemy.damage}. HP: $hp")
        sprite!!.hitAnimation()
        if (hp <= 0) {
            playerStatus = DEAD
            return true
        }
        return false
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
        return processedRange
    }

    /**
     * Verifies what animation to play depending on player status
     */
    fun checkAnimation() {
        if (playerStatus == RUN || playerStatus == RUN_FULL_SPEED){
            if (moveXDirection == GameConfig.keyMap.right) playRightRunAnimation()
            else if (moveXDirection == GameConfig.keyMap.left) playLeftRunAnimation()
            else if (previousXDirection == GameConfig.keyMap.left) playLeftRunAnimation()
            else if (moveYDirection != null) playRightRunAnimation()
        } else if (playerStatus == STAY) sprite!!.playAnimationLooped(spriteAtlas.getSpriteAnimation(STAND))
    }

    private fun playRightRunAnimation() {
        sprite!!.playAnimationLooped(spriteAtlas.getSpriteAnimation(SpritesAnimationConstants.RUN))
        previousXDirection = GameConfig.keyMap.right
    }

    private fun playLeftRunAnimation() {
        sprite!!.playAnimationLooped(spriteAtlas.getSpriteAnimation(SpritesAnimationConstants.RUN))
        sprite!!.bitmap = sprite!!.bitmap.flippedX()
        previousXDirection = GameConfig.keyMap.left
    }

    /**
     * Process player main attack. To override for each playable character.
     */
    abstract fun processMainAttack(container: Container, enemies: FastArrayList<Enemy>)

    /**
     * Default main attack checks implementation
     */
    fun Sprite.maySlashEnnemies(enemies: FastArrayList<Enemy>) {
        val enemiesToKill = mutableListOf<Enemy>()
        enemies.forEach {
            val enemySprite = it.sprite!!
            if (collidesWith(enemySprite)) {
                enemySprite.hitAnimation()
                if (it.hitBy(this@Player)) {
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

        other as Player

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