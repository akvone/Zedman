package com.kivi.zedman.system;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.kivi.zedman.screens.GameScreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

/**
 * Created by Kirill on 04.03.2016.
 */

public class Zedman extends Game {

	@Override
	public void create () {
		setScreen(new GameScreen(this));

	}

}
