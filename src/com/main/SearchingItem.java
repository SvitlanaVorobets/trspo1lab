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
        List<Integer> sortedSubarray = QuickSort.getSortedSubarray(nums, 0, nums.size() - 1);
        return sortedSubarray.get(target - 1);
    }
}
