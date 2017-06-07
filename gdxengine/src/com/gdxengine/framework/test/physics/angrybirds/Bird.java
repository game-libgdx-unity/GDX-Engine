package com.gdxengine.framework.test.physics.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.gdxengine.framework.Game;
import com.gdxengine.framework.PhysicsSprite;
import com.gdxengine.framework.Utils;
import com.gdxengine.framework.box2d.PhysicsManager;
import com.gdxengine.framework.event.TouchEvent;
import com.gdxengine.framework.event.listener.TouchListener;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.scene.BaseGameScene;
import com.gdxengine.framework.scene.TiledScene;

public class Bird extends PhysicsSprite implements TouchListener {

    public static final float RADIUS = 15;

    enum Status {
	normal, dragged, touched, released
    }

    Status status = Status.normal;

    private Sling sling;
    public float angle;

    private boolean needChaseCamera = false;

    public Bird(IGameService services, TextureRegion tr, Sling sling) {
	super(services, tr);

	float ratio = (RADIUS * 2f) / getRegionWidth();
	setScale(ratio);
	this.sling = sling;
    }

    @Override
    public void update(float gameTime) {
	// Vector2 cameraPos = new
	// Vector2(getGameServices().getCamera().position.x,getGameServices().getCamera().position.y);
	// if(cameraPos.dst(getPosition()) > 400)
	// needChaseCamera = true;

	if (status == Status.released && needChaseCamera)
	    getGameService().getCamera().position.set(getX(), getY(), 0);
    }

    public float force;

    @Override
    public String toString() {
	return "bird";
    }

    @Override
    public boolean onTouchUp(TouchEvent e) {
	if (status != Status.touched) {
	    return true;
	}
	// If mouse is up when dragging the bird
	// fire the bird
	float distanceX = getCenterX() - sling.getCenterX();
	float distanceY = getCenterY() - sling.getCenterY();
	// Here we have velocityX and velocityY of bird
	float velocityX = distanceX * -1 / 5;
	float velocityY = distanceY * -1 / 5;
	Vector2 birdVelocity = new Vector2(velocityX, velocityY);
	// get PhysicManager from gameService
	PhysicsManager physicsManager = getGameService().getService(
		PhysicsManager.class);
	// create new physicObject in physicsManager
	Body birdBody = physicsManager.addDynamicCircleObject(this,
		getRegionWidth() / 2, 4f, 0.8f, 0.5f);
	// Set above velocity for the bird body
	birdBody.setLinearVelocity(birdVelocity);
	// set bullet, it will make game run preciously
	birdBody.setBullet(true);
	// update the bird's status
	status = Status.released;
	return false;
    }

    @Override
    public boolean onTouchDown(TouchEvent e) {
	if (status == Status.released)
	    return true;
	// Get Mouse position from world
	Vector2 mousePos = getGameService().getMousePositionOnMap();
	// Rectangle around the sling
	Rectangle rec = sling.getBoundingRectangle();
	// If mouse is down in this rectangle
	if (Utils.pointInRectangle(new Rectangle(rec.x, rec.y, rec.width,
		rec.height), mousePos)) {
	    // Change the status
	    status = Status.dragged;
	}
	return false;
    }

    @Override
    public boolean onTouchDragged(TouchEvent e) {
	// if the bird is dragging, move the bird follows the mouse's position
	// but bird is always in sling's bound
	Vector2 mousePos = getGameService().getMousePositionOnMap();
	if (Utils.pointInRectangle(sling.getBoundingRectangle(), mousePos)) {
	    float mouseX = mousePos.x;
	    float mouseY = mousePos.y;
	    setX(mouseX - getRegionWidth() / 2);
	    setY(mouseY - getRegionHeight() / 2);

	    float distanceX = getX() - sling.getX();
	    float distanceY = Gdx.graphics.getHeight() - getY() - sling.getY();
	    angle = (float) Math.atan2(distanceY, distanceX);
	    // rotate the bird when dragging it
	    setRotation((float) Math.toDegrees(angle));
	    // update status of bird
	    status = Status.touched;

	    return true;
	}

	return false;
    }
}
