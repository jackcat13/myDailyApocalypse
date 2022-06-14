package extensions

/**
 * Tries to convert any string into a character but does not block in case of exception.
 * In case of error, It sends the exception in standard output instead and returns empty character.
 */
fun String.asChar(): Char {
    return try {
        this.toCharArray()[0]
    } catch(e: Exception) {
        println("Could not convert string $this to char because of following exception: $e")
        return ' '
    }
}