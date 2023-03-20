package com.main;

import java.util.List;

public class SearchingItem {
    private final List<Integer> nums;
    private final int target;


    public SearchingItem(List<Integer> nums, int target){
        this.nums = nums;
        this.target = target;
    }

    public int searchingNumber(){
        int[] sortedSubarray = QuickSort.getSortedSubarray(toIntArray(nums), 0, nums.size() - 1);
        return sortedSubarray[target - 1];
    }

    int[] toIntArray(List<Integer> list)  {
        int[] ret = new int[list.size()];
        int i = 0;
        for (Integer e : list)
            ret[i++] = e;
        return ret;
    }
}
