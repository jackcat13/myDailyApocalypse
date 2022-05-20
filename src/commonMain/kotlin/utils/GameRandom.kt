package utils

object GameRandom {
    fun ninetyPercent() = generateRand(1,10) != 10
    fun fiftyPercent() = generateRand(1,2) == 1
    fun tenPercent() = generateRand(1,10) == 1

    fun generateRand(start: Int, end: Int) = (start..end).random()
}