package com.example.chivas.testorm.view.fragment;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.chivas.testorm.R;
import com.example.chivas.testorm.utils.ContractListener;
import com.example.chivas.testorm.utils.PreferenceUtils;
import com.example.chivas.testorm.view.base.BaseFragment;

public abstract class BaseLogFragment extends BaseFragment implements ContractListener {

    private ScrollView svLog;
    private TextView tvLog;

    @Override
    protected void onPrepareView() {
        svLog = (ScrollView) rootView.findViewById(R.id.sv_log);
        tvLog = (TextView) rootView.findViewById(R.id.tv_log);
        tvLog.setText(PreferenceUtils.getString(getLogTag()));
    }

    @Override
    protected int onGetContentView() {
        return R.layout.fra_log;
    }

    @Override
    public void log(final String text, final boolean error) {
        final String input = text.concat("\n");
        showLog(error, input);
        String message = PreferenceUtils.getString(getLogTag());
        PreferenceUtils.putString(getLogTag(), message + input);
    }

    private void showLog(boolean error, String input) {
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (error) {
                    Spannable errorSpan = new SpannableString(input);
                    errorSpan.setSpan(new ForegroundColorSpan(Color.RED), 0, errorSpan.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    tvLog.append(errorSpan);
                } else {
                    tvLog.append(input);
                }

                tvLog.post(new Runnable() {
                    @Override
                    public void run() {
                        svLog.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }
        });
    }

    @Override
    public void clearLog() {
        PreferenceUtils.putString(getLogTag(), "");
        FragmentActivity activity = getActivity();
        if (null == activity) {
            return;
        }
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (null != tvLog) {
                    tvLog.setText("");
                }
            }
        });
    }

    protected abstract String getLogTag();
}
