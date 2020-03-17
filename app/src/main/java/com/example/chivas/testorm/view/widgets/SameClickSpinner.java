package com.example.chivas.testorm.view.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

/**
 * 重写Spinner，使其支持点击下拉框下同一Item会触发OnItemSelectedListener
 */
public class SameClickSpinner extends Spinner {
    public SameClickSpinner(Context context) {
        super(context);
    }

    public SameClickSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SameClickSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setSelection(int position, boolean animate) {
        super.setSelection(position, animate);
        if (position == getSelectedItemPosition()) {
            OnItemSelectedListener listener = getOnItemSelectedListener();
            if (listener != null) {
                listener.onItemSelected(this, getSelectedView(), position, getSelectedItemId());
            }
        }
    }

    @Override
    public void setSelection(int position) {
        super.setSelection(position);
        if (position == getSelectedItemPosition()) {
            OnItemSelectedListener listener = getOnItemSelectedListener();
            if (listener != null) {
                listener.onItemSelected(this, getSelectedView(), position, getSelectedItemId());
            }
        }
    }
}
