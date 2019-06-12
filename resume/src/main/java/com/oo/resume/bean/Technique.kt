package com.oo.resume.entity

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-21 17:40
 *
 */
data class Technique @JvmOverloads constructor(
    var title: String,//名字

    var percent: Float? = 1f,//熟练度

    var description: String? = null//描述

)