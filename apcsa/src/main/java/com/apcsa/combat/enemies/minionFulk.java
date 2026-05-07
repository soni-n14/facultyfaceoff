package com.apcsa.combat.enemies;

import com.apcsa.combat.Enemy;


public class MinionFulk extends Enemy{

    public static final int HP = 5;
    public static final double SPEED = 2.0;
    public static final int REWARD = 10;

    public MinionFulk(double tX, double tY) {
        super(tX, tY, HP, SPEED, REWARD);
    }

}
