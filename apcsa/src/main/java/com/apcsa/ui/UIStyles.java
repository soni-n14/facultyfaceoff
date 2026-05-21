package com.apcsa.ui;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import javafx.scene.text.*;

/**
 * Provides shared style constants and helper methods for positioning and styling all HUD elements.
 */
public class UIStyles {

    public static final Color GRID_COLOR = Color.LIGHTGRAY;
    public static final Color RANGE_FILL = Color.rgb(128, 128, 128, 0.25);
    public static final Color RANGE_STROKE = Color.GRAY;
    public static final Color BACKGROUND_COLOR = Color.BEIGE;

    public static final String TOWER_PLACEMENT_BUTTON =
        "-fx-background-color: gray;" +
        "-fx-text-fill: black;" +
        "-fx-font-size: 14px;" +
        "-fx-border-color: darkgray;" +
        "-fx-border-width: 2;" + 
        "-fx-cursor: hand;" + 
        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.4), 8, 0.2, 2, 2);" + 
        "-fx-background-radius: 8;" +
        "-fx-border-radius: 8;";

    public static final String WAVE_TEXT =
        "-fx-font-family: 'Trebuchet MS';" +
        "-fx-font-size: 22px;" +
        "-fx-font-weight: bold;" +
        "-fx-fill: white;" +
        "-fx-stroke: black;" +
        "-fx-stroke-width: 1.2;";

    public static final String MONEY_TEXT =
        "-fx-font-family: 'Impact';" +
        "-fx-font-size: 24px;" +
        "-fx-fill: #FFD84D;" +
        "-fx-stroke: black;" +
        "-fx-stroke-width: 1.5;";

    public static final String BASE_HEALTH_TEXT =
        "-fx-font-family: 'Verdana';" +
        "-fx-font-size: 23px;" +
        "-fx-font-weight: bold;" +
        "-fx-fill: #FF6B6B;" +
        "-fx-stroke: black;" +
        "-fx-stroke-width: 1.3;";
    

    /**
     * sets style and position and size of towerPlacementButton
     * @param button
     * @param x
     * @param y
     */
    public static void styleTowerPlacementButton(Button button, int x, int y) {
        button.setStyle(TOWER_PLACEMENT_BUTTON);
        button.setPrefWidth(80);
        button.setPrefHeight(40);

        position(button, x, y);
    }

    /**
     * Sets style and position of waveText
     * @param text
     * @param x
     * @param y
     */
    public static void setWaveText(Text text, int x, int y){
        text.setStyle(WAVE_TEXT);
        position(text,x,y);
    }

    /**
     * Sets style and position of moneyText
     * @param text
     * @param x
     * @param y
     */
    public static void setMoneyText(Text text, int x, int y){
        text.setStyle(MONEY_TEXT);
        position(text,x,y);
    }

    /**
     * Sets style and position of baseHealthText
     * @param text
     * @param x
     * @param y
     */
    public static void setBaseHealthText(Text text, int x, int y){
        text.setStyle(BASE_HEALTH_TEXT);
        position(text,x,y);
    }

    /**
     * Sets position of text
     * @param text
     * @param x
     * @param y
     */
    public static void position(Text text, int x, int y){
        text.setX(x);
        text.setY(y);
    }

    /**
     * Sets position of button
     * @param button
     * @param x
     * @param y
     */
    public static void position(Button button, int x, int y){
        button.setLayoutX(x);
        button.setLayoutY(y);
    }

    

}