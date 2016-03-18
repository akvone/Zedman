package com.kivi.zedman.android;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.kivi.zedman.android.controller.ActionResolverAndroid;
import com.kivi.zedman.screens.GameScreen;
import com.kivi.zedman.system.Zedman;

public class AndroidLauncher extends AndroidApplication {
    ActionResolverAndroid actionResolverAndroid;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
		config.useWakelock = true;
		actionResolverAndroid = new ActionResolverAndroid(this);
		initialize(new Zedman(actionResolverAndroid), config);
	}

}
