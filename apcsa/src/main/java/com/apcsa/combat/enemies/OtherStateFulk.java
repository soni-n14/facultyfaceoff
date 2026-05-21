package com.apcsa.combat.enemies;

import com.apcsa.combat.Enemy;


/**
 * A slow, low-HP introductory enemy that awards 10 gold on death.
 */
public class OtherStateFulk extends Enemy{

    public static final int HP = 6;
    public static final double SPEED = 1.0;
    public static final int REWARD = 10;

    public OtherStateFulk(double tX, double tY) {
        super(tX, tY, HP, SPEED, REWARD);
    }

}
