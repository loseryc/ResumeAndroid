package com.oo.resume.entity

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-22 15:37
 *
 */
data class Company @JvmOverloads constructor(
    var name: String,//公司名

    var icon: String? = null,//公司icon

    var homepage: String? = null//公司主页

)