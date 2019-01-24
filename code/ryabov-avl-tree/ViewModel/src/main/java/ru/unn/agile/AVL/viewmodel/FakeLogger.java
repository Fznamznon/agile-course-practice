package ru.unn.agile.AVL.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private List<String> logList = new ArrayList<>();

    @Override
    public void log(final String str) {
        logList.add(str);
    }

    @Override
    public List<String> getLog() {
        return logList;
    }

    @Override
    public void close() {
        logList.clear();
    }
}
