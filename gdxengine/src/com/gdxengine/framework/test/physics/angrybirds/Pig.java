package com.gdxengine.framework.test.physics.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.gdxengine.framework.PhysicsSprite;
import com.gdxengine.framework.Utils;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.scene.BaseGameScene;

public class Pig extends PhysicsSprite  {
	
	public Pig() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Pig(IGameService services, Texture texture) {
		super(services, texture);
		// TODO Auto-generated constructor stub
	}
	public Pig(IGameService services, TextureRegion region, TiledObject object,
			int width, int height) {
		super(services, region, object, width, height);
		// TODO Auto-generated constructor stub
	}
	public Pig(IGameService services, TextureRegion region, TiledObject object,
			int size) {
		super(services, region, object, size);
		// TODO Auto-generated constructor stub
	}
	public Pig(IGameService services, TextureRegion region, TiledObject object) {
		super(services, region, object);
		// TODO Auto-generated constructor stub
	}
	public Pig(IGameService services) {
		super(services);
		// TODO Auto-generated constructor stub
	}
	public Pig(PhysicsSprite physicsSprite) {
		super(physicsSprite);
		// TODO Auto-generated constructor stub
	}
	public Pig(TextureRegion region) {
		super(region);
		// TODO Auto-generated constructor stub
	}
	public Pig(IGameService services, TextureRegion tr) {
		super(services, tr);
		
	}
	@Override
	public void update(float gameTime) {
		
		if(Gdx.input.isTouched())
		{
			Vector2 cameraPos = getGameService().getMousePositionOnMap();
			checkDestroy(cameraPos.x, cameraPos.y);
		}
	}
	
	public void checkDestroy(float x, float y)
	{
		if(Utils.pointInRectangle(getBoundingRectangle(),new Vector2(x, y))) {
			World world = getBody().getWorld();
			world.destroyBody(getBody());
			((BaseGameScene)getGameService().getCurrentScene()).remove(this);
		}
	}
	
	@Override
	public String toString(){
		return "pig";
	}
}
