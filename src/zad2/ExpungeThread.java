package zad2;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class ExpungeThread implements Runnable {
    private boolean[] primes;
    private final List<Integer> smallPrimes;
    private int amountOfThreads;
    private int threadNumber;
    private static Integer nextFree = 0;
    private Thread[] threads;
    private boolean[] finishedIteration;
    private static int chooseImplementation = 1;

    public ExpungeThread(boolean[] primes, List<Integer> smallPrimes, int amountOfThreads, int threadNumber, Thread[] threads, boolean[] finishedIteration) {
        this.primes = primes;
        this.smallPrimes = smallPrimes;
        this.amountOfThreads = amountOfThreads;
        this.threadNumber = threadNumber;
        this.threads = threads;
        this.finishedIteration = finishedIteration;

    }

    @Override
    public void run() {
        if(chooseImplementation == 0)
            implementationA();
        else if(chooseImplementation == 1)
            implementationB();
        else if(chooseImplementation == 2)
            implementationC();
        else
            System.out.println("Nie wybrano implemanetacji");
    }

    public void implementationA() {
        int searchFor;
        int upperBound = primes.length;
        do {

            synchronized (nextFree) {
                searchFor = nextFree;
                nextFree++;
            }

            if (searchFor >= smallPrimes.size())
                break;

            int smallPrimesNumber = smallPrimes.get(searchFor);

            for (int i = 2; i * smallPrimesNumber < upperBound; i++) {
                primes[i * smallPrimesNumber] = false;
            }

        } while(true);
    }

    public void implementationB() {
        do {
            writeOutNumbers(smallPrimes.get(nextFree));

            synchronized (finishedIteration) {
                finishedIteration[threadNumber-1] = true;

                if(isWholeArrayTrue(finishedIteration)) {
                    nextFree++;
                    if(nextFree >= smallPrimes.size()) {
                        for (Thread i : threads)
                            i.interrupt();
                    }
                    finishedIteration.notifyAll();
                    Arrays.fill(finishedIteration, false);

                } else {
                    try {
                        finishedIteration.wait();
                    } catch (InterruptedException e) {
                        return;
                    }
                }
            }
        } while(nextFree < smallPrimes.size());

    }

    public void implementationC() {

    }

    private void writeOutNumbers(int s) {
        int flag = 2;
        if(s == 2)
            flag = 1;
        int formula;

        formula = (int) Math.pow(s, 2) + (threadNumber - 1) * flag * s;
        for (int i = 1; formula < primes.length; ++i) {
            primes[formula] = false;
            formula = (int) Math.pow(s, 2) + flag * i * amountOfThreads * s + (threadNumber - 1) * flag * s;
        }

    }

    private boolean isWholeArrayTrue(boolean[] arr) {
        for (boolean b : arr) {
            if (!b) {
                return false;
            }
        }
        return true;
    }
}
