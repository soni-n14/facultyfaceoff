package com.apcsa.world;

import com.apcsa.Main;

public class Money {
    private static int money = 100;

    public static void addMoney(int amount){
        money+=amount;
        Main.moneyText.setText("Money: "+money); 
    }

    public static void subtractMoney(int amount){
        money-=amount;
        Main.moneyText.setText("Money: "+money); 
    }

    public static boolean checkMoney(int towerCost){
        if(money >= towerCost){
            subtractMoney(towerCost);
            return true;
        }
        return false;
        
    }
}
