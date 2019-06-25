package com.oo.resume.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.oo.platform.view.BaseFragment
import com.oo.platform.viewmodel.ViewModelBinder
import com.oo.resume.R
import com.oo.resume.param.response.AccountDTO
import com.oo.resume.viewmodel.AccountViewModel
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-13 18:05
 *  $describe
 */
class MineFragment : BaseFragment() {

    lateinit var viewModel: AccountViewModel

    override fun getContentViewResId(): Int {
        return R.layout.fragment_mine
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelBinder.bind(this, AccountViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
    }

    private fun observe() {
        viewModel.getAccountInfo().observe(this, Observer { refreshView(it) })
    }

    private fun refreshView(account: AccountDTO?) {
        if (account == null) return
        avatar.setImageURI(account.avatar)
        name.setText(account.name)
        sex.setText(if (account.sex == 0) "男" else "女")
        age.setText(account.age.toString())
    }

}