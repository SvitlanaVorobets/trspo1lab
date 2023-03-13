package com.main;

import java.util.List;

public class SearchingItem {
    private final List<Integer> nums;
    private final int target;

    public SearchingItem(List<Integer> nums, int target){
        this.nums = nums;
        this.target = target;
    }

    public String searchingNumber(){
        for(int i = 0; i < nums.size(); i++){
            if(nums.get(i) == target) return nums.get(i) + " found at position " + i;
        }
        return "Not found";
    }
}
