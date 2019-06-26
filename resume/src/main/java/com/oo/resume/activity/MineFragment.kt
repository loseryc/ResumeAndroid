package com.oo.resume.activity

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.oo.platform.view.BaseFragment
import com.oo.platform.viewmodel.ViewModelBinder
import com.oo.resume.R
import com.oo.resume.param.response.AccountDTO
import com.oo.resume.viewmodel.AccountViewModel
import com.oo.resume.widget.ActionSheetDialog
import kotlinx.android.synthetic.main.fragment_mine.*

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-06-13 18:05
 *  $describe
 */
class MineFragment : BaseFragment() {

    private lateinit var viewModel: AccountViewModel

    private lateinit var loading: AlertDialog

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
        btn_save.setOnClickListener(View.OnClickListener { viewModel.update(getUIAccount()) })
        sex.setOnClickListener(View.OnClickListener {
            ActionSheetDialog(context)
                .builder()
                .setTitle("请选择性别")
                .setCancelable(false)
                .setCanceledOnTouchOutside(true)
                .addSheetItem("男", ActionSheetDialog.OnSheetItemClickListener { sex.text = "男" })
                .addSheetItem("女", ActionSheetDialog.OnSheetItemClickListener { sex.text = "女" }).show()
        })
    }

    private fun getUIAccount(): AccountDTO {
        val account = AccountDTO()
        account.name = name.text.toString()
        account.age = age.text.toString().toIntOrNull()
        account.sex = if (sex.text == "男") 0 else 1
        return account
    }

    private fun observe() {
        viewModel.getAccountInfo().observe(this, Observer { refreshView(it) })
        viewModel.getUpdateResult().observe(this, Observer { result ->
            if (result == null) return@Observer
            if (result.isLoading) {
                loading.show()
                return@Observer
            }
            loading.dismiss()
        })
    }

    private fun initLoadingDialog() {
        loading = AlertDialog.Builder(context)
            .setMessage("Loading")
            .setCancelable(false)
            .setIcon(R.mipmap.ic_launcher_round)
            .create()
    }

    private fun refreshView(account: AccountDTO?) {
        if (account == null) return
        avatar.setImageURI(account.avatar)
        name.setText(account.name)
        sex.text = if (account.sex == 0) "男" else "女"
        age.setText(account.age.toString())
        phone.text = account.phone
    }

}