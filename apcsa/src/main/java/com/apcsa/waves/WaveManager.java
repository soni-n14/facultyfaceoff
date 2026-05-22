package com.apcsa.waves;

import com.apcsa.Main;
import com.apcsa.world.GameWorld;
import com.apcsa.world.Money;
import com.apcsa.combat.*;
import com.apcsa.combat.enemies.*;
import com.apcsa.combat.towers.Farm;

import javafx.application.Platform;

/**
 * Controls the wave lifecycle: spawning enemies per wave, counting down timers, and triggering the inter-wave cooldown.
 */
public class WaveManager {

    private static int wave = 0;
    private static boolean waveCompleted = false;
    private static int waveCooldown = 5;
    private static double X = 0.5;
    private static double Y = 1.5;
    
    private static boolean cooldownRunning = false;
    public static boolean running = true;

    public static boolean allEnemiesOut = false;
        
    public static void resetWave() {
        wave = 0;
        running = false;
        waveCompleted = true;
    }
        
    public static void runWaves(){
        running = true;
        nextWave();
    }

    private static void timerTickDown(){
        for (int c = 15; c >= 0; c--) {
            if (waveCompleted || !running) return;

            int b = c;
            Platform.runLater(() -> {
                Main.timeText.setText("0:" + String.format("%02d", b));
            });

            pause(1.0);
        }

        if (waveCompleted || cooldownRunning || !running) return;

        waveCompleted = true;
        cooldownRunning = true;
        Money.addMoney(100);
        waveDoneNowCooldown();
        cooldownRunning = false;
    }

    private static void waveDoneNowCooldown(){
        for(int c = waveCooldown; c >= 0; c--){
            if (!running) return;

            int b = c; //idk why u gotta do this but i searched and u gotta

            Platform.runLater(() -> {
                Main.timeText.setText("0:" + String.format("%02d", b));
            });

            pause(1.0);

        }
        if (running) nextWave();
    }

    public static void skip() {
        if (cooldownRunning || !waveCompleted) {
            System.out.println("skipping the wave now...");
            waveCompleted = true;
            cooldownRunning = false;
            // dont need to add money here if we want but user said cost 500
        }
    }

