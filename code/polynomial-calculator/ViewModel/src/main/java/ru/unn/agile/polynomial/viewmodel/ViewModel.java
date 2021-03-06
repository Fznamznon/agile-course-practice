package ru.unn.agile.polynomial.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.unn.agile.polynomial.model.Polynomial;
import java.util.List;

public class ViewModel {
    public static final String OPERATION_PASSED =
            "Operation: %s %s = %s";

    public static final String PARSE_PASSED =
            "Parse passed: %s";

    private ILogger logger = new FakeLogger();
    private StringProperty firstPolynomialStr = new SimpleStringProperty();
    private StringProperty secondPolynomialStr = new SimpleStringProperty();
    private StringProperty resultStr = new SimpleStringProperty();
    private final StringProperty log = new SimpleStringProperty();
    private Polynomial p1 = new Polynomial();
    private Polynomial p2 = new Polynomial();
    public StringProperty firstPolynomialStrProperty() {
        return firstPolynomialStr;
    }

    public String getFirstPolynomialStr() {
        return firstPolynomialStr.get();
    }

    public void setFirstPolynomialStr(final String firstPolynomialStr) {
        this.firstPolynomialStr.set(firstPolynomialStr);
    }

    public StringProperty secondPolynomialStrProperty() {
        return secondPolynomialStr;
    }

    public String getSecondPolynomialStr() {
        return secondPolynomialStr.get();
    }

    public void setSecondPolynomialStr(final String secondPolynomialStr) {
        this.secondPolynomialStr.set(secondPolynomialStr);
    }

    public StringProperty resultStrProperty() {
        return resultStr;
    }

    public String getResultStr() {
        return resultStr.get();
    }

    public void setResultStr(final String resultStr) {
        this.resultStr.set(resultStr);
    }

    public ViewModel() {
        initDefaultFields();
    }

    public ViewModel(final ILogger logger) {
        if (logger != null) {
            this.logger = logger;
            initDefaultFields();
        } else {
            throw new IllegalArgumentException("Log error: logger cannot be null");
        }
    }

    public Polynomial parsePolynomial(final String polynomialStrSource) {
        PolynomialParser parser = new PolynomialParser(polynomialStrSource);
        Polynomial p = parser.parsePolynomial();
        addLog(String.format(PARSE_PASSED, p.toString()));
        return p;
    }


    public boolean parseInput() {
        try {
            p1 = parsePolynomial(getFirstPolynomialStr());
        } catch (ViewModelException exc) {
            setResultStr(PolynomialParser.FORMAT_ERROR + "1");
            return false;
        }
        try {
            p2 = parsePolynomial(getSecondPolynomialStr());
        } catch (ViewModelException exc) {
            setResultStr(PolynomialParser.FORMAT_ERROR + "2");
            return false;
        }
        return true;
    }

    public void add() {
        if (parseInput()) {
            setResultStr(p1.add(p2).toString());
            addLog(String.format(OPERATION_PASSED, p1, p2, getResultStr()));
        } else {
            return;
        }
    }

    public void subtract() {
        if (parseInput()) {
            setResultStr(p1.subtract(p2).toString());
            addLog(String.format(OPERATION_PASSED, p1, p2, getResultStr()));
        } else {
            return;
        }
    }

    public void multiply() {
        if (parseInput()) {
            setResultStr(p1.multiply(p2).toString());
            addLog(String.format(OPERATION_PASSED, p1, p2, getResultStr()));
        } else {
            return;
        }
    }

    private void initDefaultFields() {
        firstPolynomialStr.set("");
        secondPolynomialStr.set("");
        resultStr.set("");
    }

    public List<String> getListLog() {
        return logger.getListLog();
    }

    private void addLog(final String message) {
        logger.log(message);
        StringBuilder logMsg = new StringBuilder();
        for (String line : logger.getListLog()) {
            logMsg.append(line).append("\n");
        }
        log.set(logMsg.toString());
    }

    public String getLog() {
        return log.get();
    }

    public final void setLogger(final ILogger logger) {
        if (logger != null) {
            this.logger = logger;
        } else {
            throw new IllegalArgumentException("Logger cannot be null");
        }
    }

    public StringProperty logProperty() {
        return log;
    }





}
