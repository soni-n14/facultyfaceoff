package com.apcsa;

import com.apcsa.combat.enemies.BFB;
import com.apcsa.combat.towers.Signore;

public class TestCombat {

    public static void runIt() {

        GameWorld.startGameLoop();

        new Signore(6, 2);

    }
}