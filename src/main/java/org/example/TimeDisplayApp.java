package org.example;

public class TimeDisplayApp {
    public static void main(String[] args) {
        Thread timerThread = new Thread(new TimerTask());
        Thread messageThread = new Thread(new MessageTask());

        timerThread.start();
        messageThread.start();
    }
}