package com.apcsa.combat.enemies;

import com.apcsa.combat.Enemy;

/**
 * The Big Fulk Boss (BFB) - the strongest enemy in the game.
 * Extremely high HP and slow movement.
 */
public class BFB extends Enemy {

    public static final int HP = 1000;
    public static final double SPEED = 0.5;
    public static final int REWARD = 250;

    public BFB(double tX, double tY) {
        super(tX, tY, HP, SPEED, REWARD);
    }
}