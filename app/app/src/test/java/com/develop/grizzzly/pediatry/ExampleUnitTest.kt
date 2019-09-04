package com.develop.grizzzly.pediatry

import com.develop.grizzzly.pediatry.util.formatPhone
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun test_phone_formatting(){
        val phone1 = "+7 (999)111 -22-33"
        val phone2 = "8 (999) 111-22-33"
        println(phone1.formatPhone() == "+7(999)111-22-33")
        println(phone2.formatPhone() == "8(999)111-22-33" )
    }
}
