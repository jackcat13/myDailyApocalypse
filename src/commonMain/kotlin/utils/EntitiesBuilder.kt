package utils

import com.soywiz.korge.view.Container
import com.soywiz.korge.view.addUpdater
import com.soywiz.korim.atlas.readAtlas
import com.soywiz.korio.file.std.resourcesVfs
import entities.Imp
import entities.Player
import entities.Soldier
import utils.GameRandom.generateRand

object EntitiesBuilder{

    internal suspend fun generateImp(container: Container, currentPlayer: Player): Imp {
        val imp = imp()
        imp.initDraw(container, currentPlayer.sprite!!.x+ generateRand(-600, 600), currentPlayer.sprite!!.y+ generateRand(-600, 600))
        container.addUpdater { imp.mayFollowPlayer(currentPlayer.sprite!!) }
        return imp
    }

    internal suspend fun animations() = Animations.build()
    internal suspend fun soldier() = Soldier(spriteAtlas = resourcesVfs["soldier.xml"].readAtlas(), animations = animations())
    //suspend fun marine() = Player(spriteAtlas = resourcesVfs["marine.xml"].readAtlas())
    //suspend fun mage() = Player(spriteAtlas = resourcesVfs["mage.xml"].readAtlas())

    internal suspend fun imp() = Imp(spriteAtlas = resourcesVfs["imp.xml"].readAtlas())
}