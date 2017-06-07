package com.gdxengine.framework.test.towerdefense;

import java.lang.management.ThreadInfo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.utils.Timer.Task;
import com.gdxengine.framework.BaseGameSetting;
import com.gdxengine.framework.DefaultGameAsset;
import com.gdxengine.framework.DefaultGameSetting;
import com.gdxengine.framework.Game;
import com.gdxengine.framework.interfaces.IGameAsset;

public class TowerDefenseGame extends Game {

    LoadingScene loadScene;
    boolean load = false;
    TowerDefenseScene scene;

    @Override
    public void create() {
	// set the Oxy axis system have axis-y up
	setYdown(false);
	// initialize settings and assets
	BaseGameSetting baseGameSetting = new DefaultGameSetting();
	GameAsset asset = new GameAsset();

	// initialize game service for all scene can use later
	initializeGameService(asset, baseGameSetting, false);

	// initialize scene(s) and add them to scenes collection
	loadScene = new LoadingScene(batch);
	addScene(loadScene);
	getGameService().changeScene(LoadingScene.class);

	// Active the beginning scene, initialize first active scene

    }

    @Override
    public void resize(int width, int height) {
	super.resize(width, height);
	if (activeScene == scene) {
	    Camera camera = scene.getCameraUI();
	    camera.viewportHeight = height;
	    camera.viewportWidth = width;
	    camera.update();
	    scene.getBatchUI().setProjectionMatrix(camera.combined);
	    scene.setupCamera();
	}
    }

    @Override
    public void render() {

	if (activeScene == scene) {
	    // clear the screen
	    Gdx.gl.glClearColor(0f, 0, 0.3f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	    gameTime = Gdx.graphics.getDeltaTime();

	    // Update active scene
	    activeScene.update(gameTime);
	    camera.update();
	    batch.setProjectionMatrix(camera.combined);
	    scene.getTiledRenderer().render(camera);
	    Gdx.gl.glEnable(GL10.GL_BLEND);
	    Gdx.gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
	    scene.renderTowerRange();
	    batch.begin();
	    // Draw active scene
	    activeScene.render(gameTime);
	    batch.end();
	    // render foreground and ui of scene
	    scene.renderUIandFG(gameTime);

	} else {
	    super.render();

	    if (!load)
		Gdx.app.postRunnable(new Runnable() {

		    @Override
		    public void run() {
			gameService.loadContent();
			gameService.loadSetting();
			scene = new TowerDefenseScene(gameService);
			addScene(scene);
			getGameService().changeScene(TowerDefenseScene.class);
			load = true;
		    }
		});
	}
    }
}
