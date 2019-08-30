package com.oo.resume.activity.adapter

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView

open class SimpleViewHolder(@LayoutRes idRes: Int, parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(idRes, parent, false)) {
    val mContext:Context = itemView.context

    private var ids = SparseArray<View>()
    private var clickViews = SparseArray<View>()

    fun setText(@IdRes viewId:Int, string: CharSequence): SimpleViewHolder {
        val textView: TextView? = getView(viewId)
        textView?.text = string
        return this
    }

    fun setText(@IdRes viewId:Int,
                @StringRes stringRes: Int): SimpleViewHolder {
        val textView: TextView? = getView(viewId)
        textView?.setText(stringRes)
        return this
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : View> getView(@IdRes viewId: Int): T? {
        var view= ids.get(viewId)
        if (view == null) {
            view = itemView.findViewById(viewId)
            ids.put(viewId, view)
        }
        return view as T?
    }

    fun addOnClickListener(@IdRes vararg viewIds: Int){
        viewIds.forEach { it ->
            val clickView = getView<View>(it)
            clickView?.setOnClickListener {  }
        }
    }

}