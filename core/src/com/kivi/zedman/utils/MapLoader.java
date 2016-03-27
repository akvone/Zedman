package com.kivi.zedman.utils;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Disposable;
import com.sun.media.jfxmediaimpl.MediaDisposer;

/**
 * Class for loading .tmx maps and parsing their "Collision layer" as physical objects
 */
public class MapLoader implements Disposable {

    OrthogonalTiledMapRenderer tiledMapRenderer;
    TiledMap map;
    String mapPath;

    String layerName = "Collision layer";

    public MapLoader(String mapPath, World world){
        this.mapPath = mapPath;
        map = new TmxMapLoader().load(mapPath);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(map);
        TiledObjectUtil.parseTiledObjectLayer(world, map.getLayers().get(layerName).getObjects());
    }

    public OrthogonalTiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }

    public TiledMap getMap() {
        return map;
    }

    public String getMapPath() {
        return mapPath;
    }

    public void setLayerName(String layerName) {
        this.layerName = layerName;
    }

    public String getLayerName() {
        return layerName;
    }

    @Override
    public void dispose() {
        //TODO: dispose map
        map.dispose();
        tiledMapRenderer.dispose();
    }
}
