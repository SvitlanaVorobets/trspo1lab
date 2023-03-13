package com.main;

import java.util.List;

public class SearchingItemThread implements Runnable{
    private final List<Integer> nums;
    private final int target;

    private int startIndex;

    private int endIndex;

    public SearchingItemThread(int target, int startIndex, int endIndex, List<Integer> nums){
        this.target = target;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.nums = nums;
    }

    @Override
    public void run() {
        boolean flag = false;
        for(int i = startIndex; i < endIndex; i++){
            if(nums.get(i) == target) {
                System.out.println(nums.get(i) + " found at position " + i);
                flag = true;
            }
        }
        if(!flag) System.out.println("Not found");
    }
}
