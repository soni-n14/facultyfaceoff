package com.apcsa.combat.towers;
import java.util.List;

import com.apcsa.combat.Enemy;
import com.apcsa.combat.Tower;
import com.apcsa.world.Money;

/**
 * A non-combat tower that generates gold for the player at the end of each wave.
 */
public class Farm extends Tower {
    
    public static final int BASE_COST = 100;
    public static final int STARTER_RANGE = 2;

    private int cashPerWave;
    
    public Farm(double tX, double tY){
        super(tX, tY);

        maxLevel = 3;
        sellValue = BASE_COST / 2;
        cooldownTimer = cooldown;

        updateStats(level);
    }

    @Override
    public void update(double deltaTime, List<Enemy> enemies){
        updateAnimation(deltaTime);
    }

    /**
     * Adds this farm's per-wave cash income to the player's money total.
     */
    public void doYoThing(){
        Money.addMoney(cashPerWave);
    }

    /**
     * Updates cash income and other stats based on the current upgrade level.
     *
     * @param level the new level to apply stats for
     */
    @Override
    public void updateStats(int level){
        switch(level){
            case 1:
                cashPerWave = 50;

                damage = 0;
                range = 2;
                cooldown = 1000;
                upgradeCost = 250;
                bulletSpeed = 0.0;
                break;
            case 2:
                cashPerWave = 100;

                damage = 0;
                range = 2;
                cooldown = 1000;
                upgradeCost = 600;
                bulletSpeed = 0.0;
                break;
            case 3:
                cashPerWave = 250;

                damage = 0;
                range = 2;
                cooldown = 1000;
                upgradeCost = -1;
                bulletSpeed = 0.0;
                break;
        }
    }

}
