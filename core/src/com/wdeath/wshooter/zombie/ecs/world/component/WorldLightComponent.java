package com.wdeath.wshooter.zombie.ecs.world.component;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.ashley.core.Component;

import java.util.ArrayList;

public class WorldLightComponent implements Component {

    public RayHandler rayHandler;

    public ArrayList<PointLight> lights;

    public WorldLightComponent() {
        lights = new ArrayList<>();
    }
}
