package com.kivi.zedman;

import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.TimeUtils;
import com.kivi.zedman.controller.PlayerController;
import com.kivi.zedman.controller.ZContactListener;
import com.kivi.zedman.utils.Constants;
import com.kivi.zedman.utils.MapLoader;
import com.kivi.zedman.utils.SocketUtil;

import static com.kivi.zedman.utils.Constants.FILTER_PLAYER;
import static com.kivi.zedman.utils.Constants.FILTER_WALL;
import static com.kivi.zedman.utils.Constants.MICRO_SIDE;
import static com.kivi.zedman.utils.Constants.MICRO_SIDE_BOX;
import static com.kivi.zedman.utils.Constants.MINI_SIDE_BOX;
import static com.kivi.zedman.utils.Constants.PPM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Kirill on 06.03.2016.
 */


public class ZWorld {

    PlayerController playerController;

    public Body bodyToDelete;
    public Body bodyToChange;
    public List<Fixture> wallsToSplit;
    public Bullet bulletToCreate;

    private Player player;
    private World world;
    private OrthogonalTiledMapRenderer tiledMapRenderer;
    private SocketUtil socket;

    public float pixelSize = 0.5f;
    public long timePastLastCreate = 0;

    public static long startTime;
    public static long currentTime = 0;

    public static HashMap<String, Bot> bots; //Хэш таблица для хранения ботов по ID (cм. в SocketUtil)

    public Player getPlayer() {
        return player;
    }

    public OrthogonalTiledMapRenderer getTiledMapRenderer() {
        return tiledMapRenderer;
    }



    public ZWorld() {

        wallsToSplit = new ArrayList<Fixture>(); //Массив стенок, которые надо разделить на данном шаге
        bots = new HashMap<String, Bot>();

        world = new World(new Vector2(0, -10f), true);  //Arguments: gravity vector, and object's sleep boolean
        world.setContactListener(new ZContactListener(this));
        createWorld();

//        player = new Player(this ,createBox(64, 80 + 320, 32, 32, false));
        player = new Player(this ,createStickman(128, 320, 32, 32));
        startTime = TimeUtils.millis();
        playerController = new PlayerController(player);
        socket = new SocketUtil(this);
        socket.connectSocket();
        socket.configureSocketEvent();
    }

    public void setPP(float x, float y) {
    }

    private void createWorld() {
        MapLoader mapLoader = new MapLoader("maps/test.tmx", world);
        tiledMapRenderer = mapLoader.getTiledMapRenderer();
    }
    public Body createBox(int x, int y, int width, int height, boolean isStatic) {
        Body pBody;
        BodyDef def = new BodyDef();

        if(isStatic)
            def.type = BodyDef.BodyType.StaticBody;
        else
            def.type = BodyDef.BodyType.DynamicBody;

        def.position.set(x /PPM , y  / PPM);
        def.fixedRotation = true;
        pBody = world.createBody(def);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        pBody.createFixture(shape, 1.0f);
        shape.dispose();
        return pBody;
    }

