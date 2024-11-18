package com.example;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.opencsv.exceptions.CsvValidationException;

/**
 * Unit test for simple App.
 */
public class AppTest {

    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    void testCalculateMeanUsingReflection() throws Exception {
        List<Double> data = Arrays.asList(10.0, 20.0, 30.0);
        double expectedMean = 20.0;

        Method method = App.class.getDeclaredMethod("calculateMean", List.class);
        method.setAccessible(true); // Making method accessible
        double actualMean = (double) method.invoke(null, data);

        assertEquals(expectedMean, actualMean, "Mean calculation is wrong");
    }

    @Test
    void testCalculateMeanWithEmptyList() throws Exception {
        List<Double> data = Arrays.asList();
        double expectedMean = 0.0;

        Method method = App.class.getDeclaredMethod("calculateMean", List.class);
        method.setAccessible(true);
        double actualMean = (double) method.invoke(null, data);

        assertEquals(expectedMean, actualMean, "Mean is wrong for empty list");
    }

    @Test
    void testCalculateMedianUsingReflectionOdd() throws Exception {
        List<Double> data = Arrays.asList(10.0, 20.0, 30.0);
        double expectedMedian = 20.0;

        Method method = App.class.getDeclaredMethod("calculateMedian", List.class);
        method.setAccessible(true);
        double actualMedian = (double) method.invoke(null, data);

        assertEquals(expectedMedian, actualMedian, "Median calculation is wrong for odd-size data");
    }

    @Test
    void testCalculateMedianUsingReflectionEven() throws Exception {
        List<Double> data = Arrays.asList(10.0, 20.0, 30.0, 40.0);
        double expectedMedian = 25.0;

        Method method = App.class.getDeclaredMethod("calculateMedian", List.class);
        method.setAccessible(true);
        double actualMedian = (double) method.invoke(null, data);

        assertEquals(expectedMedian, actualMedian, "Median calculation is wrong for even-size data");
    }

    @Test
    void testCalculateMedianWithEmptyList() throws Exception {
        List<Double> data = Arrays.asList();
        double expectedMedian = 0.0;

        Method method = App.class.getDeclaredMethod("calculateMedian", List.class);
        method.setAccessible(true);
        double actualMedian = (double) method.invoke(null, data);

        assertEquals(expectedMedian, actualMedian, "Median calculation is incorrect for empty list");
    }

    @Test
    void testCalculateModeUsingReflectionSingleMode() throws Exception {
        List<Double> data = Arrays.asList(10.0, 20.0, 20.0, 30.0);
        double expectedMode = 20.0;

        Method method = App.class.getDeclaredMethod("calculateMode", List.class);
        method.setAccessible(true);
        double actualMode = (double) method.invoke(null, data);

        assertEquals(expectedMode, actualMode, "Mode calculation is incorrect for single mode");
    }

    @Test
    void testCalculateModeWithNoMode() throws Exception {
        List<Double> data = Arrays.asList(10.0, 20.0, 30.0);
        double expectedMode = 10.0; // Fallback to first element

        Method method = App.class.getDeclaredMethod("calculateMode", List.class);
        method.setAccessible(true);
        double actualMode = (double) method.invoke(null, data);

        assertEquals(expectedMode, actualMode, "Mode calculation is incorrect when there is no mode");
    }

    @Test
    void testCalculateModeMultipleModes() throws Exception {
        List<Double> data = Arrays.asList(10.0, 20.0, 20.0, 10.0);
        double expectedMode = 10.0; // Any mode valid

        Method method = App.class.getDeclaredMethod("calculateMode", List.class);
        method.setAccessible(true);
        double actualMode = (double) method.invoke(null, data);

        assertEquals(expectedMode, actualMode, "Mode calculation is incorrect for multiple modes");
    }

    @Test
    void testCalculateModeWithEmptyList() throws Exception {
        List<Double> data = Arrays.asList();
        double expectedMode = 0.0;

        Method method = App.class.getDeclaredMethod("calculateMode", List.class);
        method.setAccessible(true);
        double actualMode = (double) method.invoke(null, data);

        assertEquals(expectedMode, actualMode, "Mode calculation is incorrect for empty list");
    }
    @Test
    void testInvalidMonthReturnsNoDataMessage() throws IOException {
        String input = "15\n2022\n";  // Month out of valid range
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            App.main(null); // Run the main method
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }

        // Verify the output message
        String expectedMessage = "No data found for the given month and year.";
        assertTrue(outContent.toString().contains(expectedMessage), "Expected message not found");
    }

    @Test
    void testInvalidYearReturnsNoDataMessage() throws IOException {
        String input = "5\n2100\n";  // Invalid year
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            App.main(null);  // Run the main method
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }

        // Verify the output message
        String expectedMessage = "No data found for the given month and year.";
        assertTrue(outContent.toString().contains(expectedMessage), "Expected message not found");
    }
    @Test
    void testInvalidMonthAndYearReturnsNoDataMessage() throws IOException {
        String input = "15\n2100\n";  // Invalid month and year
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            App.main(null);  // Run the main method
        } catch (CsvValidationException e) {
            e.printStackTrace();
        }

        // Verify the output message
        String expectedMessage = "No data found for the given month and year.";
        assertTrue(outContent.toString().contains(expectedMessage), "Expected message not found");
    }
    
}
