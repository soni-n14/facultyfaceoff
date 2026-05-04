package com.apcsa;

import javafx.scene.image.ImageView;
import com.apcsa.combat.Enemy;

public class Hover {

    public static void checkMoved(ImageView imageView, Enemy enemy) {
        imageView.setOnMouseEntered(e -> {
            //System.out.println(enemy.getHp());
        });

        imageView.setOnMouseExited(e -> {
            //System.out.println("Mouse left the image");
        });
    }

}