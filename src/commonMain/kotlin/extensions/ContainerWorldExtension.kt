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

fun Container.generateWorld(world: World, background_texture: Resource<BitmapSlice<Bitmap>>) {
    world.chunks.onEach {
        generateChunk(it, background_texture)
    }
}

fun Container.generateChunk(chunk: Chunk, background_texture: Resource<BitmapSlice<Bitmap>>) {
    chunk.beginPosition.let { (bx, by) ->
        println("Generate chunk with position $bx /// $by")
        chunk.chunkContainer = container {
            image(background_texture)
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

fun Container.worldLoadingCheck(world: World, player: Player, background_texture: Resource<BitmapSlice<Bitmap>>): World {
    if (player.sprite!!.x > world.chunks[2].beginPosition.first){
        return mayRefreshRightSide(world, background_texture)
    }
    else if (player.sprite!!.x < world.chunks[0].endPosition.first){
        return mayRefreshLeftSide(world, background_texture)
    }
    if (player.sprite!!.y > world.chunks[6].beginPosition.second){
        return mayRefreshBotSide(world, background_texture)
    }
    else if (player.sprite!!.y < world.chunks[0].endPosition.second){
        return mayRefreshTopSide(world, background_texture)
    }
    return world
}

private fun Container.mayRefreshRightSide(world: World, background_texture: Resource<BitmapSlice<Bitmap>>): World {
    world.chunks[0].chunkContainer!!.invalidate()
    world.chunks[3].chunkContainer!!.invalidate()
    world.chunks[6].chunkContainer!!.invalidate()
    val chunkThreeBeginPosition = world.chunks[2].beginPosition.first + chunksSize to world.chunks[2].beginPosition.second
    val chunkThreeEndPosition = world.chunks[2].endPosition.first + chunksSize to world.chunks[2].endPosition.second
    val newChunkThree = Chunk(chunkThreeBeginPosition, chunkThreeEndPosition)
    generateChunk(newChunkThree, background_texture)
    val chunkSixBeginPosition = world.chunks[5].beginPosition.first + chunksSize to world.chunks[5].beginPosition.second
    val chunkSixEndPosition = world.chunks[5].endPosition.first + chunksSize to world.chunks[5].endPosition.second
    val newChunkSix = Chunk(chunkSixBeginPosition, chunkSixEndPosition)
    generateChunk(newChunkSix, background_texture)
    val chunkNineBeginPosition = world.chunks[8].beginPosition.first + chunksSize to world.chunks[8].beginPosition.second
    val chunkNineEndPosition = world.chunks[8].endPosition.first + chunksSize to world.chunks[8].endPosition.second
    val newChunkNine = Chunk(chunkNineBeginPosition, chunkNineEndPosition)
    generateChunk(newChunkNine, background_texture)
    return World(listOf(
            world.chunks[1], world.chunks[2], newChunkThree,
            world.chunks[4], world.chunks[5], newChunkSix,
            world.chunks[7], world.chunks[8], newChunkNine))
}

private fun Container.mayRefreshLeftSide(world: World, background_texture: Resource<BitmapSlice<Bitmap>>): World {
    world.chunks[2].chunkContainer!!.invalidate()
    world.chunks[5].chunkContainer!!.invalidate()
    world.chunks[8].chunkContainer!!.invalidate()
    val chunkZeroBeginPosition = world.chunks[0].beginPosition.first - chunksSize to world.chunks[0].beginPosition.second
    val chunkZeroEndPosition = world.chunks[0].endPosition.first - chunksSize to world.chunks[0].endPosition.second
    val newChunkZero = Chunk(chunkZeroBeginPosition, chunkZeroEndPosition)
    generateChunk(newChunkZero, background_texture)
    val chunkThreeBeginPosition = world.chunks[3].beginPosition.first - chunksSize to world.chunks[3].beginPosition.second
    val chunkThreeEndPosition = world.chunks[3].endPosition.first - chunksSize to world.chunks[3].endPosition.second
    val newChunkThree = Chunk(chunkThreeBeginPosition, chunkThreeEndPosition)
    generateChunk(newChunkThree, background_texture)
    val chunkSixBeginPosition = world.chunks[6].beginPosition.first - chunksSize to world.chunks[6].beginPosition.second
    val chunkSixEndPosition = world.chunks[6].endPosition.first - chunksSize to world.chunks[6].endPosition.second
    val newChunkSix = Chunk(chunkSixBeginPosition, chunkSixEndPosition)
    generateChunk(newChunkSix, background_texture)
    return World(listOf(
            newChunkZero, world.chunks[0], world.chunks[1],
            newChunkThree, world.chunks[3], world.chunks[4],
            newChunkSix, world.chunks[6], world.chunks[7]))
}

private fun Container.mayRefreshBotSide(world: World, background_texture: Resource<BitmapSlice<Bitmap>>): World {
    world.chunks[0].chunkContainer!!.invalidate()
    world.chunks[1].chunkContainer!!.invalidate()
    world.chunks[2].chunkContainer!!.invalidate()
    val chunkSixBeginPosition = world.chunks[6].beginPosition.first to world.chunks[6].beginPosition.second + chunksSize
    val chunkSixEndPosition = world.chunks[6].endPosition.first to world.chunks[6].endPosition.second + chunksSize
    val newChunkSix = Chunk(chunkSixBeginPosition, chunkSixEndPosition)
    generateChunk(newChunkSix, background_texture)
    val chunkSevenBeginPosition = world.chunks[7].beginPosition.first to world.chunks[7].beginPosition.second + chunksSize
    val chunkSevenEndPosition = world.chunks[7].endPosition.first to world.chunks[7].endPosition.second + chunksSize
    val newChunkSeven = Chunk(chunkSevenBeginPosition, chunkSevenEndPosition)
    generateChunk(newChunkSeven, background_texture)
    val chunkEightBeginPosition = world.chunks[8].beginPosition.first to world.chunks[8].beginPosition.second + chunksSize
    val chunkEightEndPosition = world.chunks[8].endPosition.first to world.chunks[8].endPosition.second + chunksSize
    val newChunkEight = Chunk(chunkEightBeginPosition, chunkEightEndPosition)
    generateChunk(newChunkEight, background_texture)
    return World(listOf(
            world.chunks[3], world.chunks[4], world.chunks[5],
            world.chunks[6], world.chunks[7], world.chunks[8],
            newChunkSix, newChunkSeven, newChunkEight))
}

private fun Container.mayRefreshTopSide(world: World, background_texture: Resource<BitmapSlice<Bitmap>>): World {
    world.chunks[6].chunkContainer!!.invalidate()
    world.chunks[7].chunkContainer!!.invalidate()
    world.chunks[8].chunkContainer!!.invalidate()
    val chunkZeroBeginPosition = world.chunks[0].beginPosition.first to world.chunks[0].beginPosition.second - chunksSize
    val chunkZeroEndPosition = world.chunks[0].endPosition.first to world.chunks[0].endPosition.second - chunksSize
    val newChunkZero = Chunk(chunkZeroBeginPosition, chunkZeroEndPosition)
    generateChunk(newChunkZero, background_texture)
    val chunkOneBeginPosition = world.chunks[1].beginPosition.first to world.chunks[1].beginPosition.second - chunksSize
    val chunkOneEndPosition = world.chunks[1].endPosition.first to world.chunks[1].endPosition.second - chunksSize
    val newChunkOne = Chunk(chunkOneBeginPosition, chunkOneEndPosition)
    generateChunk(newChunkOne, background_texture)
    val chunkTwoBeginPosition = world.chunks[2].beginPosition.first to world.chunks[2].beginPosition.second - chunksSize
    val chunkTwoEndPosition = world.chunks[2].endPosition.first to world.chunks[2].endPosition.second - chunksSize
    val newChunkTwo = Chunk(chunkTwoBeginPosition, chunkTwoEndPosition)
    generateChunk(newChunkTwo, background_texture)
    return World(listOf(
            newChunkZero, newChunkOne, newChunkTwo,
            world.chunks[0], world.chunks[1], world.chunks[2],
            world.chunks[3], world.chunks[4], world.chunks[5]))
}