package com.gdxengine.framework;


import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gdxengine.framework.camera.CameraManager;
import com.gdxengine.framework.effect.ShaderManager;
import com.gdxengine.framework.interfaces.IGame3DService;
import com.gdxengine.framework.interfaces.IGameAsset;
import com.gdxengine.framework.interfaces.ISceneManager;
import com.gdxengine.framework.light3d.LightManager;


public class Game3DService extends GameService implements IGame3DService {

	private CameraManager cameraManager;
	private ShaderManager shaderManager;
	private LightManager lightManager;
	
	public Game3DService(
				SpriteBatch batch,
				IGameAsset asset,
				BaseGameSetting baseGameSetting,
				ISceneManager sceneManager,
				CameraManager cameraManager,
				ShaderManager shaderManager,
				LightManager lightManager)
	{
		super(batch, asset, baseGameSetting, null, sceneManager);

		this.cameraManager = cameraManager;
		this.shaderManager = shaderManager;
		this.lightManager = lightManager;
		
		if(this.cameraManager == null){
			this.cameraManager = new CameraManager();
		}
		if(this.shaderManager == null){
			this.shaderManager = new ShaderManager();
		}
		if(this.lightManager == null){
			this.lightManager = new LightManager(new Vector3(0.1f, 0.1f, 0.1f));
		}
	}
	@Override
	public CameraManager getCameraManager() {
		// TODO Auto-generated method stub
		return cameraManager;
	}
	@Override
	public void drawText(BitmapFont font,CharSequence text,float X, float Y)
	{
		font.setUseIntegerPositions(true);
		font.draw(getSpriteBatch(), text, X,Y);
	}


	@Override
	public ShaderManager getShaderManager() {
		// TODO Auto-generated method stub
		return shaderManager;
	}



	@Override
	public LightManager getLightManager() {
		// TODO Auto-generated method stub
		return lightManager;
	}
	/* getCamera() method only used for Game 2D
	 * Please don't invoke this method.
	 */
	@Override
	public OrthographicCamera getCamera() {
		// TODO Auto-generated method stub
		throw new GdxRuntimeException("getCamera() method only used for Game 2D");
	}
}
