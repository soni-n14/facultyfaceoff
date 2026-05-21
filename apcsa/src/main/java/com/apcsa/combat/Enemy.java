package com.apcsa.combat;

import com.apcsa.world.GameWorld;
import javafx.geometry.Point2D;

/**
 * Abstract base class for all enemy types that move along the path and can take damage.
 */
public abstract class Enemy {

    protected int hp;
    protected int maxHp;
    protected double speed;
    protected int reward;

    protected double tileX;
    protected double tileY;

    protected boolean dead;
    protected boolean reachedEnd;
    protected int pathIndex;

    public Enemy(double tX, double tY, int hpp, double spee, int rewar) {
        tileX = tX;
        tileY = tY;

        maxHp = hpp;
        hp = maxHp;
        speed = spee;
        reward = rewar;

        dead = false;
        reachedEnd = false;
        pathIndex = 1;

        GameWorld.enemies.add(this);
    }
    /**
     * Updates enemy for one frame of the game.
     * For now, this moves the enemy to the right based on its speed.
     *
     * @param deltaTime the amount of time, in seconds, since last update
     */
    public void update(double deltaTime) {

        Point2D goingToPoint = GameWorld.pathPoints[pathIndex];
        Point2D comingFromPoint = GameWorld.pathPoints[pathIndex-1];

        Point2D hypotenusePoint = goingToPoint.subtract(comingFromPoint);

        double vectorX = hypotenusePoint.getX();
        double vectorY = hypotenusePoint.getY();
        double hypotenuseDistance = Math.sqrt(vectorX*vectorX + vectorY*vectorY);
        
        double currentHypotenuseDistance = Math.sqrt((goingToPoint.getX()-getTileX())*(goingToPoint.getX()-getTileX()) + (goingToPoint.getY()-getTileY())*(goingToPoint.getY()-getTileY()));
        
        if(currentHypotenuseDistance <= speed*deltaTime){
            tileX = goingToPoint.getX();
            tileY = goingToPoint.getY();
            pathIndex++;
            if (pathIndex >= GameWorld.pathPoints.length) {
                reachedEnd = true;
            }
            return;
        }

        tileX += (speed * deltaTime*vectorX)/hypotenuseDistance;
        tileY += (speed * deltaTime*vectorY)/hypotenuseDistance;
    }

    /**
     * Damages this enemy. If health reaches 0, enemy dies
     *
     * @param damage the amount of damage taken
     */
    public void takeDamage(int damage) {
        hp -= damage;

        if (hp <= 0) {
            hp = 0;
            dead = true;
        }
    }

    public boolean isDead() {
        return dead;
    }

    public boolean hasReachedEnd() {
        return reachedEnd;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public double getSpeed() {
        return speed;
    }

    public int getReward() {
        return reward;
    }

    public double getTileX() {
        return tileX;
    }

    public double getTileY() {
        return tileY;
    }
}