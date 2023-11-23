package com.zero.safpswithorder.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FPSService {
    private BigDecimal profit;
    private BigDecimal[][] resultMatrix;
    private int k;
    private int machines;
    private int drives;

    public FPSService(int k){
        this.k = k;
        resultMatrix = new BigDecimal[k+1][k+1];
    }

    public int getMachines() {
        return machines;
    }

    public int getDrives() {
        return drives;
    }

    public BigDecimal getProfit() {


        return profit.setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal[][] calculate(double lambda, double tcp, double d, double vv, double vn){
        BigDecimal ro = calcRo(lambda, tcp);
        BigDecimal max = BigDecimal.ZERO;

        int v = 0;
        int n = 0;

        for (int i = 1; i <= k; i++) {
            for (int j = 1; j <= k; j++) {
                BigDecimal result = calcPartOne(d, lambda).multiply(BigDecimal.ONE.subtract(calcPartTwo(i, j, ro, k).multiply(calcRight(i, j, ro, k)))).subtract(BigDecimal.valueOf(vv*i-vn*j));

                if (result.compareTo(max) > 0){
                    max = result;
                    v = i;
                    n = j;
                }

                if (BigDecimal.ZERO.compareTo(result) > 0) resultMatrix[i][j] = BigDecimal.ZERO;
                else resultMatrix[i][j] = result.setScale(2, RoundingMode.HALF_UP);
            }

            this.profit = max;
            this.machines = v;
            this.drives = n;
        }

        return resultMatrix;
    }

    private BigDecimal calcRight(int n, int j, BigDecimal ro, int k){
        BigDecimal b = BigDecimal.ZERO;
        if (ro.subtract(BigDecimal.valueOf(n)).compareTo(BigDecimal.valueOf(1.0e-18)) >= 0) b = calcPartThree(n, ro).multiply(calcPartFour(n, k, ro, j));

        return BigDecimal.ONE.divide((BigDecimal.ONE.add(sum(k, ro).add(b))), 1000, RoundingMode.HALF_UP);
    }

    private BigDecimal calcRo(double lambda, double tcp){
        return BigDecimal.valueOf(lambda * tcp);
    }

    private BigDecimal calcPartOne(double d, double lambda){
        return BigDecimal.valueOf(d * lambda);
    }

    private BigDecimal calcPartTwo(int n, int j, BigDecimal ro, int k){
        BigDecimal factorial = factorial(n);
        BigDecimal numerator = ro.pow(n+j*k);
        BigDecimal denominator = BigDecimal.valueOf(n).pow(j*k).multiply(factorial);
        return numerator.divide(denominator,5, RoundingMode.HALF_UP);
    }

    private BigDecimal calcPartThree(int n, BigDecimal ro){
        BigDecimal factorial = factorial(n);
        BigDecimal numerator = ro.pow(n + 1);
        BigDecimal denominator = BigDecimal.valueOf(n).multiply(factorial);
        return numerator.divide(denominator, 5, RoundingMode.HALF_UP);
    }

    private BigDecimal calcPartFour(int n, int j, BigDecimal ro, int k){
        BigDecimal divide = ro.divide(BigDecimal.valueOf(n), 5, RoundingMode.HALF_UP);
        BigDecimal numerator = BigDecimal.ONE.subtract(divide.pow(j*k));
        BigDecimal denominator = BigDecimal.ONE.subtract(divide);
        return numerator.divide(denominator, 5, RoundingMode.HALF_UP);
    }

    private BigDecimal factorial(int n){
        if (n == 0) return BigDecimal.ONE;
        return BigDecimal.valueOf(n).multiply(factorial(n - 1));
    }

    private BigDecimal sum(int n, BigDecimal ro) {
        if (n == 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal degree = ro.pow(n);
        BigDecimal factorial = factorial(n);
        BigDecimal divide = degree.divide(factorial, RoundingMode.HALF_UP);
        return divide.add(sum(n - 1, ro));
    }
}
