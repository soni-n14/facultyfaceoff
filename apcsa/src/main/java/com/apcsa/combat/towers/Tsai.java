package com.apcsa.combat.towers;
import com.apcsa.combat.Tower;

/**
 * A rapid-fire, short-range tower that attacks quickly but deals low damage per shot.
 */
public class Tsai extends Tower {

    public static final int BASE_COST = 125;
    public static final int STARTER_RANGE = 2;

    public Tsai(double tX, double tY){
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
                damage = 2;
                range = 2;
                cooldown = 0.6;
                upgradeCost = 300;
                bulletSpeed = 22.0;
                break;
            case 2:
                damage = 3;
                range = 3;
                cooldown = 0.5;
                upgradeCost = 700;
                bulletSpeed = 22.0;
                break;
            case 3:
                damage = 4;
                range = 3;
                cooldown = 0.35;
                upgradeCost = -1;
                bulletSpeed = 22.0;
                break;
        }
    }

}
