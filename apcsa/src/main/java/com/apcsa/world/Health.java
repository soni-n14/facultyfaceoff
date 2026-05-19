package com.apcsa.world;

import com.apcsa.Main;

public class Health {
    
    public static int maxBaseHealth = 100;
    public static int baseHealth = 100;

    /**
     * Subtracts health by the int amount parameter. If the health is less than equal to zero, sets to zero
     * @param amount
     */
    public static void subtractHealth(int amount){
        baseHealth -= amount;
        if (baseHealth <= 0){
            baseHealth = 0;
            Main.baseHealthText.setText(baseHealth+"/"+maxBaseHealth);
            return;
        }
        Main.baseHealthText.setText(baseHealth+"/"+maxBaseHealth);
    }

}
