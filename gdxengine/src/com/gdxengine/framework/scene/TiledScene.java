package com.gdxengine.framework.scene;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdxengine.framework.Game;
import com.gdxengine.framework.Utils;
import com.gdxengine.framework.interfaces.IGameService;

public abstract class TiledScene extends BaseGameScene {

	protected TiledMap tiledMap;
	protected TileAtlas tileAtlas;
	protected TileRenderer tileMapRenderer;

	/**
	 * @return the tileMapRenderer
	 */
	public TileMapRenderer getTileMapRenderer() {
		return tileMapRenderer;
	}

	/**
	 * @param tileMapRenderer
	 *            the tileMapRenderer to set
	 */
	public void setTileMapRenderer(TileRenderer tileMapRenderer) {
		this.tileMapRenderer = tileMapRenderer;
	}

	private boolean loadedMap = false;
	protected String mapfile;
	protected String textureDir;

	protected OrthographicCamera mainCamera;
	protected boolean enableDefaultCameraController = true;
	protected float rotationSpeed = .5f;
	

	/**
	 * Automatically setup the camera with proper zoom based screen resolution
	 * and position at map center
	 */
	public void setupCamera() {
		mainCamera = services.getCamera();
		mainCamera.zoom = SCREEN_SCALE;
		mainCamera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);
	}

	/**
	 * Setup camera at specific zoom and position at map center
	 * 
	 * @param zoom
	 */
	public void setupCamera(float zoom) {
		mainCamera = services.getCamera();
		mainCamera.zoom = zoom;
		mainCamera.position.set(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f, 0);
	}

	/**
	 * Always keep the aspect ratio is the same as targeted size (TARGET_WIDTH
	 * and TARGET_HEIGHT)
	 */
	public void keepGameAspectRatio() {
		int w = Gdx.graphics.getWidth();
		int h = Gdx.graphics.getHeight();
		if (w > h) {
			Game.viewportWidth = w;
			Game.viewportHeight = (int) (h / SCREEN_RATIO);
			Game.viewportY = (h - Game.viewportHeight) / 2;
			Game.viewportX = 0;
		} else {
			Game.viewportWidth = (int) (w / SCREEN_RATIO);
			Game.viewportHeight = h;
			Game.viewportX = (w - Game.viewportWidth) / 2;
			Game.viewportY = 0;
		}
		mainCamera.viewportWidth = Game.viewportWidth;
		mainCamera.viewportHeight = Game.viewportHeight;
	}

	public TiledScene(IGameService gameService) {
		this(gameService,"","");
	}

	public TiledScene(IGameService gameService, String mapfile,
			String textureDir) {
		super(gameService);
		if(mapfile != "" || textureDir != "")
			loadTileMapRenderer(mapfile, textureDir);
		
		
	}
	
	public TiledScene(IGameService gameService, boolean b) {
	    super(gameService, b);
	}

	@Override
	public void initialize() {

		// load tilemap by default mapfile string if user hasn't loaded before
		if (!loadedMap)
			loadTileMapRenderer(mapfile, textureDir);
		// initialize all game components
		super.initialize();
	}

	/**
	 * Get real size of map's width
	 * 
	 * @return
	 */
	public int getMapWidth() {
		return tiledMap.width * tiledMap.tileWidth;
	}

	/**
	 * Get real size of map's height
	 * 
	 * @return
	 */
	public int getMapHeight() {
		return tiledMap.height * tiledMap.tileHeight;
	}

	/**
	 * Get tiledMap of scene
	 * 
	 * @return
	 */
	public TiledMap getTileMap() {
		return tiledMap;
	}

	/**
	 * Load TiledMap by path of map file and folder path of textures that used
	 * in tiled map
	 * 
	 * @param mapfile
	 * @param textureDir
	 */
	public void loadTileMapRenderer(String mapfile, String textureDir) {

		this.mapfile = mapfile;
		this.textureDir = textureDir;

		tiledMap = TiledLoader.createMap(Gdx.files.internal(mapfile));
		tileAtlas = new TileAtlas(tiledMap, Gdx.files.internal(textureDir));

		services.removeService(TileMapRenderer.class);

		tileMapRenderer = new TileRenderer(tiledMap, tileAtlas, 8, 8);
		// You must add the tileMapRender into gameServices.
		// The TileMapGame use the service to render tile map before render the
		// scene.
		services.addService(tileMapRenderer);

		loadedMap = true;

		WORLD_WIDTH = getMapWidth();
		WORLD_HEIGHT = getMapHeight();
	}

	/**
	 * Initialize position of sprite by the position of object from tiled map.
	 * If width and height 's values is not zero, the sprite will be scaled by
	 * the new size
	 * 
	 * @param sprite
	 *            the sprite will receive changes from tiled object
	 * @param object
	 *            tiled object from tiled map
	 * @param width
	 * @param height
	 */
	public void initilizeTileObject(Sprite sprite, TiledObject object,
			float width, float height) {
		sprite.setX(object.x + object.width / 2);
		sprite.setY(getMapHeight() - object.y - object.height / 2);
		if (width > 0 && height > 0) {
			float w = object.width / width;
			float h = object.height / height;
			sprite.setScale(w, h);
		}
	}

	/**
	 * Initialize position of sprite by the position of object from tiled map.
	 * If size 's values is not zero, the sprite will be scaled by the new size
	 * 
	 * @param sprite
	 *            the sprite will receive changes from tiled object
	 * @param object
	 *            tiled object from tiled map
	 * @param size
	 */
	public void initilizeTileObject(Sprite sprite, TiledObject object,
			float size) {

		sprite.setX(object.x + object.width / 2);
		sprite.setY(tileMapRenderer.getMapHeightUnits() - object.y
				- object.height / 2);
		if (size > 0) {
			float s = object.width / size;
			sprite.setScale(s);
		}
	}

	/**
	 * Initialize position of sprite by the position of object from tiled map
	 * 
	 * @param sprite
	 *            the sprite will receive changes from tiled object
	 * @param object
	 *            tiled object from tiled map
	 */
	public void initilizeTileObject(Sprite sprite, TiledObject object) {
		initilizeTileObject(sprite, object, 0, 0);
	}

	/**
	 * @return the loadedMap
	 */
	public boolean isLoadedMap() {
		return loadedMap;
	}

	/**
	 * @param loadedMap
	 *            the loadedMap to set
	 */
	public void setLoadedMap(boolean loadedMap) {
		this.loadedMap = loadedMap;
	}

	/**
	 * @return the mapfile
	 */
	public String getMapfile() {
		return mapfile;
	}

	/**
	 * @param mapfile
	 *            the mapfile to set
	 */
	public void setMapfile(String mapfile) {
		this.mapfile = mapfile;
	}

	/**
	 * @return the textureDir
	 */
	public String getTextureDir() {
		return textureDir;
	}

	/**
	 * @param textureDir
	 *            the textureDir to set
	 */
	public void setTextureDir(String textureDir) {
		this.textureDir = textureDir;
	}

	public TileMapRenderer getTiledRenderer() {
		// TODO Auto-generated method stub
		return tileMapRenderer;
	}

	/**
	 * Render the background of scene. The blend is optimized for render a solid
	 * picture.
	 * 
	 * @param gameTime
	 */
	public void renderBackground(float gameTime) {
		// TODO Auto-generated method stub

	}

	/**
	 * Update the camera target follows the predicated x and y parameters
	 * smoothly
	 * 
	 * @param gameTime
	 * @param cameraSpeed
	 * @param x
	 * @param y
	 */
	protected void updateCameraPosition(float gameTime, int cameraSpeed,
			float x, float y) {

		OrthographicCamera camera = services.getCamera();
		float oldX = camera.position.x;
		float oldY = camera.position.y;

		if (camera.position.x > x + 1) {
			camera.translate(new Vector2(-cameraSpeed, 0));
		}
		if (camera.position.x < x - 1) {
			camera.translate(new Vector2(cameraSpeed, 0));
		}
		if (camera.position.y > y + 1) {
			camera.position.y -= cameraSpeed;
		}
		if (camera.position.y < y - 1) {
			camera.position.y += cameraSpeed;
		}

		if (camera.position.x < Gdx.graphics.getWidth() / (2 / camera.zoom)
				|| camera.position.x > getMapWidth() - Gdx.graphics.getWidth()
						/ (2 / camera.zoom)) {

			camera.position.x = oldX;
		}
		if (camera.position.y < Gdx.graphics.getHeight() / (2 / camera.zoom)
				|| camera.position.y > getMapHeight()
						- Gdx.graphics.getHeight() / (2 / camera.zoom)) {
			camera.position.y = oldY;
		}
	}

	@Override
	public boolean keyDown(int key){
	    if(super.keyDown(key))
		return true;
	    
	   // updateCamera(key);
	    
	    return false;
	}
	/**
	 * Update the camera using the default setting of engine so you can control
	 * the camera by keyboard like magnification or translation
	 * 
	 * @param gameTime
	 */
	public void updateCamera(int key) {

		if (!enableDefaultCameraController)
			return;
		if (key ==(Input.Keys.Z)) {
			mainCamera.zoom += 0.02;
		}
		if (key==(Input.Keys.X)) {
			mainCamera.zoom -= 0.02;
		}
		if (key==(Input.Keys.A)) {
//			if (mainCamera.position.x > -WORLD_WIDTH / 2)
				mainCamera.translate(-10, 0, 0);
		}
		if (key==(Input.Keys.D)) {
//			if (mainCamera.position.x < WORLD_WIDTH / 2)
				mainCamera.translate(10, 0, 0);
		}
		if (key==(Input.Keys.S)) {
//			if (mainCamera.position.y > -WORLD_HEIGHT / 2)
				mainCamera.translate(0, -10, 0);
		}
		if (key==(Input.Keys.W)) {
//			if (mainCamera.position.y < WORLD_HEIGHT / 2)
				mainCamera.translate(0, 10, 0);
		}
		if (key==(Input.Keys.C)) {
			mainCamera.rotate(-rotationSpeed, 0, 0, 1);
		}
		if (key==(Input.Keys.V)) {
			mainCamera.rotate(rotationSpeed, 0, 0, 1);
		}
	}

	/**
	 * @return the enableDefaultCameraUpdate
	 */
	public boolean isEnableDefaultCameraUpdate() {
		return enableDefaultCameraController;
	}

	/**
	 * @param enableDefaultCameraUpdate
	 *            the enableDefaultCameraUpdate to set
	 */
	public void setEnableDefaultCameraUpdate(boolean enableDefaultCameraUpdate) {
		this.enableDefaultCameraController = enableDefaultCameraUpdate;
	}

	/**
	 * @return the batch
	 */
	public SpriteBatch getBatchUI() {
		return uiBatch;
	}

}