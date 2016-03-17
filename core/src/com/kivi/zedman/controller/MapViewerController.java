package com.kivi.zedman.controller;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.kivi.zedman.utils.Constants;


/**
 * Created by Kirill on 08.03.2016.
 */
public class MapViewerController {

    OrthographicCamera cam;
    float x;
    float y;

    public MapViewerController(OrthographicCamera cam){
        this.cam = cam;
    }

    public void update (float deltaTime) {
        processKeys();
    }

    private void processKeys () {
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            cam.translate(0,0.25f* Constants.PPM);
            cam.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            cam.translate(-0.25f* Constants.PPM, 0);
            cam.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            cam.translate(0, -0.25f* Constants.PPM);
            cam.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            cam.translate(0.25f* Constants.PPM, 0);
            cam.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_9)){
            x = cam.position.x;
            y = cam.position.y;
            if (cam.viewportWidth - 1>0&&cam.viewportHeight - 1>0)
            cam.setToOrtho(false, cam.viewportWidth - (4f/9) * Constants.PPM, cam.viewportHeight - 0.25f* Constants.PPM);
            cam.position.set(x,y,0);
            cam.update();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_0)){
            x = cam.position.x;
            y = cam.position.y;
            cam.setToOrtho(false, cam.viewportWidth + (4f/9)* Constants.PPM, cam.viewportHeight+0.25f* Constants.PPM);
            cam.position.set(x,y,0);
            cam.update();
        }
    }

}
