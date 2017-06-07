package com.gdxengine.framework.test.physics.angrybirds;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdxengine.framework.DefaultSprite;
import com.gdxengine.framework.interfaces.IGameService;

public class Sling extends DefaultSprite {

	public static final float RADIUS = 64;
	
	public Sling(IGameService gameServices, TextureRegion tr) {
		super(gameServices, tr);
	}

	@Override
	public void update(float gameTime) {
		// TODO Auto-generated method stub

	}

}
