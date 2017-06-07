package com.gdxengine.framework.interfaces;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * The interface for object that have own texture. The texture will be drawn when the object is render by using render() methods
 * The interface have many methods that accept Vector2 type as parameter. This will make your coding conveniently.
 * 
 * @author Vinh
 *
 */
public interface ITextureDrawable extends IDrawable {
	
	public float getCenterX();
	public float getCenterY();
	
	public void setRegion(TextureRegion region);
	public Vector2 getPosition();
	public void setPosition(Vector2 value);
	
	public Vector2 getSize();
	public void setSize(Vector2 value);
	
	public Vector2 getScale();
	public void setScale(Vector2 value);
	
	public Vector2 getOrigin();
	public void setOrigin(Vector2 value);
	
	public Vector2 getVelocity();
	public void setVelocity(Vector2 value);
	
	public SpriteBatch getSpriteBatch();
}
