package scenes

import config.PlayableCharacter
import entities.Player
import utils.EntitiesBuilder.soldier
import utils.EntitiesBuilder.wolf

suspend fun resolveSelectedPlayer(selectedPlayer: PlayableCharacter): Player {
    return when (selectedPlayer){
        PlayableCharacter.Soldier -> soldier()
        PlayableCharacter.Wolf -> wolf()
    }
}