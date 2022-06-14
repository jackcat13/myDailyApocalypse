package extension

import extensions.asChar
import kotlin.test.Test
import kotlin.test.assertEquals

class StringExtensionTest {

    @Test
    fun `asChar should transform string into a character`() {
        assertEquals('a', "a".asChar())
        assertEquals('a', "aaaejiofezgyiu".asChar())
    }

    @Test
    fun `asChar should return empty char when invalid string provided`() {
        assertEquals(' ', "".asChar())
    }
}