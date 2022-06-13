package utils

object GameRandom {
    fun ninetyPercent() = generate(1,10) != 10
    fun fiftyPercent() = generate(1,2) == 1
    fun tenPercent() = generate(1,10) == 1

    fun generate(start: Int, end: Int) = (start..end).random()
}