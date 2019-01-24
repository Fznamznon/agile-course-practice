package ru.unn.agile.AVL.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class ViewModelLogTests {
    private ViewModel vm;
    private ILogger logger;

    protected void setLogger(final ILogger logger) {
        this.logger = logger;
        this.vm.setLogger(logger);
    }

    @Before
    public void setUp() throws IOException {
        vm = new ViewModel();
        logger = new FakeLogger();
        setLogger(logger);
    }

    @After
    public void tearDown() throws IOException {
        logger.close();
        vm = null;
    }

    @Test
    public void canLogKeySet() {
        vm.keyProperty().set("abc");

        assertTrue(vm.logListProperty().toString()
                .contains(LogStrings.KEY_SET.toString()));
    }

    @Test
    public void canLogValueSet() {
        vm.valueProperty().set("123");

        assertTrue(vm.logListProperty().toString()
                .contains(LogStrings.VALUE_SET.toString()));
    }

    @Test
    public void canLogInsertAction() {
        vm.keyProperty().set("abc");
        vm.valueProperty().set("123");
        vm.doAction();

        assertTrue(vm.logListProperty().toString()
                .contains(LogStrings.INSERT_ACTION_FIRED.toString()));
    }

    @Test
    public void canLogOperationChange() {
        vm.operationProperty().set(Operation.SEARCH);

        assertTrue(vm.logListProperty().toString()
                .contains(LogStrings.OPERATION_CHANGED.toString()));
    }

    @Test
    public void canLogSearchOperation() {
        vm.operationProperty().set(Operation.SEARCH);
        vm.keyProperty().set("abc");
        vm.doAction();

        assertTrue(vm.logListProperty().toString()
                .contains(LogStrings.SEARCH_ACTION_FIRED.toString()));
    }
}
