package com.oo.resume.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.chenenyu.router.Router
import com.chenenyu.router.annotation.InjectParam
import com.chenenyu.router.annotation.Route
import com.oo.platform.view.BaseActivity
import com.oo.platform.viewmodel.ViewModelBinder
import com.oo.resume.R
import com.oo.resume.activity.router.RouteUrl
import com.oo.resume.viewmodel.ResumeListViewModel
import kotlinx.android.synthetic.main.layout_normal_title_bar.*

@Route(RouteUrl.RESUME_DETAIL_PAGE)
class ResumeDetailActivity : BaseActivity() {

    companion object {
        const val KEY_RESUME_ID = "KEY_RESUME_ID"
    }

    private lateinit var resumeViewModel: ResumeListViewModel

    @InjectParam(key = KEY_RESUME_ID)
    lateinit var resumeId: String

    override fun getContentViewResId(): Int {
        return R.layout.activity_resume_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        bindViewModel()
        initViewState()
        observeState()
    }

    private fun initData() {
        Router.injectParams(this)
    }


    private fun bindViewModel() {
        resumeViewModel = ViewModelBinder.bind(this, ResumeListViewModel::class.java)
    }

    private fun initViewState() {
        iv_back.setOnClickListener { onBackPressed() }
        tv_title.text = "简历详情"
        tv_control.visibility = View.VISIBLE
        tv_control.text = "预览"
    }

    private fun observeState(){
        resumeViewModel.getResumeDetail().observe(this, Observer {

        })
    }

}
