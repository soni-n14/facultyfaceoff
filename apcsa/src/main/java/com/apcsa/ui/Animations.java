package com.apcsa.ui;

import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.HashMap;

import com.apcsa.Main;
import com.apcsa.combat.Enemy;
import com.apcsa.combat.Tower;

/**
 * Manages sprite rendering and animation for all towers and enemies on the
 * JavaFX pane.
 */
public class Animations {

    public static final int FRAME_WIDTH = 512;
    public static final int FRAME_HEIGHT = 512;
    public static final int DRAW_SIZE = 64;

    private static HashMap<Tower, ImageView> towerViews = new HashMap<>();
    private static HashMap<Enemy, ImageView> enemyViews = new HashMap<>();

    private static HashMap<String, Image> imageCache = new HashMap<>();

    /**
     * gets the image from from the imageCache, if the hashmap doesn't have the
     * image, it adds the image to the hashmap
     * 
     * @param path
     * @return
     */
    public static Image getImage(String path) {
        if (!imageCache.containsKey(path)) {
            Image img = new Image(Animations.class.getResourceAsStream(path));
            imageCache.put(path, img);
        }

        return imageCache.get(path);
    }

    /**
     * 
     * @param tower
     * @return String array, 0th index is the tower name, 1st index is animation
     *         state+png, 2nd index is the frame in
     *         the spritesheet to be on
     */
    public static String[] getTowerFrame(Tower tower) {
        String towerName = tower.getName();
        int frame = tower.getAnimationFrame();
        String state = tower.getState().toString();

        String[] ret = new String[3];

        ret[0] = towerName;
        ret[1] = state + ".png";
        ret[2] = "" + (frame * FRAME_WIDTH);

        return ret;
    }

    /**
     * Gets the viewport animation frame for the tower, and sets rotation
     * 
     * @param tower
     */
    public static void updateTower(Tower tower) {
        String[] data = getTowerFrame(tower);

        String path = "/fxml/sprites/" + data[0] + "/" + data[1];

        Image img = getImage(path);

        Platform.runLater(() -> {
            ImageView view = towerViews.get(tower);

            if (view == null) {
                view = new ImageView();

                view.setFitWidth(DRAW_SIZE);
                view.setFitHeight(DRAW_SIZE);

                towerViews.put(tower, view);
                Main.pane.getChildren().add(view);
                Main.waveText.toFront();
                Main.timeText.toFront();
                Main.baseHealthText.toFront();
                Main.moneyText.toFront();
            }

            view.setImage(img);

            view.setViewport(
                    new Rectangle2D(
                            Integer.parseInt(data[2]),
                            0,
                            FRAME_WIDTH,
                            FRAME_HEIGHT));

            view.setRotate(tower.getRotationDeg());

            view.setX(tower.getTileX() * DRAW_SIZE - DRAW_SIZE / 2.0);
            view.setY(tower.getTileY() * DRAW_SIZE - DRAW_SIZE / 2.0);
        });
    }

    /**
     * Updates the image to show on where the enmy is, for the unaminated enemies
     * 
     * @param enemy
     */
    public static void updateUnanimatedEnemy(Enemy enemy) {
        String path = "/fxml/sprites/" + enemy.getClass().getSimpleName() + "/MOVING.png";

        Image img = getImage(path);

        Platform.runLater(() -> {
            ImageView view = enemyViews.get(enemy);

            if (view == null) {
                view = new ImageView();

                view.setFitWidth(DRAW_SIZE);
                view.setFitHeight(DRAW_SIZE);

                enemyViews.put(enemy, view);
                Main.pane.getChildren().add(view);
                Hover.checkMoved(view, enemy);
            }

            view.setImage(img);

            view.setX(enemy.getTileX() * DRAW_SIZE - DRAW_SIZE / 2.0);
            view.setY(enemy.getTileY() * DRAW_SIZE - DRAW_SIZE / 2.0);

        });
    }

    /**
     * Removes image of enemy
     */
    public static void removeEnemy(Enemy enemy) {
        Platform.runLater(() -> {
            ImageView view = enemyViews.remove(enemy);

            if (view != null) {
                Main.pane.getChildren().remove(view);
            }
        });
    }

    /**
     * Removes image of tower
     * 
     * @param tower
     */
    public static void removeTower(Tower tower) {
        Platform.runLater(() -> {
            ImageView view = towerViews.remove(tower);

            if (view != null) {
                Main.pane.getChildren().remove(view);
            }
        });
    }
}