package com.apcsa;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import com.apcsa.combat.Enemy;
import com.apcsa.combat.Tower;

public class GameWorld {

    public static CopyOnWriteArrayList<Enemy> enemies = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Tower> towers = new CopyOnWriteArrayList<>();

    public static void startGameLoop() {
        Thread gameThread = new Thread(() -> runGameLoop());
        gameThread.setDaemon(true);
        gameThread.start();
    }

    private static void runGameLoop() {

        long lastTime = System.nanoTime();

        while (true) {

            long now = System.nanoTime();
            double deltaTime = (now - lastTime) / 1_000_000_000.0;
            lastTime = now;


            for (Enemy enemy : enemies) {

                if (enemy.isDead()) {
                    Animations.removeEnemy(enemy);
                    enemies.remove(enemy);
                } 
                else {
                    enemy.update(deltaTime);
                    Animations.updateUnanimatedEnemy(enemy);
                }
            }
            
            if(enemies.isEmpty())
                WaveManager.enemiesIsEmpty();

            for (Tower tower : towers) {

                if (tower.isRemoved()) {
                    Animations.removeTower(tower);
                    towers.remove(tower);
                } 
                else {
                    tower.update(deltaTime, new ArrayList<>(enemies));
                    Animations.updateTower(tower);
                }
            }

            try {
                Thread.sleep(16);
            }
            catch (InterruptedException e) {
                break;
            }
        }
    }
}