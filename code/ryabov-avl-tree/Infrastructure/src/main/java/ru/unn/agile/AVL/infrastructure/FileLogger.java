package ru.unn.agile.AVL.infrastructure;

import ru.unn.agile.AVL.viewmodel.ILogger;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FileLogger implements ILogger {
    private static final String DATE_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss'Z' ";

    private List<String> logList = new ArrayList<>();
    private BufferedWriter writer;
    private FileWriter internalWriter;

    public FileLogger(final String path) throws IOException {
        internalWriter = new FileWriter(path);
        writer = new BufferedWriter(internalWriter);
    }

    public FileWriter getInternalWriter() {
        return internalWriter;
    }

    @Override
    public void log(final String str) {
        try {
            logList.add(str);
            writer.append(getCurrentTimestamp() + str);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getCurrentTimestamp() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_STRING, Locale.US);
        return dateFormat.format(calendar.getTime());
    }

    @Override
    public List<String> getLog() {
        return logList;
    }

    @Override
    public void close() throws IOException {
        logList.clear();
        writer.close();
    }
}
