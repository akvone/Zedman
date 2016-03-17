package com.kivi.zedman;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.TimeUtils;
import com.kivi.zedman.controller.PlayerController;
import com.kivi.zedman.controller.ZContactListener;
import com.kivi.zedman.utils.MapLoader;

import static com.kivi.zedman.utils.Constants.PPM;

import java.util.HashMap;

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

    public static long startTime;
    public static long currentTime = 0;

    public static HashMap<String, Bot> bots; //Хэш таблица для хранения ботов по ID (cм. в SocketUtil)




    Player player;

    public Player getPlayer() {
        return player;
    }

    public OrthogonalTiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    private OrthogonalTiledMapRenderer tiledMapRenderer;


    public ZWorld() {

        bots = new HashMap<String, Bot>();

        world = new World(new Vector2(0, -10f), true);  //Arguments: gravity vector, and object's sleep boolean
        world.setContactListener(new ZContactListener(this));
        createWorld();

        player = new Player(this ,createBox(64, 80 + 320, 32, 32, false));
        startTime = TimeUtils.millis();
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
    }
    public Body createBox(int x, int y, int width, int height, boolean isStatic) {
        Body pBody;
        BodyDef def = new BodyDef();

        if(isStatic)
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x /PPM , y  / PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        pBody.createFixture(shape, 1.0f);
        shape.dispose();
        return pBody;
    }

    private Body createStickman(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        bodyDef.angularVelocity = 10;
        bodyDef.fixedRotation = true;
        Body stickman = world.createBody(bodyDef);

        CircleShape circle = new CircleShape();
        circle.setRadius(1);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 1f;

        stickman.createFixture(fixtureDef);
        circle.dispose();
        return stickman;
    }


    public void update(float delta) {
        playerController.update(delta);
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
