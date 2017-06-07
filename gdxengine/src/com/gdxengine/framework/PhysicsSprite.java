package com.gdxengine.framework;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.interfaces.IPhysicsObject;
import com.gdxengine.framework.interfaces.ITextureDrawable;
import com.gdxengine.framework.scene.TileRenderer;

/**
 * The standard class that extends the Sprite class and implement ITextureDrawable interface
 * Yours physics sprite can be derived from the class. the class have a reference to body
 * in Box2d World so you can change the properties of the body easily
 * 
 * @author Vinh
 *
 */
public class PhysicsSprite extends Sprite implements IPhysicsObject {

	IGameService services;
	private Body body;
	
	/**
	 *  Create new physics sprite with scaling the size by size of object
	 *  object usually have circle shape
	 * @param services
	 * @param region
	 * @param object
	 * @param size
	 */
	public PhysicsSprite(IGameService services, TextureRegion region,TiledObject object, int size) {
		super(region);
		this.services = services;
		
		TileRenderer render = services.getService(TileRenderer.class);
		initilizeTileObject(object, size, render);
	}
	/**
	 *  Create new physics sprite with scaling the size by width and height of object
	 *  object usually have box shape
	 * @param services
	 * @param region
	 * @param object
	 * @param size
	 */
	public PhysicsSprite(IGameService services, TextureRegion region,TiledObject object, int width, int height) {
		super(region);
		this.services = services;
		
		TileRenderer render = services.getService(TileRenderer.class);
		initilizeTileObject(object, width, height, render);
	}
	/**
	 * Create new physics sprite without scaling the size
	 * @param services
	 * @param region
	 * @param object
	 */
	public PhysicsSprite(IGameService services, TextureRegion region, TiledObject object) {
		super(region);
		this.services = services;
		TileRenderer render = services.getService(TileRenderer.class);
		initilizeTileObject(object, render);
	}
	
	public PhysicsSprite(IGameService services, Texture texture)
	{
		super(texture);
		this.services = services;	
	}
	
	public PhysicsSprite(IGameService services, TextureRegion region) {
		super(region);
		this.services = services;
	}
	
	public PhysicsSprite(IGameService services)
	{
		super();
		this.services = services;	
	}
	
	public PhysicsSprite(TextureRegion region)
	{
		super(region);
		this.services = null;
	}
	
	public PhysicsSprite(PhysicsSprite physicsSprite)
	{
		super(physicsSprite);
	}
	
	public PhysicsSprite()
	{
		super();
		this.services = null;
	}
	
	/**
	 * Initialize position of sprite by the position of object from tiled map. If
	 * width and height 's values is not zero, the sprite will be scaled by the
	 * new size
	 * 
	 * @param object tiled object from tiled map
	 * @param width
	 * @param height
	 * @param tileMapRenderer
	 */
	public void initilizeTileObject(TiledObject object, float width, float height,TileRenderer tileMapRenderer) {
		setX(object.x + object.width / 2);
		setY(tileMapRenderer.getMapHeightUnits() - object.y - object.height / 2);
		if(width > 0 && height > 0) {
			float w = object.width / width;
			float h = object.height / height;
			setScale(w, h);
		}
	}
	/**
	 * Initialize position of sprite by the position of object from tiled map. If
	 * size's values is not zero, the sprite will be scaled by the
	 * new size
	 * 
	 * @param object tiled object from tiled map
	 * @param size
	 * @param tileMapRenderer
	 */
	public void initilizeTileObject(TiledObject object, float size, TileRenderer tileMapRenderer) {
		setX(object.x + object.width / 2);
		setY(tileMapRenderer.getMapHeightUnits() - object.y - object.height / 2);
		if(size > 0) {
			float s = object.width / size;
			setScale(s);
		}
	}
	/**
	 * Initialize position of sprite by the position of object from tiled map.
	 * 
	 * @param object tiled object from tiled map
	 * @param tileMapRenderer
	 */
	public void initilizeTileObject(TiledObject object,TileRenderer tileMapRenderer) {
		initilizeTileObject(object, 0, 0,tileMapRenderer);
	}
	/**
	 * Set new position of texture region so the sprite will take another region on texture to render.
	 * @param x
	 * @param y
	 */
	public void setRegionPosition(float x, float y){
		super.setRegion(x, y, getRegionWidth(), getRegionHeight());
	}
	/**
	 * Set new position of texture region so the sprite will take another region on texture to render.
	 * @param newPosition
	 */
	public void setRegionPosition(Vector2 newPosition){
		super.setRegion(newPosition.x, newPosition.y, getRegionWidth(), getRegionHeight());
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
		return new Vector2(getX(), getY());
	}

	@Override
	public void setPosition(Vector2 value) {
		setPosition(value.x, value.y);
	}

	@Override
	public Vector2 getSize() {
		return new Vector2(getWidth(),getHeight());
	}

	@Override
	public void setSize(Vector2 value) {
		setSize(value.x, value.y);
		
	}

	@Override
	public Vector2 getScale() {
		return new Vector2(getScaleX(), getScaleY());
	}

	@Override
	public void setScale(Vector2 value) {
		setScale(value.x, value.y);
	}

	@Override
	public Vector2 getOrigin() {
		return new Vector2(getOriginX(),getOriginY());
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
	public SpriteBatch getSpriteBatch()
	{
		return services.getSpriteBatch();
	}

	/* (non-Javadoc)
	 * @see com.gdxengine.framework.IPhysicsObject#setBody(com.badlogic.gdx.physics.box2d.Body)
	 */
	@Override
	public void setBody(Body body) {
		this.body = body;
	}
	
	/* (non-Javadoc)
	 * @see com.gdxengine.framework.IPhysicsObject#getBody()
	 */
	@Override
	public Body getBody() {
		return this.body;
	}

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setVisible(boolean visible) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float gameTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setEnabled(boolean enable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDead(boolean value) {
		// TODO Auto-generated method stub
		
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
}
