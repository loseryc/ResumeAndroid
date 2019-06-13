package com.oo.resume.param.response

/**
 *   yangchao
 *    cd.uestc.superyoung@gmail.com
 *     2019-05-22 15:17
 *  工作经历
 */
class ExperienceDTO{
        var id: Long? = null

        var company: CompanyDTO? = null//公司

        var sketch: String? = null//概述

        var jobContent: String? = null//工作内容

        var start: String? = null//开始时间

        var end: String? = null//结束时间

        var station: String? = null //岗位

        var labels: List<LabelDTO>? = null//标签

        var opus: String? = null//作品link

}