    public Body createStickman(int x, int y, int width, int height) {
        Body stickman;
        BodyDef def = new BodyDef();
        def.type = BodyDef.BodyType.DynamicBody;
        def.position.set(x/PPM,y/PPM);
        def.fixedRotation = true;
        stickman = world.createBody(def);

        CircleShape shape = new CircleShape();
//        shape.setAsBox(width/PPM /2, height/PPM/2);
        shape.setRadius(width/PPM/2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2f;
        fixtureDef.friction = 1f;
        fixtureDef.filter.categoryBits = FILTER_PLAYER;
        fixtureDef.filter.maskBits = FILTER_WALL;

        stickman.createFixture(fixtureDef);
        shape.dispose();
        return stickman;
    }


    public void update(float delta) {
        if (bulletToCreate != null){
            bulletToCreate.create(this);
            bulletToCreate = null;
        }
        if (bodyToDelete != null) {
            world.destroyBody(bodyToDelete);
            bodyToDelete = null;
        }
        if (bodyToChange != null){
            bodyToChange.setType(BodyType.DynamicBody);
            Filter filter = new Filter();
            filter.categoryBits = 0;
            filter.maskBits = 0;
            bodyToChange.getFixtureList().first().setFilterData(filter);
            bodyToChange = null;
        }
        if ((wallsToSplit != null)&&(!wallsToSplit.isEmpty())){
            for (Iterator<Fixture> iterator = wallsToSplit.iterator(); iterator.hasNext();)
//                    Fixture wall :            Особый цикл, потому что иначе всё плохо
//                    wallsToSplit) {
            {
                Fixture wall = iterator.next(); //Стенка, которую разбиваем на этом прохоже
                if (wall.getBody().getUserData() == null) { //Поле UserData отвечает за степень разбиения (null - не разбивалось, 1 - разбили на кубики 1х1 метр, 2 - разбили на мелкие кубики (0.125 метра)
                    Vector2 vertex1 = new Vector2(), vertex2 = new Vector2();
                    if (wall.getShape() instanceof  PolygonShape) {
                        PolygonShape shape = (PolygonShape) wall.getShape();        //Здесь стенки, которые не разбиты ни разу
                        shape.getVertex(0, vertex1);                                //Могут быть прямоугольниками или полилинией (из .tmx)
                        shape.getVertex(2, vertex2);                                //Вытаскиваем нижнюю левую вершину (vertex1)
                    } else {                                                        //И верхнюю правую (vertex2)
                        if (wall.getShape() instanceof ChainShape) {
                            ChainShape shape = (ChainShape) wall.getShape();
                            shape.getVertex(0, vertex1);
                            shape.getVertex(2, vertex2);
                        }
                    }
                    //Собственно, цикл разбиения на квадраты 1х1 (MINI)
                    for (float x =  (vertex1.x + MINI_SIDE_BOX / 2 ); x <  (vertex2.x + MINI_SIDE_BOX /2 ); x += MINI_SIDE_BOX) {
                        for (float y =  (vertex1.y + MINI_SIDE_BOX /2); y <  (vertex2.y + MINI_SIDE_BOX /2); y += MINI_SIDE_BOX) {
                            Body body;
                            PolygonShape ps = new PolygonShape();
                            BodyDef def = new BodyDef();
                            def.type = BodyType.StaticBody;
                            ps.setAsBox(MINI_SIDE_BOX / 2, MINI_SIDE_BOX / 2, new Vector2(x, y), 0);
                            body = world.createBody(def);
                            FixtureDef fixtureDef = new FixtureDef();
                            fixtureDef.filter.categoryBits = Constants.FILTER_WALL;
                            fixtureDef.filter.maskBits = Constants.FILTER_PLAYER;
                            fixtureDef.shape = ps;
                            fixtureDef.density = 1.0f;
                            body.createFixture(fixtureDef);
                            body.setUserData("1");
                            ps.dispose();
                        }
                    }
                } else if (wall.getBody().getUserData().equals("1")) { //UserData "1" имеют кубики 1х1
                    Vector2 vertex1 = new Vector2(), vertex2 = new Vector2();   //
                    PolygonShape shape = (PolygonShape) wall.getShape();        //Аналогично, угловые вершины
                    shape.getVertex(0, vertex1);                                //
                    shape.getVertex(2, vertex2);                                //
                    //Цикл разбиения на самые мелкие квадратики (MICRO)
                    for (float x =  (vertex1.x + MICRO_SIDE_BOX / 2 ); x < (int) (vertex2.x + MICRO_SIDE_BOX /2 ); x += MICRO_SIDE_BOX) {
                        for (float y =  (vertex1.y + MICRO_SIDE_BOX /2); y < (int) (vertex2.y + MICRO_SIDE_BOX /2); y += MICRO_SIDE_BOX) {
                            Body body;
                            PolygonShape ps = new PolygonShape();
                            BodyDef def = new BodyDef();
                            def.type = BodyType.StaticBody;
                            ps.setAsBox(MICRO_SIDE_BOX / 2, MICRO_SIDE_BOX / 2, new Vector2(x, y), 0);
                            body = world.createBody(def);
                            FixtureDef fixtureDef = new FixtureDef();
                            fixtureDef.filter.categoryBits = Constants.FILTER_WALL;
                            fixtureDef.filter.maskBits = Constants.FILTER_PLAYER;
                            fixtureDef.shape = ps;
                            fixtureDef.density = 1.0f;
                            body.createFixture(fixtureDef);
                            body.setUserData("2");
                            ps.dispose();
                        }
                    }
                }
                world.destroyBody(wall.getBody()); //После добавления более мелких кусочков надо удалить стенку, которую разбивали
                iterator.remove();
            }

        }
        playerController.update(delta);
    }


    public World getWorld() {
        return world;
    }

    public void dispose() {
        world.dispose();
    }

    public SocketUtil getSocket() {
        return socket;
    }
}
