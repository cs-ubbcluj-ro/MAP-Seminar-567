package org.example.seminar6_321;

public class PrimeUtils {
    public static boolean isPrime(int x) {
        if (x < 2)
            return false;
        if (x == 2)
            return true;
        if (x % 2 == 0)
            return false;
        for (int d = 3; d < x / 2; d += 2) {
            if (x % d == 0)
                return false;
        }
        return true;
    }

    public static int countPrimes(int[] listOfNumbers, int indexStart, int step) {
        //step = number of threads we have
        int count = 0;
        int index = indexStart;
        while (index < listOfNumbers.length) {
            if (isPrime(listOfNumbers[index]))
                count += 1;
            index += step;
        }
        return count;
    }

    public static int[] generateRandomNumbers(int listLength) {
        int[] listOfNumbers = new int[listLength];
        for (int i = 0; i < listLength; i++) {
            listOfNumbers[i] = (int) (1000000 * Math.random());
        }
        return listOfNumbers;
    }

}


