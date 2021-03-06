package com.kivi.zedman.utils;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

/**
 *  Supportive class for MapLoader. Why don't we make it inner class?
 */
public class TiledObjectUtil {

    public static void parseTiledObjectLayer(World world, MapObjects objects){


        for (MapObject object : objects) {
            Shape shape;
            if (object instanceof PolylineMapObject) {
                shape = createPolyline((PolylineMapObject) object);
            } else {
                if (object instanceof EllipseMapObject){
                    CircleShape cs = new CircleShape();
                    cs.setRadius(((EllipseMapObject) object).getEllipse().height);
                    cs.setPosition(new Vector2(((EllipseMapObject) object).getEllipse().x,
                            ((EllipseMapObject) object).getEllipse().y));
                    shape = cs;
                } else {
                    if (object instanceof RectangleMapObject){
                        PolygonShape ps = new PolygonShape();
                        Vector2 center = new Vector2();
                        ((RectangleMapObject) object).getRectangle().getCenter(center);
                        center.x /= Constants.PPM;
                        center.y /= Constants.PPM;
                        ps.setAsBox(((RectangleMapObject) object).getRectangle().getWidth()/Constants.PPM/2 ,
                                ((RectangleMapObject) object).getRectangle().getHeight()/Constants.PPM/2,
                                center, 0);
                        shape = ps;
                    } else {
                        continue;
                    }

                }
            }
            Body body;
            BodyDef def = new BodyDef();
            def.type = BodyDef.BodyType.StaticBody;
            body = world.createBody(def);
            body.createFixture(shape, 1.0f);
            shape.dispose();
        }
    }

    private static ChainShape createPolyline(PolylineMapObject polyline){
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] worldVertices = new Vector2[vertices.length/2];

        for (int i = 0; i < worldVertices.length; i++){
            worldVertices[i] = new Vector2(vertices[2*i]/Constants.PPM, vertices[2*i+1]/Constants.PPM);
        }
        ChainShape cs = new ChainShape();
        cs.createChain(worldVertices);
        return cs;

    }
}
