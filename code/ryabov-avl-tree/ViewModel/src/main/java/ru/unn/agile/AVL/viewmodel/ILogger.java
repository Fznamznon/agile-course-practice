package ru.unn.agile.AVL.viewmodel;

import java.io.IOException;
import java.util.List;

public interface ILogger {
    void log(String str);

    List<String> getLog();

    void close() throws IOException;
}
