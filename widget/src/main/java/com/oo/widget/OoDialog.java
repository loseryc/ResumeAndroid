package com.oo.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class OoDialog extends Dialog implements View.OnClickListener {
    public final static int TYPE_CONFIRM = 1;
    public final static int TYPE_WARNING = 2;
    public final static int TYPE_ERROR = 3;
    public final static int TYPE_LOADING = 4;

    private final int alertType;

    private Button confirmBtn;
    private Button cancelBtn;
    private TextView tv_title;
    private TextView tv_message;

    private OoDialogListener callBack;

    private String title;
    private String message;
    private String info;
    private String cancelText;
    private String confirmText;

    private boolean isCancelable = true;

    public OoDialog(Context context) {
        this(context, R.style.AlertStyle, "");
    }

    public OoDialog(Context context, int type) {
        super(context, R.style.AlertStyle);
        this.alertType = type;
        this.info = "";
    }

    public OoDialog(Context context, int type, String info) {
        this(context, type, info, true);
    }

    public OoDialog(Context context, int type, String info, boolean isCancelable) {
        super(context, R.style.LoadingStyle);
        this.alertType = type;
        this.info = info;
        this.isCancelable = isCancelable;
    }

    public OoDialog(Context context, String confirmText, String cancelText, boolean isCancelable) {
        super(context, R.style.LoadingStyle);
        if (!TextUtils.isEmpty(confirmText) && !TextUtils.isEmpty(cancelText)) {
            this.alertType = TYPE_CONFIRM;
        } else if (!TextUtils.isEmpty(confirmText) && TextUtils.isEmpty(cancelText)) {
            this.alertType = TYPE_ERROR;
        } else {
            this.alertType = TYPE_WARNING;
        }
        this.confirmText = confirmText;
        this.cancelText = cancelText;
        this.isCancelable = isCancelable;
    }

    public void setCallBack(OoDialogListener callback) {
        this.callBack = callback;
    }

    public void setTitle(String title) {
        this.title = title;
        if (tv_title != null)
            tv_title.setText(title);
    }

    public void setMessage(String message) {
        this.message = message;
        if (tv_message != null) {
            tv_message.setText(message);
        }
    }

    public void setCancelText(String cancel) {
        this.cancelText = cancel;
        if (cancelBtn != null) {
            cancelBtn.setText(cancel);
        }
    }

    public void setCancelConfirmText(String cancel, String confirm) {
        this.cancelText = cancel;
        this.confirmText = confirm;

        if (confirmBtn != null) {
            confirmBtn.setText(confirm);
        }

        if (cancelBtn != null) {
            cancelBtn.setText(cancel);
        }
    }

    public void setInfo(String info) {
        this.info = info;
        if ((findViewById(R.id.info)) != null) {
            ((TextView) findViewById(R.id.info)).setText(this.info);
        }
    }

    private void confirmCallBack() {
        if (this.callBack != null) {
            this.callBack.confirmListener();
        }
    }

    private void cancelCallBack() {
        if (this.callBack != null) {
            this.callBack.cancelListener();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(isCancelable);

        switch (alertType) {
            case TYPE_CONFIRM:
            default:
                setContentView(R.layout.dialog_confirm);
                initView();
                bindListener();
                if (!TextUtils.isEmpty(cancelText) && !TextUtils.isEmpty(confirmText)) {
                    confirmBtn.setText(confirmText);
                    cancelBtn.setText(cancelText);
                }
                break;
            case TYPE_WARNING:
                setContentView(R.layout.dialog_confirm);
                initView();
                bindListener();
                confirmBtn.setEnabled(false);
                confirmBtn.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(cancelText)) {
                    cancelBtn.setText(cancelText);
                }
                break;
            case TYPE_ERROR:
                setContentView(R.layout.dialog_confirm);
                initView();
                bindListener();
                cancelBtn.setEnabled(false);
                cancelBtn.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(cancelText)) {
                    cancelBtn.setText(cancelText);
                }
                break;
            case TYPE_LOADING:
                if (getWindow() != null) {
                    getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }
                setContentView(R.layout.dialog_loading);

                setInfo(info);
                break;
        }

        if (alertType != TYPE_LOADING) {
            if (!TextUtils.isEmpty(title)) {
                tv_title.setText(title);
                tv_title.setVisibility(View.VISIBLE);
            } else {
                tv_title.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(message)) {
                tv_message.setText(message);
                tv_message.setVisibility(View.VISIBLE);
            } else {
                tv_message.setVisibility(View.GONE);
            }
        }
    }

    private void initView() {
        confirmBtn = findViewById(R.id.confirm_btn);
        cancelBtn = findViewById(R.id.cancel_btn);
        tv_title = findViewById(R.id.tv_title);
        tv_message = findViewById(R.id.message);
    }

    private void bindListener() {
        confirmBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cancel_btn) {
            dismiss();
            cancelCallBack();
        } else if (view.getId() == R.id.confirm_btn) {
            if (alertType == TYPE_CONFIRM || alertType == TYPE_ERROR) {
                dismiss();
                confirmCallBack();
            }
        }
    }

    public static OoDialog onButtonConfirmDialog(Context context, String message, String confirmText, OoDialogListener callBack) {
        OoDialog ooDialog = new OoDialog(context, TYPE_WARNING, "", false);
        if (TextUtils.isEmpty(confirmText)) {
            confirmText = "知道了";
        }
        ooDialog.setMessage(message);
        ooDialog.setCancelText(confirmText);
        if (callBack != null) {
            ooDialog.setCallBack(callBack);
        }
        return ooDialog;
    }


    public static void dismissDialog(OoDialog dialog) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
