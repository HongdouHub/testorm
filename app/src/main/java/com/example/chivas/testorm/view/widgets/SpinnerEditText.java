package com.example.chivas.testorm.view.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.example.chivas.testorm.R;
import com.example.chivas.testorm.adapter.widget.SpinnerEditTextListAdapter;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("AppCompatCustomView")
public class SpinnerEditText extends EditText {

    private static final float SPINNER_MIN_HEIGHT = 40f;  //默认最小高度
    private static final float SPINNER_MAX_HEIGHT = 100f; //默认最大高度
    public static final int POPUP_SHOW = 1001;    //开启PopupWindow
    public static final int POPUP_DISMISS = 1002; //关闭PopupWindow

    private Context context;
    private float spnMinHeight;
    private float spnMaxHeight;
    private boolean isAutoCheckStatus;
    private Drawable rightImage;
    private float rightImageWidth;
    private float rightImageHeight;
    private float imageClickableWidth;

    /*------------------------------初始化PopupWindow ----------------------------*/
    private PopupWindow popupWindow;
    private boolean isPopupWindowShowing = false;//当前PopupWindow是否正在显示
    private boolean isClickItemView = false; //点击下拉列表的选项
    private SpinnerEditTextListAdapter adapter;
    private ListView listView;
    private LinearLayout popupView;
    private int showType = Type.TYPE_UP; //PopupWindow显示类型
    private List<String> totalList = new ArrayList<>();  //展示的总内容
    private DrawableRightListener rightImageListener;

    /**
     * 右侧图标点击事件
     */
    interface DrawableRightListener {
        void onDrawableRightClick(View view);
    }

