package com.apcsa.world;

import com.apcsa.Main;

/**
 * Manages the player's gold balance and keeps the HUD money display in sync.
 */
public class Money {
    private static int money = 100;

    /**
     * Adds money by int parameter amount
     * @param amount
     */
    public static void addMoney(int amount){
        money+=amount;
        Main.moneyText.setText("Money: "+money); 
    }

    /**
     * Subtracts money by int parameter amount
     */
    public static void subtractMoney(int amount){
        money-=amount;
        Main.moneyText.setText("Money: "+money); 
    }

    public static void resetMoney() {
        money = 100;
        Main.moneyText.setText("Money: "+money); 
    }

    /**
     * Checks if money is greater than or equal to int parameter monToCheck, 
     * if it is, subtracts the money and returns true. Otherwise returns false
     * @param monToCheck
     * @return
     */
    public static boolean checkMoney(int monToCheck){
        if(money >= monToCheck){
            subtractMoney(monToCheck);
            return true;
        }
        return false;
        
    }

    /**
     * @return money
     */
    public static int getMoney(){
        return money;
    }

}
