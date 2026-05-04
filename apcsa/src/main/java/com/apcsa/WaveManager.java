package com.apcsa;

import com.apcsa.combat.enemies.*;

import javafx.application.Platform;

public class WaveManager {

    private static int wave = 0;
    private static boolean waveCompleted = false;
    private static int waveCooldown = 5;
    private static double X = 0;
    private static double Y = 1;
    
    private static boolean cooldownRunning = false;

    public static boolean allEnemiesOut = false;
        
    public static void runWaves(){
        nextWave();
    }

    private static void waveDoneNowCooldown(){
        for(int c = waveCooldown; c >= 0; c--){

            int b = c; //idk why u gotta do this but i searched and u gotta

            Platform.runLater(() -> {
                Main.waveText.setText("Next wave starts in "+b);
            });

            pause(1.0);

        }
        nextWave();
    }

    private static void nextWave(){
        wave++;
        actualWaveDataRunner();
    }


    /**
     * Creates new thread and calles runWaves, which runs the whole wave system
     */
    public static void runIt() {
        Thread waveThread = new Thread(() -> runWaves());
        waveThread.setDaemon(true);
        waveThread.start();
    }

    public static void actualWaveDataRunner(){

        Platform.runLater(() -> {
            Main.waveText.setText("Wave " + wave);
        });

        switch(wave){
            case 1:
                new OtherStateFulk(X,Y);
                pause(1.0);
                new OtherStateFulk(X,Y);
                break;
            case 2:
                new OtherStateFulk(X,Y);
                pause(1.0);
                new OtherStateFulk(X,Y);
                pause(1.0);
                new minionFulk(X, Y);
                break;
            case 3:
                new minionFulk(X, Y);
                pause(0.5);
                new minionFulk(X, Y);
                pause(0.5);
                new minionFulk(X, Y);
                pause(1.5);
                new minionFulk(X, Y);
                pause(0.5);
                new minionFulk(X, Y);
                pause(0.5);
                new minionFulk(X, Y);
            default:
                pause(3);
        }

        allEnemiesOut = true;

    }

    public static void pause(double time){
        try {
            Thread.sleep((int)(time*1000));
        } catch (InterruptedException e) {
            return;
        }
    }

    public static void enemiesIsEmpty() {
        if (allEnemiesOut == true&& !cooldownRunning) {
            allEnemiesOut = false;
            cooldownRunning = true;

            Thread cooldownThread = new Thread(() -> { waveDoneNowCooldown(); cooldownRunning = false; });

            cooldownThread.setDaemon(true);
            cooldownThread.start();
        }
    }

}