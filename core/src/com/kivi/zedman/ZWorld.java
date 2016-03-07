package com.kivi.zedman;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.kivi.zedman.controller.ZContactListener;

/**
 * Created by Kirill on 06.03.2016.
 */


public class ZWorld {
    World world = new World(new Vector2(0.0F, -20.0F), true);
    public static float CAMERA_WIDTH = 12.0F;
    public static float CAMERA_HEIGHT = 8.0F;
    public int width = 30;
    public int height = 8;

    public void update(float delta) {
    }

    public World getWorld() {
        return this.world;
    }

    public ZWorld() {
        this.world.setContactListener(new ZContactListener(this.world));
        this.createWorld();
    }

    public void setPP(float x, float y) {
    }

    private void createWorld() {
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        Body boxP = this.world.createBody(def);

        for(int i = 0; i < this.width; ++i) {
            Body boxGround = this.createBox(BodyType.StaticBody, 0.5F, 0.5F, 2.0F);
            boxGround.setTransform((float)i, 0.0F, 0.0F);
            ((Fixture)boxGround.getFixtureList().get(0)).setUserData("bd");
            boxGround = this.createBox(BodyType.StaticBody, 0.5F, 0.5F, 0.0F);
            boxGround.setTransform((float)i, (float)(this.height - 1), 0.0F);
            ((Fixture)boxGround.getFixtureList().get(0)).setUserData("b");
        }

    }

    private Body createBox(BodyType type, float width, float height, float density) {
        BodyDef def = new BodyDef();
        def.type = type;
        Body box = this.world.createBody(def);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width, height);
        box.createFixture(poly, density);
        poly.dispose();
        return box;
    }

    public void dispose() {
        this.world.dispose();
    }
}
