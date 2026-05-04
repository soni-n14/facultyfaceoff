package com.apcsa.combat;

import java.util.List;

import com.apcsa.Animations;
import com.apcsa.GameWorld;
import javafx.scene.image.*;

public abstract class Tower {

    //enum
    public enum TowerState {
        IDLE,
        COOLDOWN,
        SHOOTING
    }

    //fields
    public static final double OCCUPANCY_SPACE = 1.0;

    protected int damage;
    protected int range;
    protected double cooldown;
    protected int upgradeCost;
    protected int level;
    protected int maxLevel;

    protected int tileX;
    protected int tileY;

    protected Enemy target;
    protected double cooldownTimer;
    protected double shootAnimationTimer;
    protected TowerState state;

    protected boolean removed;

    protected double rotationDeg;

    protected int animationFrame;
    protected double animationFrameTimer;
    protected int animationFrameCount = 7;
    protected double animationFrameDuration = 0.2;

    protected double bulletSpeed;
    protected Image bullet;

    //constructor
    public Tower(int tX, int tY){
        level = 1;
        tileX = tX;
        tileY = tY;
        state = TowerState.IDLE;
        shootAnimationTimer = 0;
        removed = false;
        GameWorld.towers.add(this);

        String path = "/fxml/sprites/" + this.getClass().getSimpleName() + "/BULLET.png";
        //bullet = new Image(Animations.class.getResourceAsStream(path));
    }

    //non-abstract methods
    /**
     * Checks if player canUpgrade
     * @param money
     * @return true if there is a next level and money is greater than or equal to cost
     */
    public boolean canUpgrade(int money){
        if (level < maxLevel)
            return money>=upgradeCost;

        return false;
    }
   
    public boolean isRemoved(){
        return removed;
    }

    public void remove() {
        removed = true;
    }

    public void updateAnimation(double deltaTime){
        animationFrameTimer += deltaTime;
        while (animationFrameTimer >= animationFrameDuration){
            animationFrameTimer -= animationFrameDuration;
            animationFrame = (animationFrame + 1) % animationFrameCount;
        }
    }

    /**
     * Checks to see if player can upgrade, if they can, it increases their level, calls function to update stats
     * @param money
     */
    public boolean upgrade(int money){
        if (canUpgrade(money)){
            level++;
            updateStats(level);
            return true;
        }
        return false;
    }

    /**
     * Finds the first living enemy that is inside this tower's range.
     *
     * @param enemies the list of enemies currently in the game
     * @return the first enemy in range, or null if no enemy is in range
     */
    public Enemy findTarget(List<Enemy> enemies) {
        Enemy farthest = null;
        double farthestX = -999999;

        for (Enemy enemy : enemies) {
            if (enemy != null && !enemy.isDead() && isInRange(enemy)) {
                if (enemy.getTileX() > farthestX) {
                    farthestX = enemy.getTileX();
                    farthest = enemy;
                }
            }
        }

        return farthest;
    }

    /**
     * Checks whether an enemy is close enough for this tower to attack.
     *
     * @param enemy the enemy being checked
     * @return true if the enemy is within this tower's range
     */
    public boolean isInRange(Enemy enemy) {
        double dx = enemy.getTileX() - tileX;
        double dy = enemy.getTileY() - tileY;

        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance <= range;
    }

    /**
     * Makes this tower face its current target.
     * This can be expanded later when animations/rotation are added.
     *
     * @param enemy the enemy this tower should face
     */
    public void faceTarget(Enemy enemy) {
        double dx = enemy.getTileX() - tileX;
        double dy = enemy.getTileY() - tileY;

        rotationDeg = Math.toDegrees(Math.atan2(dy, dx)) - 90;
    }

    /**
     * The tower looks for a target, faces the target, waits for its cooldown,
     * and attacks when it is ready.
     *
     * @param deltaTime the amount of time, in seconds, since the last update
     * @param enemies the list of enemies currently in the game
     */
    public void update(double deltaTime, List<Enemy> enemies) {
        target = findTarget(enemies);

        if (target == null || target.isDead()) {

            if (shootAnimationTimer > 0) {
                shootAnimationTimer -= deltaTime;
                state = TowerState.SHOOTING;
            } 
            else {
                state = TowerState.IDLE;
                cooldownTimer = cooldown;
            }
        }
        else {
            faceTarget(target);

            if (shootAnimationTimer > 0) {
                shootAnimationTimer -= deltaTime;
                state = TowerState.SHOOTING;
            } 
            else if (cooldownTimer > 0) {
                cooldownTimer -= deltaTime;
                state = TowerState.COOLDOWN;
            } 
            else {
                attack(target);
                shootAnimationTimer = 0.2;
                cooldownTimer = cooldown;
                state = TowerState.SHOOTING;
            }
        }

        updateAnimation(deltaTime);
    }

    /**
     * Attacks the given enemy by dealing this tower's damage.
     *
     * @param enemy the enemy being attacked
     */
    public void attack(Enemy enemy) {
        if (enemy != null && !enemy.isDead()) {
            enemy.takeDamage(damage);
        }
    }

    //getters
    public String getName(){
        return getClass().getSimpleName();
    }
    public int getDamage() {
        return damage;
    }
    public int getRange() {
        return range;
    }
    public double getTileX() {
        return tileX;
    }
    public double getTileY() {
        return tileY;
    }
    public double getCooldown() {
        return cooldown;
    }
    public int getUpgradeCost() {
        return upgradeCost;
    }
    public int getLevel() {
        return level;
    }
    public int getMaxLevel() {
        return maxLevel;
    }
    public TowerState getState() {
        return state;
    }
    public Enemy getTarget() {
        return target;
    }
    public int getAnimationFrame(){
        return animationFrame;
    }
    public int getAnimationFrameCount(){
        return animationFrameCount;
    }
    public double getRotationDeg(){
        return rotationDeg;
    }

    //abstract methods
    /**
     * Changes damage, range, cooldown and cost based on level
     * @param level
     */
    public abstract void updateStats(int level);
}