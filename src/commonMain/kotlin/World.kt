import com.soywiz.korge.view.Container
import config.GameConfig.chunksSize

/**
 * World class to create a world object to be used in the game
 * @property chunks List of 9 chunks of the world. These chunks can be regenerated depending on player position.
 */
class World(
        val chunks: List<Chunk> = listOf(
                Chunk(-2*chunksSize to -2*chunksSize, -chunksSize to -chunksSize),//1
                Chunk(-chunksSize to -2*chunksSize, 0.0 to -chunksSize),//2
                Chunk(0.0 to -2*chunksSize, chunksSize to -chunksSize),//3
                Chunk(-2*chunksSize to -chunksSize, -chunksSize to 0.0),//4
                Chunk(-chunksSize to -chunksSize, 0.0 to 0.0),//5
                Chunk(0.0 to -chunksSize, chunksSize to 0.0),//6
                Chunk(-2*chunksSize to 0.0, -chunksSize to chunksSize),//7
                Chunk(-chunksSize to 0.0, 0.0 to chunksSize),//8
                Chunk(0.0 to 0.0, chunksSize to chunksSize)//9
        )
)

/**
 * Chunk class used by the world.
 */
class Chunk(val beginPosition: Pair<Double, Double> = 0.0 to 0.0,
            val endPosition: Pair<Double, Double> = chunksSize to chunksSize,
            var chunkContainer: Container? = null
)