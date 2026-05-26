package com.apcsa.ui;

import com.apcsa.combat.towers.Farm;
import com.apcsa.combat.towers.Signore;
import com.apcsa.combat.towers.Kirsh;
import com.apcsa.combat.towers.Welsh;
import com.apcsa.combat.towers.Tsai;
import com.apcsa.world.GameWorld;
import com.apcsa.world.Money;

import java.util.HashMap;

import com.apcsa.Main;
import com.apcsa.combat.Tower;

import javafx.geometry.Point2D;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;

/**
 * Handles all player input including tower-placement mouse clicks, tower
 * selection, and keyboard shortcuts.
 */
public class InputManager {

    private static boolean isInPlaceMode = false;
    private static boolean isInTowerSelectedMode = false;
    private static HashMap<String, Class> towerClasses = new HashMap<>();
    private static HashMap<String, Integer> towerClassesRange = new HashMap<>();
    private static HashMap<String, Integer> towerClassesBaseCost = new HashMap<>();

    private static Class clasz;

    // runs off start, putting values into the hashmaps
    static {

        towerClasses.put("Signore", Signore.class);
        towerClassesRange.put("Signore", Signore.STARTER_RANGE);
        towerClassesBaseCost.put("Signore", Signore.BASE_COST);

        towerClasses.put("Farm", Farm.class);
        towerClassesRange.put("Farm", Farm.STARTER_RANGE);
        towerClassesBaseCost.put("Farm", Farm.BASE_COST);

        towerClasses.put("Kirsh", Kirsh.class);
        towerClassesRange.put("Kirsh", Kirsh.STARTER_RANGE);
        towerClassesBaseCost.put("Kirsh", Kirsh.BASE_COST);

        towerClasses.put("Welsh", Welsh.class);
        towerClassesRange.put("Welsh", Welsh.STARTER_RANGE);
        towerClassesBaseCost.put("Welsh", Welsh.BASE_COST);

        towerClasses.put("Tsai", Tsai.class);
        towerClassesRange.put("Tsai", Tsai.STARTER_RANGE);
        towerClassesBaseCost.put("Tsai", Tsai.BASE_COST);

    }

    /**
     * Called when the button is clicked.
     * 
     * @param name
     */
    public static void imgNumberClicked(String name) {

        isInTowerSelectedMode = false;
        Main.rangePreviewPlaced.setVisible(isInPlaceMode);

        clasz = towerClasses.get(name);
        double rad = towerClassesRange.get(name) * 64.0;

        Main.rangePreview.setRadius(rad);

        Image img = new Image(InputManager.class.getResourceAsStream("/fxml/sprites/" + name + "/PREVIEW.png"));
        Main.towerPreview.setImage(img);

        Main.rangePreview.setVisible(!isInPlaceMode);
        Main.towerPreview.setVisible(!isInPlaceMode);
        Main.upgradeButton.setVisible(isInTowerSelectedMode);
        Main.sellButton.setVisible(isInTowerSelectedMode);

        if (isInPlaceMode == false) {
            isInPlaceMode = true;
            setupMouseClick(clasz, name);
            return;
        }

        isInPlaceMode = false;
    }

