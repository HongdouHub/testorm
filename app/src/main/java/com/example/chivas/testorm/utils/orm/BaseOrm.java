package com.example.chivas.testorm.utils.orm;

import com.example.chivas.testorm.bean.OpModelType;
import com.example.chivas.testorm.utils.Benchmark;
import com.example.chivas.testorm.utils.OrmRunner;
import com.example.chivas.testorm.utils.random.RandomValuesManager;

public abstract class BaseOrm {
    
    protected OrmRunner mRunner;
    protected long mCount;
    protected Benchmark mBenchmark;
    
    public void setUp(OrmRunner runner) {
        this.mRunner = runner;
    }

    public void setNumberEntities(long numberEntities) {
        this.mCount = numberEntities;
    }

    public void setBenchmark(Benchmark benchmark) {
        this.mBenchmark = benchmark;
    }

    protected void startBenchmark(String name) {
        mBenchmark.start(name);
    }

    protected void stopBenchmark() {
        log(mBenchmark.stop());
    }

    public abstract String getName();

    public abstract void run(OpModelType type);

    public void gc() {

    }
    
    protected void log(String text) {
        mRunner.log(text, this);
    }

    public boolean randomBoolean() {
        return RandomValuesManager.getInstance().createRandomBoolean();
    }

    public byte randomByte() {
        return RandomValuesManager.getInstance().createRandomByte();
    }

    public short randomShort() {
        return RandomValuesManager.getInstance().createRandomShort();
    }

    public int randomInt() {
        return RandomValuesManager.getInstance().createRandomInt();
    }

    public int randomInt(int maxValue) {
        return RandomValuesManager.getInstance().createRandomInt(maxValue);
    }

    public long randomLong() {
        return RandomValuesManager.getInstance().createRandomLong();
    }

    public double randomDouble() {
        return RandomValuesManager.getInstance().createRandomDouble();
    }

    public float randomFloat() {
        return RandomValuesManager.getInstance().createRandomFloat();
    }

    public String randomString() {
        return RandomValuesManager.getInstance().createRandomString();
    }

    public byte[] randomByteArrays() {
        return RandomValuesManager.getInstance().createRandomByteArrays();
    }

    protected void assertEntityCount(long size) {
        if (size != mCount) {
            throw new IllegalStateException("Expected " + mCount + " but actual number is " + size);
        }
    }

    protected void assertGreaterOrEqualToNumberOfEntities(long count) {
        if (count < mCount) {
            throw new IllegalStateException("Expected at least " + mCount + " but actual number is " + count);
        }
    }
}
