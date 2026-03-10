package com.dsa.arrays;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;



public class LawFeeDistributionSystem {

    // LAST
    private static final BigDecimal ANNUAL_LIMIT = new BigDecimal("243013.44");
    private static final BigDecimal TAX_RATE = new BigDecimal("0.05");
    private static final BigDecimal INDIV_RATE = new BigDecimal("0.55");
    private static final BigDecimal POOL_RATE = new BigDecimal("0.40");

    public static void main(String[] args) {
        // Input Data
        Map<String, BigDecimal> fees = new LinkedHashMap<>();
        fees.put("YO", new BigDecimal("178644.78"));
        fees.put("FI", new BigDecimal("272705.17"));
        fees.put("ANE", new BigDecimal("249622.04"));
        fees.put("IYS", new BigDecimal("427095.94"));
        fees.put("EED", new BigDecimal("0.0"));

        calculateAndPrintDistribution(fees);
    }

    public static void calculateAndPrintDistribution(Map<String, BigDecimal> fees) {
        Map<String, BigDecimal> finalPayouts = new LinkedHashMap<>();
        Map<String, BigDecimal> poolSharesOnly = new HashMap<>();
        BigDecimal totalTaxToState = BigDecimal.ZERO;
        BigDecimal totalPoolAmount = BigDecimal.ZERO;

        // 1. STAGE ONE: Tax Calculation & 55% Individual Cut
        for (String name : fees.keySet()) {
            BigDecimal gross = fees.get(name);

            // Calculate 5% State Tax
            BigDecimal tax = gross.multiply(TAX_RATE).setScale(2, RoundingMode.HALF_UP);
            totalTaxToState = totalTaxToState.add(tax);

            // Calculate 55% Individual and 40% Pool from Gross
            BigDecimal share55 = gross.multiply(INDIV_RATE).setScale(2, RoundingMode.HALF_UP);
            BigDecimal share40 = gross.multiply(POOL_RATE).setScale(2, RoundingMode.HALF_UP);

            totalPoolAmount = totalPoolAmount.add(share40);
            poolSharesOnly.put(name, BigDecimal.ZERO);

            // Take 55% first, capped at Limit
            if (share55.compareTo(ANNUAL_LIMIT) >= 0) {
                finalPayouts.put(name, ANNUAL_LIMIT);
                // If 55% alone exceeds limit, overflow goes to the communal pool
                totalPoolAmount = totalPoolAmount.add(share55.subtract(ANNUAL_LIMIT));
            } else {
                finalPayouts.put(name, share55);
            }
        }

        // 2. STAGE TWO: Iterative Pool Distribution (Redistributing Overflows)
        distributePool(totalPoolAmount, finalPayouts, poolSharesOnly);

        // 3. Output Results
        System.out.println("====================================================");
        System.out.println("          LAWYER FEE DISTRIBUTION REPORT            ");
        System.out.println("====================================================");
        System.out.printf("Total State Tax (5%%): %s TL%n", totalTaxToState);
        System.out.println("----------------------------------------------------");
        System.out.printf("%-5s | %-12s | %-12s | %-12s%n", "NAME", "INDIV (55%)", "POOL (40%)", "TOTAL PAYOUT");
        System.out.println("----------------------------------------------------");

        for (String name : fees.keySet()) {
            BigDecimal gross = fees.get(name);
            BigDecimal s55Actual = gross.multiply(INDIV_RATE).setScale(2, RoundingMode.HALF_UP);
            // If the 55% was originally higher than the payout, it means they were capped
            if (s55Actual.compareTo(finalPayouts.get(name)) > 0) {
                s55Actual = finalPayouts.get(name);
            }

            System.out.printf("%-5s | %12.2f | %12.2f | %12.2f%n",
                    name,
                    s55Actual,
                    poolSharesOnly.get(name),
                    finalPayouts.get(name));
        }
        System.out.println("====================================================");
    }

    private static void distributePool(BigDecimal pool, Map<String, BigDecimal> payouts, Map<String, BigDecimal> poolOnly) {
        while (pool.compareTo(new BigDecimal("0.01")) >= 0) {
            List<String> eligible = new ArrayList<>();
            for (String name : payouts.keySet()) {
                if (payouts.get(name).compareTo(ANNUAL_LIMIT) < 0) {
                    eligible.add(name);
                }
            }

            if (eligible.isEmpty()) break; // Everyone reached the 243,013.44 limit

            BigDecimal share = pool.divide(BigDecimal.valueOf(eligible.size()), 2, RoundingMode.FLOOR);

            // Handle tiny remainders (cents)
            if (share.compareTo(BigDecimal.ZERO) == 0) {
                for (String name : eligible) {
                    if (pool.compareTo(BigDecimal.ZERO) <= 0) break;
                    payouts.put(name, payouts.get(name).add(new BigDecimal("0.01")));
                    poolOnly.put(name, poolOnly.get(name).add(new BigDecimal("0.01")));
                    pool = pool.subtract(new BigDecimal("0.01"));
                }
                break;
            }

            BigDecimal overflowThisRound = BigDecimal.ZERO;
            for (String name : eligible) {
                BigDecimal current = payouts.get(name);
                BigDecimal space = ANNUAL_LIMIT.subtract(current);

                if (share.compareTo(space) >= 0) {
                    payouts.put(name, ANNUAL_LIMIT);
                    poolOnly.put(name, poolOnly.get(name).add(space));
                    overflowThisRound = overflowThisRound.add(share.subtract(space));
                } else {
                    payouts.put(name, current.add(share));
                    poolOnly.put(name, poolOnly.get(name).add(share));
                }
            }
            // Pool for next loop = overflows + remainders from division
            pool = overflowThisRound.add(pool.subtract(share.multiply(BigDecimal.valueOf(eligible.size()))));
        }
    }
}