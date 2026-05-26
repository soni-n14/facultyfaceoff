package com.apcsa.combat.towers;
import com.apcsa.combat.Tower;

/**
 * A high-damage sniper tower with long range and a slow fire rate.
 */
public class Welsh extends Tower {

    public static final int BASE_COST = 350;
    public static final int STARTER_RANGE = 6;

    public Welsh(double tX, double tY){
        super(tX, tY);

        maxLevel = 3;
        sellValue = BASE_COST / 2;
        cooldownTimer = cooldown;

        updateStats(level);
    }

    @Override
    public void updateStats(int level){
        switch(level){
            case 1:
                damage = 8;
                range = 6;
                cooldown = 2.5;
                upgradeCost = 700;
                bulletSpeed = 15.0;
                break;
            case 2:
                damage = 13;
                range = 7;
                cooldown = 2.2;
                upgradeCost = 1200;
                bulletSpeed = 15.0;
                break;
            case 3:
                damage = 20;
                range = 8;
                cooldown = 2.0;
                upgradeCost = -1;
                bulletSpeed = 15.0;
                break;
        }
    }

}
