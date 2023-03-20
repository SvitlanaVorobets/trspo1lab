package com.main;

import java.util.*;

public class Main {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        System.out.println("Write target");
        int target = input.nextInt();
        int numberOfThreads = 3;
        WorkWithFile workWithFile = new WorkWithFile();
        List<Integer> list = workWithFile.createList();

        System.out.println("The list: ");
        System.out.println(list);
        workWithFile.saveData(list);

        workWithFile.saveAnswer(numberOfThreads, list, target);
    }
}
