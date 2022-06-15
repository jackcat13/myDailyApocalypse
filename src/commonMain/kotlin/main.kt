import com.soywiz.korge.Korge
import com.soywiz.korge.Korge.Config
import module.MainModule

/**
 * Entry point of the game
 */
suspend fun main() = Korge(Config(module = MainModule))