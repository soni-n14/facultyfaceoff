package com.apcsa.combat.enemies;

import com.apcsa.combat.Enemy;

/**
 * A very fast, fragile enemy that awards 10 gold on death.
 */
public class ScoutFulk extends Enemy {

    public static final int HP = 4;
    public static final double SPEED = 3.2;
    public static final int REWARD = 10;

    public ScoutFulk(double tX, double tY) {
        super(tX, tY, HP, SPEED, REWARD);
    }
}