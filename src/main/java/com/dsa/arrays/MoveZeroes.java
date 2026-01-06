package com.dsa.arrays;

public class MoveZeroes {
    public static void main(String[] args) {
        int [] nums = {0,1,0,3,12};
        moveZeroes(nums);
    }
    public static void moveZeroes(int[] nums) {

        int nonZeroCount = 0;
        for (int i = 0; i < nums.length; i++){
            if (nums[i] != 0){
                int temp = nums[i];
                nums[i] = nums[nonZeroCount];
                nums[nonZeroCount] = temp;
                nonZeroCount++;
            }
        }

//        for (int i = 0; i < nums.length; i++){
//            if (nums[i] == 0){
//                left++;
//                zeroCount++;
//            }else{
//                nums[i-left] = nums[i];
//                nums[right] = 0;
//                right--;
//            }
//        }

//        while(left < right){
//            if(nums[left] == 0){
//
//            }
//        }


//        int left = 0;
//        int right = nums.length - 1;
//        while(left < right){
//            if(nums[left] == 0){
//                if(nums[right] != 0){
//                    nums[right] = 0;
//                    nums[left] = nums[right];
//                    right--;
//                }
//            }
//
//            left++;
//        }
//
        for(int i = 0; i < nums.length; i++){
            System.out.print(nums[i] + " ");
        }

    }

}
