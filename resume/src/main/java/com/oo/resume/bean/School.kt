package com.oo.resume.entity

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-22 15:37
 *
 */
data class School @JvmOverloads constructor(
    var name: String,//学校名

    var icon: String? = null,//学校icon

    var labels: List<Label>? = null//标签

)