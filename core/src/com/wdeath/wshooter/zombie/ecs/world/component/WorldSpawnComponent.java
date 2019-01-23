package com.wdeath.wshooter.zombie.ecs.world.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.HashMap;

public class WorldSpawnComponent implements Component {

    public HashMap<String, Vector2> spawns;

    public ArrayList<Vector2> spawnZombies;
}
