package com.dsa.arrays;

import java.util.Arrays;
import java.util.HashMap;

public class MajorElement {
    public static void main(String[] args) {

//        int numsArr [] = new int[] {2,2,1,1,1,2,2};
//        int result = majorityElement(numsArr);
//        System.out.println(result);


        int numsArr2[] = new int[]{3, 2, 3, 2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 2};
        int result2 = majorityElement(numsArr2);
        System.out.println(result2);


    }

    public static int majorityElement(int[] nums) {
        Arrays.sort(nums);

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                map.put(nums[i], map.get(nums[i]) + 1);
            } else {
                map.put(nums[i], 1);
            }
        }

        for (int i : map.keySet()){
            if (map.get(i) > nums.length / 2){
                return i;
            }
        }
//        for (Integer i : map.keySet()) {
//            System.out.println(i + " value : " + map.get(i));
//        }


        return 0;
    }
}
