package extensions

import Chunk
import World
import com.soywiz.korge.view.Container
import com.soywiz.korge.view.container
import com.soywiz.korge.view.image
import com.soywiz.korge.view.position
import com.soywiz.korge.view.solidRect
import com.soywiz.korim.bitmap.Bitmap
import com.soywiz.korim.bitmap.BitmapSlice
import com.soywiz.korio.resources.Resource
import config.GameConfig.chunksSize
import entities.Player
import utils.GameRandom
import utils.GameRandom.fiftyPercent

fun Container.generateWorld(world: World, backgroundTexture: Resource<BitmapSlice<Bitmap>>) {
    world.chunks.onEach {
        generateChunk(it, backgroundTexture)
    }
}

fun Container.generateChunk(chunk: Chunk, backgroundTexture: Resource<BitmapSlice<Bitmap>>) {
    chunk.beginPosition.let { (bx, by) ->
        println("Generate chunk with position $bx /// $by")
        chunk.chunkContainer = container {
            image(backgroundTexture)
            mayGenerateBuilds(bx, by)
        }.position(bx, by)
        sendChildToBack(chunk.chunkContainer!!)
    }
}

fun Container.mayGenerateBuilds(bx: Double, by: Double) {
    if (fiftyPercent()){
        val buildXPosition = GameRandom.generateRand(bx.toInt(), (bx+chunksSize).toInt())
        val buildYPosition = GameRandom.generateRand(by.toInt(), (by+chunksSize).toInt())
        sendChildToFront(solidRect(200, 200)
                .position(buildXPosition, buildYPosition)) //TODO: building collisions and design
        println("Building generate at $buildXPosition / $buildYPosition")
    }
}

fun Container.worldLoadingCheck(world: World, player: Player, backgroundTexture: Resource<BitmapSlice<Bitmap>>): World {
    if (player.sprite!!.x > world.chunks[2].beginPosition.first){
        return mayRefreshRightSide(world, backgroundTexture)
    }
    else if (player.sprite!!.x < world.chunks[0].endPosition.first){
        return mayRefreshLeftSide(world, backgroundTexture)
    }
    if (player.sprite!!.y > world.chunks[6].beginPosition.second){
        return mayRefreshBotSide(world, backgroundTexture)
    }
    else if (player.sprite!!.y < world.chunks[0].endPosition.second){
        return mayRefreshTopSide(world, backgroundTexture)
    }
    return world
}

private fun Container.mayRefreshRightSide(world: World, backgroundTexture: Resource<BitmapSlice<Bitmap>>): World {
    world.chunks[0].chunkContainer!!.invalidate()
    world.chunks[3].chunkContainer!!.invalidate()
    world.chunks[6].chunkContainer!!.invalidate()
    val chunkThreeBeginPosition = world.chunks[2].beginPosition.first + chunksSize to world.chunks[2].beginPosition.second
    val chunkThreeEndPosition = world.chunks[2].endPosition.first + chunksSize to world.chunks[2].endPosition.second
    val newChunkThree = Chunk(chunkThreeBeginPosition, chunkThreeEndPosition)
    generateChunk(newChunkThree, backgroundTexture)
    val chunkSixBeginPosition = world.chunks[5].beginPosition.first + chunksSize to world.chunks[5].beginPosition.second
    val chunkSixEndPosition = world.chunks[5].endPosition.first + chunksSize to world.chunks[5].endPosition.second
    val newChunkSix = Chunk(chunkSixBeginPosition, chunkSixEndPosition)
    generateChunk(newChunkSix, backgroundTexture)
    val chunkNineBeginPosition = world.chunks[8].beginPosition.first + chunksSize to world.chunks[8].beginPosition.second
    val chunkNineEndPosition = world.chunks[8].endPosition.first + chunksSize to world.chunks[8].endPosition.second
    val newChunkNine = Chunk(chunkNineBeginPosition, chunkNineEndPosition)
    generateChunk(newChunkNine, backgroundTexture)
    return World(listOf(
            world.chunks[1], world.chunks[2], newChunkThree,
            world.chunks[4], world.chunks[5], newChunkSix,
            world.chunks[7], world.chunks[8], newChunkNine))
}

