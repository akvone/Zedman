package com.kivi.zedman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.kivi.zedman.utils.Constants;

import static com.kivi.zedman.utils.Constants.PPM;

/**
 * Created by 1 on 17.03.2016.
 */
public class Player {

    private Vector2 previousPosition;
    private Vector2 position;
    private ZWorld zWorld;
    Body body;
    Sprite sprite;

    public Player(ZWorld zWorld, Body bd){
        this.body = bd;
        this.zWorld = zWorld;
        loadTexture();
        previousPosition = new Vector2(0,0);
    }



    private void loadTexture(){
        Texture texture = new Texture(Gdx.files.internal("data/atlas.png"));
        TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / 2, texture.getHeight() / 2);
        sprite = new Sprite(tmp[0][0]);
    }

    public boolean hasMoved(){  //Смотрит, переместился ли игрок за delta, чтобы не послылать на сервер ифнормацию, если игрок стоит
        if (previousPosition.x != body.getPosition().x || previousPosition.y != body.getPosition().y){
            previousPosition.x = body.getPosition().x;
            previousPosition.y = body.getPosition().y;
            return true;
        }
        return false;

    }

    public void render(SpriteBatch batch){
        float x = zWorld.getPlayer().getPosition().x * Constants.PPM;           //Drawing random
        float y = zWorld.getPlayer().getPosition().y * Constants.PPM;           //texture around
        batch.begin();
        sprite.setCenter(x, y);
        sprite.draw(batch);                                                     //player
        batch.end();
    }
    public Body createBullet(float x, float y){
        Body bullet;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x, y);
        def.bullet = true;
        def.fixedRotation = true;

        bullet = zWorld.getWorld().createBody(def);

        CircleShape shape = new CircleShape();
        shape.setRadius(1/20f);

//        shape.setPosition(new Vector2(x, y));
        FixtureDef fixture = new FixtureDef();
        fixture.shape = shape;


        bullet.createFixture(fixture);
        bullet.applyLinearImpulse(100f, 0f, bullet.getWorldCenter().x, bullet.getWorldCenter().y, true);
        return bullet;
    }
    public void update(float dt){

    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }
}
