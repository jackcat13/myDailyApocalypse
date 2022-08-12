package utils

import com.soywiz.korge.view.Container
import com.soywiz.korim.atlas.readAtlas
import com.soywiz.korio.file.std.resourcesVfs
import entities.Imp
import entities.Player
import entities.Soldier
import entities.Wolf
import extensions.addUpdaterWithPause
import utils.GameRandom.generateRand

/**
 * Object to build the entities of the game (players/enemies)
 */
object EntitiesBuilder{

    /**
     * Generate imp enemy with basic IA following the player
     * @param container The container in which the imp is generated.
     * @param currentPlayer The player to follow.
     */
    internal suspend fun generateImp(container: Container, currentPlayer: Player): Imp {
        val imp = imp()
        imp.initDraw(container, currentPlayer.sprite!!.x+ generateRand(-600, -300), currentPlayer.sprite!!.y+ generateRand(-600, 600))
        container.addUpdaterWithPause { imp.mayFollowPlayer(currentPlayer) }
        imp.mayHitPlayer(container, currentPlayer)
        return imp
    }

    /**
     * Generates the animations of the game
     */
    internal suspend fun animations() = Animations.build()

    /**
     * Generate soldier playable character.
     */
    internal suspend fun soldier() = Soldier(spriteAtlas = resourcesVfs["soldier.xml"].readAtlas(), animations = animations())

    /**
     * Generate wolf playable character.
     */
    internal suspend fun wolf() = Wolf(spriteAtlas = resourcesVfs["wolf.xml"].readAtlas(), animations = animations())

    /**
     * Generate imp enemy.
     */
    internal suspend fun imp() = Imp(spriteAtlas = resourcesVfs["imp.xml"].readAtlas())
}