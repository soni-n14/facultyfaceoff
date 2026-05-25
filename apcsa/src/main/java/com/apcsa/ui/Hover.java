package com.apcsa.ui;

import com.apcsa.Main;
import com.apcsa.combat.Enemy;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * Attaches hover-based HP tooltip behaviour to enemy ImageViews.
 */
public class Hover {

    /**
     * Adds hover behavior to the imageView
     * When mouse enters, shows HP of enemy above image
     * When enemy moves, udpate is called, which updates position and text of the
     * text. It also updates based on enemy health
     * 
     * @param imageView
     * @param enemy
     */
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

    /**
     * Updates position and text of the text
     * 
     * @param hpText
     * @param imageView
     * @param enemy
     */
    private static void update(Text hpText, ImageView imageView, Enemy enemy) {
        hpText.setX(imageView.getX() + 15);
        hpText.setY(imageView.getY() - 5);
        hpText.setText(enemy.getHp() + " / " + enemy.getMaxHp());
    }
}