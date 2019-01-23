package com.wdeath.wshooter.zombie.ecs.level.coms;

import com.badlogic.ashley.core.Component;
import com.wdeath.wshooter.zombie.ecs.level.LevelData;

public class LevelComponent implements Component {

    public LevelData data;
    public float timeSpawn;
    public float timeBegin;
    public boolean isRun = false;
}
