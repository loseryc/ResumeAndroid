package com.oo.resume.entity

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-21 17:19
 *
 */
data class BaseInfo @JvmOverloads constructor(
    var name: String,//名字

    var phone: String,//电话

    var age: Int? = null,//年龄

    var sex: Int? = null,//性别 0:男 1:女

    var email: String? = null,//邮件

    var avatar: String? = null//头像

)