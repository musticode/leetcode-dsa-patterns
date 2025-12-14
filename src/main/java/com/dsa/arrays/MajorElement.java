package com.dsa.arrays;

import java.util.Arrays;

public class MajorElement {
    public static void main(String[] args) {

        int numsArr [] = new int[] {2,2,1,1,1,2,2};
        int result = majorityElement(numsArr);
        System.out.println(result);


        int numsArr2 [] = new int[] {3,2,3};
        int result2 = majorityElement(numsArr2);
        System.out.println(result2);


    }

    public static int majorityElement(int[] nums) {
        Arrays.sort(nums);

        int major = 0;

        for (int i = 0; i < nums.length; i++) {

        }


        return 0;
    }
}
