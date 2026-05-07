package com.apcsa.combat.towers;
import com.apcsa.combat.Tower;

public class Signore extends Tower {

    public static final int BASE_COST = 50;
    public static final int STARTER_RANGE = 3;
    
    public Signore(double tX, double tY){
        super(tX, tY);
        
        maxLevel = 3;
        cooldownTimer = cooldown;

        updateStats(level);
    }


    @Override
    public void updateStats(int level){
        switch(level){
            case 1:
                damage = 3;
                range = 3;
                cooldown = 1.2;
                upgradeCost = 250;
                bulletSpeed = 20.0;
                break;
            case 2:
                damage = 4;
                range = 4;
                cooldown = 1.0;
                upgradeCost = 600;
                bulletSpeed = 20.0;
                break;
            case 3:
                damage = 5;
                range = 5;
                cooldown = 0.8;
                upgradeCost = -1;
                bulletSpeed = 20.0;
                break;
        }
    }



}
