package com.main;

import java.util.*;
import java.util.stream.Stream;

public class SearchingItemThread implements Runnable{
    private int[] arr;
    private int left;
    private int right;
    private int k;
    private int median;

    public SearchingItemThread(int[] arr, int left, int right, int k){
        this.arr = arr;
        this.left = left;
        this.right = right;
        this.k = k;
    }

    @Override
    public void run() {
       median = select(0, arr.length - 1, k);
    }

    private int select(int left, int right, int k) {
        if (left == right) {
            return arr[left];
        }
        int pivotIndex = partition(left, right);
        if (k == pivotIndex) {
            return arr[k];
        } else if (k < pivotIndex) {
            return select(left, pivotIndex - 1, k);
        } else {
            return select(pivotIndex + 1, right, k);
        }
    }

    private int partition(int left, int right) {
        int pivotIndex = getPivot(left, right);
        int pivotValue = arr[pivotIndex];
        swap(pivotIndex, right);
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (arr[i] < pivotValue) {
                swap(i, storeIndex);
                storeIndex++;
            }
        }
        swap(storeIndex, right);
        return storeIndex;
    }

    private int getPivot(int left, int right) {
        return new Random().nextInt(right - left + 1) + left;
    }

    private void swap(int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public int getMedian() {
        return median;
    }
}
