package com.kivi.zedman.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.kivi.zedman.ZWorld;
import com.kivi.zedman.screens.GameScreen;
import com.kivi.zedman.utils.Constants;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WorldRenderer {
    Box2DDebugRenderer renderer = new Box2DDebugRenderer(); //!!! Must be changed in releases
    public static float CAMERA_WIDTH = 36f;
    public static float CAMERA_HEIGHT = 24f;
    public float ppuX;
    public float ppuY;
    ZWorld zWorld;
    public OrthographicCamera cam;
    private SpriteBatch spriteBatch;
    Texture texture;
    Texture playerTexture;
    Sprite playerSprite;
    Body player;
    public Map<String, TextureRegion> textureRegions;
    Array<Body> bodies;
    private OrthogonalTiledMapRenderer tmr;

    public WorldRenderer(ZWorld zWorld, boolean debug) {
        this.zWorld = zWorld;
        this.ppuX = (float) Gdx.graphics.getWidth() / CAMERA_WIDTH;
        this.ppuY = (float) Gdx.graphics.getHeight() / CAMERA_HEIGHT;
        this.spriteBatch = new SpriteBatch();
        textureRegions = new HashMap();
        loadTextures();
        cam = new OrthographicCamera(CAMERA_WIDTH * Constants.PPM, CAMERA_HEIGHT *Constants.PPM);
        SetCamera(0, 0);
        bodies = new Array<Body>();
        tmr = zWorld.getTiledMapRenderer();
        player = zWorld.getPlayer();
    }

    private void loadTextures() {
        texture = new Texture(Gdx.files.internal("data/atlas.png"));
        TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / 2, texture.getHeight() / 2);
        textureRegions.put("player", tmp[0][0]);
        textureRegions.put("brick1", tmp[0][1]);
        textureRegions.put("brick2", tmp[1][0]);
        textureRegions.put("brick3", tmp[1][1]);
        playerTexture = texture;        //Temporary solution
        playerSprite = new Sprite(tmp[0][0]);
    }

    public void SetCamera(float x, float y) {
        cam.position.set(x, y, 0);
        cam.update();
    }

    public void update(float delta){
        cam.update();                        // Do not move it down. Trust me.
        zWorld.getWorld().step(delta, 6, 2); //Arguments: 1)zWorld update time 2)6 3)2 best practice
        spriteBatch.setProjectionMatrix(cam.combined);
        tmr.setView(cam);
    }

    public void render(float delta) {
        update(delta);
		tmr.render();                                                           //Render map textures
        renderer.render(zWorld.getWorld(), cam.combined.scl(Constants.PPM));
        spriteBatch.begin();
        float x = zWorld.getPlayer().getPosition().x * Constants.PPM;           //Drawing random
        float y = zWorld.getPlayer().getPosition().y * Constants.PPM;           //texture around
        playerSprite.setCenter(x,y);
        playerSprite.draw(spriteBatch);                                //player
//        this.drawPlayer();
//        drawBlocks();
//        this.font.drawMultiLine(this.spriteBatch, "friction: " + this.zWorld.getPlayer().getFriction() + "\ngrounded: " + WorldController.grounded + "\nvelocityX:" + this.zWorld.getPlayer().getVelocity().x, this.zWorld.getPlayer().getPosition().x * this.ppuX + 20.0F, this.zWorld.getPlayer().getPosition().y * this.ppuY);
        spriteBatch.end();
    }

    private void drawBlocks() {
        this.zWorld.getWorld().getBodies(bodies);
        Body body = null;
        for (int i = 0; i < 10; i++)
            body = bodies.get(i);
        if (body != null && (body.getFixtureList().get(0)).getUserData() != null && (body.getFixtureList().get(0)).getUserData().equals("b")) {
            this.spriteBatch.draw(this.textureRegions.get("brick1"), (body.getPosition().x - 0.5F) * this.ppuX, (body.getPosition().y - 0.5F) * this.ppuY, 1.0F * this.ppuX, 1.0F * this.ppuY);
        }
        if (body != null && ((Fixture) body.getFixtureList().get(0)).getUserData() != null && ((Fixture) body.getFixtureList().get(0)).getUserData().equals("bd")) {
            this.spriteBatch.draw((TextureRegion) this.textureRegions.get("brick2"), (body.getPosition().x - 0.5F) * this.ppuX, (body.getPosition().y - 0.5F) * this.ppuY, 1.0F * this.ppuX, 1.0F * this.ppuY);

        }
    }


//    private void drawPlayer() {
//        this.spriteBatch.draw((TextureRegion) this.textureRegions.get("player"), (this.zWorld.getPlayer().getPosition().x - 0.4F) * this.ppuX, (this.zWorld.getPlayer().getPosition().y - 0.4F) * this.ppuY, 0.8F * this.ppuX, 0.8F * this.ppuY);
//    }


    public void dispose() {
        this.zWorld.dispose();
        texture.dispose();
        playerTexture.dispose();
        spriteBatch.dispose();
    }
}