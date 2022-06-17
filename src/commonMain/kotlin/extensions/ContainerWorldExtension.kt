package extensions

import Chunk
import World
import com.soywiz.korge.view.*
import com.soywiz.korge.view.fast.FastSprite
import com.soywiz.korge.view.fast.FastSpriteContainer
import com.soywiz.korge.view.fast.fastSpriteContainer
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.BitmapSlice
import com.soywiz.korim.format.readBitmapSlice
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.file.std.resourcesVfs
import config.GameConfig.chunksSize
import entities.Player
import extensions.ProceduralGeneratedObjects.backgroundFastSpriteContainer
import extensions.ProceduralGeneratedObjects.obstacles
import extensions.ProceduralGenerationConstants.ALTAR_PER_CHUNK
import extensions.ProceduralGenerationConstants.BACKGROUND_SPRITE
import extensions.ProceduralGenerationConstants.BONES_PER_CHUNK
import extensions.ProceduralGenerationConstants.INFERNUS_ALTAR_SPRITE
import extensions.ProceduralGenerationConstants.INFERNUS_BONES_SPRITE
import extensions.ProceduralGenerationConstants.INFERNUS_CRATER_SPRITE
import utils.GameRandom
import utils.GameRandom.fiftyPercent

/**
 * Handles initialization of fast sprite containers for performances purpose.
 */
fun Container.initFastSpriteContainers() {
    backgroundFastSpriteContainer = fastSpriteContainer { }
    fastSpriteContainer {  }.name(INFERNUS_ALTAR_SPRITE)
    fastSpriteContainer {  }.name(INFERNUS_BONES_SPRITE)
    fastSpriteContainer {  }.name(INFERNUS_CRATER_SPRITE)
}

/**
 * Handles the game world generation.
 * @param world Mutable world for generation or regeneration
 * @param backgroundTexture The background texture to use for chunks generated
 */
fun Container.generateWorld(world: World) {
    world.chunks.onEach {
        generateChunk(it)
    }
}

private fun Container.generateChunk(chunk: Chunk) {
    chunk.beginPosition.let { (bx, by) ->
        println("Generate chunk with position $bx /// $by")
        chunk.chunkContainer = chunkContainer(bx, by)
        generateBuilds(bx, by)
        mayGenerateCrater(bx,by)
        sendChildToBack(chunk.chunkContainer!!)
    }
}

private fun Container.chunkContainer(bx: Double, by: Double): Container {
    stage!!.launchImmediately {
        backgroundFastSpriteContainer.alloc(FastSprite(resourcesVfs[BACKGROUND_SPRITE].readBitmapSlice())
            .apply {
                xf = bx.toFloat()
                yf = by.toFloat()
                scaledWidth = chunksSize
                scaledHeight = chunksSize
            }.also { println("Create background: $it") }
        )
    }
    return container {}.position(bx, by).apply {
        hitTestEnabled = false
        scaledWidth = chunksSize
        scaledHeight = chunksSize
    }
}

private fun Container.mayGenerateCrater(bx: Double, by: Double) {
    stage!!.launchImmediately {
        if (fiftyPercent()) {
            generateBackgroundSprite(bx, by, resourcesVfs[INFERNUS_CRATER_SPRITE].readBitmapSlice().withName(INFERNUS_CRATER_SPRITE))
        }
    }
}

private fun Container.generateBuilds(bx: Double, by: Double) {
    stage!!.launchImmediately {
        val altarSprite = resourcesVfs[INFERNUS_ALTAR_SPRITE].readBitmapSlice().withName(INFERNUS_ALTAR_SPRITE)
        val bonesSprite = resourcesVfs[INFERNUS_BONES_SPRITE].readBitmapSlice().withName(INFERNUS_BONES_SPRITE)
        for (i in 1..ALTAR_PER_CHUNK) { generateBackgroundSprite(bx, by, altarSprite) }
        for (i in 1..BONES_PER_CHUNK) { generateBackgroundSprite(bx, by, bonesSprite) }
    }
}

