import com.soywiz.korge.view.Container
import config.GameConfig.chunksSize

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

class Chunk(val beginPosition: Pair<Double, Double> = 0.0 to 0.0,
            val endPosition: Pair<Double, Double> = chunksSize to chunksSize,
            var chunkContainer: Container? = null
)