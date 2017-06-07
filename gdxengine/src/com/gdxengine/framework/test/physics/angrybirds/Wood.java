package com.gdxengine.framework.test.physics.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.gdxengine.framework.PhysicsSprite;
import com.gdxengine.framework.Utils;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.scene.BaseGameScene;

public class Wood extends PhysicsSprite{
	
	float life = 1f;
	
	public Wood(IGameService services, TextureRegion tr, TiledObject obj) {
		super(services, tr, obj);
		
	}
	public Wood(IGameService gameServices, TextureRegion region,
			TiledObject object, int size) {
		super(gameServices, region, object, size);
	}
	public Wood(IGameService gameServices, TextureRegion tr) {
		super(gameServices, tr);
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
	
	public void takeDamage(float damage){
		life -= damage;
		
		if(life < 0.8f)
		{
			if( getTexture() == Asset.block)
				setTexture(Asset.damaged_block);
			else if( getTexture() == Asset.smallblock)
				setTexture(Asset.damaged_smallblock);
			else if( getTexture() == Asset.longwood)
				setTexture(Asset.damaged_longwood);
			else if( getTexture() == Asset.tallwood)
				setTexture(Asset.damaged_tallwood);
		}
		
		if( life <= 0)
		{
			((BaseGameScene)getGameService().getCurrentScene()).remove(this);
			getBody().setUserData("remove");
		}
	}
	
	@Override
	public String toString(){
		return "brick";
	}

}
