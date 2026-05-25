package com.apcsa.combat.towers;
import com.apcsa.combat.Tower;

public class Kirsh extends Tower {

    public static final int BASE_COST = 150;
    public static final int STARTER_RANGE = 5;
    
    public Kirsh(double tX, double tY){
        super(tX, tY);
        
        maxLevel = 3;
        cooldownTimer = cooldown;

        updateStats(level);
    }


    @Override
    public void updateStats(int level){
        switch(level){
            case 1:
                damage = 2;
                range = 5;
                cooldown = 1.5;
                upgradeCost = 400;
                bulletSpeed = 20.0;
                break;
            case 2:
                damage = 3;
                range = 6;
                cooldown = 1.5;
                upgradeCost = 1000;
                bulletSpeed = 20.0;
                break;
            case 3:
                damage = 4;
                range = 7;
                cooldown = 1;
                upgradeCost = -1;
                bulletSpeed = 20.0;
                break;
        }
    }



}
