package com.example.apgroupassignment;

import com.opencsv.CSVReader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticalAnalyticsViewController {

    @FXML
    private Label mean, median, mode, max, min, sd;

    @FXML
    public void initialize() {
        calculate();
    }

    private void calculate() {
        String csvPath = "src/main/resources/test_result.csv";

        try (CSVReader reader = new CSVReader(new FileReader(csvPath))) {
            List<Integer> scores = new ArrayList<>();

            List<String[]> records = reader.readAll();

            for (String[] record : records) {
                int score = Integer.parseInt(record[2]);
                scores.add(score);
            }

            // Mean
            double meanValue = calculateMean(scores);
            mean.setText(String.format("%.2f", meanValue));

            // Median
            double medianValue = calculateMedian(scores);
            median.setText(String.format("%.2f", medianValue));

            // Mode
            int modeValue = calculateMode(scores);
            mode.setText(Integer.toString(modeValue));

            // Max
            int maxValue = Collections.max(scores);
            max.setText(Integer.toString(maxValue));

            // Min
            int minValue = Collections.min(scores);
            min.setText(Integer.toString(minValue));

            // Standard Deviation
            double sdValue = calculateStandardDeviation(scores, meanValue);
            sd.setText(String.format("%.2f", sdValue));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double calculateMean(List<Integer> scores) {
        return scores.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    private double calculateMedian(List<Integer> scores) {
        List<Integer> sortedScores = scores.stream().sorted().collect(Collectors.toList());
        int size = sortedScores.size();
        if (size % 2 == 0) {
            int mid1 = sortedScores.get(size / 2 - 1);
            int mid2 = sortedScores.get(size / 2);
            return (double) (mid1 + mid2) / 2;
        } else {
            return sortedScores.get(size / 2);
        }
    }

    private int calculateMode(List<Integer> scores) {
        Map<Integer, Integer> frequencyMap = new HashMap<>();
        for (int score : scores) {
            frequencyMap.put(score, frequencyMap.getOrDefault(score, 0) + 1);
        }
        int modeValue = 0;
        int maxFrequency = 0;
        for (Map.Entry<Integer, Integer> entry : frequencyMap.entrySet()) {
            if (entry.getValue() > maxFrequency) {
                modeValue = entry.getKey();
                maxFrequency = entry.getValue();
            }
        }
        return modeValue;
    }

    private double calculateStandardDeviation(List<Integer> scores, double mean) {
        double sum = scores.stream().mapToDouble(score -> Math.pow(score - mean, 2)).sum();
        double variance = sum / scores.size();
        return Math.sqrt(variance);
    }
}

