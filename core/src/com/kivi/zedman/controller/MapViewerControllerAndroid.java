package com.kivi.zedman.controller;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.kivi.zedman.system.ActionResolver;

/**
 * Created by Kirill on 18.03.2016.
 */
public class MapViewerControllerAndroid implements MapViewerController {
    private float x;
    private float y;
    private OrthographicCamera cam;
    private ActionResolver androidActionResolver;

    public MapViewerControllerAndroid(OrthographicCamera cam,ActionResolver actionResolver){
        this.cam = cam;
        androidActionResolver = actionResolver;
    }

    @Override
    public void update (float deltaTime) {
        processTouches();
    }

    private void processTouches(){
        float scale = androidActionResolver.getScaleFactor();
        System.out.println(scale);
    }

}
