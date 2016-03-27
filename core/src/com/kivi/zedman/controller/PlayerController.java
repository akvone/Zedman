package com.kivi.zedman.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.physics.box2d.Body;
import com.kivi.zedman.Player;

/**
 * Created by 1 on 09.03.2016.
 */
public class PlayerController {
    Player player;

    public PlayerController(Player pl){
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
            player.getBody().applyForceToCenter(0, 300, false);
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SLASH)){
            player.createBullet(player.getBody().getWorldCenter().x+0.5f, player.getBody().getWorldCenter().y);
            Gdx.app.log("Bullet position", player.getBody().getWorldCenter().x + " : " + player.getBody().getWorldCenter().y);
        }

        player.getBody().setLinearVelocity(horizontalForce * 5, player.getBody().getLinearVelocity().y);

    }
}
