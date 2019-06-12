package com.oo.resume.entity

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-24 17:29
 *
 */
data class Label @JvmOverloads constructor(
    val name: String,//名称

    var description: String? = null//描述

)