package com.oo.resume.data.response

/**
 *  yangchao yangchao
 *  cd.uestc.superyong@gmail.com  cd.uestc.superyoung@gmail.com
 *     2019-05-21 16:34
 *
 */
class ResumeDTO {

    var shortLink: String? = null//简历短链

    var account: AccountDTO? = null//基本信息

    var baseInfo: BaseInfoDTO? = null

    var synopsis: String? = null//个人简介

    var exeprience: Int? = null//多少年经验

    var company: CompanyDTO? = null//当前公司

    var jobLabel: String? = null//职位描述

    var language: List<LanguageDTO>? = null//语言

    var technique: List<TechniqueDTO>? = null//技术

    var experiences: List<ExperienceDTO>? = null//工作经验

    var education: List<EducationDTO>? = null//教育经历

}