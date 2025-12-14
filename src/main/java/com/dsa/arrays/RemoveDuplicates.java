package com.dsa.arrays;

import java.util.*;

public class RemoveDuplicates {
    public static void main(String[] args) {
        int numsArr [] = new int[] {1,1,2};
        int result = removeDuplicates(numsArr);
        System.out.println(result);

        int numsArr2 [] = new int[] {0,0,1,1,1,2,2,3,3,4};
        int result2 = removeDuplicates(numsArr2);
        System.out.println(result2);
    }

    public static int removeDuplicates2(int[] nums) {
        Set<Integer> s = new HashSet<>();
        int idx = 0;
        for (int i = 0; i < nums.length; i++) {
            if (!s.contains(nums[i])) {
                s.add(nums[i]);
                nums[idx++] = nums[i];
            }
        }
        return idx;
    }

    public static int removeDuplicates(int[] nums) {
        Arrays.sort(nums);
        Set<Integer> hashSet = new HashSet<>();

        for (int i = 0; i < nums.length; i++){
            hashSet.add(nums[i]);
        }

        System.out.println(hashSet);

        int [] solution= new int[hashSet.size()];
        //solution =

        int i= 0;
        Iterator iterator = hashSet.iterator();
        while(iterator.hasNext()){
         solution[i] = (int) iterator.next();
         i++;
        }

        return solution.length;

    }
}
