package com.avasthi.android.apps.roadbuddy;

/**
 * Created by vavasthi on 15/3/15.
 */
public class StatisticsPack {

    public static double mean(double[] values) {

        int count = values.length;
        double sum = 0;
        for (int i = 0; i < values.length; ++i) {
            sum = sum + values[i];
        }
        return sum/values.length;
    }
    public static float mean(float[] values) {

        int count = values.length;
        float sum = 0;
        for (int i = 0; i < values.length; ++i) {
            sum = sum + values[i];
        }
        return sum/values.length;
    }
    private static float[] sort(float[] values) {
        for (int i = 0; i < values.length; ++i) {
            for (int j = i + 1; j < values.length; ++j) {
                if (values[i] > values[j]) {
                    float tmp = values[i];
                    values[i] = values[j];
                    values[j] = tmp;
                }
            }
        }
        return values;
    }
    private static double[] sort(double[] values) {
        for (int i = 0; i < values.length; ++i) {
            for (int j = i + 1; j < values.length; ++j) {
                if (values[i] > values[j]) {
                    double tmp = values[i];
                    values[i] = values[j];
                    values[j] = tmp;
                }
            }
        }
        return values;
    }
    public static double median(double[] values) {
        if (values.length % 2 == 0) {
            int middle =  values.length / 2;
            return (values[middle -1] + values[middle])/2;
        }
        else {
            return values[(values.length + 1)/2];
        }
    }
    public static float median(float[] values) {
        if (values.length % 2 == 0) {
            int middle =  values.length / 2;
            return (values[middle -1] + values[middle])/2;
        }
        else {
            return values[(values.length + 1)/2];
        }
    }
    public static double stddev(double[] values) {
        double mean = mean(values);
        double sum = 0;
        for (int i = 0; i < values.length; ++i) {
            sum += ((values[i] - sum) * (values[i] - sum));
        }
        return Math.sqrt(sum/values.length);
    }
    public static float stddev(float[] values) {
        float mean = mean(values);
        float sum = 0;
        for (int i = 0; i < values.length; ++i) {
            sum += ((values[i] - sum) * (values[i] - sum));
        }
        return (float)Math.sqrt((double)(sum/values.length));
    }
}
