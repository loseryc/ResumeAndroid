package com.oo.resume.constance

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-06-10 17:02
 *
 */
interface Regex {
    companion object {
        const val MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$"
        const val EMAIL = "^([a-z0-9A-Z]+[-|\\\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\\\.)+[a-zA-Z]{2,}\$"
    }
}