private fun Container.mayRefreshLeftSide(world: World, backgroundTexture: Resource<BitmapSlice<Bitmap>>): World {
    world.chunks[2].chunkContainer!!.invalidate()
    world.chunks[5].chunkContainer!!.invalidate()
    world.chunks[8].chunkContainer!!.invalidate()
    val chunkZeroBeginPosition = world.chunks[0].beginPosition.first - chunksSize to world.chunks[0].beginPosition.second
    val chunkZeroEndPosition = world.chunks[0].endPosition.first - chunksSize to world.chunks[0].endPosition.second
    val newChunkZero = Chunk(chunkZeroBeginPosition, chunkZeroEndPosition)
    generateChunk(newChunkZero, backgroundTexture)
    val chunkThreeBeginPosition = world.chunks[3].beginPosition.first - chunksSize to world.chunks[3].beginPosition.second
    val chunkThreeEndPosition = world.chunks[3].endPosition.first - chunksSize to world.chunks[3].endPosition.second
    val newChunkThree = Chunk(chunkThreeBeginPosition, chunkThreeEndPosition)
    generateChunk(newChunkThree, backgroundTexture)
    val chunkSixBeginPosition = world.chunks[6].beginPosition.first - chunksSize to world.chunks[6].beginPosition.second
    val chunkSixEndPosition = world.chunks[6].endPosition.first - chunksSize to world.chunks[6].endPosition.second
    val newChunkSix = Chunk(chunkSixBeginPosition, chunkSixEndPosition)
    generateChunk(newChunkSix, backgroundTexture)
    return World(listOf(
            newChunkZero, world.chunks[0], world.chunks[1],
            newChunkThree, world.chunks[3], world.chunks[4],
            newChunkSix, world.chunks[6], world.chunks[7]))
}

private fun Container.mayRefreshBotSide(world: World, backgroundTexture: Resource<BitmapSlice<Bitmap>>): World {
    world.chunks[0].chunkContainer!!.invalidate()
    world.chunks[1].chunkContainer!!.invalidate()
    world.chunks[2].chunkContainer!!.invalidate()
    val chunkSixBeginPosition = world.chunks[6].beginPosition.first to world.chunks[6].beginPosition.second + chunksSize
    val chunkSixEndPosition = world.chunks[6].endPosition.first to world.chunks[6].endPosition.second + chunksSize
    val newChunkSix = Chunk(chunkSixBeginPosition, chunkSixEndPosition)
    generateChunk(newChunkSix, backgroundTexture)
    val chunkSevenBeginPosition = world.chunks[7].beginPosition.first to world.chunks[7].beginPosition.second + chunksSize
    val chunkSevenEndPosition = world.chunks[7].endPosition.first to world.chunks[7].endPosition.second + chunksSize
    val newChunkSeven = Chunk(chunkSevenBeginPosition, chunkSevenEndPosition)
    generateChunk(newChunkSeven, backgroundTexture)
    val chunkEightBeginPosition = world.chunks[8].beginPosition.first to world.chunks[8].beginPosition.second + chunksSize
    val chunkEightEndPosition = world.chunks[8].endPosition.first to world.chunks[8].endPosition.second + chunksSize
    val newChunkEight = Chunk(chunkEightBeginPosition, chunkEightEndPosition)
    generateChunk(newChunkEight, backgroundTexture)
    return World(listOf(
            world.chunks[3], world.chunks[4], world.chunks[5],
            world.chunks[6], world.chunks[7], world.chunks[8],
            newChunkSix, newChunkSeven, newChunkEight))
}

private fun Container.mayRefreshTopSide(world: World, backgroundTexture: Resource<BitmapSlice<Bitmap>>): World {
    world.chunks[6].chunkContainer!!.invalidate()
    world.chunks[7].chunkContainer!!.invalidate()
    world.chunks[8].chunkContainer!!.invalidate()
    val chunkZeroBeginPosition = world.chunks[0].beginPosition.first to world.chunks[0].beginPosition.second - chunksSize
    val chunkZeroEndPosition = world.chunks[0].endPosition.first to world.chunks[0].endPosition.second - chunksSize
    val newChunkZero = Chunk(chunkZeroBeginPosition, chunkZeroEndPosition)
    generateChunk(newChunkZero, backgroundTexture)
    val chunkOneBeginPosition = world.chunks[1].beginPosition.first to world.chunks[1].beginPosition.second - chunksSize
    val chunkOneEndPosition = world.chunks[1].endPosition.first to world.chunks[1].endPosition.second - chunksSize
    val newChunkOne = Chunk(chunkOneBeginPosition, chunkOneEndPosition)
    generateChunk(newChunkOne, backgroundTexture)
    val chunkTwoBeginPosition = world.chunks[2].beginPosition.first to world.chunks[2].beginPosition.second - chunksSize
    val chunkTwoEndPosition = world.chunks[2].endPosition.first to world.chunks[2].endPosition.second - chunksSize
    val newChunkTwo = Chunk(chunkTwoBeginPosition, chunkTwoEndPosition)
    generateChunk(newChunkTwo, backgroundTexture)
    return World(listOf(
            newChunkZero, newChunkOne, newChunkTwo,
            world.chunks[0], world.chunks[1], world.chunks[2],
            world.chunks[3], world.chunks[4], world.chunks[5]))
}