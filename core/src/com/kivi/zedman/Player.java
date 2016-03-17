package com.kivi.zedman;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.kivi.zedman.utils.Constants;

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
