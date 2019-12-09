package zad2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SieveEratosthenes {
    public static final Integer UPPER_BOUND_OF_SEARCHING_PRIMES = 1000000000;

    private boolean[] primes;
    private List<Integer> smallPrimes = new ArrayList<>();

    public SieveEratosthenes() {

        this.primes = new boolean[(int) Math.sqrt(UPPER_BOUND_OF_SEARCHING_PRIMES)];
        Arrays.fill(primes, true);
        int amountOfGeneratedPrimes = generateSmallPrimes();
        System.out.println("amountOfGeneratedPrimes: " + amountOfGeneratedPrimes);
    }

    public static void main(String[] args) {
        SieveEratosthenes sieveEratosthenes = new SieveEratosthenes();
        sieveEratosthenes.primes = new boolean[UPPER_BOUND_OF_SEARCHING_PRIMES];
        Arrays.fill(sieveEratosthenes.primes, true);

        int useThreadPool = 8;

        Thread[] threads = new Thread[useThreadPool];
        boolean[] finishedIteration = new boolean[threads.length];
        Arrays.fill(finishedIteration, false);

        for(int i = 0; i < useThreadPool; i++) {
            threads[i] = new Thread(new ExpungeThread(sieveEratosthenes.primes, sieveEratosthenes.smallPrimes, useThreadPool,i + 1, threads, finishedIteration));
        }

        long start = System.nanoTime();
        for(int i = 0; i < useThreadPool; i++) {
            threads[i].start();
        }

        try {
            for(int i = 0; i < useThreadPool; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long finish = System.nanoTime();

        System.out.println("duration: " + (double)((finish - start) / 1000000) + "ms");
    }

    private int generateSmallPrimes() {
        int upperBound = (int) Math.sqrt(UPPER_BOUND_OF_SEARCHING_PRIMES);
        int i;

        for(int multiple = 2; multiple <= Math.sqrt(upperBound); ++multiple) {
            if(!primes[multiple]) {
                continue;
            }
            i = 2;
            while(multiple * i < upperBound) {
                primes[multiple * i] = false;
                i++;
            }
        }

        int result = 0;
        for(int pos = 2; pos < upperBound; pos++) {
            if(primes[pos]) {
                smallPrimes.add(pos);
                result++;
            }
        }

        return result;
    }

    public static int countPrimeNumbers(final boolean[] primes) {
        int count = 0;
        for(int i = 2; i < primes.length; i++) {
            if(primes[i])
                count++;
        }
        return count;
    }
}
