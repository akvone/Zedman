package com.kivi.zedman.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by 1 on 09.03.2016.
 */
public class PlayerController {
    Body player;

    public PlayerController(Body pl){
        player = pl;
    }

    public void update (float deltaTime) {
        processKeys();
    }

    private void processKeys () {
        int horizontalForce = 0;

        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            horizontalForce -= 1;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            horizontalForce += 1;
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.applyForceToCenter(0, 300, false);
        }

        player.setLinearVelocity(horizontalForce * 5, player.getLinearVelocity().y);

    }
}
