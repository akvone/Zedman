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
    World world;

    PlayerController playerController;

    public int width = 50;
    public int height = 20;

    public float pixelSize = 0.5f;
    public long timePastLastCreate = 0;

    Body player;

    public Body getPlayer() {
        return player;
    }

    public OrthogonalTiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    private OrthogonalTiledMapRenderer tiledMapRenderer;


    public ZWorld() {

        world = new World(new Vector2(0, -9.8f), true);  //Arguments: gravity vector, and object's sleep boolean
        world.setContactListener(new ZContactListener(this.world));
        createWorld();

        player = createStickman(32, 290, 32, 32);
        playerController = new PlayerController(player);
    }

    public void setPP(float x, float y) {
    }

    private void createWorld() {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        Body boxP = this.world.createBody(def);

        MapLoader mapLoader = new MapLoader("Maps/test.tmx", world);
        tiledMapRenderer = mapLoader.getTiledMapRenderer();


//        for(int i = 0; i < this.width; i++) {
//            Body boxGround = createBox(pixelSize, pixelSize, 0);
//            boxGround.setTransform(i, 0, 0);
//            boxGround.getFixtureList().get(0).setUserData("Ground");
//            boxGround = createBox(pixelSize, pixelSize, 0);
//            boxGround.setTransform(i, height - 1, 0);
//            boxGround.getFixtureList().get(0).setUserData("Ceiling");
//        }
    }

    public Body createBox(int x, int y, int width, int height, boolean isStatic) {
        Body pBody;
        BodyDef def = new BodyDef();

        if(isStatic)
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x /PPM , y  / PPM);
        def.fixedRotation = false;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        pBody.createFixture(shape, 1.0f);
        shape.dispose();
        return pBody;
    }

    private Body createStickman(int x, int y, int width, int height) {
        Body stickman;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x /PPM , y  / PPM);
        def.fixedRotation = false;
        stickman = world.createBody(def);

//        PolygonShape shape = new PolygonShape();
//        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);
//        shape.setAsBox(width / 3 / PPM, height / 3 / PPM);

        CircleShape circle = new CircleShape();

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 1f;

        stickman.createFixture(fixtureDef);
        circle.dispose();;
        return stickman;
    }

//    private Body createRotatingCircle(){
//        BodyDef bodyDef = new BodyDef();
//        bodyDef.type = BodyType.DynamicBody;
//        bodyDef.angularVelocity = 10;
//        bodyDef.fixedRotation = true;
//        Body stickman = world.createBody(bodyDef);
//
//        CircleShape circle = new CircleShape();
//        circle.setRadius(1);
//        FixtureDef fixtureDef = new FixtureDef();
//        fixtureDef.shape = circle;
//        fixtureDef.density = 0.5f;
//        fixtureDef.friction = 0.5f;
//        fixtureDef.restitution = 1f;
//
//        stickman.createFixture(fixtureDef);
//        circle.dispose();
//        return stickman;
//    }


    public void update(float delta) {
        playerController.update(delta);

        if(Gdx.input.isKeyPressed(Input.Keys.Y)) {
            player.setTransform(10,10,0);
        }
//        if (System.currentTimeMillis()>timePastLastCreate+10) {
//            Body stickman = createStickman();
//            stickman.setTransform((float) Math.random() * 50, (float) Math.random() * 20, 0);
//            stickman.setFixedRotation(true);
//            timePastLastCreate = System.currentTimeMillis();
//        }
    }


    public World getWorld() {
        return world;
    }

    public void dispose() {
        world.dispose();
    }
}
