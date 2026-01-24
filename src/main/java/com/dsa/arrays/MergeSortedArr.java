package com.dsa.arrays;

import java.util.Arrays;
import java.util.Collections;

public class MergeSortedArr {
    public static void main(String[] args) {
        int [] nums1 = {1,2,3,0,0,0};
        int [] nums2 = {2,5,6};
        merge(nums1, 3, nums2, 3);

    }

    public static void merge(int[] nums1, int m, int[] nums2, int n) {

        // m 2 , n 3
        int [] mergedArr = new int[m + n];

        for (int i = 0; i < m; i++){
            mergedArr[i] = nums1[i];
        }

        for (int i = 0; i < n; i++){
            mergedArr[m + i] = nums2[i];
        }
        Arrays.sort(mergedArr);





        for(int i = 0; i < mergedArr.length; i++){
            System.out.print(mergedArr[i] + " ");
        }

    }
}
