package com.dsa.arrays;

public class RemoveElement {
    public static void main(String[] args) {

        int[] nums = {3,2,2,3};
        int val = 3;
        System.out.println(removeElement(nums, val));


        int [] nums2 = {0,1,2,2,3,0,4,2};
        int val2 = 2;
        System.out.println(removeElement(nums2, val2));

        int [] nums3 = {3,1,3,3,3};
        int val3 = 3;
        System.out.println(removeElement(nums3, val3));
    }

    public static int solutionRemoveElement(int [] nums, int val){
        int left = 0;
        int right = nums.length;
        while (left < right) {
            if (nums[left] == val) {
                nums[left] = nums[right - 1];
                right--;
            } else {
                left++;
            }
        }
        return right;
    }

    public static  int removeElement(int [] nums, int val){
        int targetCount= 0;
        for (int num : nums) {
            if (num != val) {
                targetCount++;
            }
        }
        int [] expectedArr = new int[targetCount];
        int left = 0;
        int right= nums.length -1;
        while (left < right){
            if (nums[right] == val){
                right--;
            }
            if (nums[left] == val){
                if (nums[right] != val){
                    nums[left] = nums[right];
                    right--;
                }
            }
            left++;
        }
        return targetCount;
    }

    public static int removeElement4(int [] nums, int val){
        int targetCount= 0;
        for (int num : nums) {
            if (num != val) {
                targetCount++;
            }
        }
        int [] expectedArr = new int[targetCount];
        int left = 0;
        int right = nums.length - 1;
        while (left < right){
            if (nums[left] == val){
                if (nums[right] != val){
                    nums[left] = nums[right];
                    right--;
                }
            }
            left++;
        }
        return targetCount;
    }

    public static int removeElement3(int [] nums, int val){
        int left = 0;
        int right = nums.length - 1;
        int targetCount= 0;
        for (int num : nums) {
            if (num != val) {
                targetCount++;
            }
        }
        int [] expectedArr = new int[targetCount];
        System.out.println("targget : "+expectedArr.length);

        int whileIndex = 0;
        while(left < right){
            if (nums[right] == val){
                right--;
            }else {
                expectedArr[whileIndex] = nums[left];
            }

            if (nums[left] != val){
                expectedArr[whileIndex] = nums[left];
            }
            left++;
        }
        return expectedArr.length;
    }



    public static int removeElement2(int[] nums, int val) {

        int left = 0;
        int right = nums.length - 1;
        int [] newNums = new int[nums.length];
        while(left < right){
            if (nums[left] == val){
                nums[left] = nums[right];
                newNums[left] = nums[left];
                left++;
            }else{
                newNums[left] = nums[left];
                left++;
            }
        }

        return newNums.length;
    }
}
