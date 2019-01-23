package com.wdeath.wshooter.zombie.weapon;

import java.util.HashMap;

public class WeaponData {

    public static HashMap<String, WeaponData> weapons;

    public static void init(){
        weapons = new HashMap<>();
        add(new WeaponGunBuilder().create());
    }

    private static void add(WeaponData data){
        weapons.put(data.name, data);
    }

    public String name;
    public float speed;
    public float recharge;
    public int ammunitionMax;
    public float damage;

}
