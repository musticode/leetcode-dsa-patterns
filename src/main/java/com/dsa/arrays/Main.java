package com.dsa.arrays;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
//        Input: nums = [5,3,6,1,12], original = 3
//        Output: 24

        int numsArr [] = new int[] {5, 3, 6, 1, 12};
        int original = 3;
        int result = findFinalValue(numsArr, original);
        System.out.println(result);

        int numsArr2 [] = new int[] {2,7,9};
        int original2 = 4;
        int result2 = findFinalValue(numsArr2, original2);
        System.out.println(result2);


        int numsArr3 [] = new int[] {8,19,4,2,15,3};
        int original3 = 2;
        int result3 = findFinalValue(numsArr3, original3);
        System.out.println(result3);
    }

    public static int findFinalValue(int[] nums, int original) {
        Arrays.sort(nums);

        for(int i = 0; i < nums.length; i++){
            if (nums[i] == original){
                original = original * 2;
                findFinalValue(nums, original);
            }
        }

        return original;
    }
}