    private static void nextWave(){
        wave++;
        waveCompleted = false;
        allEnemiesOut = false;

        for(Tower t : GameWorld.towers){
            if (t instanceof Farm){
                ((Farm) t).doYoThing();
            }
        }

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

    /**
     * Starts the countdown timer thread, updates the wave label, and spawns the enemy sequence for the current wave number.
     */
    public static void actualWaveDataRunner(){

        Thread timerThread = new Thread(() -> timerTickDown());
        timerThread.setDaemon(true);
        timerThread.start();

        Platform.runLater(() -> {
            Main.waveText.setText("Wave " + wave);
        });

        switch(wave){
            case 1:
                new OtherStateFulk(X, Y);
                pause(0.9);
                new OtherStateFulk(X, Y);
                pause(0.9);
                new OtherStateFulk(X, Y);
                pause(0.9);
                new OtherStateFulk(X, Y);
                pause(0.9);
                new OtherStateFulk(X, Y);
                break;

            case 2:
                new OtherStateFulk(X, Y);
                pause(0.7);
                new OtherStateFulk(X, Y);
                pause(0.7);
                new MinionFulk(X, Y);
                pause(0.7);
                new OtherStateFulk(X, Y);
                pause(0.7);
                new MinionFulk(X, Y);
                pause(0.7);
                new OtherStateFulk(X, Y);
                break;

            case 3:
                new MinionFulk(X, Y);
                pause(0.45);
                new MinionFulk(X, Y);
                pause(0.45);
                new MinionFulk(X, Y);
                pause(0.45);
                new OtherStateFulk(X, Y);
                pause(0.45);
                new OtherStateFulk(X, Y);
                pause(0.45);
                new MinionFulk(X, Y);
                pause(0.45);
                new MinionFulk(X, Y);
                pause(0.45);
                new OtherStateFulk(X, Y);
                break;

            case 4:
                new MinionFulk(X, Y);
                pause(0.4);
                new MinionFulk(X, Y);
                pause(0.4);
                new OtherStateFulk(X, Y);
                pause(0.35);
                new OtherStateFulk(X, Y);
                pause(0.35);
                new MinionFulk(X, Y);
                pause(0.4);
                new MinionFulk(X, Y);
                pause(0.4);
                new ScoutFulk(X, Y);
                pause(0.3);
                new ScoutFulk(X, Y);
                break;

            case 5:
                new ScoutFulk(X, Y);
                pause(0.25);
                new ScoutFulk(X, Y);
                pause(0.25);
                new MinionFulk(X, Y);
                pause(0.35);
                new MinionFulk(X, Y);
                pause(0.35);
                new MinionFulk(X, Y);
                pause(0.35);
                new OtherStateFulk(X, Y);
                pause(0.3);
                new ScoutFulk(X, Y);
                pause(0.25);
                new ScoutFulk(X, Y);
                pause(0.25);
                new MinionFulk(X, Y);
                break;

            case 6:
                new SoldierFulk(X, Y);
                pause(0.7);
                new MinionFulk(X, Y);
                pause(0.3);
                new MinionFulk(X, Y);
                pause(0.3);
                new ScoutFulk(X, Y);
                pause(0.2);
                new ScoutFulk(X, Y);
                pause(0.2);
                new OtherStateFulk(X, Y);
                pause(0.25);
                new OtherStateFulk(X, Y);
                pause(0.25);
                new MinionFulk(X, Y);
                pause(0.3);
                new SoldierFulk(X, Y);
                break;

            case 7:
                new SoldierFulk(X, Y);
                pause(0.55);
                new ScoutFulk(X, Y);
                pause(0.18);
                new ScoutFulk(X, Y);
                pause(0.18);
                new ScoutFulk(X, Y);
                pause(0.18);
                new MinionFulk(X, Y);
                pause(0.25);
                new MinionFulk(X, Y);
                pause(0.25);
                new SoldierFulk(X, Y);
                pause(0.55);
                new OtherStateFulk(X, Y);
                pause(0.2);
                new OtherStateFulk(X, Y);
                pause(0.2);
                new MinionFulk(X, Y);
                break;

            case 8:
                new BruteFulk(X, Y);
                pause(1.0);
                new ScoutFulk(X, Y);
                pause(0.15);
                new ScoutFulk(X, Y);
                pause(0.15);
                new ScoutFulk(X, Y);
                pause(0.15);
                new MinionFulk(X, Y);
                pause(0.25);
                new MinionFulk(X, Y);
                pause(0.25);
                new SoldierFulk(X, Y);
                pause(0.45);
                new ScoutFulk(X, Y);
                pause(0.15);
                new ScoutFulk(X, Y);
                pause(0.15);
                new MinionFulk(X, Y);
                pause(0.25);
                new BruteFulk(X, Y);
                break;

            case 9:
                new SoldierFulk(X, Y);
                pause(0.4);
                new SoldierFulk(X, Y);
                pause(0.4);
                new ScoutFulk(X, Y);
                pause(0.15);
                new ScoutFulk(X, Y);
                pause(0.15);
                new ScoutFulk(X, Y);
                pause(0.15);
                new ScoutFulk(X, Y);
                pause(0.15);
                new MinionFulk(X, Y);
                pause(0.2);
                new MinionFulk(X, Y);
                pause(0.2);
                new MinionFulk(X, Y);
                pause(0.2);
                new OtherStateFulk(X, Y);
                pause(0.2);
                new OtherStateFulk(X, Y);
                pause(0.2);
                new BruteFulk(X, Y);
                break;

            case 10:
                new BruteFulk(X, Y);
                pause(0.8);
                new SoldierFulk(X, Y);
                pause(0.35);
                new SoldierFulk(X, Y);
                pause(0.35);
                new MinionFulk(X, Y);
                pause(0.18);
                new MinionFulk(X, Y);
                pause(0.18);
                new MinionFulk(X, Y);
                pause(0.18);
                new ScoutFulk(X, Y);
                pause(0.12);
                new ScoutFulk(X, Y);
                pause(0.12);
                new ScoutFulk(X, Y);
                pause(0.12);
                new ScoutFulk(X, Y);
                pause(0.12);
                new SoldierFulk(X, Y);
                pause(0.35);
                new BruteFulk(X, Y);
                break;

            case 11:
                new BruteFulk(X, Y);
                pause(0.7);
                new BruteFulk(X, Y);
                pause(0.7);
                new SoldierFulk(X, Y);
                pause(0.3);
                new SoldierFulk(X, Y);
                pause(0.3);
                new ScoutFulk(X, Y);
                pause(0.1);
                new ScoutFulk(X, Y);
                pause(0.1);
                new ScoutFulk(X, Y);
                pause(0.1);
                new ScoutFulk(X, Y);
                pause(0.1);
                new MinionFulk(X, Y);
                pause(0.16);
                new MinionFulk(X, Y);
                pause(0.16);
                new MinionFulk(X, Y);
                pause(0.16);
                new MinionFulk(X, Y);
                pause(0.16);
                new SoldierFulk(X, Y);
                pause(0.3);
                new BruteFulk(X, Y);
                break;

            case 12:
                new BruteFulk(X, Y);
                pause(0.65);
                new SoldierFulk(X, Y);
                pause(0.25);
                new SoldierFulk(X, Y);
                pause(0.25);
                new SoldierFulk(X, Y);
                pause(0.25);
                new ScoutFulk(X, Y);
                pause(0.09);
                new ScoutFulk(X, Y);
                pause(0.09);
                new ScoutFulk(X, Y);
                pause(0.09);
                new ScoutFulk(X, Y);
                pause(0.09);
                new ScoutFulk(X, Y);
                pause(0.09);
                new MinionFulk(X, Y);
                pause(0.14);
                new MinionFulk(X, Y);
                pause(0.14);
                new MinionFulk(X, Y);
                pause(0.14);
                new OtherStateFulk(X, Y);
                pause(0.14);
                new OtherStateFulk(X, Y);
                pause(0.14);
                new BruteFulk(X, Y);
                pause(0.65);
                new SoldierFulk(X, Y);
                break;

            case 13:
                new BruteFulk(X, Y);
                pause(0.55);
                new BruteFulk(X, Y);
                pause(0.55);
                new SoldierFulk(X, Y);
                pause(0.22);
                new SoldierFulk(X, Y);
                pause(0.22);
                new SoldierFulk(X, Y);
                pause(0.22);
                new MinionFulk(X, Y);
                pause(0.12);
                new MinionFulk(X, Y);
                pause(0.12);
                new MinionFulk(X, Y);
                pause(0.12);
                new MinionFulk(X, Y);
                pause(0.12);
                new ScoutFulk(X, Y);
                pause(0.08);
                new ScoutFulk(X, Y);
                pause(0.08);
                new ScoutFulk(X, Y);
                pause(0.08);
                new ScoutFulk(X, Y);
                pause(0.08);
                new ScoutFulk(X, Y);
                pause(0.08);
                new SoldierFulk(X, Y);
                pause(0.22);
                new BruteFulk(X, Y);
                break;

            case 14:
                new BruteFulk(X, Y);
                pause(0.5);
                new SoldierFulk(X, Y);
                pause(0.18);
                new SoldierFulk(X, Y);
                pause(0.18);
                new SoldierFulk(X, Y);
                pause(0.18);
                new SoldierFulk(X, Y);
                pause(0.18);
                new ScoutFulk(X, Y);
                pause(0.07);
                new ScoutFulk(X, Y);
                pause(0.07);
                new ScoutFulk(X, Y);
                pause(0.07);
                new ScoutFulk(X, Y);
                pause(0.07);
                new ScoutFulk(X, Y);
                pause(0.07);
                new ScoutFulk(X, Y);
                pause(0.07);
                new MinionFulk(X, Y);
                pause(0.11);
                new MinionFulk(X, Y);
                pause(0.11);
                new MinionFulk(X, Y);
                pause(0.11);
                new MinionFulk(X, Y);
                pause(0.11);
                new BruteFulk(X, Y);
                pause(0.5);
                new BruteFulk(X, Y);
                break;

            case 15:
                new BruteFulk(X, Y);
                pause(0.45);
                new BruteFulk(X, Y);
                pause(0.45);
                new SoldierFulk(X, Y);
                pause(0.16);
                new SoldierFulk(X, Y);
                pause(0.16);
                new SoldierFulk(X, Y);
                pause(0.16);
                new SoldierFulk(X, Y);
                pause(0.16);
                new MinionFulk(X, Y);
                pause(0.1);
                new MinionFulk(X, Y);
                pause(0.1);
                new MinionFulk(X, Y);
                pause(0.1);
                new MinionFulk(X, Y);
                pause(0.1);
                new ScoutFulk(X, Y);
                pause(0.06);
                new ScoutFulk(X, Y);
                pause(0.06);
                new ScoutFulk(X, Y);
                pause(0.06);
                new ScoutFulk(X, Y);
                pause(0.06);
                new ScoutFulk(X, Y);
                pause(0.06);
                new ScoutFulk(X, Y);
                pause(0.06);
                new ScoutFulk(X, Y);
                pause(0.06);
                new SoldierFulk(X, Y);
                pause(0.16);
                new SoldierFulk(X, Y);
                pause(0.16);
                new BruteFulk(X, Y);
                pause(0.45);
                new BruteFulk(X, Y);
                break;

            default:
                pause(3);
        }

        allEnemiesOut = true;

    }

    /**
     * Blocks the current thread for the specified number of seconds to space out enemy spawns.
     *
     * @param time the duration to sleep in seconds
     */
    public static void pause(double time){
        try {
            Thread.sleep((int)(time*1000));
        } catch (InterruptedException e) {
            return;
        }
    }

    /**
     * Called by the game loop when the enemy list empties mid-wave; awards bonus gold and starts the cooldown early.
     */
    public static void enemiesIsEmpty() {
        if (allEnemiesOut == true&& !cooldownRunning && !waveCompleted) {
            allEnemiesOut = false;
            cooldownRunning = true;
            waveCompleted = true;

            Thread cooldownThread = new Thread(() -> { 
                waveCompleted = true; 
                Money.addMoney(250); 
                waveDoneNowCooldown(); 
                cooldownRunning = false; 
            });

            cooldownThread.setDaemon(true);
            cooldownThread.start();
        }
    }

}