package com.develop.grizzzly.pediatry.util

import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import java.security.NoSuchAlgorithmException


fun String.isEmail(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.isPhoneNumber(): Boolean {
    return PhoneNumberUtil.getInstance().isPossibleNumber(this, "US")
}


fun String.md5(): String {
    val md5 = "MD5"
    try {
        val digest = java.security.MessageDigest
            .getInstance(md5)
        digest.update(this.toByteArray())
        val messageDigest = digest.digest()

        val hexString = StringBuilder()
        for (aMessageDigest in messageDigest) {
            var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
            while (h.length < 2)
                h = "0$h"
            hexString.append(h)
        }
        return hexString.toString()

    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }

    return ""
}

fun String.formatPhone(): String {
    var a = this.toCharArray()
    var result = ""
    result += if (a[0]== '+') {
        "${a[0]}${a[1]}(${a[2]}${a[3]}${a[4]})${a[5]}${a[6]}${a[7]}-${a[8]}${a[9]}-${a[10]}${a[11]}"
    } else {
        "+7(${a[1]}${a[2]}${a[3]})${a[4]}${a[5]}${a[6]}-${a[7]}${a[8]}-${a[9]}${a[10]}"

    }
    return result
}

