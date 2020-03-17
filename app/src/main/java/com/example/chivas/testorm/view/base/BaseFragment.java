package com.example.chivas.testorm.view.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.chivas.testorm.R;
import com.example.chivas.testorm.view.widgets.LoadingDialog;

import javax.annotation.Nullable;

public abstract class BaseFragment extends Fragment {

//    private Unbinder unbind;
    protected View rootView;
    private LoadingDialog loadingDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(onGetContentView(), container, false);
//        unbind = ButterKnife.bind(this, rootView);
        onPrepareView();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onPrepareData(savedInstanceState);
    }

    /**
     * 初始化view
     */
    protected abstract void onPrepareView();

    /**
     * 初始化数据
     */
    protected void onPrepareData(Bundle bundle) {
        // do nothing
    }

    /**
     * 获取内容布局的资源id
     *
     * @return 返回需要显示的布局id
     */
    protected abstract int onGetContentView();

    /**
     * 显示对话框加载进度条
     *
     * @param msg
     * @param isCancelable 点击侧边是否取消
     */
    public void showLoadingDialog(final String msg, final boolean isCancelable) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(getActivity());
                }
                if (!loadingDialog.isShowing()) {
                    loadingDialog.show(msg, isCancelable);
                }
            }
        });
    }

    /**
     * 显示对话框加载进度条
     */
    public void showLoadingDialog(final String msg) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(getActivity());
                }
                if (!loadingDialog.isShowing()) {
                    loadingDialog.show(msg, false);
                }
            }
        });
    }

    /**
     * 显示对话框加载进度条
     *
     * @param isCancelable 点击侧边是否取消
     */
    public void showLoadingDialog(final boolean isCancelable) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(getActivity());
                }
                if (!loadingDialog.isShowing()) {
                    loadingDialog.show(getString(R.string.loading_comm_dlg_str), isCancelable);
                }
            }
        });
    }

    /**
     * 显示对话框加载进度条
     */
    public void showLoadingDialog() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingDialog == null) {
                    loadingDialog = new LoadingDialog(getActivity());
                }
                if (!loadingDialog.isShowing()) {
                    loadingDialog.show(getString(R.string.loading_comm_dlg_str), false);
                }
            }
        });
    }

    /**
     * 隐藏对话框加载进度条
     */
    public void hideLoadingDialog() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (loadingDialog != null) {
                    loadingDialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        if (unbind != null) {
//            unbind.unbind();
//        }
    }

}
