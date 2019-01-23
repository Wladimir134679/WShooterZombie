package com.wdeath.wshooter.zombie.ecs.world.component;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class WorldDrawComponent implements Component {

    public SpriteBatch batch;
    public OrthographicCamera camera;
    public OrthogonalTiledMapRenderer mapRenderer;
}
