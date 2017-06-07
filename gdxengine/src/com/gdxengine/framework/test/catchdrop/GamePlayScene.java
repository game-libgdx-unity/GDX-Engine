package com.gdxengine.framework.test.catchdrop;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;

import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.scene.BaseGameScene;

public class GamePlayScene extends BaseGameScene {

	Bucker bucker;
	DropCollection drops;
	float timer = 10;

	
	public GamePlayScene(IGameService gameService) {
		super(gameService,true);
		//initialize for player
		bucker = new Bucker(getGameService(), Asset.buckerTexture);
		addDrawbleObject(bucker);
		//initialize for bullets
		drops = new DropCollection(getGameService());	
		getGameService().addService(drops);
		addDrawbleObject(drops);
	}
	
	@Override
	public void render(float gameTime) {
		super.render(gameTime);
	}

	@Override
	public void onRenderForeground(float gameTime) {
	    // TODO Auto-generated method stub
	    
	}
}
