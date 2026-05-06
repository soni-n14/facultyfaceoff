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
    private static String tower = "Signore";

    public static void imgClicked(){
        Main.rangePreview.setRadius(4 * 64);
        Image img = new Image(InputManager.class.getResourceAsStream("/fxml/sprites/Signore/PREVIEW.png"));
        Main.towerPreview.setImage(img);

        Main.rangePreview.setVisible(!isInPlaceMode);
        Main.towerPreview.setVisible(!isInPlaceMode);
        if(isInPlaceMode == false){
            isInPlaceMode = true;
            setupMouseClick();
            return;
        }
        isInPlaceMode = false;
    }

    public static void setupMouseClick() {

        Main.scene.setOnMouseClicked(e -> {
            if(isInPlaceMode){
                double tX = ((int) e.getX() / 64) + 0.5;
                double tY = ((int) e.getY() / 64) + 0.5;
                Point2D spot = new Point2D(tX, tY);
                if (!GameWorld.occupied.contains(spot)) {
                    if(Money.checkMoney(Signore.BASE_COST)){
                        new Signore(tX, tY);
                        GameWorld.occupied.add(spot);
                        isInPlaceMode = false;
                    }
                }
            }
            Main.rangePreview.setVisible(isInPlaceMode);
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
        button.setOnMouseClicked(e -> imgClicked());
    }

}