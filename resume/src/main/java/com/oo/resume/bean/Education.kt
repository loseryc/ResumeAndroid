package com.oo.resume.entity

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-22 15:37
 *
 */
data class Education @JvmOverloads constructor(
    var school: School,//学校

    var record: String,//学历

    var start: String,//开始时间

    var end: String?,//结束时间

    var major: String? = null//专业

)