package com.kivi.zedman.system;

import com.badlogic.gdx.Game;
import com.kivi.zedman.screens.GameScreen;

/**
 * Created by Kirill on 04.03.2016.
 */

public class Zedman extends Game {

	public ActionResolver androidActionResolver;

	public Zedman(ActionResolver actionResolver) {
		androidActionResolver = actionResolver;
	}

	@Override
	public void create () {
		setScreen(new GameScreen(this, androidActionResolver));
	}
}
