package com.gdxengine.framework.test.catchdrop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gdxengine.framework.CollectionItem;

public class Drop extends CollectionItem {
	
	private Rectangle bound;
	final Vector2 position = new Vector2();
	
	public Drop() {
		super();
		resetPosition();
		bound = new Rectangle(position.x, position.y, 32, 32);
	}
	
	void resetPosition() {
		position.y = 0;
		position.x = MathUtils.random(Gdx.graphics.getWidth());
	}

	@Override
	public void update(float gameTime) {
		position.y += 5;
		
		if(position.y > Gdx.graphics.getHeight())
		{
			resetPosition();
		}
	}
	
	public Rectangle getBound()
	{
		bound.x = position.x;
		bound.y = position.y;
		return bound;
	}
}
