package com.oo.resume.activity.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.MultipleItemRvAdapter
import com.chad.library.adapter.base.provider.BaseItemProvider
import com.facebook.drawee.view.SimpleDraweeView
import com.oo.resume.R
import com.oo.resume.data.response.BaseInfoDTO


class ResumeDetailAdapter : MultipleItemRvAdapter<Any, BaseViewHolder>(null) {


    init {
        finishInitialize()
    }

    override fun registerItemProvider() {
        mProviderDelegate.registerProvider(BasicInfoProvider())
    }

    override fun getViewType(t: Any?): Int {
        return when (t) {
            t is BaseInfoDTO -> TYPE_BASIC_INFO

            else -> TYPE_BASIC_INFO
        }
    }

    companion object {
        const val TYPE_BASIC_INFO = 0X001
    }



    fun addBasic(basic: BaseInfoDTO?) {
        if (basic != null) {
            getData().add(basic)
        }
        notifyDataSetChanged()
    }



    inner class BasicInfoProvider : BaseItemProvider<BaseInfoDTO, BaseViewHolder>() {
        override fun convert(helper: BaseViewHolder?, data: BaseInfoDTO?, position: Int) {
            if (helper == null || data == null) return
            helper.setText(R.id.tv_name, data.name ?: "")
            helper.getView<SimpleDraweeView>(R.id.sdv_avatar)?.setImageURI(data.avatar)
            helper.addOnClickListener(R.id.cl_basic_wrapper)
        }

        override fun viewType(): Int {
            return TYPE_BASIC_INFO
        }

        override fun layout(): Int {
            return R.layout.item_resume_detail_basic
        }

    }

}