package com.apcsa.ui;

import com.apcsa.Main;
import com.apcsa.combat.Enemy;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class Hover {

    public static void checkMoved(ImageView imageView, Enemy enemy) {

        Text hpText = new Text();
        hpText.setVisible(false);
        Main.pane.getChildren().add(hpText);

        imageView.setOnMouseEntered(e -> {
            hpText.setVisible(true);
            update(hpText, imageView, enemy);
        });

        imageView.setOnMouseExited(e -> {
            hpText.setVisible(false);
        });

        imageView.xProperty().addListener((obs, oldVal, newVal) -> {
            if (hpText.isVisible()) {
                update(hpText, imageView, enemy);
            }
        });

        imageView.yProperty().addListener((obs, oldVal, newVal) -> {
            if (hpText.isVisible()) {
                update(hpText, imageView, enemy);
            }
        });
    }

    private static void update(Text hpText, ImageView imageView, Enemy enemy) {
        hpText.setX(imageView.getX() + 15);
        hpText.setY(imageView.getY() - 5);
        hpText.setText(enemy.getHp() + " / " + enemy.getMaxHp());
    }
}