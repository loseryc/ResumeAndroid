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
import com.oo.resume.activity.adapter.ResumeDetailAdapter
import com.oo.resume.activity.router.RouteUrl
import com.oo.resume.data.response.ResumeDTO
import com.oo.resume.viewmodel.ResumeListViewModel
import kotlinx.android.synthetic.main.activity_resume_detail.*
import kotlinx.android.synthetic.main.layout_normal_title_bar.*

@Route(RouteUrl.RESUME_DETAIL_PAGE)
class ResumeDetailActivity : BaseActivity() {

    companion object {
        const val KEY_RESUME_ID = "KEY_RESUME_ID"
    }

    private lateinit var resumeViewModel: ResumeListViewModel

    private var mAdapter = ResumeDetailAdapter()

    @InjectParam(key = KEY_RESUME_ID)
    var resumeId: Long? = null

    override fun getContentViewResId(): Int {
        return R.layout.activity_resume_detail
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindViewModel()
        initData()
        initViewState()
        observeState()
        bindListener()
    }

    private fun bindListener() {
        mAdapter.setOnItemChildClickListener { _, view, position ->
            when(view.id){
                R.id.cl_basic_wrapper ->
                    Router.build(RouteUrl.EDIT_RESUME_BASE_INFO)
                        .go(this){ status,_,_->
                            if (status.isSuccessful){

                            }
                        }
            }
        }
    }

    private fun bindViewModel() {
        resumeViewModel = ViewModelBinder.bind(this, ResumeListViewModel::class.java)
    }

    private fun initData() {
        Router.injectParams(this)
        resumeViewModel.resumeDetail(resumeId)
    }


    private fun initViewState() {
        iv_back.setOnClickListener { onBackPressed() }
        tv_title.text = "简历详情"
        tv_control.visibility = View.VISIBLE
        tv_control.text = "预览"
        rv_resume_detail_list.adapter = mAdapter
    }

    private fun observeState() {
        resumeViewModel.getResumeDetail()
                .observe(this, Observer { resumeResult ->
                    if (resumeResult == null) return@Observer
                    if (resumeResult.isSuccess){
                        refreshResumeList(resumeResult.data)
                    }
                })
    }

    private fun refreshResumeList(data: ResumeDTO?) {
        mAdapter.addBasic(data?.baseInfo)
    }

}
