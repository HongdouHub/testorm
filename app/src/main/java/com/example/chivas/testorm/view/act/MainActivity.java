package com.example.chivas.testorm.view.act;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.chivas.dbres.db.greendao.utils.GreenDaoUtils;
import com.example.chivas.dbres.db.objectbox.utils.ObjectBoxUtils;
import com.example.chivas.dbres.db.realm.utils.RealmUtils;
import com.example.chivas.dbres.db.room.utils.RoomUtils;
import com.example.chivas.dbres.utils.LogUtils;
import com.example.chivas.testorm.R;
import com.example.chivas.testorm.adapter.ViewPagerAdapter;
import com.example.chivas.testorm.bean.OpModelType;
import com.example.chivas.testorm.utils.ContractListener;
import com.example.chivas.testorm.utils.OrmRunner;
import com.example.chivas.testorm.utils.orm.BaseOrm;
import com.example.chivas.testorm.utils.orm.GreenDaoOrm;
import com.example.chivas.testorm.utils.orm.ObjectBoxOrm;
import com.example.chivas.testorm.utils.orm.RealmOrm;
import com.example.chivas.testorm.utils.orm.RoomOrm;
import com.example.chivas.testorm.view.base.BaseActivity;
import com.example.chivas.testorm.view.widgets.SameClickSpinner;
import com.example.chivas.testorm.view.widgets.SpinnerEditText;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity implements
        ViewPager.OnPageChangeListener, OrmRunner.Callback {

    private static final String TAG = "MainActivity";
    private static final int VIEW_PAGER_STATE_FINISHED = 2;

    CheckBox cbGreenDao;
    CheckBox cbObjectBox;
    CheckBox cbRealm;
    CheckBox cbRoom;
    SameClickSpinner spOpModel;
    SameClickSpinner spOpSpModel;
    EditText edtInputRuns;
    SpinnerEditText edtCount;
    Button btnExecute;

    TextView tvFraGreenDao;
    TextView tvFraObjectBox;
    TextView tvFraRealm;
    TextView tvFraRoom;
    ViewPager vgFragments;

    private long mExitTime;
    private final SparseArray<OpModelType[]> selectMap = new SparseArray<OpModelType[]>() {
        {
            put(0, OpModelType.CRUD);
            put(1, OpModelType.QUERY_STR);
            put(2, OpModelType.QUERY_INT);
            put(3, OpModelType.QUERY_PRIMARY_ID);
        }
    };
    private ViewPagerAdapter viewPagerAdapter;
    private OrmRunner mRunner;

    @Override
    protected void onPrepareView() {
        initView();
        String[] operateCounts = getResources().getStringArray(R.array.item_operate_count_list);
        edtCount.replaceList(Arrays.asList(operateCounts));

        String[] operateModels = getResources().getStringArray(R.array.item_operate_model_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, operateModels);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spOpModel.setAdapter(adapter);

        spOpModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                OpModelType[] type = selectMap.get(position);
                ArrayAdapter<OpModelType> adapter = new ArrayAdapter<OpModelType>(MainActivity.this, android.R.layout.simple_spinner_item, type);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spOpSpModel.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // do nothing
            }
        });
        initViewPager();
    }

    private void initView() {
        cbGreenDao = (CheckBox) findViewById(R.id.cb_GreenDao);
        cbObjectBox = (CheckBox) findViewById(R.id.cb_ObjectBox);
        cbRealm = (CheckBox) findViewById(R.id.cb_Realm);
        cbRoom = (CheckBox) findViewById(R.id.cb_Room);
        spOpModel = (SameClickSpinner) findViewById(R.id.sp_op_model);
        spOpSpModel = (SameClickSpinner) findViewById(R.id.sp_op_sp_model);
        edtInputRuns = (EditText) findViewById(R.id.edt_input_runs);
        edtCount = (SpinnerEditText) findViewById(R.id.edt_count);
        btnExecute = (Button) findViewById(R.id.btn_execute);
        tvFraGreenDao = (TextView) findViewById(R.id.tv_fra_greenDao);
        tvFraObjectBox = (TextView) findViewById(R.id.tv_fra_objectBox);
        tvFraRealm = (TextView) findViewById(R.id.tv_fra_realm);
        tvFraRoom = (TextView) findViewById(R.id.tv_fra_room);
        vgFragments = (ViewPager) findViewById(R.id.vg_fragments);

        btnExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickExecute(v);
            }
        });

        tvFraGreenDao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTitle(v);
            }
        });
        tvFraObjectBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTitle(v);
            }
        });
        tvFraRealm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTitle(v);
            }
        });
        tvFraRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickTitle(v);
            }
        });
    }

    private void initViewPager() {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        vgFragments.setAdapter(viewPagerAdapter);
        vgFragments.addOnPageChangeListener(this);
    }

    @Override
    protected int onGetContentView() {
        return R.layout.activity_main;
    }

    public void onClickExecute(View view) {
        btnExecute.setEnabled(false);
        clearFocus();
        clearLogs();

        boolean greenDao = cbGreenDao.isChecked();
        boolean objectBox = cbObjectBox.isChecked();
        boolean realm = cbRealm.isChecked();
        boolean room = cbRoom.isChecked();
        OpModelType type = (OpModelType) spOpSpModel.getSelectedItem();
        long runs = 0;
        long count = 0;
        try {
            runs = Long.parseLong(edtInputRuns.getText().toString());
            count = Long.parseLong(edtCount.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            LogUtils.e(TAG, "onClickExecute exception: " + e);
            return;
        }
        run(type, runs, count, greenDao, objectBox, realm, room);
    }

    private void run(OpModelType type, long runs, long count,
                     boolean greenDao, boolean objectBox, boolean realm, boolean room) {
        List<BaseOrm> tests = new ArrayList<>();
        mRunner = new OrmRunner(this, runs, count);
        if (greenDao) {
            tests.add(new GreenDaoOrm());
        }
        if (objectBox) {
            tests.add(new ObjectBoxOrm());
        }
        if (realm) {
            tests.add(new RealmOrm());
        }
        if (room) {
            tests.add(new RoomOrm());
        }
        mRunner.run(type, tests);
    }

    private void clearLogs() {
        int count = viewPagerAdapter.getCount();
        for (int i = 0; i < count; i++) {
            Fragment fragment = viewPagerAdapter.getItem(i);
            if (null != fragment) {
                ((ContractListener) fragment).clearLog();
            }
        }
    }

    private void clearFocus() {
        View currentFocus = MainActivity.this.getCurrentFocus();
        if (currentFocus != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
            }
            currentFocus.clearFocus();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击返回的时候，如果菜单处于打开状态，则先关闭菜单
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            final long currentTime = System.currentTimeMillis();
            Log.i(TAG, "onKeyDown currentTime = " + currentTime + "#mExitTime=" + mExitTime);
            if ((currentTime - mExitTime) < 2000) {
                appExit();
            } else {
                Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
                mExitTime = currentTime;
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void appExit() {
        closeAllDataBase();
        finish();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // do nothing
    }

    @Override
    public void onPageSelected(int position) {
        // do nothing
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == VIEW_PAGER_STATE_FINISHED) {
            setTabIndex(vgFragments.getCurrentItem());
        }
    }

    /**
     * 根据现有Fragment设置Tab标题状态
     * @param currentItem 当前Fragment的位置
     */
    private void setTabIndex(int currentItem) {
        Resources resources = getResources();
        int activeColor = resources.getColor(R.color.Color_I);
        int inactiveColor = resources.getColor(R.color.Color_B);

        tvFraGreenDao.setTextColor(inactiveColor);
        tvFraObjectBox.setTextColor(inactiveColor);
        tvFraRealm.setTextColor(inactiveColor);
        tvFraRoom.setTextColor(inactiveColor);
        switch (currentItem) {
            case 0:
                tvFraGreenDao.setTextColor(activeColor);
                break;
            case 1:
                tvFraObjectBox.setTextColor(activeColor);
                break;
            case 2:
                tvFraRealm.setTextColor(activeColor);
                break;
            case 3:
                tvFraRoom.setTextColor(activeColor);
                break;
            default:
        }
    }

    public void onClickTitle(View view) {
        switch (view.getId()) {
            case R.id.tv_fra_greenDao:
                vgFragments.setCurrentItem(0);
                break;
            case R.id.tv_fra_objectBox:
                vgFragments.setCurrentItem(1);
                break;
            case R.id.tv_fra_realm:
                vgFragments.setCurrentItem(2);
                break;
            case R.id.tv_fra_room:
                vgFragments.setCurrentItem(3);
                break;
                default:
        }
    }

    @Override
    public void log(String text, boolean error, BaseOrm orm) {
        Fragment fragment = null;
        if (orm instanceof GreenDaoOrm) {
            fragment = viewPagerAdapter.getItem(0);
        } else if (orm instanceof ObjectBoxOrm) {
            fragment = viewPagerAdapter.getItem(1);
        } else if (orm instanceof RealmOrm) {
            fragment = viewPagerAdapter.getItem(2);
        } else {
            fragment = viewPagerAdapter.getItem(3);
        }

        if (fragment != null) {
            ((ContractListener) fragment).log(text, error);
        }
    }

    @Override
    public void done() {
        mRunner = null;
        closeAllDataBase();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                btnExecute.setEnabled(true);
            }
        });
    }

    private void closeAllDataBase() {
        GreenDaoUtils.closeAllDataBase();   // 关闭GreenDao数据库
        ObjectBoxUtils.closeAllDataBase();  // 关闭ObjectBox数据库
        RealmUtils.closeAllDataBase();      // 关闭Realm数据库
        RoomUtils.closeAllDataBase();       // 关闭Room数据库
    }
}
