package com.algo.monster.miscellaneous.math;

import java.util.Arrays;
import java.util.Scanner;

class NthPrime {
    public static int nthPrime(int n) {
        boolean[] primes = new boolean[100001];
        Arrays.fill(primes, true);
        primes[0] = primes[1] = false;
        int nPrimes = 0;
        int nthPrime = 0;
        for (int i = 2; i <= 100000; i++) {
            if (primes[i]) {
                nPrimes ++;
                if (nPrimes == n) {
                    nthPrime = i;
                    break;
                }

                for (int j = i +i; j <= 100000; j+=i) {
                    primes[j] = false;
                }
            }
        }
        return nthPrime;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = Integer.parseInt(scanner.nextLine());
        scanner.close();
        int res = nthPrime(n);
        System.out.println(res);
    }
}
