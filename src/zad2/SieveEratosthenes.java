package zad2;

public class SieveEratosthenes {
    public final Integer mill = 1000000;
    public final Integer tenMill = 10000000;
    public final Integer hundredMill = 100000000;
    public final Integer billion = 1000000000;

    private final int[] sieve = new int[tenMill];

    public static void main(String[] args) {
        SieveEratosthenes sieveEratosthenes = new SieveEratosthenes();

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }




}
