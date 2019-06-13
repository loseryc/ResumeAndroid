package com.oo.resume.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.oo.platform.view.BaseFragment
import com.oo.platform.viewmodel.ViewModelBinder
import com.oo.resume.R
import com.oo.resume.param.response.ResumeDTO
import com.oo.resume.viewmodel.ResumeListViewModel
import kotlinx.android.synthetic.main.fragment_resume_list.*
import kotlinx.android.synthetic.main.item_resume_card.view.*

class ResumeListFragment : BaseFragment() {


    private lateinit var viewmodel: ResumeListViewModel
    private lateinit var adapter: ResumeAdapter

    override fun getContentViewResId(): Int {
        return R.layout.fragment_resume_list
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show()
    }

    inner class ResumeAdapter : RecyclerView.Adapter<ResumeCardHoler>() {

        private var reusmes: List<ResumeDTO>? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResumeCardHoler {
            return ResumeCardHoler(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_resume_card, parent, false)
            )
        }

        fun setData(reusmes: List<ResumeDTO>?) {
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
        fun bindData(resume: ResumeDTO?) {
            if (resume == null) return
            itemView.tv_name.text = resume.baseInfo?.name
            itemView.tv_company.text = resume.company?.name
        }
    }


}
