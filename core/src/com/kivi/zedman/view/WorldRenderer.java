package com.kivi.zedman.view;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.utils.Array;
import com.kivi.zedman.ZWorld;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WorldRenderer {
    Box2DDebugRenderer renderer = new Box2DDebugRenderer();
    public static float CAMERA_WIDTH = 5.0F;
    public static float CAMERA_HEIGHT = 15.0F;
    public float ppuX;
    public float ppuY;
    ZWorld world;
    public OrthographicCamera cam;
    private SpriteBatch spriteBatch;
    Texture texture;
    public Map<String, TextureRegion> textureRegions;
    BitmapFont font = new BitmapFont();
    Array<Body> bodies;

    public WorldRenderer(ZWorld world, float w, float h, boolean debug) {
        this.world = world;
        CAMERA_WIDTH = w;
        CAMERA_HEIGHT = h;
        this.ppuX = (float) Gdx.graphics.getWidth() / CAMERA_WIDTH;
        this.ppuY = (float) Gdx.graphics.getHeight() / CAMERA_HEIGHT;
        this.spriteBatch = new SpriteBatch();
        this.textureRegions = new HashMap();
        this.loadTextures();
        this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        this.SetCamera(CAMERA_WIDTH / 2.0F, CAMERA_HEIGHT / 2.0F);
        bodies = new Array<Body>();
    }

    private void loadTextures() {
        texture = new Texture(Gdx.files.internal("data/atlas.png"));
        TextureRegion tmp[][] = TextureRegion.split(texture, texture.getWidth() / 2, texture.getHeight() / 2);
        textureRegions.put("player", tmp[0][0]);
        textureRegions.put("brick1", tmp[0][1]);
        textureRegions.put("brick2", tmp[1][0]);
        textureRegions.put("brick3", tmp[1][1]);
    }

    public void SetCamera(float x, float y) {
        this.cam.position.set(x, y, 0.0F);
        this.cam.update();
    }

    public void render(float delta) {
        this.renderer.render(this.world.getWorld(), this.cam.combined);
        this.spriteBatch.begin();
//        this.drawPlayer();
        this.drawBlocks();
//        this.font.drawMultiLine(this.spriteBatch, "friction: " + this.world.getPlayer().getFriction() + "\ngrounded: " + WorldController.grounded + "\nvelocityX:" + this.world.getPlayer().getVelocity().x, this.world.getPlayer().getPosition().x * this.ppuX + 20.0F, this.world.getPlayer().getPosition().y * this.ppuY);
        this.spriteBatch.end();
        this.world.getWorld().step(delta, 4, 4);
    }

    private void drawBlocks() {
        this.world.getWorld().getBodies(bodies);
        Body body = null;
        for (int i = 0; i < bodies.size; i++)
            body = bodies.get(i);
        if (body != null && ((Fixture) body.getFixtureList().get(0)).getUserData() != null && ((Fixture) body.getFixtureList().get(0)).getUserData().equals("b")) {
            this.spriteBatch.draw((TextureRegion) this.textureRegions.get("brick1"), (body.getPosition().x - 0.5F) * this.ppuX, (body.getPosition().y - 0.5F) * this.ppuY, 1.0F * this.ppuX, 1.0F * this.ppuY);
        }

        if (body != null && ((Fixture) body.getFixtureList().get(0)).getUserData() != null && ((Fixture) body.getFixtureList().get(0)).getUserData().equals("bd")) {
            this.spriteBatch.draw((TextureRegion) this.textureRegions.get("brick2"), (body.getPosition().x - 0.5F) * this.ppuX, (body.getPosition().y - 0.5F) * this.ppuY, 1.0F * this.ppuX, 1.0F * this.ppuY);
        }
    }


//    private void drawPlayer() {
//        this.spriteBatch.draw((TextureRegion) this.textureRegions.get("player"), (this.world.getPlayer().getPosition().x - 0.4F) * this.ppuX, (this.world.getPlayer().getPosition().y - 0.4F) * this.ppuY, 0.8F * this.ppuX, 0.8F * this.ppuY);
//    }


    public void dispose() {
        this.world.dispose();
    }
}
