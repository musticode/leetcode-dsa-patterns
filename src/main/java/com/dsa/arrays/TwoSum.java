package com.dsa.arrays;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TwoSum {
    public static void main(String[] args) {

        int arr [] = new int[] {2,7,11,15};
        int target = 9;
        int result [] = twoSum3(arr, target);
        System.out.println(result[0] + " " + result[1]);

        System.out.println("----------------");
        int arr2 [] = new int[] {9,3,2,4};
        int target2 = 5;
        int result2 [] = twoSum3(arr2, target2);
        System.out.println(result2[0] + " " + result2[1]);


        System.out.println("----------------");
        int arr3 [] = new int[] {3,3};
        int target3 = 6;
        int result3 [] = twoSum3(arr3, target3);
        System.out.println(result3[0] + " " + result3[1]);

        System.out.println("----------------");
        int arr4 [] = new int[] {0,4,3,0};
        int target4 = 0;
        int result4 [] = twoSum3(arr4, target4);
        System.out.println(result4[0] + " " + result4[1]);

        System.out.println("----------------");
        int arr5 [] = new int[] {-1,-2,-3,-4,-5};
        int target5 = -8;
        int result5 [] = twoSum3(arr5, target5);
        System.out.println(result5[0] + " " + result5[1]);

        System.out.println("----------------");
        int arr6 [] = new int[] {3,2,95,4,-3};
        int target6 = 92;
        int result6 [] = twoSum4(arr6, target6);
        System.out.println(result6[0] + " " + result6[1]);

    }

    public static int [] twoSum4(int [] nums, int target){


        // formula
        // num1 + num2 = target
        // target - num1 = num2
        // target - num2 = num1
        // 1 + 3 = target 4
        // 92 - 3 => 3 , 3
        //
        //


        int [] result = new int[2];
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++){
            int diff = target - nums[i];
            if (map.containsKey(diff)){
                int index = map.get(diff);
                result[0] = index;
                result[1] = i;
                break;
            }else{
                map.put(nums[i],i );
            }
        }
        return result;
    }

    public static int [] twoSum3(int [] nums, int target){
        int firstIndex = 0;
        int secondIndex = 0;
        int tempToSearch = Integer.MIN_VALUE;
        boolean finished = false;
        for (int i = 0; i < nums.length && !finished; i++){
//            System.out.println("nums[i] : " + nums[i]);
//            System.out.println("target : " + target);

            if (nums[i] < 0) {
//                System.out.println("negative");
//                System.out.println("positive");
                tempToSearch = (target) - (nums[i]); //  9- 2 = 7
                firstIndex = i;

                for (int j = i + 1; j < nums.length; j++){
                    if (nums[j] == tempToSearch){
                        secondIndex = j;
                        finished = true;
                        break;
                    }
                }
            }else{
                if ((nums[i]) <= (target)){
//                    System.out.println("positive");
                    tempToSearch = (target) - (nums[i]); //  9- 2 = 7
                    firstIndex = i;

                    for (int j = i + 1; j < nums.length; j++){
                        if (nums[j] == tempToSearch){
                            secondIndex = j;
                            finished = true;
                            break;
                        }
                    }
                }
            }
        }
        return new int[]{firstIndex, secondIndex};
    }

    public static int[] twoSum2(int [] nums, int target){

        Map<Integer, Integer> map = new HashMap<>();
        Integer firstIndex = 0;
        Integer secondIndex = 0;
        Integer temp = 0;


        for (int i = 0; i < nums.length; i++) {
            Integer index = nums[i];
            System.out.println("index : " + index);
            Integer val = target - nums[i];
            System.out.println("val : " + val);
            map.put(index, val); //
        }

//        Iterator iterator = map.keySet().iterator();
//        int
//        while (iterator.hasNext()){
//
//        }

        // doğruuu
        //int keySet = map.keySet().size();
        int forIndex = 0;
        for (Integer key : map.keySet()){
            temp = map.get(key);
            firstIndex = forIndex;
            System.out.println("firstIndex : " + firstIndex);

            if (map.containsKey(temp)){
                secondIndex = map.get(temp);
                System.out.println("secondIndex : " + secondIndex);
                break;
            }
            forIndex++;

        }


//        for (int i = 0; i < nums.length; i++) {
//            //temp =
//            map.keySet().iterator();
//            System.out.println("temp : " + temp);
//            firstIndex = i;
//            if (map.containsKey(temp)) {
//                secondIndex = map.get(temp);
//                break;
//            }
//
//        }





/*        for(int i = 0; i < nums.length; i++){
            if (nums[i] < target){
                temp = target - nums[i];
                firstIndex = i;

                for(int j = i; j < nums.length - i; j++){
                    if (nums[j] != temp){
                    }else{
                        secondIndex =j ;
                        break;
                    }
                }
            }
        }*/


        return new int[] {firstIndex, secondIndex};
    }

    public static int[] twoSum(int[] nums, int target) {

        int firstIndex = 0;
        int secondIndex = 0;
        int tempSum = 0;
        for (int i = 0; i < nums.length; i++){
            if (nums[i] < target){ // 2 < 9
                tempSum = target - nums[i]; // 9-2 = 7
                firstIndex = i;
//                System.out.println("first :" + firstIndex);
                System.out.println("tempSum: " + tempSum);
                int j = i + 1;
                while (j <= nums.length -1 -i){
                    if (tempSum == nums[j]){
                        System.out.println("tempSum: " + tempSum + " nums[j]: " + nums[j]);
                        secondIndex = j;
//                        System.out.println("second :" + secondIndex);
                        System.out.println("firstIndex: " + firstIndex + " secondIndex: " + secondIndex);
                        break;
                    }
                    j++;
                }
//                for (int j = i; j < nums.length - 1 - i; j++){
//                    if (tempSum == nums[j]){
//                        secondIndex = j;
//                        System.out.println("second :" + secondIndex);
//                        System.out.println("firstIndex: " + firstIndex + " secondIndex: " + secondIndex);
//                        break;
//                    }
//                }
            }
        }
        //System.out.println("firstIndex: " + firstIndex + " secondIndex: " + secondIndex);
        int [] sol = {firstIndex, secondIndex};
        return sol;
    }
}
