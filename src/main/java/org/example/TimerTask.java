package org.example;

import java.util.concurrent.TimeUnit;


class TimerTask implements Runnable {
    @Override
    public void run() {
        long startTime = System.currentTimeMillis();

        while (true) {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - startTime;

            long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsedTime);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsedTime);
            long hours = TimeUnit.MILLISECONDS.toHours(elapsedTime);

            System.out.printf("Час: %02d:%02d:%02d%n", hours, minutes, seconds);

            try {
                Thread.sleep(1000); // Затримка 1 секунда
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
