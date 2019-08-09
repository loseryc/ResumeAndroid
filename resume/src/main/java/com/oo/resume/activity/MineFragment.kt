package com.oo.resume.activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.chenenyu.router.Router
import com.oo.platform.view.BaseFragment
import com.oo.platform.viewmodel.ViewModelBinder
import com.oo.resume.R
import com.oo.resume.activity.router.RouteUrl
import com.oo.resume.data.response.AccountDTO
import com.oo.resume.viewmodel.AccountViewModel
import com.oo.widget.BottomDialog
import com.oo.widget.OoDialog
import com.oo.widget.WriteContentDialog
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-13 18:05
 *  $describe
 */
class MineFragment : BaseFragment() {

    private lateinit var viewModel: AccountViewModel

    private lateinit var loading: OoDialog

    override fun getContentViewResId(): Int {
        return R.layout.fragment_mine
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelBinder.bind(this, AccountViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLoadingDialog()
        observe()
        setListener()
    }

    private fun setListener() {

        sex.setOnClickListener {
            BottomDialog(context)
                    .builder()
                    .setTitle("请选择性别")
                    .setCancelable(false)
                    .setCanceledOnTouchOutside(true)
                    .addSheetItem("男") { sex.text = "男" }
                    .addSheetItem("女") { sex.text = "女" }.show()
        }
        password.setOnClickListener { Router.build(RouteUrl.RESET_PASSWORD_PAGE).go(this) }
        exit.setOnClickListener { viewModel.logout() }
        tv_name.setOnClickListener {
            WriteContentDialog.Builder()
                    .setContentText(tv_name.text)
                    .onConfirm { text ->
                        tv_name.setText(text)
                        viewModel.update(getUIAccount())
                    }.show(activity)
        }
    }

    private fun getUIAccount(): AccountDTO {
        val account = AccountDTO()
        account.name = tv_name.text.toString()
        account.age = age.text.toString().toIntOrNull()
        account.sex = if (sex.text == "男") 0 else 1
        return account
    }

    private fun observe() {
        viewModel.getAccountInfo().observe(viewLifecycleOwner, Observer { refreshView(it) })

        viewModel.getLogoutResult().observe(viewLifecycleOwner, Observer {
            Router.build(RouteUrl.SIGNIN_PAGE).go(this)
            activity?.finish()
        })

        viewModel.getUpdateResult().observe(viewLifecycleOwner, Observer { result ->
            if (result == null) return@Observer
            if (result.isLoading) {
                loading.show()
                return@Observer
            }
            loading.dismiss()
        })
    }

    private fun initLoadingDialog() {
        loading = OoDialog(context, OoDialog.TYPE_LOADING, "Loading")
    }

    private fun refreshView(account: AccountDTO?) {
        if (account == null) return
        avatar.setImageURI(account.avatar)
        tv_name.text = account.name
        sex.text = if (account.sex == 0) "男" else "女"
        age.setText(account.age.toString())
        phone.text = account.phone
    }

}