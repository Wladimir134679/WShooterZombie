package com.wdeath.wshooter.zombie.weapon;

public abstract class WeaponBuilder {

    public WeaponData data;

    public WeaponData create(){
        data = new WeaponData();
        createData();
        return data;
    }

    public abstract void createData();
}
