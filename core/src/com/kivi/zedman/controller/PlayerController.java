package com.kivi.zedman.controller;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.kivi.zedman.Player;

/**
 * Created by 1 on 09.03.2016.
 */
public class PlayerController {
    Player player;

    boolean jump;
    boolean runLeft;
    boolean runRight;
    boolean teleport;
    boolean fire;

    public PlayerController(Player pl){
        player = pl;
    }

    public void update (float deltaTime) {
        process();
    }

    private void process () {
        int horizontalForce = 0;

        runRight = false;
        runLeft = false;
        jump = false;
        teleport = false;


        if (Gdx.app.getType()== Application.ApplicationType.Android) {
            processTouches();
        }
        if (Gdx.app.getType()== Application.ApplicationType.Desktop) {
            processKeys();
        }
        if(jump) {
            player.getBody().applyForceToCenter(0, 300, false);
        }
        if (runLeft) {
            horizontalForce -= 1;
        }
        if (runRight){
            horizontalForce += 1;
        }
        player.getBody().setLinearVelocity(horizontalForce * 5, player.getBody().getLinearVelocity().y);
        if (teleport){
            player.getBody().setTransform(10,10,0);
        }
        if(fire){
            player.createBullet();
        }

    }

    private void processKeys () {
        runLeft = Gdx.input.isKeyPressed(Input.Keys.LEFT);
        runRight = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
        jump = Gdx.input.isKeyJustPressed(Input.Keys.UP);
        teleport = Gdx.input.isKeyJustPressed(Input.Keys.Y);
        fire = Gdx.input.isKeyPressed(Input.Keys.SPACE);
    }

    private void processTouches () {
        if (Gdx.input.isTouched(0)){
            float x;
            float y;
            for (int i=0;Gdx.input.isTouched(i);i++) {
                x=Gdx.input.getX(i);
                y=Gdx.input.getY(i);
                x=x/(float)Gdx.graphics.getWidth()*1280;
                y=720-y/(float)Gdx.graphics.getHeight()*720;
                runLeft = (x > 0 && x < 128 && y > 0 && y < 128)||runLeft;
                runRight = (x > 128 && x < 256 && y >0 && y < 128)||runRight;
                jump = (x > 1280-128 && x < 1280 && y >0 && y < 128)||jump;
                teleport = (x > 1280-256 && x < 1280-128 && y >0 && y < 128)||teleport;
            }
        }
    }
}
