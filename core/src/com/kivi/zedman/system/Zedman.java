package com.kivi.zedman.system;

import com.badlogic.gdx.Game;
import com.kivi.zedman.screens.GameScreen;

/**
 * Created by Kirill on 04.03.2016.
 */

public class Zedman extends Game {

	@Override
	public void create () {
		setScreen(new GameScreen(this));

	}

}
