//package com.kivi.zedman.controller;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.math.Vector2;
//import com.badlogic.gdx.physics.box2d.Contact;
//import com.badlogic.gdx.physics.box2d.WorldManifold;
//import com.kivi.zedman.ZWorld;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//import java.util.HashMap;
//
///**
// * Created by Kirill on 06.03.2016.
// */
//
//public class WorldController {
//    private ZWorld world;
//    public static boolean grounded;
//
//    public WorldController(ZWorld world) {
//        this.world = world;
//    }
//
//    public void resetWay() {
//    }
//
//    public void dispose() {
//    }
//
//    public void update(float delta) {
//        grounded = this.isPlayerGrounded(Gdx.graphics.getDeltaTime());
//        this.world.getPlayer().update(delta);
//    }
//
//    private boolean isPlayerGrounded(float deltaTime) {
//        this.world.groundedPlatform = null;
//        List contactList = this.world.getWorld().getContactList();
//
//        for(int i = 0; i < contactList.size(); ++i) {
//            Contact contact = (Contact)contactList.get(i);
//            if(contact.isTouching() && (contact.getFixtureA() == this.world.getPlayer().playerSensorFixture || contact.getFixtureB() == this.world.getPlayer().playerSensorFixture)) {
//                Vector2 pos = this.world.getPlayer().getPosition();
//                WorldManifold manifold = contact.getWorldManifold();
//                boolean below = true;
//
//                for(int j = 0; j < manifold.getNumberOfContactPoints(); ++j) {
//                    below &= manifold.getPoints()[j].y < pos.y - 0.4F;
//                }
//
//                if(!below) {
//                    return false;
//                } else {
//                    return true;
//                }
//            }
//        }
//
//        return false;
//    }
//
//}
