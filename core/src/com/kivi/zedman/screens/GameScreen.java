package com.kivi.zedman.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.net.Socket;
import com.kivi.zedman.controller.MapViewerController;
import com.kivi.zedman.utils.SocketUtil;
import com.kivi.zedman.view.WorldRenderer;
import com.kivi.zedman.ZWorld;
import com.kivi.zedman.view.OnscreenControlRenderer;
import com.kivi.zedman.view.ScreenLogger;
import com.badlogic.gdx.graphics.GL20;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.emitter.Emitter;

public class GameScreen extends ZedmanScreen {
	private final float UPDATE_TIME = 1/60f; //Частота, с которой обновления позиции игрока посылаются на сервер
	float timer;							 //Таймер, отвечающий за эту частоту

	ScreenLogger screenLogger;
	OnscreenControlRenderer controlRenderer;
	WorldRenderer worldRenderer;
	MapViewerController mapViewerController;
	ZWorld zworld;
	SocketUtil socket;

	public GameScreen (Game game) {
		super(game);
	}

	@Override
	public void show () {
		zworld = new ZWorld();
		screenLogger = new ScreenLogger(zworld);
		controlRenderer = new OnscreenControlRenderer();
		worldRenderer = new WorldRenderer(zworld, true);
		mapViewerController = new MapViewerController(worldRenderer.cam);
		socket = new SocketUtil(zworld);
		socket.connectSocket();
		socket.configureSocketEvent();


		Gdx.gl.glClearColor(0f, 0f, 0f, 0f); //Set clear color as black
	}

	public void updateServer(float dt){  //Посылает на сервер координаты игрока при его перемещении
		timer += dt;
		if ( timer >= UPDATE_TIME && zworld.getPlayer() != null && zworld.getPlayer().hasMoved()){
			timer = 0;
			JSONObject data = new JSONObject();
			try {
				data.put("x", zworld.getPlayer().getPosition().x);
				data.put("y", zworld.getPlayer().getPosition().y);
				socket.getSocket().emit("playerMoved", data); //Отправка на сервер JSON объекта
			} catch (JSONException e) {
				Gdx.app.log("SocketIO", "Failed to send update to server");
			}
		}
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //And clear the screen with this color

		updateServer(delta);
		mapViewerController.update(delta);
		zworld.update(delta);

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
