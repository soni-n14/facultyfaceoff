package com.apcsa.world;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import com.apcsa.combat.Enemy;
import com.apcsa.combat.Tower;
import com.apcsa.ui.Animations;
import com.apcsa.waves.WaveManager;

import javafx.geometry.Point2D;

public class GameWorld {

    public static CopyOnWriteArrayList<Enemy> enemies = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Tower> towers = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Point2D> occupied = new CopyOnWriteArrayList<>();

    public static Point2D[] pathPoints = {
        new Point2D(0.5, 1.5), 
        //pathIndex 1
        new Point2D(4.5, 1.5), 
        //2
        new Point2D(4.5, 4.5),
        //3
        new Point2D(8.5, 4.5),
        //4
        new Point2D(8.5, 2.5), 
        //5
        new Point2D(12.5, 2.5) //when reach here should destroy enemy
    };


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
                    Money.addMoney(enemy.getReward());
                    Animations.removeEnemy(enemy);
                    enemies.remove(enemy);
                } 

                else if(enemy.hasReachedEnd()){
                    Health.subtractHealth(enemy.getHp());
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