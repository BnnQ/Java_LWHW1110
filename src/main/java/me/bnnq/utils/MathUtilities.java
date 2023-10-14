package me.bnnq.utils;

public class MathUtilities
{
    public static int factorial(int number) {
        int result = 1;
        for (int i = 2; i <= number; ++i) {
            result *= i;
        }
        return result;
    }

    public static boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }

        if (number == 2) {
            return true;
        }

        if (number % 2 == 0) {
            return false;
        }

        int sqrtOfNum = (int) Math.sqrt(number);
        for (int i = 3; i <= sqrtOfNum; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

}
