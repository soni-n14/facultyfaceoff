package com.apcsa.combat.towers;
import java.util.List;

import com.apcsa.combat.Enemy;
import com.apcsa.combat.Tower;
import com.apcsa.world.Money;

public class Farm extends Tower {
    
    public static final int BASE_COST = 300;
    public static final int STARTER_RANGE = 2;

    private int cashPerWave;
    
    public Farm(double tX, double tY){
        super(tX, tY);

        maxLevel = 3;
        cooldownTimer = cooldown;

        updateStats(level);
    }

    @Override
    public void update(double deltaTime, List<Enemy> enemies){
        updateAnimation(deltaTime);
    }

    public void doYoThing(){
        Money.addMoney(cashPerWave);
    }

    @Override
    public void updateStats(int level){
        switch(level){
            case 1:
                cashPerWave = 100;

                damage = 0;
                range = 2;
                cooldown = 1000;
                upgradeCost = 750;
                bulletSpeed = 0.0;
                break;
            case 2:
                cashPerWave = 250;

                damage = 0;
                range = 2;
                cooldown = 1000;
                upgradeCost = 4000;
                bulletSpeed = 0.0;
                break;
            case 3:
                cashPerWave = 550;

                damage = 0;
                range = 2;
                cooldown = 1000;
                upgradeCost = -1;
                bulletSpeed = 0.0;
                break;
        }
    }

}
