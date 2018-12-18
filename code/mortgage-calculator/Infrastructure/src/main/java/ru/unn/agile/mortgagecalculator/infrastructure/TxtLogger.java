package ru.unn.agile.mortgagecalculator.infrastructure;

import ru.unn.agile.mortgagecalculator.viewmodel.legacy.ILogger;

import java.io.*;
import java.util.Locale;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class TxtLogger implements ILogger {
    private final BufferedWriter writer;
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private String logFile;

    private static String getTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return dateFormat.format(cal.getTime());
    }

    @Override
    public void log(final String str) {
        if (writer == null) {
            return;
        }
        try {
            writer.write(getTime() + ": INFO " + str);
            writer.newLine();

            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TxtLogger(final String logFile) {
        this.logFile = logFile;
        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(logFile));
        } catch (Exception e) {
            e.printStackTrace();
        }

        writer = logWriter;
    }

    @Override
    public List<String> getLog() {
        ArrayList<String> logs = new ArrayList<>();
        if (writer == null) {
            return logs;
        }
        try {
            FileReader fileReader = new FileReader(logFile);

            BufferedReader reader = new BufferedReader(fileReader);

            String record = reader.readLine();

            while (record != null) {
                logs.add(record);
                record = reader.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return logs;
    }

}
