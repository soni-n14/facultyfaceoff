package com.apcsa.world;

import com.apcsa.Main;
import javafx.application.Platform;

/**
 * Tracks the player's base health and triggers the game-over state when it
 * reaches zero.
 */
public class Health {

    public static int maxBaseHealth = 100;
    public static int baseHealth = 100;

    /**
     * Subtracts health by the int amount parameter. If the health is less than
     * equal to zero, sets to zero
     * 
     * @param amount
     */
    public static void subtractHealth(int amount) {
        baseHealth -= amount;
        if (baseHealth <= 0) {
            baseHealth = 0;
            Platform.runLater(() -> Main.baseHealthText.setText(baseHealth + "/" + maxBaseHealth));
            gameOver();
            return;
        }
        Platform.runLater(() -> Main.baseHealthText.setText(baseHealth + "/" + maxBaseHealth));
    }

    /**
     * Game over when base health drops to zero.
     */
    public static void gameOver() {
        Main.showGameOver();
    }

}
