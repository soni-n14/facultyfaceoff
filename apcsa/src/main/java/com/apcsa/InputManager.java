package com.apcsa;

import com.apcsa.combat.towers.Signore;
import javafx.scene.Scene;

public class InputManager {

    public static void setupMouseClick(Scene scene) {

        scene.setOnMouseClicked(e -> {
            double x = e.getX() / 64;
            double y = e.getY() / 64;

            new Signore((int)x, (int)y);
        });
    }
}