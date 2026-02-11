package com.dsa.arrays;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class LawyerFeeCalc2 {


    private static final BigDecimal ANNUAL_LIMIT = new BigDecimal("243013.44");
    private static final BigDecimal TAX_RATE = new BigDecimal("0.05");
    private static final BigDecimal INDIVIDUAL_RATE = new BigDecimal("0.55");
    private static final BigDecimal POOL_RATE = new BigDecimal("0.40");

    public static Map<String, BigDecimal> calculation(Map<String, BigDecimal> currentAnnualEarnings){
        Map<String, BigDecimal> finalPayouts = new HashMap<>();
        BigDecimal totalPool = BigDecimal.ZERO;

        for (String  name : currentAnnualEarnings.keySet()){
            BigDecimal totalFee = currentAnnualEarnings.get(name);
            BigDecimal individual55 = totalFee
                    .multiply(INDIVIDUAL_RATE)
                    .setScale(2, RoundingMode.HALF_UP);
            BigDecimal pool40 = totalFee
                    .multiply(POOL_RATE)
                    .setScale(2, RoundingMode.HALF_UP);

            totalPool = totalPool.add(pool40);

            BigDecimal annualUsed = currentAnnualEarnings.getOrDefault(name, BigDecimal.ZERO);
            BigDecimal remainingLimit = ANNUAL_LIMIT
                    .subtract(annualUsed)
                    .max(BigDecimal.ZERO);

            if (individual55.compareTo(remainingLimit) > 0) {
                // If 55% exceeds limit, take what's left and move overflow to pool
                finalPayouts.put(name, remainingLimit);
                BigDecimal overflow = individual55.subtract(remainingLimit);
                totalPool = totalPool.add(overflow);
            } else {
                finalPayouts.put(name, individual55);
            }
        }

        //pool calculation
//        poolCalculation(totalPool, finalPayouts);

        return finalPayouts;
    }

    public static void poolCalculation(BigDecimal totalPool, Map<String, BigDecimal> payouts){

        BigDecimal eachLawyerMax = totalPool.divide(totalPool, payouts.size(), RoundingMode.FLOOR); //totalPool / BigDecimal.valueOf(payouts.size());
        for (String name : payouts.keySet()){
            if (payouts.get(name).compareTo(ANNUAL_LIMIT) < 0){
                BigDecimal addingAmount = eachLawyerMax.subtract(payouts.get(name));
                BigDecimal newAmount = payouts.get(name).add(addingAmount);
                payouts.put(name, newAmount);
            }
        }

    }




    public static Map<String, BigDecimal> calculateDistribution(
            Map<String, BigDecimal> lawyerFeeMap, // The 100% total fees generated per lawyer
            Map<String, BigDecimal> currentAnnualEarnings
    ) {
        Map<String, BigDecimal> finalPayouts = new HashMap<>();
        BigDecimal totalPool = BigDecimal.ZERO;

        // Initialize payouts and calculate initial 55% + initial 40% Pool
        for (String name : lawyerFeeMap.keySet()) {
            BigDecimal totalFee = lawyerFeeMap.get(name);
            BigDecimal individual55 = totalFee
                    .multiply(INDIVIDUAL_RATE)
                    .setScale(2, RoundingMode.HALF_UP);
            BigDecimal pool40 = totalFee
                    .multiply(POOL_RATE)
                    .setScale(2, RoundingMode.HALF_UP);

            // Add the 40% to the global pool immediately
            totalPool = totalPool.add(pool40);

            BigDecimal annualUsed = currentAnnualEarnings.getOrDefault(name, BigDecimal.ZERO);
            BigDecimal remainingLimit = ANNUAL_LIMIT
                    .subtract(annualUsed)
                    .max(BigDecimal.ZERO);

            if (individual55.compareTo(remainingLimit) > 0) {
                // If 55% exceeds limit, take what's left and move overflow to pool
                finalPayouts.put(name, remainingLimit);
                BigDecimal overflow = individual55.subtract(remainingLimit);
                totalPool = totalPool.add(overflow);
            } else {
                finalPayouts.put(name, individual55);
            }
        }

        // STAGE 2: Distribute the Total Accumulated Pool
        distributePool(totalPool, finalPayouts, currentAnnualEarnings);

        return finalPayouts;
    }

    private static void distributePool(BigDecimal amount, Map<String, BigDecimal> payouts, Map<String, BigDecimal> annual) {
        boolean redistributionNeeded = true;
        Set<String> cappedLawyers = new HashSet<>();

        while (redistributionNeeded && amount.compareTo(new BigDecimal("0.01")) >= 0) {
            redistributionNeeded = false;
            List<String> eligible = new ArrayList<>();

            for (String name : annual.keySet()) {
                BigDecimal totalSoFar = annual
                        .getOrDefault(name, BigDecimal.ZERO)
                        .add(payouts.get(name));
                if (!cappedLawyers.contains(name) && totalSoFar.compareTo(ANNUAL_LIMIT) < 0) {
                    eligible.add(name);
                } else {
                    cappedLawyers.add(name);
                }
            }

            if (eligible.isEmpty()) break;

            BigDecimal share = amount.divide(BigDecimal.valueOf(eligible.size()), 2, RoundingMode.FLOOR);
            BigDecimal overflow = BigDecimal.ZERO;

            for (String name : eligible) {
                BigDecimal currentTotal = annual
                        .getOrDefault(name, BigDecimal.ZERO)
                        .add(payouts.get(name));
                BigDecimal space = ANNUAL_LIMIT.subtract(currentTotal);

                if (share.compareTo(space) >= 0) {
                    payouts.put(name, payouts
                            .get(name)
                            .add(space));
                    overflow = overflow.add(share.subtract(space));
                    cappedLawyers.add(name);
                    redistributionNeeded = true;
                } else {
                    payouts.put(name, payouts
                            .get(name)
                            .add(share));
                }
            }
            // Add any remainders from division to the overflow
            BigDecimal remainder = amount.subtract(share.multiply(BigDecimal.valueOf(eligible.size())));
            amount = overflow.add(remainder);
        }

    }


    public static void main(String[] args) {
   /* Map<String, Double> currentEarnings = new HashMap<>();
    currentEarnings.put("YO", 178644.78); // Very close to limit
    currentEarnings.put("FI", 272705.17);
    currentEarnings.put("AEO", 249622.04);
    currentEarnings.put("IYS", 427095.94);


    Map<String, Double> results = calculateDistribution(1128067.93, currentEarnings);
    System.out.println(results);*/

        Map<String, BigDecimal> fees = new HashMap<>();
        fees.put("YO", new BigDecimal("178644.78"));
        fees.put("FI", new BigDecimal("272705.17"));
        fees.put("AEO", new BigDecimal("249622.04"));
        fees.put("IYS", new BigDecimal("427095.94"));


// ... add others

/*        Map<String, BigDecimal> annual = new HashMap<>();
        annual.put("YO", BigDecimal.ZERO);*/
//        annual.put("FI", BigDecimal.ZERO);
//        annual.put("AEO", BigDecimal.ZERO);
//        annual.put("IYS", BigDecimal.ZERO);
// ... add others

        Map<String, BigDecimal> result = calculation(fees);
        System.out.println(result);


/*        for (String res : results.keySet()) {
            System.out.println("KEY : " + res + " result : " + results.get(res));
        }*/
// Alice will hit her limit quickly, and the rest of her 55% will flow to Bob and Charlie.

    }
}
