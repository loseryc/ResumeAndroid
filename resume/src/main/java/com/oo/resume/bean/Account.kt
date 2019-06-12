package com.oo.resume.bean

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-02 17:16
 *  $describe
 */
data class Account @JvmOverloads constructor(
    var phone: String? = null,//电话

    var name: String? = null,//名字

    var age: Int? = null,//年龄

    var sex: Int? = null,

    var avatar: String? = null,//头像

    var session_key: String? = null,//会话

    var session_user: String? = null


)