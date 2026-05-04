package com.apcsa.combat.towers;
import com.apcsa.combat.Tower;

public class Signore extends Tower {

    public static final int BASE_COST = 100;
    public static final int STARTER_RANGE = 4;
    
    public Signore(int tX, int tY){
        super(tX, tY);
        maxLevel = 3;
        updateStats(level);
        bulletSpeed = 20.0;
        cooldownTimer = cooldown;
    }

    /**
     * Changes damage, range, cooldown and cost based on level
     * @param level
     */
    @Override
    public void updateStats(int level){
        switch(level){
            case 1:
                damage = 2;
                range = 4;
                cooldown = 0.5;
                upgradeCost = 250;
                break;
            case 2:
                damage = 4;
                range = 5;
                cooldown = 0.4;
                upgradeCost = 600;
                break;
            case 3:
                damage = 5;
                range = 5;
                cooldown = 0.2;
                upgradeCost = -1;
                break;
        }
    }



}
