package com.wdeath.wshooter.zombie.ecs.world.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class WorldMapComponent implements Component {

    public TiledMap tiledMap;

    public ArrayList<Vector2> lights;
}
