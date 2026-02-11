package com.dsa.arrays;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class LawyerFeeCalculationDist {

    private static final BigDecimal LIMIT = new BigDecimal("243013.44");
    private static final BigDecimal TAX_RATE = new BigDecimal("0.05");
    private static final BigDecimal INDIV_RATE = new BigDecimal("0.55");
    private static final BigDecimal POOL_RATE = new BigDecimal("0.40");

    public static Map<String, BigDecimal> calculate(Map<String, BigDecimal> fees) {
        Map<String, BigDecimal> payouts = new HashMap<>();
        BigDecimal totalPool = BigDecimal.ZERO;

        // Stage 1: Tax & 55% Individual Cut
        for (String name : fees.keySet()) {
            BigDecimal gross = fees.get(name);
            BigDecimal net = gross.multiply(BigDecimal.ONE.subtract(TAX_RATE));

            BigDecimal share55 = net
                    .multiply(INDIV_RATE)
                    .setScale(2, RoundingMode.HALF_UP);
            BigDecimal share40 = net
                    .multiply(POOL_RATE)
                    .setScale(2, RoundingMode.HALF_UP);
            totalPool = totalPool.add(share40);

            if (share55.compareTo(LIMIT) >= 0) {
                payouts.put(name, LIMIT);
                totalPool = totalPool.add(share55.subtract(LIMIT)); // Extreme overflow
            } else {
                payouts.put(name, share55);
            }
        }

        // Stage 2: Recursive Pool Distribution
        distributeRemainingPool(totalPool, payouts);
        return payouts;
    }

    private static void distributeRemainingPool(BigDecimal pool, Map<String, BigDecimal> payouts) {
        while (pool.compareTo(new BigDecimal("0.01")) >= 0) {
            List<String> eligible = new ArrayList<>();
            for (String name : payouts.keySet()) {
                if (payouts
                        .get(name)
                        .compareTo(LIMIT) < 0) eligible.add(name);
            }

            if (eligible.isEmpty()) break; // Everyone is at LIMIT

            // Calculate equal share from current pool
            BigDecimal share = pool.divide(BigDecimal.valueOf(eligible.size()), 2, RoundingMode.FLOOR);

            // If share is 0 but pool > 0, distribute remaining cents one by one
            if (share.compareTo(BigDecimal.ZERO) == 0) {
                for (String name : eligible) {
                    if (pool.compareTo(BigDecimal.ZERO) <= 0) break;
                    payouts.put(name, payouts
                            .get(name)
                            .add(new BigDecimal("0.01")));
                    pool = pool.subtract(new BigDecimal("0.01"));
                }
                break;
            }

            BigDecimal totalDistributedThisRound = BigDecimal.ZERO;
            BigDecimal overflowFromThisRound = BigDecimal.ZERO;

            for (String name : eligible) {
                BigDecimal current = payouts.get(name);
                BigDecimal space = LIMIT.subtract(current);

                if (share.compareTo(space) >= 0) {
                    payouts.put(name, LIMIT);
                    overflowFromThisRound = overflowFromThisRound.add(share.subtract(space));
                } else {
                    payouts.put(name, current.add(share));
                }
                totalDistributedThisRound = totalDistributedThisRound.add(share);
            }
            // Pool for next round = overflow + rounding remainders
            pool = overflowFromThisRound.add(pool.subtract(totalDistributedThisRound));
        }
    }


    public static void main(String[] args) {


        Map<String, BigDecimal> fees = new HashMap<>();
        fees.put("YO", new BigDecimal("178324.14"));
        fees.put("FI", new BigDecimal("272705.17"));
        fees.put("AEO", new BigDecimal("249622.04"));
        fees.put("IYS", new BigDecimal("427095.94"));
        fees.put("EED", new BigDecimal("0.0"));

        Map<String, BigDecimal> results = calculate(fees);

        System.out.println("--- Final Lawyer Payouts ---LASTTT");
        results.forEach((name, amount) ->
                System.out.printf("%s: %,.2f TL%n", name, amount));
    }
}