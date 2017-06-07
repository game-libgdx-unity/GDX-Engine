package com.gdxengine.framework.test.physics.angrybirds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.gdxengine.framework.BaseGameSetting;
import com.gdxengine.framework.DefaultGameSetting;
import com.gdxengine.framework.Game;

public class AngryBirds extends Game {

	AngryBirdScene scene;
	@Override
	public void create() {		
		//set the Oxy axis system have axis-y up
		setYdown(false);
		//initialize settings and assets
		BaseGameSetting baseGameSetting = new DefaultGameSetting();
		Asset asset = new Asset();
		
		//initialize game service for all scene can use later
		initializeGameService(asset, baseGameSetting);
		
		//initialize scene(s) and add them to scenes collection
		scene = new AngryBirdScene(gameService);
		addScene(scene);
		//Active the beginning scene, initialize first active scene
		getGameService().changeScene(AngryBirdScene.class);
		
		
	}
	
	@Override
	public void render() {		

		if(activeScene == scene) {

			//clear screen
			Gdx.gl.glClearColor(0f, 0, 0.3f, 1);
			Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
			//enable the below code line if you have called the keepAspectRatio() method from TiledScene
			//Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);
			//render background image
			//disable Blend for better performance.
			Gdx.gl.glDisable(GL10.GL_BLEND);
			batch.begin();
			scene.renderBackground(gameTime);
			batch.end();
			Gdx.gl.glEnable(GL10.GL_BLEND);
			
			//try to get tileRenderer if the tileRender was inserted into game services in TileMapScene
			TileMapRenderer tileRender = scene.getTiledRenderer();
			
			//render background tile map
			tileRender.render(gameService.getCamera());
	
			gameTime = Gdx.graphics.getDeltaTime();
			
			//Update active scene
			activeScene.update(gameTime);
			camera.update();
		    batch.setProjectionMatrix(camera.combined);
			batch.begin();
			//Draw active scene
			activeScene.render(gameTime);
			batch.end();
	
			scene.renderUIandFG(gameTime);
			
			//scene.renderBox2dDebugger();
		
		} else {
			super.render();
		}
	}
}
