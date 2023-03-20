package com.main;

import java.util.*;
import java.util.stream.Stream;

public class SearchingItemThread implements Runnable{
    private int[] subarray;
    private int k;
    private int[] kthValues;
    private int threadIndex;

    public SearchingItemThread(int[] subarray, int k, int[] kthValues, int threadIndex){
        this.subarray = subarray;
        this.k = k;
        this.kthValues = kthValues;
        this.threadIndex = threadIndex;
    }

    @Override
    public void run() {
        int[] sortedSubarray = QuickSort.getSortedSubarray(subarray, 0, subarray.length - 1);
        kthValues[threadIndex] = sortedSubarray[k - 1];
    }
}
