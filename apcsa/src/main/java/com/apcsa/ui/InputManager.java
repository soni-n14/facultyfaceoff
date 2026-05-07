package com.apcsa.ui;

import com.apcsa.combat.towers.Signore;
import com.apcsa.world.GameWorld;
import com.apcsa.world.Money;
import com.apcsa.Main;
import com.apcsa.combat.Tower;

import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.control.Button;

public class InputManager {

    private static boolean isInPlaceMode = false;
    private static boolean isInTowerSelectedMode = false;

    private static Class<? extends Tower> clasz;

    public static void imgClicked(String name){
        try {
            Class<?> burner = Class.forName("com.apcsa.combat.towers." + name);
            clasz = (Class<? extends Tower>) burner;

        } catch (ClassNotFoundException e) {
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
                    }
                }
                if(!oneClicked){
                    isInTowerSelectedMode = false;
                }
            }
                
            Main.rangePreview.setVisible(isInPlaceMode);
            Main.rangePreviewPlaced.setVisible(isInTowerSelectedMode);
            Main.towerPreview.setVisible(isInPlaceMode);
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

    public static void setUpImageClick(Button button){
        button.setOnMouseClicked(e -> imgClicked(button.getText()));
    }

    public static void inPlacedClickedMode(){
        
    }

}