package scenes

import com.soywiz.korge.tests.ViewsForTesting
import entities.Soldier
import utils.EntitiesBuilder.soldier
import kotlin.test.Test
import kotlin.test.assertEquals

class PlayerResolverTest : ViewsForTesting()  {

    @Test
    fun resolve_selected_player_should_return_soldier() = viewsTest{
        assertEquals(soldier(), resolveSelectedPlayer(Soldier::class))
    }

    @Test
    fun resolve_selected_player_should_return_soldier_when_invalid_input() = viewsTest{
        assertEquals(soldier(), resolveSelectedPlayer(Soldier::class))
    }
}