    public void setDrawableRightListener(DrawableRightListener listener) {
        this.rightImageListener = listener;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case POPUP_SHOW:
                    showPopupWindow();
                    break;
                case POPUP_DISMISS:
                    isPopupWindowShowing = false;
                    break;
                default:
            }
        }
    };

    public SpinnerEditText(Context context) {
        super(context);
        this.context = context;
        initAttrs(context, null);
        initView();
    }

    public SpinnerEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initAttrs(context, attrs);
        initView();
    }

    public SpinnerEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initAttrs(context, attrs);
        initView();
    }

    private void initView() {
        setLongClickable(false);
        setCustomSelectionActionModeCallback(defaultCustomSelectionAction); //设置默认屏蔽ActionMode菜单
        setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI); //请求IME输入法不要显示额外的文本UI
        addTextChangedListener(new TextWatcher() { //文本内容变换事件
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (isAutoCheckStatus) {
                    setStatus(TextUtils.isEmpty(s.toString()) ? Status.STATUS_EXCEPTION :
                            Status.STATUS_NORMAL);
                }
                if (TextUtils.isEmpty(s)) {
                    delayShowPopupWindow();
                } else {
                    dismissPopupWindow();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // do nothing
            }
        });
        setOnFocusChangeListener(new OnFocusChangeListener() { //焦点变换事件
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (popupWindow != null && hasFocus) {
                    delayShowPopupWindow();
                }
            }
        });
        setOnClickListener(new OnClickListener() { //点击事件
            @Override
            public void onClick(View v) {
                if (popupWindow != null && isFocused() &&
                        TextUtils.isEmpty(getText())) {
                    delayShowPopupWindow();
                } else {
                    dismissPopupWindow();
                }
            }
        });
        setRightCompoundDrawable(); //设置右侧图标
        setDrawableRightListener(defaultRightListener); //设置右侧图标的点击事件
        initPopupWindow();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SpinnerEditText);
            try {
                spnMinHeight = typedArray.getDimension(R.styleable.SpinnerEditText_spn_min_height, SPINNER_MIN_HEIGHT);
                spnMaxHeight = typedArray.getDimension(R.styleable.SpinnerEditText_spn_max_height, SPINNER_MAX_HEIGHT);
                rightImage = typedArray.getDrawable(R.styleable.SpinnerEditText_right_image_icon);
                rightImageWidth = typedArray.getDimension(R.styleable.SpinnerEditText_right_image_width, 40f);
                rightImageHeight = typedArray.getDimension(R.styleable.SpinnerEditText_right_image_height, 40f);
                imageClickableWidth = typedArray.getDimension(R.styleable.SpinnerEditText_image_clickable_width, 60f);
                isAutoCheckStatus = typedArray.getBoolean(R.styleable.SpinnerEditText_auto_check_status, false);
            } finally {
                typedArray.recycle();
            }
        }
    }

    private void initPopupWindow() {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(context);
            popupView = (LinearLayout) View.inflate(context, R.layout.spn_edt_comm_top_view, null);
            listView = (ListView) popupView.findViewById(R.id.lv_pop_top);
            adapter = new SpinnerEditTextListAdapter(context);
            adapter.setOnItemClickListener(new SpinnerEditTextListAdapter.OnItemClickListener() {
                @Override
                public void onClickItem(String item) {
                    isClickItemView = true;
                    setText(item);
                    setSelection(item.length());
                    dismissPopupWindow();
                }
            });

            listView.setAdapter(adapter);
            popupWindow.setContentView(popupView);
            popupView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            popupWindow.setWidth(AbsListView.LayoutParams.WRAP_CONTENT);
            popupWindow.setHeight(AbsListView.LayoutParams.WRAP_CONTENT);
            popupWindow.setSoftInputMode(ListPopupWindow.INPUT_METHOD_NEEDED);

            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//            popupWindow.setAnimationStyle(R.style.CommSpinnerAnimationFromBottom);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(false);
            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    mHandler.sendEmptyMessageDelayed(POPUP_DISMISS, 200);
                }
            });
        }
    }

    public void replaceList(List<String> list) {
        totalList = list;
        adapter.replaceList(list);
    }

    private void delayShowPopupWindow() {
        if (!mHandler.hasMessages(POPUP_SHOW)) {
            mHandler.sendEmptyMessageDelayed(POPUP_SHOW, 200);
        }
    }

    private void showPopupWindow() {
        if (isClickItemView) {
            isClickItemView = false;
            return;
        }
        isPopupWindowShowing = true;
        if (totalList.isEmpty()) {
            dismissPopupWindow();
        } else {
            updateHeightAndShow();
        }
    }

    private void updateHeightAndShow() {
        post(new Runnable() {
            @Override
            public void run() {
                int height = getRealHeight();
                if (showType == Type.TYPE_UP) {
                    showAsPopUp(height);
                } else {
                    showAsPopBottom(height);
                }
            }
        });
    }

    private void showAsPopUp(int height) {
//        popupWindow.setAnimationStyle(R.style.CommSpinnerAnimationFromUp);
        popupView.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int[] location = new int[2];
        getLocationInWindow(location);

        //计算显示位置 如果高度不够自动调整到合适高度
        int offSetY = -getHeight() - height;
        if (offSetY + location[1] < 0) { //上方不够展示
            int realHeight = location[1] - getHeight() / 2; //只取上方剩余空间
            offSetY = -(location[1] + getHeight() / 2); //计算出PopupWindow展示Y轴坐标
            setPopupHeight(realHeight);
        } else {
            setPopupHeight(height);
        }
        popupWindow.showAsDropDown(this, 0, offSetY);
    }

    private void showAsPopBottom(int height) {
        setPopupHeight(height);
        popupWindow.showAsDropDown(this, 0, 0);
    }

    private void setPopupHeight(int height) {
        popupWindow.setHeight(height);
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.width = getWidth();
        params.height = height;
        listView.setLayoutParams(params);
    }

    private int getRealHeight() {
        int willShowHeight = 0;
        if (listView.getCount() != 0) {
            View view = adapter.getView(0, null, null);
            view.measure(0, 0);
            willShowHeight = view.getMeasuredHeight() * listView.getCount();
        }
        if (spnMaxHeight > 0 && willShowHeight > spnMaxHeight) {
            willShowHeight = (int) spnMaxHeight;
        }

        Rect rect = new Rect();
        getGlobalVisibleRect(rect);

        showType = (rect.top <= willShowHeight || willShowHeight < spnMaxHeight) ? Type.TYPE_DOWN :
                Type.TYPE_UP;

        if (willShowHeight < spnMinHeight) {
            willShowHeight = (int) spnMinHeight;
        }
        return willShowHeight;
    }

    private void dismissPopupWindow() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return handleTouchDown(event);
            case MotionEvent.ACTION_UP:
                if (handleTouchUp(event)) {
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    private boolean handleTouchUp(MotionEvent event) {
        if (rightImage != null && rightImageListener != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();

            Rect rect = new Rect();
            getGlobalVisibleRect(rect);

            // 设置右侧图标可点击宽度
            float clickableWidth = rightImageWidth > imageClickableWidth ? rightImageWidth : imageClickableWidth;
            rect.left = rect.right - (int) clickableWidth;
            if (rect.contains(eventX, eventY)) {
                rightImageListener.onDrawableRightClick(this);
                return true;
            }
        }
        return false;
    }

    private boolean handleTouchDown(MotionEvent event) {
        if (rightImage != null && rightImageListener != null) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();

            Rect rect = new Rect();
            getGlobalVisibleRect(rect);

            // 设置右侧图标可点击宽度
            float clickableWidth = rightImageWidth > imageClickableWidth ? rightImageWidth : imageClickableWidth;
            rect.left = rect.right - (int) clickableWidth;
            if (rect.contains(eventX, eventY)) {
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTextContextMenuItem(int id) { //禁止输入粘贴版内容
        return id != android.R.id.paste && super.onTextContextMenuItem(id);
    }

    private void setRightCompoundDrawable() {
        if (rightImage != null) {
            rightImage.setBounds(0, 0, (int) rightImageWidth, (int) rightImageHeight);
            setCompoundDrawables(null, null, rightImage, null);
        }
    }

    private DrawableRightListener defaultRightListener = new DrawableRightListener() {
        @Override
        public void onDrawableRightClick(View view) {
            if (popupWindow == null) {
                return;
            }
            if (isPopupWindowShowing) {
                dismissPopupWindow();
                isPopupWindowShowing = false;
            } else {
                requestFocus();
                delayShowPopupWindow();
            }
        }
    };

    private ActionMode.Callback defaultCustomSelectionAction = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            // do nothing
        }
    };

    public void setStatus(int status) {
        setBackgroundResource(status == Status.STATUS_NORMAL ? R.drawable.spn_edt_comm_white_shape :
                R.drawable.spn_edt_comm_red_shape);
    }

    private final class Status {
        static final int STATUS_NORMAL = 0;    //默认显示状态
        static final int STATUS_EXCEPTION = 1; //此状态下编辑框显示为红色
    }

    private final class Type {
        static final int TYPE_UP = 0;   //PopupWindow向上显示
        static final int TYPE_DOWN = 1; //PopupWindow向下显示
    }
}
