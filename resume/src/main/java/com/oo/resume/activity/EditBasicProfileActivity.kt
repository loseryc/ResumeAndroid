package com.oo.resume.activity

import android.os.Bundle
import com.chenenyu.router.annotation.Route
import com.oo.platform.view.BaseActivity
import com.oo.resume.R
import com.oo.resume.activity.router.RouteUrl
import kotlinx.android.synthetic.main.activity_edit_basic_profile.*
import kotlinx.android.synthetic.main.layout_edit_profile.view.*
import kotlinx.android.synthetic.main.layout_normal_title_bar.*

@Route(RouteUrl.EDIT_RESUME_BASE_INFO)
class EditBasicProfileActivity : BaseActivity() {

    companion object {
        const val KEY_BASE_INFO = "KEY_BASE_INFO"
    }

    override fun getContentViewResId(): Int {
        return R.layout.activity_edit_basic_profile
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        initViewState()
        bindListener()
        observeState()
    }


    private fun initData() {

    }

    private fun initViewState() {
        ll_name.tv_title.text = "姓名"
        ll_sex.tv_title.text = "性别"
        ll_phone.tv_title.text = "电话"
        ll_age.tv_title.text = "年龄"
        ll_email.tv_title.text = "邮箱"
        tv_title.text = "编辑个人信息"
    }

    private fun bindListener() {

    }

    private fun observeState() {

    }
}
