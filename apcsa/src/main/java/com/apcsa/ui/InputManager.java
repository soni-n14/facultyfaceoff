package com.apcsa.ui;

import com.apcsa.combat.towers.Signore;
import com.apcsa.Main;
import com.apcsa.combat.Tower;
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
                double x = e.getX() / 64;
                double y = e.getY() / 64;

                new Signore(x, y);
                isInPlaceMode = false;
            }
            Main.rangePreview.setVisible(isInPlaceMode);
            Main.towerPreview.setVisible(isInPlaceMode);
        });

        Main.scene.setOnMouseMoved(e -> {
            Main.rangePreview.setCenterX(e.getX());
            Main.rangePreview.setCenterY(e.getY());
            Main.towerPreview.setX(e.getX() - 32);
            Main.towerPreview.setY(e.getY() - 32);
        });

    }

    public static void setUpImageClick(Button button){
        button.setOnMouseClicked(e -> imgClicked());
    }

}