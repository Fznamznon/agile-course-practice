package ru.unn.agile.AVL.infrastructure;

import org.junit.Before;
import ru.unn.agile.AVL.viewmodel.ViewModelLogTests;

import java.io.IOException;

public class ViewModelFileLoggerTests extends ViewModelLogTests {
    private static final String LOG_NAME = "ryabov-avl-tree-ViewModelFileLoggerTests.log";

    @Before
    @Override
    public void setUp() throws IOException {
        super.setUp();
        setLogger(new FileLogger(LOG_NAME));
    }

}
