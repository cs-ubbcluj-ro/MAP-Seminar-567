package org.example.seminar6_322_1;

import java.util.Random;

//Petian Simona
public class PrimeUtils {
    public static boolean estePrim(int number) {
        if (number < 2)
            return false;
        if (number == 2)
            return true;
        for (int i = 2; i <= number / 2; i++) {
            if (number % i == 0)
                return false;
        }
        return true;
    }

    public static int countPrimes(int[] listOfNumbers, int start, int pasi) {
        int contor = 0;
        for (int i = start; i < listOfNumbers.length; i += pasi) {
            if (estePrim(listOfNumbers[i]))
                contor++;
        }
        System.out.println("Contor din "+start+" este: "+contor);
        return contor;
    }

    public static int[] generateList(int n) {
        int[] listOfNumbers = new int[n];
        for (int i = 0; i < n; i++) {
            Random rand = new Random();
            int nr = rand.nextInt(100000);
            listOfNumbers[i] = nr;
        }

        return listOfNumbers;
    }
}
