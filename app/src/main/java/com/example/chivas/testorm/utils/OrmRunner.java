package com.example.chivas.testorm.utils;

import android.annotation.SuppressLint;
import android.os.Environment;

import com.example.chivas.dbres.utils.AppContext;
import com.example.chivas.dbres.utils.LogUtils;
import com.example.chivas.testorm.bean.OpModelType;
import com.example.chivas.testorm.utils.orm.BaseOrm;

import java.io.File;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

public class OrmRunner {
    private static final String LOG_TITLE_START_FORMAT = "开始测试- 数据量: %d 个实例\n时间： %s\n";
    private static final String LOG_TITLE_END_FORMAT = "测试结束\n时间： %s\n";
    private static final String LOG_RUNNER_TITLE_FORMAT = "%s %s (%d/%d)\n------------------------------";

    public interface Callback {
        void log(String text, boolean error, BaseOrm orm);

        void done();
    }

    private final Callback mCallback;
    private final long mRuns;
    private final long mCount;

    private volatile boolean mRunning;
    private volatile boolean mDestroyed;
    private Subscription subscribe;

    public OrmRunner(Callback mCallback, long mRuns, long mCount) {
        this.mCallback = mCallback;
        this.mRuns = mRuns;
        this.mCount = mCount;
    }

    public void run(final OpModelType type, final List<BaseOrm> ormList) {
        if (mRunning) {
            LogUtils.e("OrmRunner run: Already running!");
            return;
        }

        mRunning = true;
        subscribe = Observable.create(new Observable.OnSubscribe<BaseOrm>() {
            @Override
            public void call(Subscriber<? super BaseOrm> subscriber) {
                for (BaseOrm orm : ormList) {
                    if (!mDestroyed) {
                        subscriber.onNext(orm);
                    }
                }
                subscriber.onCompleted();
            }
        }).observeOn(Schedulers.io()).observeOn(Schedulers.io())
                .subscribe(new Subscriber<BaseOrm>() {
                    @Override
                    public void onCompleted() {
                        mRunning = false;
                        mCallback.done();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(new Exception(e));
                    }

                    @Override
                    public void onNext(BaseOrm t) {
                        OrmRunner.this.run(type, t);
                    }
                });
    }

    @SuppressLint("DefaultLocale")
    public void run(OpModelType type, BaseOrm orm) {
        orm.setNumberEntities(mCount);
        Benchmark benchmark = createBenchmark(type, orm);
        orm.setBenchmark(benchmark);
        RuntimeException running = null;
        RuntimeException done = null;

        log(String.format(LOG_TITLE_START_FORMAT, mCount, DateUtils.getCurrentTime(System.currentTimeMillis())), orm);
        for (int i = 1; i <= mRuns; i++) {
            log(String.format(LOG_RUNNER_TITLE_FORMAT, orm.getName(), type, i, mRuns), orm);
            orm.setUp(this);

            try {
                orm.run(type);
            } catch (RuntimeException e) {
                running = e;
            }
            try {
                orm.gc();
            } catch (RuntimeException e) {
                done = e;
            }

            if (null != running || null != done) {
                logError("Aborted because of " + (running != null ?
                        running.getMessage() : done.getMessage()), orm);
                break;
            }
            benchmark.commit();
            if (mDestroyed) {
                break;
            }
        }
        log(String.format(LOG_TITLE_END_FORMAT, DateUtils.getCurrentTime(System.currentTimeMillis())), orm);
    }

    public void log(String text, BaseOrm orm) {
        log(text, false, orm);
    }

    public void logError(String text, BaseOrm orm) {
        log(text, true, orm);
    }

    public void log(String text, boolean error, BaseOrm orm) {
        mCallback.log(text, error, orm);
    }

    protected Benchmark createBenchmark(OpModelType type, BaseOrm orm) {
        String name = orm.getName() + "-" + type.type + "-" + mCount + ".tsv";
        File dir = Environment.getExternalStorageDirectory();
        File file = new File(dir, name);
        if (dir == null || !dir.canWrite()) {
            File appFile = new File(AppContext.getAppContext().getFilesDir(), name);
            LogUtils.i("Using file " + appFile.getAbsolutePath() + " because " + file.getAbsolutePath() +
                    " is not writable - please grant the storage permission to the app");
            file = appFile;
        }
        return new Benchmark(file);
    }
}
