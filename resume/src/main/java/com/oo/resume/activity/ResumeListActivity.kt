package com.oo.resume.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.oo.platform.view.BaseActivity
import com.oo.platform.viewmodel.ViewModelBinder
import com.oo.resume.R
import com.oo.resume.entity.Resume
import com.oo.resume.viewmodel.ResumeListViewModel
import kotlinx.android.synthetic.main.activity_resume_list.*
import kotlinx.android.synthetic.main.item_resume_card.*
import kotlinx.android.synthetic.main.item_resume_card.view.*

class ResumeListActivity : BaseActivity() {


    lateinit var viewmodel: ResumeListViewModel
    lateinit var adapter: ResumeAdapter

    override fun getContentViewResId(): Int {
        return R.layout.activity_resume_list
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        bindViewModel()
        observe()
        setListener()
    }

    private fun initView() {
        adapter = ResumeAdapter()
        rv_resume.adapter = adapter
    }

    private fun bindViewModel() {
        viewmodel = ViewModelBinder.bind(this, ResumeListViewModel::class.java)
    }

    private fun observe() {
        viewmodel.getResumeList().observe(this, Observer { result ->
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
        viewmodel.refresh()
    }

    private fun setListener() {
        srl_refresh.setOnRefreshListener { viewmodel.refresh() }
    }


    private fun showToast(content: String?) {
        if (content == null) return
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
    }

    inner class ResumeAdapter : RecyclerView.Adapter<ResumeCardHoler>() {

        var reusmes: List<Resume>? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResumeCardHoler {
            return ResumeCardHoler(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_resume_card, parent, false)
            )
        }

        fun setData(reusmes: List<Resume>?) {
            this.reusmes = reusmes
            notifyDataSetChanged()
        }

        override fun getItemCount(): Int {
            return reusmes?.size ?: 0
        }

        override fun onBindViewHolder(holder: ResumeCardHoler, position: Int) {
            holder.bindData(reusmes?.getOrNull(position))
        }

    }

    inner class ResumeCardHoler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(resume: Resume?) {
            if (resume == null) return
            itemView.tv_name.text = resume.baseInfo.name
            itemView.tv_company.text = resume.company?.name
        }
    }


}
