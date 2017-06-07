package com.gdxengine.framework.interfaces;

import com.badlogic.gdx.physics.box2d.Body;

public interface IPhysicsObject extends ITextureDrawable {

	public abstract void setBody(Body body);

	public abstract Body getBody();

	public abstract void setX(float x);

	public abstract void setY(float y);

	public abstract void setRotation(float angle);

	public abstract int getRegionWidth();

	public abstract int getRegionHeight();

	public abstract float getX();

	public abstract float getY();
}