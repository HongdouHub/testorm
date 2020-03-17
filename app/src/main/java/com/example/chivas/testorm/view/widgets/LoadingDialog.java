package com.example.chivas.testorm.view.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chivas.testorm.R;

public class LoadingDialog {

    private TextView tvMessage;
    private View view;
    private ImageView ivLoading;
    private Dialog dialog;
    private AnimationDrawable anim;

    public LoadingDialog(Context context) {
        view = LayoutInflater.from(context).inflate(
                R.layout.dlg_comm_loading_view, null);
        tvMessage = (TextView) view.findViewById(R.id.tvMessage);
        ivLoading = (ImageView) view.findViewById(R.id.ivLoading);
        ivLoading.setBackgroundResource(R.drawable.anim_loading);
        anim = (AnimationDrawable) ivLoading.getBackground();
        dialog = new Dialog(context, R.style.Activity_Pop);
        dialog.setContentView(view);
        WindowManager.LayoutParams localLayoutParams = dialog.getWindow()
                .getAttributes();
        localLayoutParams.gravity = Gravity.CENTER;
    }

    public void cancel() {
        dialog.cancel();
    }

    /**
     * 是否正在显示
     *
     * @return
     */
    public boolean isShowing() {
        return dialog.isShowing();
    }

    /**
     * 取消dialog
     */
    public void dismiss() {
        if (dialog != null) {
            if (anim != null)
                anim.stop();
            dialog.dismiss();
        }
    }

    /**
     * 显示dialog
     */
    public void show(String message, boolean isCancelable) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.setCanceledOnTouchOutside(isCancelable);
            if (!anim.isRunning()) {
                anim.start();
            }
            if (tvMessage != null && !TextUtils.isEmpty(message)) {
                tvMessage.setText(message);
            }
            dialog.show();
        }
    }

    /**
     * 不可取消的加载框
     *
     * @param message
     */
    public void showCannotCancel(String message) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.setCancelable(false);
            if (!anim.isRunning()) {
                anim.start();
            }
            if (tvMessage != null && !TextUtils.isEmpty(message)) {
                tvMessage.setText(message);
            }
            dialog.show();
        }
    }

    public void setMessage(String message) {
        if (dialog != null && dialog.isShowing()) {
            if (tvMessage != null && !TextUtils.isEmpty(message)) {
                tvMessage.setText(message);
            }
        }
    }

}
