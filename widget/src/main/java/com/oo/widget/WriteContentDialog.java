package com.oo.widget;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

public class WriteContentDialog extends DialogFragment implements View.OnClickListener {

    private static final String TAG = WriteContentDialog.class.getName();

    private Builder builder;
    private TextView  tv_cancel, tv_confirm;
    private EditText et_content;

    private WriteContentDialog(Builder builder) {
        this.builder = builder;
    }

    private static WriteContentDialog newInstance(Builder builder) {
        Bundle args = new Bundle();
        WriteContentDialog fragment = new WriteContentDialog(builder);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_write_content, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (builder==null) return;
        if (getDialog() == null) return;
        if (getDialog().getWindow() == null) return;
        getDialog().getWindow().setLayout(builder.width,builder.height);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        initViewState();
        bindListener();
    }

    private void initView(View parent) {
        if (parent == null) return;
        et_content = parent.findViewById(R.id.et_content);
        tv_cancel = parent.findViewById(R.id.tv_cancel);
        tv_confirm = parent.findViewById(R.id.tv_confirm);
    }

    private void bindListener() {
        if (builder == null) return;
        tv_cancel.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
    }

    private void initViewState() {
        if (builder == null) return;

        if (builder.contentText != null) {
            et_content.setText(builder.contentText);
        }

        if (builder.confirmText != null) {
            tv_confirm.setText(builder.confirmText);
        }

        if (builder.cancelText != null) {
            tv_cancel.setText(builder.cancelText);
        }
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.tv_cancel) {
            if (builder.cancelListener != null) {
                builder.cancelListener.onCancel();
            }
        } else if (i == R.id.tv_confirm) {
            if (builder.confirmListener != null) {
                builder.confirmListener.onConfirm(et_content.getText());
            }
        }
        dismissAllowingStateLoss();
    }

    public static class Builder {
        private CharSequence contentText;
        private CharSequence confirmText;
        private CharSequence cancelText;

        private ConfirmListener confirmListener;
        private CancelListener cancelListener;

        private int width = MATCH_PARENT;
        private int height = WRAP_CONTENT;

        public Builder() {

        }

        public Builder setContentText(CharSequence contentText) {
            this.contentText = contentText;
            return this;
        }

        public Builder setConfirmText(CharSequence confirmText) {
            this.confirmText = confirmText;
            return this;
        }

        public Builder setCancelText(CharSequence cancelText) {
            this.cancelText = cancelText;
            return this;
        }


        public Builder onConfirm(ConfirmListener confirmListener) {
            this.confirmListener = confirmListener;
            return this;
        }

        public Builder onCancel(CancelListener cancelListener) {
            this.cancelListener = cancelListener;
            return this;
        }

        public Builder setWidth(int width) {
            if (width != WRAP_CONTENT && width != MATCH_PARENT && width < 0) return this;
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            if (height != WRAP_CONTENT && height != MATCH_PARENT && height < 0) return this;
            this.height = height;
            return this;
        }

        @Nullable
        public WriteContentDialog show(FragmentActivity activity) {
            if (activity == null) return null;
            final FragmentManager fragmentManager = activity.getSupportFragmentManager();
            Fragment dialog = fragmentManager.findFragmentByTag(TAG);
            if (!(dialog instanceof WriteContentDialog)) {
                dialog = newInstance(this);
            }
            if (!activity.isFinishing() && !dialog.isAdded()) {
                ((WriteContentDialog) dialog).show(fragmentManager, TAG);
            }
            return (WriteContentDialog) dialog;

        }

    }

    public interface ConfirmListener {
        void onConfirm(CharSequence sequence);
    }

    public interface CancelListener {
        void onCancel();
    }

}
