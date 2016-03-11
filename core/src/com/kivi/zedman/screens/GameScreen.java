package com.kivi.zedman.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.kivi.zedman.controller.CameraController;
import com.kivi.zedman.controller.PlayerController;
import com.kivi.zedman.view.WorldRenderer;
import com.kivi.zedman.ZWorld;
import com.kivi.zedman.view.OnscreenControlRenderer;
import com.kivi.zedman.view.ScreenLogger;
import com.badlogic.gdx.graphics.GL20;

import static com.kivi.zedman.utils.Constants.PPM;
public class GameScreen extends ZedmanScreen {
	ScreenLogger screenLogger;
	OnscreenControlRenderer controlRenderer;
	WorldRenderer worldRenderer;
	CameraController cameraController;
	PlayerController playerController;

	ZWorld zworld;

	public GameScreen (Game game) {
		super(game);
	}

	@Override
	public void show () {
		zworld = new ZWorld();
		screenLogger = new ScreenLogger();
		controlRenderer = new OnscreenControlRenderer();
		worldRenderer = new WorldRenderer(zworld, true);
		cameraController = new CameraController(worldRenderer.cam);
		playerController = new PlayerController(zworld.getPlayer());


		Gdx.gl.glClearColor(0f, 0f, 0f, 0f); //Set clear color as black
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //And clear the screen with this color

		cameraController.update(delta);
		playerController.update(delta);

		screenLogger.render();
		controlRenderer.render();
		worldRenderer.render(delta);

		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			game.setScreen(new GameScreen(game));
		}
	}

	@Override
	public void hide () {
		Gdx.app.debug("Zedman", "dispose game screen");
		screenLogger.dispose();
		controlRenderer.dispose();
		worldRenderer.dispose();
	}


}
