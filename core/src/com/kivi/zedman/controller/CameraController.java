package com.kivi.zedman.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.kivi.zedman.utils.Constants;


/**
 * Created by Kirill on 08.03.2016.
 */
public class CameraController {

    OrthographicCamera cam;

    public CameraController(OrthographicCamera cam){
        this.cam = cam;
    }

    public void update (float deltaTime) {
        processKeys();
    }

    private void processKeys () {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.translate(0,1);
            cam.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.translate(-1, 0);
            cam.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            cam.translate(0, -1);
            cam.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            cam.translate(1, 0);
            cam.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_9)){
            cam.setToOrtho(false,cam.viewportWidth-1* Constants.PPM,cam.viewportHeight-1*Constants.PPM);
            cam.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_0)){
            cam.setToOrtho(false,cam.viewportWidth+1*Constants.PPM,cam.viewportHeight+1* Constants.PPM);
            cam.update();
        }
    }

}
