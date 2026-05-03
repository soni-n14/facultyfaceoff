package com.apcsa;

import java.util.*;

import com.apcsa.combat.Enemy;
import com.apcsa.combat.Tower;

public class GameWorld {

    public static ArrayList<Enemy> enemies = new ArrayList<>();
    public static ArrayList<Tower> towers = new ArrayList<>();

    public static void startGameLoop() {
        Thread gameThread = new Thread(() -> runGameLoop());
        gameThread.setDaemon(true);
        gameThread.start();
    }

    private static void runGameLoop() {

        long lastTime = System.nanoTime();

        while (true) {

            long now = System.nanoTime();
            double deltaTime =
                (now - lastTime) / 1_000_000_000.0;

            lastTime = now;

            Iterator<Enemy> enemyIt = enemies.iterator();

            while (enemyIt.hasNext()) {

                Enemy enemy = enemyIt.next();

                if (enemy.isDead()) {

                    Animations.removeEnemy(enemy);
                    enemyIt.remove();

                } 

                else {

                    enemy.update(deltaTime);
                    Animations.updateUnanimatedEnemy(enemy);
                }
            }

            Iterator<Tower> towerIt = towers.iterator();

            while (towerIt.hasNext()) {

                Tower tower = towerIt.next();

                if (tower.isRemoved()) {

                    Animations.removeTower(tower);
                    towerIt.remove();

                } else {

                    tower.update(deltaTime, enemies);
                    Animations.updateTower(tower);
                }
            }

            //debug logs

            for (Enemy enemy : enemies) {
                //System.out.println("Enemy HP: " + enemy.getHp());
            }

            for (Tower tower : towers) {
                System.out.println("Tower state: " + tower.getState());
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