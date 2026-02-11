package com.dsa.arrays;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LawyerFeeCalculator {
        public static final double ANNUAL_LIMIT = 243013.44;
        public static final double TAX_RATE = 0.05;
        public static final double HANDLER_RATE = 0.55;
        public static final double POOL_RATE = 0.40;

        public static void distribute(double totalFee, Map<String, Double> lawyerFeeMap){

            //int totalLawyerCount, double[] currentAnnualEarnings
            //double [] payouts = new double[totalLawyerCount];

            //pay state
            double tax = totalFee * TAX_RATE;
            double distributableAmountMoney = totalFee - tax; // 95'lik para

            //handle %55 share for the lead lawyer
            for( String key : lawyerFeeMap.keySet()){
                double handlerMoney = totalFee * HANDLER_RATE;
                Double newMoneyToTake = lawyerFeeMap.get(key);
            }



        }

        public static void distributeFees(double totalFee, int handlerIndex, double[] currentAnnualEarnings) {
            int totalLawyers = currentAnnualEarnings.length;
            double[] payouts = new double[totalLawyers];

            // 1. Pay the State
            double tax = totalFee * TAX_RATE;
            double distributableAmount = totalFee - tax;

            // 2. Handle the 55% share for the lead lawyer
            double handlerShare = totalFee * HANDLER_RATE;
            double poolAmount = totalFee * POOL_RATE;

            double handlerRemainingLimit = Math.max(0, ANNUAL_LIMIT - currentAnnualEarnings[handlerIndex]);

            if (handlerShare > handlerRemainingLimit) {
                payouts[handlerIndex] = handlerRemainingLimit;
                poolAmount += (handlerShare - handlerRemainingLimit); // Overflow goes to pool
            } else {
                payouts[handlerIndex] = handlerShare;
            }

            // 3. Distribute the Pool (Iterative redistribution)
            distributePool(poolAmount, payouts, currentAnnualEarnings);

            // Print Results
            System.out.println("--- Distribution Results ---");
            System.out.printf("State Tax (5%%): %.2f TRY%n", tax);
            for (int i = 0; i < totalLawyers; i++) {
                System.out.printf("Lawyer %d: Received %.2f TRY (Total Annual: %.2f TRY)%n",
                        i, payouts[i], currentAnnualEarnings[i] + payouts[i]);
            }
        }

        private static void distributePool(double amountToDistribute, double[] currentPayouts, double[] annualEarnings) {
            boolean redistributionNeeded = true;
            Set<Integer> cappedLawyers = new HashSet<>();

            while (redistributionNeeded && amountToDistribute > 0.01) {
                redistributionNeeded = false;

                // Count lawyers who can still receive money
                int eligibleCount = 0;
                for (int i = 0; i < annualEarnings.length; i++) {
                    if (!cappedLawyers.contains(i) && (annualEarnings[i] + currentPayouts[i]) < ANNUAL_LIMIT) {
                        eligibleCount++;
                    } else {
                        cappedLawyers.add(i);
                    }
                }

                if (eligibleCount == 0) break; // Everyone is capped

                double share = amountToDistribute / eligibleCount;
                double nextRoundPool = 0;

                for (int i = 0; i < annualEarnings.length; i++) {
                    if (cappedLawyers.contains(i)) continue;

                    double remainingLimit = ANNUAL_LIMIT - (annualEarnings[i] + currentPayouts[i]);

                    if (share >= remainingLimit) {
                        currentPayouts[i] += remainingLimit;
                        nextRoundPool += (share - remainingLimit);
                        cappedLawyers.add(i);
                        redistributionNeeded = true;
                    } else {
                        currentPayouts[i] += share;
                    }
                }
                amountToDistribute = nextRoundPool;
            }
        }

        public static void main(String[] args) {
            // Example: 5 lawyers, Lawyer 0 handled the case.
            // Some lawyers are already near their limit.
            double[] earnings = {240000.00, 100000.00, 50000.00, 243000.00, 0.00};
            double [] earnings2 = { 109324.14, 268715.99, 226939.28, 384447.23};
            //distributeFees(50000.00, 0, earnings);
            distributeFees( 1128067.93 , 0, earnings2);
        }

    }
