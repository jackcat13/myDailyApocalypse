package extension

import extensions.asChar
import kotlin.test.Test
import kotlin.test.assertEquals

class StringExtensionTest {

    @Test
    fun asChar_should_transform_string_into_a_character() {
        assertEquals('a', "a".asChar())
        assertEquals('a', "aaaejiofezgyiu".asChar())
    }

    @Test
    fun asChar_should_return_empty_char_when_invalid_string_provided() {
        assertEquals(' ', "".asChar())
    }
}