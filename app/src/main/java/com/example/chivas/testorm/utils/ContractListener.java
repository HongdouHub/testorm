package com.example.chivas.testorm.utils;

public interface ContractListener {

    void clearLog();

    void log(String text, boolean error);
}
