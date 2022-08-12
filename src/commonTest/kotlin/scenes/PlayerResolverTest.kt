package scenes

import com.soywiz.korge.tests.ViewsForTesting
import config.PlayableCharacter
import entities.Soldier
import entities.Wolf
import utils.EntitiesBuilder.soldier
import utils.EntitiesBuilder.wolf
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerResolverTest : ViewsForTesting()  {

    @Test
    fun resolve_selected_player_should_return_soldier() = viewsTest{
        assertEquals(soldier(), resolveSelectedPlayer(PlayableCharacter.Soldier))
    }

    @Test
    fun resolve_selected_player_should_return_wolf() = viewsTest{
        assertEquals(wolf(), resolveSelectedPlayer(PlayableCharacter.Wolf))
    }
}