package com.dsa.arrays;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;



public class LawyerFeeCalculator2 {

    private static final BigDecimal LIMIT = new BigDecimal("243013.44");
    private static final BigDecimal TAX_RATE = new BigDecimal("0.05");
    private static final BigDecimal INDIVIDUAL_RATE = new BigDecimal("0.55");
    private static final BigDecimal POOL_RATE = new BigDecimal("0.40");

    public static Map<String, BigDecimal> distribute(Map<String, BigDecimal> lawyerFeeMap) {
        Map<String, BigDecimal> payouts = new HashMap<>();
        BigDecimal totalPool = BigDecimal.ZERO;
        BigDecimal totalTaxPaid = BigDecimal.ZERO;
        int lawyerCount = lawyerFeeMap.size();

        // 1. Deduct Tax and Calculate Initial Shares
        for (String name : lawyerFeeMap.keySet()) {
            BigDecimal grossFee = lawyerFeeMap.get(name);

            // Calculate and remove 5% tax
            BigDecimal tax = grossFee
                    .multiply(TAX_RATE)
                    .setScale(2, RoundingMode.HALF_UP);
            totalTaxPaid = totalTaxPaid.add(tax);
            BigDecimal netFee = grossFee.subtract(tax);
            System.out.println("netfeee : for " + name + " "  + netFee);

            // Calculate 55% and 40% from the remaining 95%
            BigDecimal share55 = netFee
                    .multiply(INDIVIDUAL_RATE)
                    .setScale(2, RoundingMode.HALF_UP);
            BigDecimal share40 = netFee
                    .multiply(POOL_RATE)
                    .setScale(2, RoundingMode.HALF_UP);

            totalPool = totalPool.add(share40);

            // Assign 55% (Capped by limit)
            if (share55.compareTo(LIMIT) >= 0) {
                payouts.put(name, LIMIT);
                totalPool = totalPool.add(share55.subtract(LIMIT));
            } else {
                payouts.put(name, share55);
            }
        }

        // 2. Initial Pool Split
        BigDecimal equalPoolShare = totalPool.divide(BigDecimal.valueOf(lawyerCount), 2, RoundingMode.FLOOR);
        BigDecimal overflowToRedistribute = BigDecimal.ZERO;

        for (String name : payouts.keySet()) {
            BigDecimal current = payouts.get(name);
            BigDecimal potential = current.add(equalPoolShare);

            if (potential.compareTo(LIMIT) > 0) {
                payouts.put(name, LIMIT);
                overflowToRedistribute = overflowToRedistribute.add(potential.subtract(LIMIT));
            } else {
                payouts.put(name, potential);
            }
        }

        // 3. Final Redistribution of the Overflow
        if (overflowToRedistribute.compareTo(BigDecimal.ZERO) > 0) {
            redistributeOverflow(overflowToRedistribute, payouts);
        }

        // Optional: Print total tax for transparency
        System.out.println("Total State Tax Paid: " + totalTaxPaid + " TL");

        return payouts;
    }

    private static void redistributeOverflow(BigDecimal amount, Map<String, BigDecimal> payouts) {
        boolean needed = true;
        while (needed && amount.compareTo(new BigDecimal("0.01")) >= 0) {
            needed = false;
            List<String> eligible = new ArrayList<>();
            for (String name : payouts.keySet()) {
                if (payouts
                        .get(name)
                        .compareTo(LIMIT) < 0) eligible.add(name);
            }

            if (eligible.isEmpty()) break;

            BigDecimal share = amount.divide(BigDecimal.valueOf(eligible.size()), 2, RoundingMode.FLOOR);
            BigDecimal nextRoundOverflow = BigDecimal.ZERO;

            for (String name : eligible) {
                BigDecimal current = payouts.get(name);
                BigDecimal space = LIMIT.subtract(current);

                if (share.compareTo(space) >= 0) {
                    payouts.put(name, LIMIT);
                    nextRoundOverflow = nextRoundOverflow.add(share.subtract(space));
                    needed = true;
                } else {
                    payouts.put(name, current.add(share));
                }
            }
            amount = nextRoundOverflow;
        }
    }

    public static void main(String[] args) {
//        fees.put("YO", new BigDecimal("11864.78"));
//        fees.put("FI", new BigDecimal("272705.17"));
//        fees.put("AEO", new BigDecimal("249622.04"));
//        fees.put("IYS", new BigDecimal("427095.94"));
//        fees.put("EED", new BigDecimal("0.0"));


        Map<String, BigDecimal> fees = new HashMap<>();
        fees.put("YO", new BigDecimal("178324.14"));
        fees.put("FI", new BigDecimal("272705.17"));
        fees.put("AEO", new BigDecimal("249622.04"));
        fees.put("IYS", new BigDecimal("427095.94"));
        fees.put("EED", new BigDecimal("0.0"));

        Map<String, BigDecimal> results = distribute(fees);

        System.out.println("--- Final Lawyer Payouts ---");
        results.forEach((name, amount) ->
                System.out.printf("%s: %,.2f TL%n", name, amount));
    }
}