private fun Container.generateBackgroundSprite(x: Double, y: Double, sprite: BitmapSlice<Bitmap>) {
    val buildXPosition = GameRandom.generateRand(x.toInt(), (x+chunksSize).toInt())
    val buildYPosition = GameRandom.generateRand(y.toInt(), (y+chunksSize).toInt())
    hitTest(x, y)?.takeIf { it is Sprite }
            ?.let {//Generate sprite of background at another position if another one is already present
                generateBackgroundSprite(buildXPosition.toDouble(), buildYPosition.toDouble(), sprite)
            }
            ?: (parent!!.findViewByName(sprite.name!!) as FastSpriteContainer).alloc(FastSprite(sprite)
                .apply {
                    xf = buildXPosition.toFloat()
                    yf = buildYPosition.toFloat()
                }
                .also { obstacles.add(it) }
                .also { println("create $sprite at $x / $y") }
            )
}

/**
 * Checks if the world needs to be generated again.
 * It happens when the player is reaching the most far chunks
 * @param world The world to regenerate
 * @param player The player that moves in the world
 * @param backgroundTexture The background texture to use for the chunk
 */
fun Container.worldLoadingCheck(world: World, player: Player): World {
    if (player.sprite!!.x > world.chunks[2].beginPosition.first){
        return mayRefreshRightSide(world)
    }
    else if (player.sprite!!.x < world.chunks[0].endPosition.first){
        return mayRefreshLeftSide(world)
    }
    if (player.sprite!!.y > world.chunks[6].beginPosition.second){
        return mayRefreshBotSide(world)
    }
    else if (player.sprite!!.y < world.chunks[0].endPosition.second){
        return mayRefreshTopSide(world)
    }
    return world
}

private fun Container.mayRefreshRightSide(world: World): World {
    world.chunks[0].chunkContainer!!.invalidate()
    world.chunks[3].chunkContainer!!.invalidate()
    world.chunks[6].chunkContainer!!.invalidate()
    val chunkThreeBeginPosition = world.chunks[2].beginPosition.first + chunksSize to world.chunks[2].beginPosition.second
    val chunkThreeEndPosition = world.chunks[2].endPosition.first + chunksSize to world.chunks[2].endPosition.second
    val newChunkThree = Chunk(chunkThreeBeginPosition, chunkThreeEndPosition)
    generateChunk(newChunkThree)
    val chunkSixBeginPosition = world.chunks[5].beginPosition.first + chunksSize to world.chunks[5].beginPosition.second
    val chunkSixEndPosition = world.chunks[5].endPosition.first + chunksSize to world.chunks[5].endPosition.second
    val newChunkSix = Chunk(chunkSixBeginPosition, chunkSixEndPosition)
    generateChunk(newChunkSix)
    val chunkNineBeginPosition = world.chunks[8].beginPosition.first + chunksSize to world.chunks[8].beginPosition.second
    val chunkNineEndPosition = world.chunks[8].endPosition.first + chunksSize to world.chunks[8].endPosition.second
    val newChunkNine = Chunk(chunkNineBeginPosition, chunkNineEndPosition)
    generateChunk(newChunkNine)
    return World(listOf(
            world.chunks[1], world.chunks[2], newChunkThree,
            world.chunks[4], world.chunks[5], newChunkSix,
            world.chunks[7], world.chunks[8], newChunkNine))
}

private fun Container.mayRefreshLeftSide(world: World): World {
    world.chunks[2].chunkContainer!!.invalidate()
    world.chunks[5].chunkContainer!!.invalidate()
    world.chunks[8].chunkContainer!!.invalidate()
    val chunkZeroBeginPosition = world.chunks[0].beginPosition.first - chunksSize to world.chunks[0].beginPosition.second
    val chunkZeroEndPosition = world.chunks[0].endPosition.first - chunksSize to world.chunks[0].endPosition.second
    val newChunkZero = Chunk(chunkZeroBeginPosition, chunkZeroEndPosition)
    generateChunk(newChunkZero)
    val chunkThreeBeginPosition = world.chunks[3].beginPosition.first - chunksSize to world.chunks[3].beginPosition.second
    val chunkThreeEndPosition = world.chunks[3].endPosition.first - chunksSize to world.chunks[3].endPosition.second
    val newChunkThree = Chunk(chunkThreeBeginPosition, chunkThreeEndPosition)
    generateChunk(newChunkThree)
    val chunkSixBeginPosition = world.chunks[6].beginPosition.first - chunksSize to world.chunks[6].beginPosition.second
    val chunkSixEndPosition = world.chunks[6].endPosition.first - chunksSize to world.chunks[6].endPosition.second
    val newChunkSix = Chunk(chunkSixBeginPosition, chunkSixEndPosition)
    generateChunk(newChunkSix)
    return World(listOf(
            newChunkZero, world.chunks[0], world.chunks[1],
            newChunkThree, world.chunks[3], world.chunks[4],
            newChunkSix, world.chunks[6], world.chunks[7]))
}

