package com.wdeath.wshooter.zombie.game;

import com.wdeath.wshooter.zombie.weapon.Weapon;
import com.wdeath.wshooter.zombie.weapon.WeaponData;

import java.util.ArrayList;

public class PlayerGameData {

    public static String NAME_PLAYER = "Player";
    public static int money = 999999;

    public static WeaponData weapon;
    public static ArrayList<Integer> listBuyWeapon;

    public static void load(){
        NAME_PLAYER = "Player";
        money = 999;
        weapon = WeaponData.weapons.get(1);
        listBuyWeapon = new ArrayList<>();
        listBuyWeapon.add(1);
    }

    public static boolean isBuyWeapon(int id){
        for(Integer it : listBuyWeapon)
            if(it == id)
                return true;
        return false;
    }
}
