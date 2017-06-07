package com.gdxengine.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.gdxengine.framework.camera.CameraManager;
import com.gdxengine.framework.effect.ShaderManager;
import com.gdxengine.framework.interfaces.IGame3DService;
import com.gdxengine.framework.interfaces.IGameAsset;
import com.gdxengine.framework.interfaces.IScene;
import com.gdxengine.framework.light3d.LightManager;
/**
 * The abstract class for game class. For example, you wants to create asteroid game,
 * The Asteroid.java should be inherited from Game class
 * 
 * The Class used to create 3D game only. if you wants 2D games, please use the Game class instead of Game3D class
 * 
 * @author Vinh
 *
 */
public abstract class Game3D extends Game {
	
	/**
	 * Initialze the 3D game service by instance of GameAsset and GameSetting, CameraManager, ShaderManager and LightManager
	 * @param asset
	 * @param baseGameSetting
	 */
	public void initializeGameService(IGameAsset asset,BaseGameSetting baseGameSetting, CameraManager camera, ShaderManager shader, LightManager light)
	{
		batch = new SpriteBatch();
		scenes = new Array<IScene>();
		super.initializeGameService(asset, baseGameSetting);
		
		if(camera == null){
			camera = new CameraManager();
		}
		if(shader == null){
			shader = new ShaderManager();
		}
		if(light == null){
			light = new LightManager(new Vector3(0.1f, 0.1f, 0.1f));
		}
		gameService = new Game3DService(batch, asset, baseGameSetting, sceneManager, camera, shader, light);
	}
	
	@Override
	public void render() {		

		gameTime = Gdx.graphics.getDeltaTime();

		activeScene.update(gameTime);
		
		activeScene.render(gameTime);
	}

	@Override
	public IGame3DService getGameService() {
		// TODO Auto-generated method stub
		return (IGame3DService) gameService;
	}	
}
