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
	Body player;

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
		player = createBox(32, 290, 32, 32, false);
		zworld.setPlayer(player);
		playerController = new PlayerController(player);


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

	public Body createBox(int x, int y, int width, int height, boolean isStatic) {
		Body pBody;
		BodyDef def = new BodyDef();

		if(isStatic)
			def.type = BodyDef.BodyType.StaticBody;
		else
			def.type = BodyDef.BodyType.DynamicBody;

		def.position.set(x /PPM , y  / PPM);
		def.fixedRotation = true;
		pBody = zworld.getWorld().createBody(def);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

		pBody.createFixture(shape, 1.0f);
		shape.dispose();
		return pBody;
	}
}
