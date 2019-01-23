package com.wdeath.wshooter.zombie.ecs.player.coms;

import box2dLight.ConeLight;
import box2dLight.PointLight;
import com.badlogic.ashley.core.Component;

public class PlayerLightComponent implements Component {

    public PointLight aura;
    public ConeLight lanter;
}