    /**
     * This is the function that sets up mouse click for the grid, whether while in
     * place mode, or not in place mode.
     * If it is in place mode, it places a new class if the user has enough money
     * and the place isn't occupied.
     * If it is not in place mode, it checks if it clicked anything, and if it did
     * click something, it calls insidePlaced.
     * It also changes the rangePreview's and towerPreview's position whenever mouse
     * is moved
     * 
     * @param clasz
     * @param name
     */
    public static void setupMouseClick(Class clasz, String name) {

        Main.scene.setOnMouseClicked(e -> {
            double tX = ((int) e.getX() / 64) + 0.5;
            double tY = ((int) e.getY() / 64) + 0.5;
            Point2D spot = new Point2D(tX, tY);

            int cost = towerClassesBaseCost.get(name);

            if (isInPlaceMode && !GameWorld.occupied.contains(spot) && Money.checkMoney(cost)) {

                try {
                    clasz.getConstructor(double.class, double.class).newInstance(tX, tY);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                GameWorld.occupied.add(spot);
                isInPlaceMode = false;

            }

            else if (!isInPlaceMode) {
                boolean oneClicked = false;
                for (Tower tower : GameWorld.towers) {
                    if (tower.getTileX() == tX && tower.getTileY() == tY) {
                        oneClicked = true;
                        insidePlaced(tower, tX, tY);
                    }
                }

                if (!oneClicked)
                    isInTowerSelectedMode = false;

            }

            Main.rangePreview.setVisible(isInPlaceMode);
            Main.rangePreviewPlaced.setVisible(isInTowerSelectedMode);
            Main.towerPreview.setVisible(isInPlaceMode);
            Main.upgradeButton.setVisible(isInTowerSelectedMode);
            Main.sellButton.setVisible(isInTowerSelectedMode);
        });

        Main.scene.setOnMouseMoved(e -> {
            int tileX = (int) (e.getX() / 64);
            int tileY = (int) (e.getY() / 64);

            double centerX = tileX * 64 + 32;
            double centerY = tileY * 64 + 32;

            Main.rangePreview.setCenterX(centerX);
            Main.rangePreview.setCenterY(centerY);
            Main.towerPreview.setX(centerX - 32);
            Main.towerPreview.setY(centerY - 32);
        });

    }

    /**
     * makes upgrade button visible, towerselected mode too, sets range, and also
     * when upgrade button is clicked, it upgrades
     * 
     * @param tower
     * @param tX
     * @param tY
     */
    public static void insidePlaced(Tower tower, double tX, double tY) {

        Main.upgradeButton.setVisible(true);
        if (tower.getLevel() < tower.getMaxLevel()) {
            Main.upgradeButton.setText("Upgrade: $" + tower.getUpgradeCost());
        } else {
            Main.upgradeButton.setText("Max Level");
        }
        isInTowerSelectedMode = true;

        Main.sellButton.setVisible(true);
        Main.sellButton.setText("Sell: $" + tower.getSellValue());

        // position buttons next to the clicked tower
        double towerPixelX = tX * 64;
        double towerPixelY = tY * 64;
        double btnWidth = 145;
        double btnHeight = 40;
        double gap = 4;

        double btnX = towerPixelX + 32 + gap;
        if (btnX + btnWidth > 800) {
            btnX = towerPixelX - 32 - gap - btnWidth;
        }

        double upgradeBtnY = towerPixelY - btnHeight - gap;
        double sellBtnY = upgradeBtnY + btnHeight + gap;

        if (upgradeBtnY < 0) {
            upgradeBtnY = gap;
            sellBtnY = upgradeBtnY + btnHeight + gap;
        }
        if (sellBtnY + btnHeight > 600) {
            sellBtnY = 600 - btnHeight - gap;
            upgradeBtnY = sellBtnY - btnHeight - gap;
        }

        Main.upgradeButton.setLayoutX(btnX);
        Main.upgradeButton.setLayoutY(upgradeBtnY);
        Main.sellButton.setLayoutX(btnX);
        Main.sellButton.setLayoutY(sellBtnY);

        Main.rangePreviewPlaced.setCenterX(tX * 64);
        Main.rangePreviewPlaced.setCenterY(tY * 64);

        Main.rangePreviewPlaced.setRadius(tower.getRange() * 64);
        Main.upgradeButton.setOnMouseClicked(e -> {

            if (tower.upgrade()) {
                Main.rangePreviewPlaced.setRadius(tower.getRange() * 64.0);
                if (tower.getLevel() < tower.getMaxLevel()) {
                    Main.upgradeButton.setText("Upgrade: $" + tower.getUpgradeCost());
                } else {
                    Main.upgradeButton.setText("Max Level");
                }
            }
        });

        Main.sellButton.setOnMouseClicked(e -> {
            tower.remove();
            GameWorld.occupied.remove(new Point2D(tX, tY));
            Money.addMoney(tower.getSellValue());
            isInTowerSelectedMode = false;
            Main.upgradeButton.setVisible(false);
            Main.sellButton.setVisible(false);
            Main.rangePreviewPlaced.setVisible(false);
        });

    }

    /**
     * Called from a different class, it has a listener to see when the img is
     * clicked, button should have text of a tower
     * 
     * @param button
     */
    public static void setUpImageClick(Button button) {
        button.setOnMouseClicked(e -> {
            String text = button.getText();
            if (text.contains(":")) {
                text = text.split(":")[0];
            }
            imgNumberClicked(text);
        });
    }

    /**
     * Whenever a key is pressed, calls pressed
     */
    public static void setUpKeybindManager() {
        Main.scene.setOnKeyPressed(e -> pressed(e.getCode()));
    }

    /**
     * Checks to see what key is passed
     * Right now, if the key is esc, it gets out of every mode.
     * 
     * @param e
     */
    public static void pressed(KeyCode e) {
        Main.upgradeButton.setVisible(false);
        Main.sellButton.setVisible(false);
        if (e == KeyCode.ESCAPE) {
            isInPlaceMode = false;
            isInTowerSelectedMode = false;

            Main.rangePreview.setVisible(false);
            Main.rangePreviewPlaced.setVisible(false);
            Main.towerPreview.setVisible(false);
            Main.upgradeButton.setVisible(false);
            Main.sellButton.setVisible(false);
        }
    }

}