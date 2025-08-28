package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Assignment8Runner {
    public static void main(String[] args) {
        Assignment8 assignment = new Assignment8();

        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        List<Future<List<Integer>>> futures = new ArrayList<>();

        for(int i = 0; i < 1000; i++) {
            futures.add(executor.submit(assignment::getNumbers));
        }

        executor.shutdown();

        List<Integer> allNumbers = new ArrayList<>();

        for (Future<List<Integer>> future : futures) {
            try{
                allNumbers.addAll(future.get());
            } catch (InterruptedException e) {
                System.out.println("Thread was interrupted: " + e.getMessage());
                Thread.currentThread().interrupt();
            } catch (Exception e) {
                System.out.println("Error while fetching numbers: " + e.getMessage());
            }
        }

        System.out.println("Total numbers fetched: " + allNumbers.size());

        Map<Integer, Integer> frequencyMap = new TreeMap<>();
        for (int i = 1; i <= 10; i++) {
            frequencyMap.put(i,0);
        }

        for(int num : allNumbers) {
            frequencyMap.put(num, frequencyMap.get(num) + 1);
        }

        StringBuilder output = new StringBuilder();
        frequencyMap.forEach((key, value) ->
                output.append(key).append("=").append(value).append(", "));

        if(output.length() > 0) {
            output.setLength(output.length() - 2);
        }

        System.out.println(output);

    }
}
