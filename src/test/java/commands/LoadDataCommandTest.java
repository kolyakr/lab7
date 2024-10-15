package commands;

import models.TaxPayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class LoadDataCommandTest {
    private TaxPayer taxPayer;
    private LoadDataCommand loadDataCommand;

    @BeforeEach
    void setUp() {
        taxPayer = mock(TaxPayer.class);
        loadDataCommand = new LoadDataCommand(taxPayer);
    }

    @Test
    void testLoadDataValidInput() throws IOException {
        String input = "test_load.csv\n"; // Valid filename
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> loadDataCommand.execute());
        verify(taxPayer).loadFromFile("test_load.csv"); // Check load method called

        System.setIn(System.in); // Restore original input stream
    }

    @Test
    void testLoadDataIOException() throws IOException {
        doThrow(new IOException("File error")).when(taxPayer).loadFromFile(any()); // Mock exception
        String input = "test_load.csv\n"; // Valid filename
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> loadDataCommand.execute()); // Verify exception is handled gracefully

        System.setIn(System.in); // Restore original input stream
    }

    @Test
    void testLoadDataEmptyInput() throws IOException {
        String input = "\n"; // Empty input
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> loadDataCommand.execute());
        verify(taxPayer, never()).loadFromFile(any()); // Ensure loadFromFile is not called

        System.setIn(System.in); // Restore original input stream
    }

    @Test
    void testLoadDataInvalidFilename() throws IOException {
        String input = "invalid_file.txt\n"; // Invalid filename
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> loadDataCommand.execute());
        verify(taxPayer).loadFromFile("invalid_file.txt"); // Ensure loadFromFile is called with invalid filename

        System.setIn(System.in); // Restore original input stream
    }

    // Additional tests for better coverage
    @Test
    void testLoadDataSpecialCharactersInFilename() throws IOException {
        String input = "test_load_@#$.csv\n"; // Filename with special characters
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> loadDataCommand.execute());
        verify(taxPayer).loadFromFile("test_load_@#$.csv"); // Ensure loadFromFile is called with special characters

        System.setIn(System.in); // Restore original input stream
    }

    @Test
    void testLoadDataLongFilename() throws IOException {
        String input = "a_very_long_filename_that_exceeds_normal_length.csv\n"; // Very long filename
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> loadDataCommand.execute());
        verify(taxPayer).loadFromFile("a_very_long_filename_that_exceeds_normal_length.csv"); // Ensure loadFromFile is called

        System.setIn(System.in); // Restore original input stream
    }

    @Test
    void testLoadDataInvalidCharactersInFilename() throws IOException {
        String input = "inva|id*name.csv\n"; // Invalid characters in filename
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> loadDataCommand.execute());
        verify(taxPayer).loadFromFile("inva|id*name.csv"); // Ensure loadFromFile is called with invalid filename

        System.setIn(System.in); // Restore original input stream
    }

    @Test
    void testLoadDataMultipleFileLoadAttempts() throws IOException {
        String input = "test_load_1.csv\ntest_load_2.csv\n"; // Multiple valid filenames
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> loadDataCommand.execute());
        verify(taxPayer, times(2)).loadFromFile(anyString()); // Ensure loadFromFile is called for each attempt

        System.setIn(System.in); // Restore original input stream
    }

    // New tests for enhanced coverage
    @Test
    void testLoadDataWithMixedInputs() throws IOException {
        String input = "test_load.csv\ninvalid_file.txt\n\nvalid_file.csv\n"; // Mixed inputs
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> loadDataCommand.execute());
        verify(taxPayer).loadFromFile("test_load.csv"); // Ensure first valid filename is processed
        verify(taxPayer, times(0)).loadFromFile("invalid_file.txt"); // Invalid file should not be called
        verify(taxPayer, times(1)).loadFromFile("valid_file.csv"); // Ensure last valid filename is processed

        System.setIn(System.in); // Restore original input stream
    }

    @Test
    void testLoadDataWithSequentialValidAndInvalid() throws IOException {
        String input = "valid_file.csv\ninvalid_file.txt\nvalid_file_2.csv\n"; // Sequential valid and invalid filenames
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> loadDataCommand.execute());
        verify(taxPayer).loadFromFile("valid_file.csv"); // Check first valid filename
        verify(taxPayer, times(0)).loadFromFile("invalid_file.txt"); // Invalid should not be called
        verify(taxPayer).loadFromFile("valid_file_2.csv"); // Check second valid filename

        System.setIn(System.in); // Restore original input stream
    }

    @Test
    void testLoadDataWithNullFilename() throws IOException {
        String input = "null\n"; // Simulate loading a file named "null"
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        assertDoesNotThrow(() -> loadDataCommand.execute());
        verify(taxPayer).loadFromFile("null"); // Ensure loadFromFile is called with "null"

        System.setIn(System.in); // Restore original input stream
    }
}
