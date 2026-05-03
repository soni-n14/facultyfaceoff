package com.apcsa;

import com.apcsa.combat.enemies.BFB;

public class WaveManager {

    private static int wave = 0;
    private static boolean waveCompleted = true;

    public static void runIt() {
        Thread waveThread = new Thread(/*you guys have to add something to run here*/);
        waveThread.setDaemon(true);
        waveThread.start();
    }
}