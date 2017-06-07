package com.gdxengine.framework;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.math.Vector2;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.interfaces.ITextureDrawable;
import com.gdxengine.framework.scene.TileRenderer;

/**
 * The standard class that extends the Sprite class and implement
 * ITextureDrawable interface Yours sprite can be derived from the class.
 * 
 * ITextureDrawable interface used for objects that have its own texture.
 * Because Sprite class have texture so the class will make Sprite object draw
 * by using Spritebatch from game service. More details, please view render()
 * method
 * 
 * @author Vinh
 * 
 */
public abstract class DefaultSprite extends Sprite implements ITextureDrawable {

    boolean isEnabled = true;
    boolean isVisible = true;

    IGameService services;
    private boolean isDead;

    public DefaultSprite(IGameService services, Texture texture) {
	super(texture);
	this.services = services;
    }

    public DefaultSprite(IGameService services, TextureRegion region) {
	super(region);
	this.services = services;
    }

    public DefaultSprite(IGameService services) {
	super();
	this.services = services;
    }

    public DefaultSprite(TextureRegion region) {
	super(region);
	this.services = null;
    }

    public DefaultSprite(DefaultSprite region) {
	super(region);
	this.isDead = region.isDead;
	this.isEnabled = region.isEnabled;
	this.isVisible = region.isVisible;
	this.services = region.getGameService();
    }

    public DefaultSprite() {
	super();
	this.services = null;
    }

    public DefaultSprite(Texture texture) {
	super(texture);
	this.services = null;
    }

    /**
     * Initialize position of sprite by the position of object from tiled map.
     * If width and height 's values is not zero, the sprite will be scaled by
     * the new size
     * 
     * @param object
     *            tiled object from tiled map
     * @param width
     * @param height
     * @param tileMapRenderer
     */
    public void initilizeTileObject(TiledObject object, float width,
	    float height, TileRenderer tileMapRenderer) {
	setX(object.x + object.width / 2);
	setY(tileMapRenderer.getMapHeightUnits() - object.y - object.height / 2);
	if (width > 0 && height > 0) {
	    float w = object.width / width;
	    float h = object.height / height;
	    setScale(w, h);
	}
    }

    /**
     * Initialize position of sprite by the position of object from tiled map.
     * If size's values is not zero, the sprite will be scaled by the new size
     * 
     * @param object
     *            tiled object from tiled map
     * @param size
     * @param tileMapRenderer
     */
    public void initilizeTileObject(TiledObject object, float size,
	    TileRenderer tileMapRenderer) {
	setX(object.x + object.width / 2);
	setY(tileMapRenderer.getMapHeightUnits() - object.y - object.height / 2);
	if (size > 0) {
	    float s = object.width / size;
	    setScale(s);
	}
    }

    /**
     * Initialize position of sprite by the position of object from tiled map.
     * 
     * @param object
     *            tiled object from tiled map
     * @param tileMapRenderer
     */
    public void initilizeTileObject(TiledObject object,
	    TileRenderer tileMapRenderer) {
	initilizeTileObject(object, 0, 0, tileMapRenderer);
    }

    /**
     * Set new position of texture region so the sprite will take another region
     * on texture to render.
     * 
     * @param x
     * @param y
     */
    public void setRegionPosition(float x, float y) {
	super.setRegion(x, y, getRegionWidth(), getRegionHeight());
    }

    /**
     * Set new position of texture region so the sprite will take another region
     * on texture to render.
     * 
     * @param newPosition
     */
    public void setRegionPosition(Vector2 newPosition) {
	super.setRegion(newPosition.x, newPosition.y, getRegionWidth(),
		getRegionHeight());
    }

    @Override
    public boolean isEnabled() {
	// TODO Auto-generated method stub
	return isEnabled;
    }

    @Override
    public void setEnabled(boolean enable) {
	isEnabled = enable;

    }

    @Override
    public float getCenterX() {
	// TODO Auto-generated method stub
	return getX() + getOriginX();
    }

    @Override
    public float getCenterY() {
	// TODO Auto-generated method stub
	return getY() + getOriginY();
    }

    @Override
    public boolean isVisible() {
	// TODO Auto-generated method stub
	return isVisible;
    }

    @Override
    public void setVisible(boolean visible) {
	isVisible = visible;
    }

    @Override
    public void render(float gameTime) {
	super.draw(services.getSpriteBatch());
    }

    @Override
    public void dispose() {

    }

    @Override
    public IGameService getGameService() {
	return services;
    }

    @Override
    public boolean isDead() {
	// TODO Auto-generated method stub
	return isDead;
    }

    @Override
    public void setDead(boolean value) {
	isEnabled = !value;
	isVisible = !value;
	isDead = value;
    }

    @Override
    public void pause() {
	// TODO Auto-generated method stub

    }

    @Override
    public void resume() {
	// TODO Auto-generated method stub

    }

    @Override
    public Vector2 getPosition() {
	// TODO Auto-generated method stub
	Vector2.tmp.set(getX(),getY());
	    return Vector2.tmp;
    }

    @Override
    public void setPosition(Vector2 value) {
	setPosition(value.x, value.y);
    }

    @Override
	public Vector2 getSize() {
	    Vector2.tmp.set(getWidth(),getHeight());
	    return Vector2.tmp;
	}

    @Override
    public void setSize(Vector2 value) {
	setSize(value.x, value.y);

    }

    @Override
    public Vector2 getScale() {
	Vector2.tmp.set(getScaleX(),getScaleY());
	    return Vector2.tmp;
    }

    @Override
    public void setScale(Vector2 value) {
	setScale(value.x, value.y);
    }

    @Override
    public Vector2 getOrigin() {
	Vector2.tmp.set(getOriginX(),getOriginY());
	    return Vector2.tmp;
    }

    @Override
    public void setOrigin(Vector2 value) {
	setOrigin(value.x, value.y);
    }

    @Override
    public Vector2 getVelocity() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void setVelocity(Vector2 value) {
	// TODO Auto-generated method stub

    }

    @Override
    public void initialize() {
	// TODO Auto-generated method stub

    }

    @Override
    public SpriteBatch getSpriteBatch() {
	return services.getSpriteBatch();
    }
}
