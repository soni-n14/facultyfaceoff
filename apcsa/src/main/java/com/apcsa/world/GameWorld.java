package com.apcsa.world;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import com.apcsa.combat.Enemy;
import com.apcsa.combat.Tower;
import com.apcsa.ui.Animations;
import com.apcsa.waves.WaveManager;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Holds all live game state and runs the main game loop that updates enemies, towers, and animations each frame.
 */
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


    /**
     * Spawns a daemon thread that runs the game loop continuously until the application exits.
     */
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

    /**
     * Draws the path tiles on the canvas and marks each tile as occupied so towers cannot be placed on them.
     *
     * @param gc the GraphicsContext used to fill the path tiles
     */
    public static void paintPathAndOccupy(GraphicsContext gc) {
        gc.setFill(Color.LIGHTGREEN);

        for (int i = 1; i < pathPoints.length; i++) {
            double x1 = pathPoints[i - 1].getX();
            double y1 = pathPoints[i - 1].getY();
            double x2 = pathPoints[i].getX();
            double y2 = pathPoints[i].getY();

            for (double x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) {
                for (double y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                    Point2D p = new Point2D(x, y);
                    gc.fillRect((x - 0.5) * 64, (y - 0.5) * 64, 64, 64);
                    if (!occupied.contains(p)) occupied.add(p);
                }
            }
        }
    }

}