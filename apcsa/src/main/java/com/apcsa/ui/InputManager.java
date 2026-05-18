package com.apcsa.ui;

import com.apcsa.combat.towers.Signore;
import com.apcsa.world.GameWorld;
import com.apcsa.world.Money;
import com.apcsa.Main;
import com.apcsa.combat.Tower;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Button;

public class InputManager {

    private static boolean isInPlaceMode = false;
    private static boolean isInTowerSelectedMode = false;

    private static Class<? extends Tower> clasz;

    public static void imgNumberClicked(String name){
        try { 
            //TODO, Neelehs will do : make a hashmap with name of class assigned to the actual class, then get it using the hashmap . 
            clasz = (Class<? extends Tower>) Class.forName("com.apcsa.combat.towers." + name);
        } 
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        isInTowerSelectedMode = false;
        Main.rangePreviewPlaced.setVisible(isInPlaceMode);

        double rad = 0;

        try {
            rad = clasz.getField("STARTER_RANGE").getInt(null) * 64.0;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        Main.rangePreview.setRadius(rad);
        
        Image img = new Image(InputManager.class.getResourceAsStream("/fxml/sprites/" + name + "/PREVIEW.png"));
        Main.towerPreview.setImage(img);

        Main.rangePreview.setVisible(!isInPlaceMode);
        Main.towerPreview.setVisible(!isInPlaceMode);
        Main.upgradeButton.setVisible(isInTowerSelectedMode);

        if(isInPlaceMode == false){
            isInPlaceMode = true;
            setupMouseClick(clasz);
            return;
        }

        isInPlaceMode = false;
    }

    public static void setupMouseClick(Class<? extends Tower> clasz) {

        Main.scene.setOnMouseClicked(e -> {
            double tX = ((int) e.getX() / 64) + 0.5;
            double tY = ((int) e.getY() / 64) + 0.5;
            Point2D spot = new Point2D(tX, tY);

            int cost = 0;
            try {
                cost = clasz.getField("BASE_COST").getInt(null);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                ex.printStackTrace();
            }

            if(isInPlaceMode && !GameWorld.occupied.contains(spot) && Money.checkMoney(cost)){
                try {
                    clasz.getConstructor(double.class, double.class).newInstance(tX, tY);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                GameWorld.occupied.add(spot);
                isInPlaceMode = false;
            }

            else if(!isInPlaceMode){
                boolean oneClicked = false;
                for(Tower tower : GameWorld.towers){
                    if(tower.getTileX() == tX && tower.getTileY() == tY){
                        isInTowerSelectedMode = true;

                        Main.rangePreviewPlaced.setCenterX(tX * 64);
                        Main.rangePreviewPlaced.setCenterY(tY * 64);

                        Main.rangePreviewPlaced.setRadius(tower.getRange() * 64);
                        oneClicked = true;
                        insidePlaced(tower);
                    }
                }
                if(!oneClicked){
                    isInTowerSelectedMode = false;
                }
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

    public static void insidePlaced(Tower tower){

        Main.upgradeButton.setVisible(true);
        Main.upgradeButton.setOnMouseClicked(e -> {

            if (tower.upgrade()) {
                Main.rangePreviewPlaced.setRadius(tower.getRange() * 64.0);
            }
        });

    }

    public static void setUpImageClick(Button button){
        button.setOnMouseClicked(e -> imgNumberClicked(button.getText()));
    }

    public static void setUpKeybindManager(){
        Main.scene.setOnKeyPressed(e -> pressed(e.getCode()));
    }

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