package com.wdeath.wshooter.zombie.ecs.player.coms;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.wdeath.wshooter.zombie.damage.Damage;

import java.util.ArrayList;

public class PlayerDamageComponent implements Component {

    public ArrayList<Damage> list;

    public PlayerDamageComponent() {
        list = new ArrayList<>();
    }
}
