package com.apcsa.combat.enemies;

import com.apcsa.combat.Enemy;

public class SoldierFulk extends Enemy {

    public static final int HP = 6;
    public static final double SPEED = 2.2;
    public static final int REWARD = 35;

    public SoldierFulk(double tX, double tY) {
        super(tX, tY, HP, SPEED, REWARD);
    }
}