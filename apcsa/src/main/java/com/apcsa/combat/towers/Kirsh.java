package com.apcsa.combat.towers;
import com.apcsa.combat.Tower;

public class Kirsh extends Tower {

    public static final int BASE_COST = 300;
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
                range = 2;
                cooldown = 1.5;
                upgradeCost = 400;
                bulletSpeed = 20.0;
                break;
            case 2:
                damage = 3;
                range = 3;
                cooldown = 1.5;
                upgradeCost = 1000;
                bulletSpeed = 20.0;
                break;
            case 3:
                damage = 4;
                range = 4;
                cooldown = 1;
                upgradeCost = -1;
                bulletSpeed = 20.0;
                break;
        }
    }



}
