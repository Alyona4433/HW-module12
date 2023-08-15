package task2;

import java.util.concurrent.Semaphore;

public class FizzBuzzMultithreaded {
    private int n;
    private volatile int currentNumber;
    private Semaphore fizzSem, buzzSem, fizzBuzzSem, numberSem;

    public FizzBuzzMultithreaded(int n) {
        this.n = n;
        currentNumber = 1;
        fizzSem = new Semaphore(0);
        buzzSem = new Semaphore(0);
        fizzBuzzSem = new Semaphore(0);
        numberSem = new Semaphore(1);
    }

    public void fizz(Runnable printFizz) throws InterruptedException {
        for (int i = 3; i <= n; i += 3) {
            if (i % 5 != 0) {
                fizzSem.acquire();
                printFizz.run();
                releaseNext();
            }
        }
    }

    public void buzz(Runnable printBuzz) throws InterruptedException {
        for (int i = 5; i <= n; i += 5) {
            if (i % 3 != 0) {
                buzzSem.acquire();
                printBuzz.run();
                releaseNext();
            }
        }
    }

    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for (int i = 15; i <= n; i += 15) {
            fizzBuzzSem.acquire();
            printFizzBuzz.run();
            releaseNext();
        }
    }

    public void number(Runnable printNumber) throws InterruptedException {
        while (currentNumber <= n) {
            numberSem.acquire();
            if (currentNumber % 3 == 0 && currentNumber % 5 == 0) {
                fizzBuzzSem.release();
            } else if (currentNumber % 3 == 0) {
                fizzSem.release();
            } else if (currentNumber % 5 == 0) {
                buzzSem.release();
            } else {
                printNumber.run();
                releaseNext();
            }
        }
    }

    private void releaseNext() {
        currentNumber++;
        if (currentNumber <= n) {
            numberSem.release();
        }
    }

    public static void main(String[] args) {
        int n = 15;
        FizzBuzzMultithreaded fizzBuzz = new FizzBuzzMultithreaded(n);

        Thread threadA = new Thread(() -> {
            try {
                fizzBuzz.fizz(() -> System.out.print("fizz, "));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(() -> {
            try {
                fizzBuzz.buzz(() -> System.out.print("buzz, "));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadC = new Thread(() -> {
            try {
                fizzBuzz.fizzbuzz(() -> System.out.print("fizzbuzz, "));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadD = new Thread(() -> {
            try {
                fizzBuzz.number(() -> System.out.print(fizzBuzz.currentNumber + (fizzBuzz.currentNumber < fizzBuzz.n ? ", " : ". ")));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        try {
            threadA.join();
            threadB.join();
            threadC.join();
            threadD.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
