package com.oo.resume.activity

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.lifecycle.Observer
import com.chenenyu.router.annotation.Route
import com.oo.platform.view.BaseActivity
import com.oo.platform.viewmodel.ViewModelBinder
import com.oo.resume.R
import com.oo.resume.activity.router.RouteUrl
import com.oo.resume.viewmodel.ResetPasswordViewModel
import com.oo.widget.OoDialog
import kotlinx.android.synthetic.main.activity_reset_password.*

/**
 *  $author yangchao
 *  $email  cd.uestc.superyoung@gmail.com
 *  $date   2019-07-24 16:07
 *  $describe
 */
@Route(RouteUrl.RESET_PASSWORD_PAGE)
class ResetPasswordActivity : BaseActivity() {

    lateinit var viewModel: ResetPasswordViewModel

    private lateinit var loading: OoDialog

    override fun getContentViewResId(): Int {
        return R.layout.activity_reset_password
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelBinder.bind(this, ResetPasswordViewModel::class.java)
        loading = OoDialog(this, OoDialog.TYPE_LOADING, "Loading")
        observe()
        setListener()
    }

    private fun observe() {
        viewModel.getResetPasswordResult().observe(this, Observer { result ->
            if (result == null) return@Observer
            if (result.isLoading) {
                loading.show()
                return@Observer
            }
            loading.dismiss()
            if (result.isSuccess) {
                Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show()
            } else if (!TextUtils.isEmpty(result.errors?.msg)) {
                Toast.makeText(this, result.errors?.msg, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "密码修改失败", Toast.LENGTH_SHORT).show()
            }
        })
        viewModel.getAccountInfo().observe(this, Observer { account ->
            tv_phone.text = account.phone
        })
    }

    private fun setListener() {
        btn_submit.setOnClickListener { submit() }
        iv_back.setOnClickListener { finish() }
    }

    private fun submit() {
        val oldPassword = et_old_password.text.toString()
        val newPassword = et_new_password.text.toString()
        val confrimPassword = et_password_confirm.text.toString()
        if (illegale(oldPassword, newPassword, confrimPassword)) return

        viewModel.update(oldPassword, newPassword)
    }

    private fun illegale(oldPassword: String?, newPassword: String?, confirmPassword: String?): Boolean {
        if (oldPassword.isNullOrBlank()) {
            Toast.makeText(this, "请输入旧密码", Toast.LENGTH_SHORT).show()
            return true
        }
        if (newPassword.isNullOrBlank() || confirmPassword.isNullOrBlank()) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show()
            return true
        }
        if (newPassword != confirmPassword) {
            Toast.makeText(this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show()
            return true
        }
        return false
    }
}