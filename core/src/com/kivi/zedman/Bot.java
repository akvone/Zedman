package com.kivi.zedman;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

import static com.kivi.zedman.utils.Constants.FILTER_PLAYER;
import static com.kivi.zedman.utils.Constants.FILTER_WALL;
import static com.kivi.zedman.utils.Constants.PPM;

/**
 * Created by 1 on 15.03.2016.
 */
public class Bot {

    public Vector2 positionFromServer;          //Во избежание рантайма
    public boolean updated = true;              //В update обновляются координаты, принятые с сервера

    private Body body;

    public Bot(Body body){
        this.body = body;
    }

    public Bot(ZWorld zWorld){
        this.body = createBot(128, 320, 32, zWorld);
    }

    public Bot(ZWorld zWorld, Vector2 position){
        this.body = createBot((int) position.x, (int) position.y, 32, zWorld);
    }

    public void update(float dt){

    }
    public Body createBot(int x, int y, int width, ZWorld zWorld) {
        Body stickman;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.StaticBody;
        def.position.set(x/PPM,y/PPM);
        def.fixedRotation = true;
        stickman = zWorld.getWorld().createBody(def);

        CircleShape shape = new CircleShape();
//        shape.setAsBox(width/PPM /2, height/PPM/2);
        shape.setRadius(width/PPM/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2f;
        fixtureDef.friction = 1f;
        fixtureDef.filter.categoryBits = FILTER_PLAYER;
        fixtureDef.filter.maskBits = FILTER_WALL;

        stickman.createFixture(fixtureDef);
        shape.dispose();
        return stickman;
    }

    public Body getBody() {
        return body;
    }
}
