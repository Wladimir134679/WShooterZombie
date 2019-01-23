package com.wdeath.wshooter.zombie.weapon;

public class WeaponGunBuilder extends WeaponBuilder {
    @Override
    public void createData() {
        data.name = "Gun";
        data.speed = 0.1f;
        data.ammunitionMax = 20;
        data.recharge = 0.3f;
        data.damage = 1f;
    }
}
