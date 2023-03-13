package com.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
                int end = 1001000;
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
        String res = "";
        long start = 0, end = 0;
        System.out.println("How to find answer? 1 for simple, 2 for multithreading");
        switch (input.nextInt()) {
            case 1:
                start = System.currentTimeMillis();
                SearchingItem item = new SearchingItem(list, target);
                res = item.searchingNumber();
                end = System.currentTimeMillis();
                System.out.println(res);
                break;
            case 2:
                start = System.currentTimeMillis();
                findResultByMultithreading(numberOfThreads, list, target);
                end = System.currentTimeMillis();
                break;
            default:
                System.out.println("Sth is wrong");
        }
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.print("Execution time is " + formatter.format((end - start) / 1000d) + " seconds\n");

        System.out.println("Write answer to file? Type y for yes, and n for no");
        input.nextLine();
        switch (input.nextLine()) {
            case "y" -> saveInFile(res);
            case "n" -> {
                break;
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
        int sizeOfList = list.size();
        int range = sizeOfList/numberOfThreads;
        for (int i = 0; i <= numberOfThreads - 1; i++) {
            Thread searcher;
            if (i == numberOfThreads - 1) {
                searcher = new Thread(new SearchingItemThread(target, i * range, sizeOfList - 1, list));
            } else {
                searcher = new Thread(new SearchingItemThread(target, i * range, i * range + range - 1, list));
            }
            searcher.start();
        }
    }
}
