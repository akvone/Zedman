package com.kivi.zedman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.kivi.zedman.controller.PlayerController;
import com.kivi.zedman.controller.ZContactListener;
import com.kivi.zedman.utils.MapLoader;

import static com.kivi.zedman.utils.Constants.PPM;

/**
 * Created by Kirill on 06.03.2016.
 */


public class ZWorld {

    PlayerController playerController;

    private Body player;
    private World world;
    private OrthogonalTiledMapRenderer tiledMapRenderer;

    public Body getPlayer() {
        return player;
    }
    public World getWorld() {
        return world;
    }
    public OrthogonalTiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }


    public ZWorld() {
        world = new World(new Vector2(0, -9.8f), true);  //Arguments: gravity vector, and object's sleep boolean
        world.setContactListener(new ZContactListener(this.world));
        createWorld();
        player = createStickman(128,320,32,32);
        playerController = new PlayerController(player);
    }

    public void setPP(float x, float y) {
    }

    private void createWorld() {
        MapLoader mapLoader = new MapLoader("maps/test.tmx", world);
        tiledMapRenderer = mapLoader.getTiledMapRenderer();
    }

    private Body createStickman(int x, int y, int width, int height) {
        Body stickman;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x/PPM,y/PPM);
        def.fixedRotation = true;
        stickman = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/PPM, height/PPM);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 1f;

        stickman.createFixture(fixtureDef);
        shape.dispose();
        return stickman;
    }


    public void update(float delta) {
        playerController.update(delta);
    }



    public void dispose() {
        world.dispose();
    }
}
