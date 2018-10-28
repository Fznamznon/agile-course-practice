package ru.unn.agile.mathstatistics.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MathStatisticsTest {

    @Test
    public void canCalculateMeanWithOneNumbers() {
        Integer[] data = {10};

        Double mean = MathStatistics.meanCalculate(data);

        assertEquals(new Double(10), mean);
    }

    @Test
    public void canCalculateMeanWithOneNegativeInteger() {
        Integer[] data = {-23};

        Double mean = MathStatistics.meanCalculate(data);

        assertEquals(new Double(-23), mean);
    }

    @Test
    public void canCalculateMeanPositiveIntegers() {
        Integer[] data = {10, 5, 25, 30};

        Double mean = MathStatistics.meanCalculate(data);

        assertEquals(new Double(17.5), mean);
    }

    @Test
    public void canCalculateMeanWithDouble() {
        Double[] data = {1.2, 3.3, 0.2, 8.1, 0.9};

        Double mean = MathStatistics.meanCalculate(data);

        assertEquals(new Double(2.74), mean);
    }

    @Test
    public void canCalculateMeanWithIntegersAndDouble() {
        Number[] data = {1.2, 3.3, 11, 2, 8.9, 2, 36, 7.3};

        Double mean = MathStatistics.meanCalculate(data);

        assertEquals(new Double(8.9625), mean);
    }

    @Test
    public void canCalculateMeanWithNegativeNumbers() {
        Number[] data = {-3, 8.3, 86.2, -3.5};

        Double mean = MathStatistics.meanCalculate(data);

        assertEquals(new Double(22.0), mean);
    }

    @Test
    public void canCalculateNegativeMean() {
        Double[] data = {-10.2, 4.3, 0.0, -31.9, 3.0, 0.33};

        Double mean = MathStatistics.meanCalculate(data);

        assertEquals(new Double(-5.745), mean);
    }

    @Test
    public void canCalculateMeanWithBigSizeOfDataArray() {
        Integer[] data = getMonotonicArray(0, 1000);
        Double expected = new Double((0 + 999) / 2.0);

        Double mean = MathStatistics.meanCalculate(data);

        assertEquals(expected, mean);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsWhenCalculateMeanWithNullElement() {
        Number[] data = {1.2, 2, -5, null, 8, 7};

        Double mean = MathStatistics.meanCalculate(data);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsWhenCalculateMeanWithNullData() {
        Double[] data = null;

        Double mean = MathStatistics.meanCalculate(data);
    }

    @Test
    public void canCalculateDispersionWithOneNumber() {
        Integer[] data = {8};

        Double dispersion = MathStatistics.dispersionCalculate(data);

        assertEquals(new Double(0), dispersion);
    }

    @Test
    public void canCalculateDispersionWithOneNegativeNumber() {
        Integer[] data = {-23};

        Double dispersion = MathStatistics.dispersionCalculate(data);

        assertEquals(new Double(0), dispersion);
    }

    @Test
    public void canCalculateDispersionWithNegativeNumbers() {
        Number[] data = {9, 3, -1, 9, -8};

        Double dispersion = MathStatistics.dispersionCalculate(data);

        assertEquals(new Double(41.44), dispersion);
    }

    @Test
    public void canCalculateDispersionWithArrayOfZeros() {
        Number[] data = {0, 0, 0, 0};

        Double dispersion = MathStatistics.dispersionCalculate(data);

        assertEquals(new Double(0.0), dispersion);
    }

    @Test
    public void canCalculateDispersionWithDouble() {
        Number[] data = {7.2, 64.3, -13.8, 0.6, 23.1};

        Double dispersion = MathStatistics.dispersionCalculate(data);

        assertEquals(new Double(717.1096), dispersion);
    }

    @Test
    public void canCalculateDispersionWithBigSizeOfDataArray() {
        Integer[] data = getMonotonicArray(0, 1000);

        Double dispersion = MathStatistics.dispersionCalculate(data);

        assertEquals(new Double(83333.25), dispersion);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsWhenCalculateDispersionWithNullElement() {
        Number[] data = {2, -3, 5, null, 1, 8};

        Double dispersion = MathStatistics.dispersionCalculate(data);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsWhenCalculateDispersionWithNullData() {
        Double[] data = null;

        Double dispersion = MathStatistics.dispersionCalculate(data);
    }


    private Integer[] getMonotonicArray(final int start, final int end) {
        Integer[] data = new Integer[end];
        for (int i = start; i < data.length; ++i) {
            data[i] = i;
        }
        return data;
    }

}
