package org.example;


class MessageTask implements Runnable {
    @Override
    public void run() {
        while (true) {
            System.out.println("Минуло 5 секунд");

            try {
                Thread.sleep(5000); // Затримка 5 секунд
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}