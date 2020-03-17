package com.example.chivas.testorm.view.base;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

public abstract class BaseActivity extends FragmentActivity {

//    private Unbinder unbind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(onGetContentView());
//        unbind = ButterKnife.bind(this);
        onPrepareView();
        onRequestData();
    }

    /**
     * 初始化view
     */
    protected abstract void onPrepareView();

    /**
     * 初始化数据
     */
    protected void onRequestData() {
        // do nothing
    }

    /**
     * 获取内容布局的资源id
     *
     * @return 返回需要显示的布局id
     */
    protected abstract int onGetContentView();

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (unbind != null) {
//            unbind.unbind();
//        }
    }
}
