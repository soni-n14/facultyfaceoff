package com.apcsa.ui;

import com.apcsa.combat.towers.Farm;
import com.apcsa.combat.towers.Signore;
import com.apcsa.world.GameWorld;
import com.apcsa.world.Money;

import java.util.HashMap;

import com.apcsa.Main;
import com.apcsa.combat.Tower;

import javafx.geometry.Point2D;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;

public class InputManager {

    private static boolean isInPlaceMode = false;
    private static boolean isInTowerSelectedMode = false;
    private static HashMap<String, Class> towerClasses = new HashMap<>();
    private static HashMap<String, Integer> towerClassesRange = new HashMap<>();
    private static HashMap<String, Integer> towerClassesBaseCost = new HashMap<>();

    private static Class clasz;

    //runs off start, putting values into the hashmaps
    static {

        towerClasses.put("Signore", Signore.class);
        towerClassesRange.put("Signore", Signore.STARTER_RANGE);
        towerClassesBaseCost.put("Signore",Signore.BASE_COST);

        towerClasses.put("Farm", Farm.class);
        towerClassesRange.put("Farm", Farm.STARTER_RANGE);
        towerClassesBaseCost.put("Farm",Farm.BASE_COST);
    }

    /**
     * Called when the button is clicked.
     * @param name
     */
    public static void imgNumberClicked(String name){

        isInTowerSelectedMode = false;
        Main.rangePreviewPlaced.setVisible(isInPlaceMode);
        

        clasz = towerClasses.get(name);
        double rad =  towerClassesRange.get(name) * 64.0;

        Main.rangePreview.setRadius(rad);
        
        Image img = new Image(InputManager.class.getResourceAsStream("/fxml/sprites/" + name + "/PREVIEW.png"));
        Main.towerPreview.setImage(img);

        Main.rangePreview.setVisible(!isInPlaceMode);
        Main.towerPreview.setVisible(!isInPlaceMode);
        Main.upgradeButton.setVisible(isInTowerSelectedMode);

        if(isInPlaceMode == false){
            isInPlaceMode = true;
            setupMouseClick(clasz, name);
            return;
        }

        isInPlaceMode = false;
    }

    /**
     * This is the function that sets up mouse click for the grid, whether while in place mode, or not in place mode. 
     * If it is in place mode, it places a new class if the user has enough money and the place isn't occupied.
     * If it is not in place mode, it checks if it clicked anything, and if it did click something, it calls insidePlaced. 
     * It also changes the rangePreview's and towerPreview's position whenever mouse is moved
     * @param clasz
     * @param name
     */
    public static void setupMouseClick(Class clasz, String name) {

        Main.scene.setOnMouseClicked(e -> {
            double tX = ((int) e.getX() / 64) + 0.5;
            double tY = ((int) e.getY() / 64) + 0.5;
            Point2D spot = new Point2D(tX, tY);

            int cost = towerClassesBaseCost.get(name);

            if(isInPlaceMode && !GameWorld.occupied.contains(spot) && Money.checkMoney(cost)){

                try { clasz.getConstructor(double.class, double.class).newInstance(tX, tY); }
                catch (Exception ex) { ex.printStackTrace(); }
                
                GameWorld.occupied.add(spot);
                isInPlaceMode = false;

            }

            else if(!isInPlaceMode){
                boolean oneClicked = false;
                for(Tower tower : GameWorld.towers){
                    if(tower.getTileX() == tX && tower.getTileY() == tY){
                        oneClicked = true;
                        insidePlaced(tower, tX, tY);
                    }
                }
                
                if(!oneClicked)
                    isInTowerSelectedMode = false;
                
            }
                
            Main.rangePreview.setVisible(isInPlaceMode);
            Main.rangePreviewPlaced.setVisible(isInTowerSelectedMode);
            Main.towerPreview.setVisible(isInPlaceMode);
            Main.upgradeButton.setVisible(isInTowerSelectedMode);
        });

        Main.scene.setOnMouseMoved(e -> {
            int tileX = (int)(e.getX() / 64);
            int tileY = (int)(e.getY() / 64);

            double centerX = tileX * 64 + 32;
            double centerY = tileY * 64 + 32;

            Main.rangePreview.setCenterX(centerX);
            Main.rangePreview.setCenterY(centerY);
            Main.towerPreview.setX(centerX - 32);
            Main.towerPreview.setY(centerY - 32);
        });

    }

    /**
     * makes upgrade button visible, towerselected mode too, sets range, and also when upgrade button is clicked, it upgrades
     * @param tower
     * @param tX
     * @param tY
     */
    public static void insidePlaced(Tower tower, double tX, double tY){

        Main.upgradeButton.setVisible(true);
        isInTowerSelectedMode = true;

        Main.rangePreviewPlaced.setCenterX(tX * 64);
        Main.rangePreviewPlaced.setCenterY(tY * 64);

        Main.rangePreviewPlaced.setRadius(tower.getRange() * 64);
        Main.upgradeButton.setOnMouseClicked(e -> {

            if (tower.upgrade()) {
                Main.rangePreviewPlaced.setRadius(tower.getRange() * 64.0);
            }
        });

    }

    /**
     * Called from a different class, it has a listener to see when the img is clicked, button should have text of a tower
     * @param button
     */
    public static void setUpImageClick(Button button){
        button.setOnMouseClicked(e -> imgNumberClicked(button.getText()));
    }

    /**
     * Whenever a key is pressed, calls pressed
     */
    public static void setUpKeybindManager(){
        Main.scene.setOnKeyPressed(e -> pressed(e.getCode()));
    }

    /**
     * Checks to see what key is passed
     * Right now, if the key is esc, it gets out of every mode.
     * @param e
     */
    public static void pressed(KeyCode e){
        Main.upgradeButton.setVisible(false);
        if(e == KeyCode.ESCAPE){
            isInPlaceMode = false;
            isInTowerSelectedMode = false;

            Main.rangePreview.setVisible(false);
            Main.rangePreviewPlaced.setVisible(false);
            Main.towerPreview.setVisible(false);
            Main.upgradeButton.setVisible(false);
        }
    }

}