package scenes

import entities.Player
import entities.Soldier
import utils.EntitiesBuilder
import kotlin.reflect.KClass

suspend fun resolveSelectedPlayer(selectedPlayer: KClass<out Player>): Player {
    return when (selectedPlayer){
        Soldier::class -> EntitiesBuilder.soldier()
        else -> EntitiesBuilder.soldier()
    }
}