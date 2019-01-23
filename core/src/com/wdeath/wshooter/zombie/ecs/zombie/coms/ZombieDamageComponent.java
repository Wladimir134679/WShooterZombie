package com.wdeath.wshooter.zombie.ecs.zombie.coms;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.wdeath.wshooter.zombie.damage.Damage;

import java.util.ArrayList;

public class ZombieDamageComponent implements Component {

    public ArrayList<Damage> list;

    public ZombieDamageComponent() {
        list = new ArrayList<>();
    }
}
