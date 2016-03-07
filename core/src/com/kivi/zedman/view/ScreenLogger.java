package com.kivi.zedman.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

/**
 * Created by Kirill on 04.03.2016.
 */

//Need to rewrite
public class ScreenLogger {
    private SpriteBatch batch;
    private BitmapFont font;

    public ScreenLogger() {
        loadAssets();
    }

    private void loadAssets () {
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(Color.RED);
    }

    public void render () {
        ArrayList<Object> systemInformation = getInformation();
        batch.begin();
        for (int i = 0; i<systemInformation.size(); i++){
            font.draw(batch, systemInformation.get(i).toString(), 0, Gdx.graphics.getHeight()-20*i);
        }
        batch.end();
    }

    public ArrayList<Object> getInformation() {
        ArrayList<Object> systemInformation = new ArrayList<Object>();
        systemInformation.add("Vir W - "+ Gdx.graphics.getWidth()+
                " Vir H - "+ Gdx.graphics.getHeight()+
                " FPS - "+ Gdx.graphics.getFramesPerSecond());
        systemInformation.add("1 touchX - "+ Gdx.input.getX(0)+" touchY - "+ Gdx.input.getY(0));
        systemInformation.add("2 touchX - "+ Gdx.input.getX(1)+" touchY - "+ Gdx.input.getX(1));

        return systemInformation;
    }

    public void dispose () {
        batch.dispose();
    }
}