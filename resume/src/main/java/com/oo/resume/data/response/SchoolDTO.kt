package com.oo.resume.data.response

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-22 15:37
 *
 */
class SchoolDTO {
    var id: Long? = null

    var name: String? = null//学校名

    var icon: String? = null//学校icon

    var labels: List<LabelDTO>? = null//标签
}