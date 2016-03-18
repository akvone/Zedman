package com.kivi.zedman.controller;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.Application.ApplicationType;

import static com.kivi.zedman.utils.Constants.*;


/**
 * Created by Kirill on 08.03.2016.
 */

public class MapViewerControllerDesktop implements MapViewerController {
    private float x;
    private float y;
    private OrthographicCamera cam;

    public MapViewerControllerDesktop(OrthographicCamera cam){
        this.cam = cam;
    }

    @Override
    public void update (float deltaTime) {
        processKeys();
    }

    private void processKeys () {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.translate(0, 10);
            cam.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.translate(-10, 0);
            cam.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            cam.translate(0, -10);
            cam.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            cam.translate(10, 0);
            cam.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_9)){
            x = cam.position.x;
            y = cam.position.y;
            if (cam.viewportWidth - CAMERA_WIDTH/10>0&&cam.viewportHeight - CAMERA_HEIGHT/10>0) {
                cam.setToOrtho(false, cam.viewportWidth - CAMERA_WIDTH/10, cam.viewportHeight - CAMERA_HEIGHT/10);
            }
            cam.position.set(x, y, 0);
            cam.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_0)){
            x = cam.position.x;
            y = cam.position.y;
            cam.setToOrtho(false, cam.viewportWidth + CAMERA_WIDTH/10, cam.viewportHeight+CAMERA_HEIGHT/10);
            cam.position.set(x,y,0);
            cam.update();
        }
    }
}
