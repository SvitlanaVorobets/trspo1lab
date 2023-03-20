package com.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class WorkWithFile {
    Scanner input = new Scanner(System.in);

    public List<Integer> createList(){
        List<Integer> list = List.of();
        System.out.println("Create new data(1) or read from file(2)?");
        switch (input.nextInt()) {
            case 1 -> {
                System.out.println("Set default options(1) or new(2)?");
                int listLimit = 1000000;
                int start = 1;
                int end = 2001000;
                if (input.nextInt() == 2) {
                    System.out.println("Write limit: ");
                    listLimit = input.nextInt();
                    System.out.println("Write start: ");
                    start = input.nextInt();
                    System.out.println("Write end: ");
                    end = input.nextInt();
                }
                list = ThreadLocalRandom.current()
                        .ints(start, end)
                        .boxed()
                        .distinct()
                        .limit(listLimit)
                        .collect(Collectors.toList());
                input.nextLine();
            }
            case 2 -> {
                System.out.println("Write the name of file: ");
                input.nextLine();
                String fileName = input.nextLine();
                File myObj = new File(fileName);
                Scanner myReader;
                try {
                    myReader = new Scanner(myObj);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                String res = myReader.nextLine();
                list = transform(res);
                myReader.close();
            }
            default -> System.out.println("Sth is wrong");
        }
        return list;
    }

    public void saveData(List<Integer> list){
        System.out.println("Save data to file? Type y for yes, and n for no");
        switch (input.nextLine()){
            case "y":
                saveInFile(String.valueOf(list));
                break;
            case "n":
                break;
            default:
                System.out.println("Sth is wrong");
        }
    }

    public void saveAnswer(int numberOfThreads, List<Integer> list, int target){
        int res;
        long start, end;
        SearchingItem item = new SearchingItem(list, target);
        System.out.println("Simple");

        start = System.currentTimeMillis();
        res = item.searchingNumber();
        end = System.currentTimeMillis();
        System.out.println(res + " is at position " + target);
        outputTime(start, end);

        System.out.println("\nMultithreading");
        start = System.currentTimeMillis();
        findResultByMultithreading(numberOfThreads, list, target);
        end = System.currentTimeMillis();
        outputTime(start, end);

        System.out.println("Write answer to file? Type y for yes, and n for no");
        switch (input.nextLine()) {
            case "y" -> saveInFile(item.searchingNumber() + " is at position " + target);
            case "n" -> {
            }
            default -> System.out.println("Sth is wrong");
        }
    }

    public void saveInFile(String value){
        System.out.println("Write the name of file: ");
        String fileName = input.nextLine();
        File myObj = new File(fileName);
        FileWriter myWriter;
        try {
            myWriter = new FileWriter(fileName);
            myWriter.write(value);
            myWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Integer> transform(String res){
        String[] crap = {"[", "]", ","};
        List<Integer> tempList = new ArrayList<>();
        for (String replace : crap) {
            res = res.replace(replace, "").trim();
        }
        String[] values = res.split(" ");
        for (String value : values) {
            tempList.add(Integer.parseInt(value.trim()));
        }
        return tempList;
    }

    public void findResultByMultithreading(int numberOfThreads, List<Integer> list, int target){
//        int[][] subarrays = new int[numberOfThreads][];
//        int subarraySize = list.size() / numberOfThreads;
//        int remaining = list.size() % numberOfThreads;
//        int index = 0;
//        for (int i = 0; i < numberOfThreads; i++) {
//            int size = subarraySize;
//            if (i < remaining) {
//                size++;
//            }
//            subarrays[i] = new int[size];
//            for (int j = 0; j < size; j++) {
//                subarrays[i][j] = list.get(index++);
//            }
//        }

        int[][] subarrays = new int[numberOfThreads][];
        int subarraySize = list.size() / numberOfThreads;
        for (int i = 0; i < numberOfThreads; i++) {
            int startIndex = i * subarraySize;
            int endIndex = (i == numberOfThreads - 1) ? list.size() : (i + 1) * subarraySize;
            subarrays[i] = Arrays.copyOfRange(toIntArray(list), startIndex, endIndex);
        }

        Thread[] searcher = new Thread[numberOfThreads];
        int[] kthValues = new int[numberOfThreads];
        int res = 0;
        for (int i = 0; i <= numberOfThreads - 1; i++) {
            SearchingItemThread searchingItemThread;
            searchingItemThread = new SearchingItemThread(subarrays[i], target, kthValues, i);
            searcher[i] = new Thread(searchingItemThread);

            searcher[i].start();
        }
        for (int i = 0; i < numberOfThreads; i++) {
            try {
                searcher[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Arrays.toString(kthValues));
//        int[] sortedSubarray = QuickSort.getSortedSubarray(kthValues, 0, kthValues.length - 1);

        System.out.println(res + " is at position " + target);
    }

    public void outputTime(long start, long end){
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.print("Execution time is " + formatter.format((end - start) / 1000d) + " seconds\n");
    }

    int[] toIntArray(List<Integer> list)  {
        int[] ret = new int[list.size()];
        int i = 0;
        for (Integer e : list)
            ret[i++] = e;
        return ret;
    }
}
