package com.apcsa.combat.enemies;

import com.apcsa.combat.Enemy;

public class BruteFulk extends Enemy {

    public static final int HP = 10;
    public static final double SPEED = 1.0;
    public static final int REWARD = 50;

    public BruteFulk(double tX, double tY) {
        super(tX, tY, HP, SPEED, REWARD);
    }
}