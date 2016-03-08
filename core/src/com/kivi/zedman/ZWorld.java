package com.kivi.zedman;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.kivi.zedman.controller.ZContactListener;

/**
 * Created by Kirill on 06.03.2016.
 */


public class ZWorld {
    World world;

    public int width = 100;
    public int height = 20;

    public ZWorld() {
        world = new World(new Vector2(0, -10), true);  //Arguments: gravity vector, and object's sleep boolean
        this.world.setContactListener(new ZContactListener(this.world));
        this.createWorld();
    }

    public void setPP(float x, float y) {
    }

    private void createWorld() {
        Body stickman = createStickman();
        stickman.setTransform(1, 4, 0);
        stickman.setFixedRotation(true);

        for(int i = 0; i < this.width; i++) {
            Body boxGround = createBox(0.5f, 0.5f, 0);
            boxGround.setTransform(i, 0, 0); //
            boxGround.getFixtureList().get(0).setUserData("Ground");
            boxGround = createBox(0.5f, 0.5f, 0);
            boxGround.setTransform(i, height - 1, 0);
            boxGround.getFixtureList().get(0).setUserData("Ceiling");
        }

    }

    private Body createBox(float width, float height, float density) {
        BodyDef def = new BodyDef();
        def.type = BodyType.StaticBody;
        Body box = world.createBody(def);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);
        box.createFixture(shape, density);
        shape.dispose();
        return box;
    }

    private Body createStickman(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyType.DynamicBody;
        Body stickman = world.createBody(bodyDef);
        CircleShape circle = new CircleShape();
        circle.setRadius(1);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = circle;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.6f;
        stickman.createFixture(fixtureDef);
        circle.dispose();
        return stickman;
    }


    public void update(float delta) {
    }

    public World getWorld() {
        return this.world;
    }

    public void dispose() {
        this.world.dispose();
    }
}
