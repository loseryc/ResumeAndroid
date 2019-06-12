package com.oo.resume.entity

import com.oo.resume.bean.Account

/**
 *  yangchao yangchao
 *  cd.uestc.superyong@gmail.com  cd.uestc.superyoung@gmail.com
 *     2019-05-21 16:34
 *
 */
data class Resume @JvmOverloads constructor(

    var shortLink: String,//简历短链

    var account: Account,//基本信息

    var baseInfo: BaseInfo,

    var synopsis: String? = null,//个人简介

    var exeprience: Int,//多少年经验

    var company: Company? = null,//当前公司

    var jobLabel: String? = null,//职位描述

    var language: List<Language>? = null,//语言

    var technique: List<Technique>? = null,//技术

    var experiences: List<Experience>? = null,//工作经验

    var education: List<Education>? = null//教育经历

)