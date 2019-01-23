package com.wdeath.wshooter.zombie.ecs.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldMapComponent;
import com.wdeath.wshooter.zombie.ecs.world.component.WorldPhysicsComponent;

public class WorldLoader {

    public static WorldMapComponent loader(String file){
        WorldMapComponent component = new WorldMapComponent();
        component.tiledMap = new TiledMap();
        TmxMapLoader loader = new TmxMapLoader();

        component.tiledMap = loader.load(file);

        return component;
    }

    public static WorldPhysicsComponent createPhysicsComponent(WorldMapComponent map, Entity world){
        WorldPhysicsComponent physics = new WorldPhysicsComponent();
        physics.world = new World(new Vector2(0, 0), false);
        physics.debug = new Box2DDebugRenderer();

        BodyDef defB = new BodyDef();
        defB.type = BodyDef.BodyType.StaticBody;
        defB.fixedRotation = true;
        defB.position.set(0, 0);

        physics.wall = physics.world.createBody(defB);

        BodyTypePhysics type1 = new BodyTypePhysics();
        type1.data = world;
        type1.type = BodyTypePhysics.BodyType.WORLD_WALL;
        physics.wall.setUserData(type1);

        TiledMapTileLayer walls = (TiledMapTileLayer)map.tiledMap.getLayers().get("wall");
        for(int xi = 0; xi < walls.getWidth(); xi++){
            for(int yi = 0; yi < walls.getHeight(); yi++){
                TiledMapTileLayer.Cell cell = walls.getCell(xi, yi);
                if(cell == null)
                    continue;
                createWall(physics, xi, yi, world);
            }
        }
        createBorder(map, world, physics);

        return physics;
    }

    private static void createBorder(WorldMapComponent map, Entity world, WorldPhysicsComponent physics){
        int width = (int)map.tiledMap.getProperties().get("width");
        int height = (int)map.tiledMap.getProperties().get("height");

        createBorderWall(physics, world,0, -1, width, 1);
        createBorderWall(physics, world,-1, 0, 1, height);
        createBorderWall(physics, world,width, 0, 1, height);
        createBorderWall(physics, world,0, height, width, 1);
    }

    private static Fixture createBorderWall(WorldPhysicsComponent physics, Entity world, float x, float y, float w, float h){
        ChainShape shape = new ChainShape();
        Vector2[] vecs = new Vector2[4];
        int id = 0;
        vecs[id++] = new Vector2(x, y);
        vecs[id++] = new Vector2(x + w, y);
        vecs[id++] = new Vector2(x + w, y + h);
        vecs[id++] = new Vector2(x, y + h);
        shape.createLoop(vecs);
        FixtureDef defF = new FixtureDef();
        defF.friction = 0;
        defF.shape = shape;
        defF.filter.groupIndex = BodyTypePhysics.ALL_OTHER;
        defF.filter.categoryBits = BodyTypePhysics.CATEGORY_WALL;

        Fixture fix = physics.wall.createFixture(defF);
        return fix;
    }

    private static void createWall(WorldPhysicsComponent physics, int x, int y, Entity world){
        ChainShape shape = new ChainShape();
        Vector2[] vecs = new Vector2[4];
        int id = 0;
        vecs[id++] = new Vector2(x, y);
        vecs[id++] = new Vector2(x + 1, y);
        vecs[id++] = new Vector2(x + 1, y + 1);
        vecs[id++] = new Vector2(x, y + 1);
        shape.createLoop(vecs);
        FixtureDef defF = new FixtureDef();
        defF.friction = 0.5f;
        defF.shape = shape;
        defF.filter.groupIndex = BodyTypePhysics.ALL_OTHER;
        defF.filter.categoryBits = BodyTypePhysics.CATEGORY_WALL;
        Fixture fix = physics.wall.createFixture(defF);
    }
}