private fun Container.mayRefreshBotSide(world: World): World {
    world.chunks[0].chunkContainer!!.invalidate()
    world.chunks[1].chunkContainer!!.invalidate()
    world.chunks[2].chunkContainer!!.invalidate()
    val chunkSixBeginPosition = world.chunks[6].beginPosition.first to world.chunks[6].beginPosition.second + chunksSize
    val chunkSixEndPosition = world.chunks[6].endPosition.first to world.chunks[6].endPosition.second + chunksSize
    val newChunkSix = Chunk(chunkSixBeginPosition, chunkSixEndPosition)
    generateChunk(newChunkSix)
    val chunkSevenBeginPosition = world.chunks[7].beginPosition.first to world.chunks[7].beginPosition.second + chunksSize
    val chunkSevenEndPosition = world.chunks[7].endPosition.first to world.chunks[7].endPosition.second + chunksSize
    val newChunkSeven = Chunk(chunkSevenBeginPosition, chunkSevenEndPosition)
    generateChunk(newChunkSeven)
    val chunkEightBeginPosition = world.chunks[8].beginPosition.first to world.chunks[8].beginPosition.second + chunksSize
    val chunkEightEndPosition = world.chunks[8].endPosition.first to world.chunks[8].endPosition.second + chunksSize
    val newChunkEight = Chunk(chunkEightBeginPosition, chunkEightEndPosition)
    generateChunk(newChunkEight)
    return World(listOf(
            world.chunks[3], world.chunks[4], world.chunks[5],
            world.chunks[6], world.chunks[7], world.chunks[8],
            newChunkSix, newChunkSeven, newChunkEight))
}

private fun Container.mayRefreshTopSide(world: World): World {
    world.chunks[6].chunkContainer!!.invalidate()
    world.chunks[7].chunkContainer!!.invalidate()
    world.chunks[8].chunkContainer!!.invalidate()
    val chunkZeroBeginPosition = world.chunks[0].beginPosition.first to world.chunks[0].beginPosition.second - chunksSize
    val chunkZeroEndPosition = world.chunks[0].endPosition.first to world.chunks[0].endPosition.second - chunksSize
    val newChunkZero = Chunk(chunkZeroBeginPosition, chunkZeroEndPosition)
    generateChunk(newChunkZero)
    val chunkOneBeginPosition = world.chunks[1].beginPosition.first to world.chunks[1].beginPosition.second - chunksSize
    val chunkOneEndPosition = world.chunks[1].endPosition.first to world.chunks[1].endPosition.second - chunksSize
    val newChunkOne = Chunk(chunkOneBeginPosition, chunkOneEndPosition)
    generateChunk(newChunkOne)
    val chunkTwoBeginPosition = world.chunks[2].beginPosition.first to world.chunks[2].beginPosition.second - chunksSize
    val chunkTwoEndPosition = world.chunks[2].endPosition.first to world.chunks[2].endPosition.second - chunksSize
    val newChunkTwo = Chunk(chunkTwoBeginPosition, chunkTwoEndPosition)
    generateChunk(newChunkTwo)
    return World(listOf(
            newChunkZero, newChunkOne, newChunkTwo,
            world.chunks[0], world.chunks[1], world.chunks[2],
            world.chunks[3], world.chunks[4], world.chunks[5]))
}