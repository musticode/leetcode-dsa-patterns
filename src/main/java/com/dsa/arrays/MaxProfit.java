package com.dsa.arrays;

public class MaxProfit {
    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println(maxProfit(prices));


        int[] prices2 = {2, 1, 4};
        System.out.println(maxProfit(prices2));


    }

    public static int maxProfit(int[] prices) {
        int maxProfit = Integer.MIN_VALUE;
        int minNumber = Integer.MAX_VALUE;
        for (int i = 0; i < prices.length; i++) {
            for (int j = i; j < prices.length - i; j++) {
                int profit = prices[j] - prices[i];
                if (profit > 0) {
                    maxProfit = Math.max(maxProfit, profit);
                }
            }
            if (maxProfit > 0) {
                return maxProfit;
            }

        }
        return 0;
//        return maxProfit > 0 ? maxProfit : 0;

    }
}
