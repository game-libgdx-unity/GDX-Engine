package com.gdxengine.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gdxengine.framework.interfaces.IGame;
import com.gdxengine.framework.interfaces.IGameAsset;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.interfaces.IScene;
import com.gdxengine.framework.interfaces.ISceneManager;

/**
 * The abstract class for game class. For example, you wants to create asteroid
 * game, The Asteroid.java should be inherited from Game class
 * 
 * The Class used to create 2D game only. if you wants 3D games, please use the
 * Game3D class instead of Game class
 * 
 * @author Vinh
 * 
 */
public abstract class Game implements IGame {

    // viewport
    public static int viewportX = 0;
    public static int viewportY = 0;
    public static int viewportWidth = 0;
    public static int viewportHeight = 0;
    // default set axis-y down
    protected static boolean ydown = true;
    public static boolean isSupportOpenGL20 = false;
    // game objects
    protected IGameService gameService;
    protected OrthographicCamera camera;
    protected SpriteBatch batch;
    protected BaseGameSetting baseGameSetting = null;
    protected IGameAsset asset = null;
    protected ISceneManager sceneManager = null;
    protected int activeSceneIndex = -1;
    protected IScene activeScene;

    protected Array<IScene> scenes;
    protected float gameTime;

    /**
     * @return Check usage of y-axis down
     */
    public static boolean isYdown() {
	return ydown;
    }

    /**
     * @param ydown
     *            Set usage of y-axis down
     */
    public void setYdown(boolean ydown) {
	Game.ydown = ydown;
	float w = Gdx.graphics.getWidth();
	float h = Gdx.graphics.getHeight();
	if (camera == null)
	    camera = new OrthographicCamera(Gdx.graphics.getWidth(),
		    Gdx.graphics.getHeight());
	if (ydown)
	    ((OrthographicCamera) this.camera).setToOrtho(ydown);
    }

    /**
     * Initialze the game service by instance of GameAsset and GameSetting and load content
     * from Game Asset and Setting
     * 
     * @param asset
     * @param baseGameSetting
     */
    public void initializeGameService(IGameAsset asset,
	    BaseGameSetting baseGameSetting) {
	this.initializeGameService(asset, baseGameSetting, true);
    }

    /**
     * Initialze the game service by instance of GameAsset and GameSetting
     * 
     * @param asset
     * @param baseGameSetting
     * @param isLoading Need to load game asset and game setting
     */
    public void initializeGameService(IGameAsset asset,
	    BaseGameSetting baseGameSetting, boolean isLoading) {

	viewportWidth = Gdx.graphics.getWidth();
	viewportHeight = Gdx.graphics.getHeight();
	isSupportOpenGL20 = Gdx.graphics.isGL20Available();
	setYdown(ydown);
	batch = new SpriteBatch();
	scenes = new Array<IScene>();
	this.asset = asset;
	if (this.asset != null) {
	    if (isLoading)
		this.asset.load();
	} else {
	    this.asset = new DefaultGameAsset();
	}
	this.baseGameSetting = baseGameSetting;
	// load setting
	if (this.baseGameSetting != null) {
	    if (isLoading)
		this.baseGameSetting.loadSetting();
	} else {
	    this.baseGameSetting = new DefaultGameSetting();
	}
	sceneManager = new ISceneManager() {

	    @Override
	    public void changeScene(int newScene) {
		// scenes.get(activeScene).pause();
		if (activeSceneIndex != newScene) {
		    activeScene = scenes.get(newScene);
		    activeSceneIndex = newScene;
		    activeScene.initScene();
		}
	    }

	    @Override
	    public <T> void changeScene(Class<T> sceneType) {

		for (int i = 0; i < scenes.size; i++) {
		    if (scenes.get(i).getClass() == sceneType) {
			changeScene(i);
			return;
		    }
		}
		throw new GdxRuntimeException(
			"the scene class cannot be found!");
	    }

	    @Override
	    public IScene getScene(int sceneIndex) {
		// TODO Auto-generated method stub
		return scenes.get(sceneIndex);
	    }

	    @SuppressWarnings("unchecked")
	    @Override
	    public <T> T getScene(Class<T> sceneType) {

		for (int i = 0; i < scenes.size; i++) {
		    if (scenes.get(i).getClass() == sceneType) {
			return (T) scenes.get(i);
		    }
		}
		throw new GdxRuntimeException(
			"the scene class cannot be found!");
	    }

	    @Override
	    public IScene getScene() {
		return activeScene;
	    }
	};

	gameService = new GameService(batch, asset, baseGameSetting, camera,
		sceneManager);
    }

    @Override
    public void resize(int width, int height) {

	camera.viewportHeight = height;
	camera.viewportWidth = width;
	camera.update();
	batch.setProjectionMatrix(camera.combined);

    }

    @Override
    public void dispose() {

	getSetting().saveSetting();

	batch.dispose();
	asset.dispose();

	for (IScene scene : scenes)
	    scene.dispose();
    }

    @Override
    public void render() {
	Gdx.gl.glClearColor(0f, 0, 0.3f, 1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	Gdx.gl.glViewport(viewportX, viewportY, viewportWidth, viewportHeight);

	gameTime = Gdx.graphics.getDeltaTime();

	// Update active scene
	activeScene.update(gameTime);
	camera.update();
	batch.setProjectionMatrix(camera.combined);
	batch.begin();
	// Draw active scene
	activeScene.render(gameTime);
	batch.end();

    }

    @Override
    public IGameService getGameService() {
	// TODO Auto-generated method stub
	return gameService;
    }

    @Override
    public void pause() {
	// save setting
	getSetting().saveSetting();
	// pause scene
	activeScene.pause();
    }

    @Override
    public void resume() {
	// save game
	// setting.saveSetting();
	// pause scene
	activeScene.resume();
    }

    /**
     * @return the setting
     */
    protected BaseGameSetting getSetting() {
	return baseGameSetting;
    }

    /**
     * @param baseGameSetting
     *            the setting to set
     */
    protected void setSetting(BaseGameSetting baseGameSetting) {
	this.baseGameSetting = baseGameSetting;
    }

    /**
     * @return the asset
     */
    protected IGameAsset getAsset() {
	return asset;

    }

    /**
     * @param asset
     *            the asset to set
     */
    protected void setAsset(IGameAsset asset) {
	this.asset = asset;
    }

    /**
     * @return the activeScene
     */
    protected int getActiveSceneIndex() {
	return activeSceneIndex;
    }

    /**
     * change the current active scene
     * 
     * @param activeScene
     */
    protected void changeScene(int activeScene) {
	sceneManager.changeScene(activeScene);
    }

    /**
     * Add scene to game's scene collection
     * 
     * @param scene
     */
    protected void addScene(IScene scene) {
	scenes.add(scene);
    }

    /**
     * @return the activeScene
     */
    public IScene getActiveScene() {
	return activeScene;
    }

    /**
     * @param activeScene
     *            the activeScene to set
     */
    public void setActiveScene(IScene activeScene) {
	this.activeScene = activeScene;
    }
}
