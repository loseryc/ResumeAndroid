package com.oo.resume.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.chenenyu.router.Router
import com.oo.platform.view.BaseFragment
import com.oo.platform.viewmodel.ViewModelBinder
import com.oo.resume.R
import com.oo.resume.activity.ResumeDetailActivity.Companion.KEY_RESUME_ID
import com.oo.resume.activity.router.RouteUrl.Companion.RESUME_DETAIL_PAGE
import com.oo.resume.data.response.ResumeDTO
import com.oo.resume.viewmodel.ResumeListViewModel
import kotlinx.android.synthetic.main.fragment_resume_list.*
import kotlinx.android.synthetic.main.item_resume_card.view.*

class ResumeListFragment : BaseFragment() {

    private lateinit var viewModel: ResumeListViewModel
    private lateinit var adapter: ResumeAdapter

    override fun getContentViewResId(): Int {
        return R.layout.fragment_resume_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelBinder.bind(this, ResumeListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observe()
        setListener()
    }

    private fun initView() {
        adapter = ResumeAdapter()
        rv_resume.adapter = adapter
    }

    private fun observe() {
        viewModel.getResumeList().observe(this, Observer { result ->
            if (result == null) return@Observer
            if (result.isLoading) {
                srl_refresh.isRefreshing = true
                return@Observer
            }
            srl_refresh.isRefreshing = false
            if (result.isSuccess) {
                adapter.setData(result.data)
            } else if (result.errors != null) {
                showToast(result.errors.msg)
            }
        })
        viewModel.refresh()
    }

    private fun setListener() {
        srl_refresh.setOnRefreshListener { viewModel.refresh() }
    }

    private fun showToast(content: String?) {
        if (content == null) return
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
    }

    inner class ResumeAdapter : RecyclerView.Adapter<ResumeCardHolder>() {

        private var reusmes: List<ResumeDTO>? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResumeCardHolder {
            return ResumeCardHolder(
                    LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_resume_card, parent, false)
            )
        }

        fun setData(resumes: List<ResumeDTO>?) {
            this.reusmes = resumes
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return reusmes?.size ?: 0
        }

        override fun onBindViewHolder(holder: ResumeCardHolder, position: Int) {
            holder.bindData(reusmes?.getOrNull(position))
        }
    }

    inner class ResumeCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(resume: ResumeDTO?) {
            if (resume == null) return
            itemView.tv_name.text = resume.baseInfo?.name
            itemView.tv_company_with_position.text = String.format("%s · %s", resume.company?.name, resume.jobLabel)

            val baseProfile = StringBuilder()
            if ((resume.exeprience ?: 0) > 0) {
                baseProfile.append(resume.exeprience).append("年经验")
            }
            if ((resume.account?.age ?:0) > 0){
                baseProfile.append(" · ").append(resume.account?.age).append("岁")
            }
            if (!resume.education.isNullOrEmpty()) {
                baseProfile.append(" · ").append(resume.education?.get(0)?.record)
            }
            itemView.tv_base_profile.text = baseProfile.toString()
            itemView.sdv_avatar?.setImageURI(resume.account?.avatar)
            itemView.setOnClickListener {
                Router.build(RESUME_DETAIL_PAGE)
                        .with(KEY_RESUME_ID,resume.id)
                        .go(this@ResumeListFragment)
            }
        }
    }


}
