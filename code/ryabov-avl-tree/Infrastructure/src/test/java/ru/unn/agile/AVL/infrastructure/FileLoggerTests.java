package ru.unn.agile.AVL.infrastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

import static org.junit.Assert.assertTrue;

public class FileLoggerTests {
    private static final String LOG_NAME = "ryabov-avl-tree-FileLoggerTests.log";
    public static final String TEST_STRING = "test";

    private FileLogger logger;

    @Before
    public void setUp() throws IOException {
        logger = new FileLogger(LOG_NAME);
    }

    @After
    public void tearDown() throws IOException {
        logger.close();
    }

    @Test
    public void canCreateFile() {
        assertTrue(Files.exists(FileSystems.getDefault().getPath(LOG_NAME)));
    }

    @Test
    public void canLogTestString() {
        logger.log(TEST_STRING);

        assertTrue(logger.getLog().contains(TEST_STRING));
    }

    @Test
    public void canLogTestStringToFile() throws IOException {
        logger.log(TEST_STRING);
        String data = new String(Files.readAllBytes(FileSystems.getDefault().getPath(LOG_NAME)));

        assertTrue(data.contains(TEST_STRING));
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void failsOnWriteToClosedWriter() throws IOException {
        logger.getInternalWriter().close();
        exception.expect(IOException.class);
        logger.log(TEST_STRING);
    }
}
