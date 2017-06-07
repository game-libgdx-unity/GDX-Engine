package com.gdxengine.framework.box2d;

import com.badlogic.gdx.math.Vector2;

public class VPoint {

	public float x;
	public float y;
	float oldx,oldy;

	public void setPos(float argX, float argY) {
		x = oldx = argX;
		y = oldy = argY;
	}

	public void update() {
		float tempx = x;
		float tempy = y;
		x += x - oldx;
		y += y - oldy;
		oldx = tempx;
		oldy = tempy;
	}

	public void applyGravity(float dt) {
		y -= 10.0f*dt; //gravity magic number
	}

	public void setX(float argX) {
		x = argX;
	}

	public void setY(float argY) {
		y = argY;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public Vector2 point() {
	    return new Vector2(x, y);
	}
}
