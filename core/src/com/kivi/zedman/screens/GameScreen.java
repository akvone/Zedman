package com.kivi.zedman.screens;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.kivi.zedman.controller.MapViewerController;
import com.kivi.zedman.system.ActionResolver;
import com.kivi.zedman.view.ScreenControlRenderer;
import com.kivi.zedman.view.ScreenControlRendererDesktop;
import com.kivi.zedman.view.WorldRenderer;
import com.kivi.zedman.ZWorld;
import com.kivi.zedman.view.ScreenControlRendererAndroid;
import com.kivi.zedman.view.ScreenLogger;
import com.badlogic.gdx.graphics.GL20;

public class GameScreen extends ZedmanScreen {
	private ScreenLogger screenLogger;
	private ScreenControlRenderer controlRenderer;
	private WorldRenderer worldRenderer;
	private MapViewerController mapViewerController;
	private ZWorld zworld;

	private ActionResolver androidActionResolver;


	public GameScreen (Game game, ActionResolver actionResolver) {
		super(game);
		androidActionResolver = actionResolver;
	}

	@Override
	public void show () {
		zworld = new ZWorld();
		screenLogger = new ScreenLogger(zworld);
		worldRenderer = new WorldRenderer(zworld, true);
		mapViewerController = new MapViewerController(worldRenderer.cam);
		platformDependency();

		Gdx.gl.glClearColor(0f, 0f, 0f, 0f); //Set clear color as black
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //And clear the screen with this color

		mapViewerController.update(delta);
		zworld.update(delta);

		worldRenderer.render(delta);
		screenLogger.render();
		controlRenderer.render();

		//TODO Change location of this
		if (Gdx.input.isKeyPressed(Keys.ESCAPE)) {
			game.setScreen(new GameScreen(game,androidActionResolver));
		}
	}

	@Override
	public void hide () {
		Gdx.app.debug("Zedman", "dispose game screen");
		screenLogger.dispose();
		controlRenderer.dispose();
		worldRenderer.dispose();
	}

	private void platformDependency(){
		if (Gdx.app.getType()==ApplicationType.Desktop) {
			//Change this!
			controlRenderer = new ScreenControlRendererDesktop();
		}
		else if(Gdx.app.getType()==ApplicationType.Android){
			controlRenderer = new ScreenControlRendererAndroid();
		}
	}
}
