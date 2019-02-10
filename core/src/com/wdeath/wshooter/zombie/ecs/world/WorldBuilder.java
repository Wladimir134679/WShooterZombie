package com.wdeath.wshooter.zombie.ecs.world;

import box2dLight.PointLight;
import box2dLight.RayHandler;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.wdeath.wshooter.zombie.ecs.world.component.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class WorldBuilder {

    public Entity world;
    private WorldMapComponent map;
    private WorldPhysicsComponent physics;

    public WorldBuilder create(){
        world = new Entity();
        return this;
    }

    public WorldBuilder createMap(String fileName){
        map = WorldLoader.loader(fileName);
        world.add(map);
        createSpawns();
        return this;
    }

    private void createSpawns(){
        WorldSpawnComponent spawn = new WorldSpawnComponent();
        spawn.spawns = new HashMap<>();
        spawn.spawnZombies = new ArrayList<>();

        MapLayer mapObjs = (MapLayer)map.tiledMap.getLayers().get("spawn");
        MapObjects objs = mapObjs.getObjects();
        Iterator<MapObject> iter = objs.iterator();

        while(iter.hasNext()){
            MapObject obj = iter.next();
            Vector2 point = new Vector2();
            point.set((float)obj.getProperties().get("x"), (float)obj.getProperties().get("y"));
            point.scl(1/32f);
            String name = (String)obj.getProperties().get("name");

            if(name.equals("zombie")){
                spawn.spawnZombies.add(point);
            }else {
                spawn.spawns.put(name, point);
            }
        }

        world.add(spawn);
    }

    public WorldBuilder createPhysics(){
        physics = WorldLoader.createPhysicsComponent(map, world);
        physics.world.setContactListener(new WorldContactPhysicsListener());
        world.add(physics);
        return this;
    }

    public WorldBuilder createDraw(){
        WorldDrawComponent drawComponent = new WorldDrawComponent();
        drawComponent.batch = new SpriteBatch();
        drawComponent.camera = new OrthographicCamera();
        drawComponent.camera.setToOrtho(false, Gdx.graphics.getWidth() / 32f, Gdx.graphics.getHeight() / 32f);
        drawComponent.mapRenderer = new OrthogonalTiledMapRenderer(map.tiledMap, 1/32f);
        world.add(drawComponent);
        return this;
    }

    public WorldBuilder createLight(){
        WorldLightComponent lightComponent = new WorldLightComponent();
        lightComponent.rayHandler = new RayHandler(physics.world);
        lightComponent.rayHandler.setAmbientLight(0.005f);
        lightComponent.rayHandler.setCulling(true);
        world.add(lightComponent);

        WorldMapComponent map = world.getComponent(WorldMapComponent.class);
        MapLayer mapObjs = (MapLayer)map.tiledMap.getLayers().get("light");
        MapObjects objs = mapObjs.getObjects();
        Iterator<MapObject> iter = objs.iterator();
        Filter filter = new Filter();
        filter.categoryBits = BodyTypePhysics.CATEGORY_LIGHT;
        filter.maskBits = BodyTypePhysics.MASK_LIGHT_WORLD;
//        filter.groupIndex = BodyTypePhysics.
        while(iter.hasNext()){
            MapObject obj = iter.next();
            Vector2 point = new Vector2();
            point.set((float)obj.getProperties().get("x"), (float)obj.getProperties().get("y"));
            point.scl(1f/32);
            float r = (float)obj.getProperties().get("r");

            Color color = Color.BLACK;
            PointLight light = new PointLight(lightComponent.rayHandler, 100);
            light.setColor(color);
            light.setDistance(r);
            light.setPosition(point);
            light.setSoftnessLength(0.1f);
            light.setContactFilter(filter);
            lightComponent.lights.add(light);

            createLightBody(point);
        }

        return this;
    }

    private void createLightBody(Vector2 pos){
        WorldPhysicsComponent physicsComponent = world.getComponent(WorldPhysicsComponent.class);
        CircleShape shape = new CircleShape();
        shape.setPosition(pos);
        shape.setRadius(0.05f);

        FixtureDef defF = new FixtureDef();
        defF.shape = shape;
        defF.filter.categoryBits = BodyTypePhysics.CATEGORY_WALL;

        Fixture fix = physicsComponent.wall.createFixture(defF);
        BodyTypePhysics.add(fix, null, BodyTypePhysics.BodyType.WORLD_WALL);
    }

    public Entity get(){
        return world;
    }

    /////////////////////////////////////////////  SYSTEMS

//    public WorldCameraSystem sCreateCamera(String namePlayer){
//        WorldCameraSystem sys = new WorldCameraSystem(world, namePlayer);
//        return sys;
//    }
//
//    public WorldDrawTiledMapSystem sCreateDrawTiled(){
//        WorldDrawTiledMapSystem draw = new WorldDrawTiledMapSystem(world);
//        return draw;
//    }
//
//    public WorldPhysicsSystem sCreatePhysics(){
//        WorldPhysicsSystem sys = new WorldPhysicsSystem(world);
//        return sys;
//    }
//
//    public WorldPhysicsDebugSystem sCreatePhysicsDebug(){
//        WorldPhysicsDebugSystem debug = new WorldPhysicsDebugSystem(world);
//        return debug;
//    }
//
//    public WorldUpdateLightSystem sCreateUpdateLight(){
//        WorldUpdateLightSystem sys = new WorldUpdateLightSystem(world);
//        return sys;
//    }
}
