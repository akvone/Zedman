package com.kivi.zedman.android.controller;

/**
 * Created by Kirill on 18.03.2016.
 */

import android.content.Context;
import android.os.Handler;
import android.view.ScaleGestureDetector;

import com.kivi.zedman.system.ActionResolver;

public class ActionResolverAndroid implements ActionResolver{
    Handler handler;
    Context context;

    public ActionResolverAndroid(Context context) {
        handler = new Handler();
        this.context = context;
    }

    @Override
    public float getScaleFactor() {

        return scale
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scale = detector.getScaleFactor();
            return true;
        }
    }
}
