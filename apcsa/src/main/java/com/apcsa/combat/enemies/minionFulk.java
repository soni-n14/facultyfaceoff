package com.apcsa.combat.enemies;

import com.apcsa.combat.Enemy;


public class MinionFulk extends Enemy{

    public static final int HP = 3;
    public static final double SPEED = 2.0;
    public static final int REWARD = 25;

    public MinionFulk(double tX, double tY) {
        super(tX, tY, HP, SPEED, REWARD);
    }

}
