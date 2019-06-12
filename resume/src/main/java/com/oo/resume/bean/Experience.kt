package com.oo.resume.entity

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-22 15:17
 *  工作经历
 */
data class Experience @JvmOverloads constructor(
    var company: Company,//公司

    var sketch: String,//概述

    var jobContent: String,//工作内容

    var start: String,//开始时间

    var end: String? = null,//结束时间

    var station: String? = null, //岗位

    var labels: List<Label>? = null,//标签

    var opus: String? = null//作品link

)