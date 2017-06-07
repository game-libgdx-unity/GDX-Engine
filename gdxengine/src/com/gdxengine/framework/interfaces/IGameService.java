package com.gdxengine.framework.interfaces;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.gdxengine.framework.BaseGameSetting;

/**
 * The interface for all game services in your game. include 3D or 2D game.
 * 
 * @author Vinh
 *
 */
public interface IGameService {
	/**
	 * get the SpriteBatch of your game
	 * @return
	 */
	public SpriteBatch getSpriteBatch();
	/**
	 * get the Game Asset of your game
	 * @return
	 */
	public IGameAsset getGameAsset();
	/**
	 * get the Game Setting of your game
	 * @return
	 */
	public BaseGameSetting getGameSetting();
	/**
	 * Get the camera of your game
	 * This class should be used in 2D Game only
	 * In 3D Game, the IGame3DService interface have method to get CameraManager object.
	 * CameraManager class will manage all camera in your game and you can get or set the
	 * Current Active Camera easily
	 * 
	 * @return
	 */
	public OrthographicCamera getCamera();
	/**
	 * Check the service is disposed or not
	 * 
	 * @return
	 */
	public boolean isDisposed();
	/**
	 * Remove custom services by Index
	 * 
	 * @param index Index of service
	 */
	public boolean removeService(int index);
	/**
	 * Remove custom services by Its type
	 * 
	 * @param type type of servce
	 */
	public boolean removeService(Class<?> type);
	/**
	 * Get the value of service by index of the service
	 * @param index
	 * @return The value of service
	 */
	public Object getService(int index);
	/**
	 * Get the value of service by it' Class type
	 * @param <T> Type of service
	 * @param type Class of Object will receive value of service
	 * @return the reference of service
	 */
	public <T> T getService(Class<T> type);
	/**
	 * Add value to Game Service and turn it into a service of game by key (index)
	 * 
	 * @param value value of service to add
	 * @param key Index of service
	 */
	public void addService(IService value, int key);
	/**
	 * Add value to Game Service and turn it into a service of game, no need  key or index
	 * 
	 * @param value value of service to add
	 */
	public void addService(IService value);
	/**
	 * Draw a custom text in string type
	 * @param font Font will used to draw.
	 * @param text text will be drawn
	 * @param X position-x of location being draw
	 * @param Y position-y of location being draw
	 */
	public void drawText(BitmapFont font, CharSequence text, float X, float Y);
	/**
	 * Draw a custom texture
	 * @param textureRegion The texture will be drawn
	 * @param X position-x of location being draw
	 * @param Y position-y of location being draw
	 */
	public void drawTexture(Texture textureRegion, float X, float Y);
	/**
	 * Draw a custom texture region
	 * @param texture The texture region will be drawn
	 * @param X position-x of location being draw
	 * @param Y position-y of location being draw
	 */
	public void drawTextureRegion(TextureRegion texture, float X, float Y);
	/**
	 * Play a sound that have loaded successfully and specify the volume
	 * @param sound
	 * @param volume
	 */
	public void playSound(Sound sound, float volume);
	/**
	 * Play a sould that have loaded successfully
	 * @param sound
	 */
	public void playSound(Sound sound);
	/**
	 * Play a music that have loaded successfully
	 * @param music
	 */
	public void playMusic(Music music);
	/**
	 * change the current active scene by its Index
	 * @param index Index of scene
	 */
	public void changeScene(int index);
	/**
	 * change the current active scene by its Type
	 * @param SceneType the Class type of Scene
	 */
	public void changeScene(Class<?> SceneType);
	/**
	 * modify setting of game from game setting
	 * 
	 * @param value value to change
	 * @param key key of value
	 */
	public void modifySetting(Object value, int key);
	/**
	 * Get a setting value of game from game setting by its Key (Index)
	 */
	public Object getSetting(int key);
	/**
	 * load setting of game from game setting
	 */
	public void loadSetting();
	/**
	 * Save setting of game from game setting
	 */
	public void saveSetting();
	/**
	 * load content from game asset
	 */
	public void loadContent();
	/**Get bound of current scene
	 * @return the static variable tmp in Rectangle class
	 */
	public Rectangle getScreenBound();
	
	/**
	 * Get the size of windows game in vector2
	 * @return
	 */
	public Vector2 getWindowSize();
	
	/**
	 * Get scene by its index
	 * @param sceneIndex
	 * @return
	 */
	public IScene getScene(int sceneIndex);
	
	/**get the scene in game by scene class type
	 * @param sceneClass
	 * @return
	 */
	public <T> T getScene(Class<T> sceneClass);
	
	/**set the scene in game by scene class type
	 * @param sceneClass
	 */
	public <T> void setCurrentScene(Class<T> sceneClass);
	
	/**set the current active scene in game by scene index
	 * @param sceneIndex
	 */
	public void setCurrentScene(int sceneIndex);
	
	/**Get the current active scene in game
	 * @return
	 */
	public IScene getCurrentScene();

	/***Usage for tiled map only. Get the position on map from mouse position on screen.
	 * @return mouse position on map
	 */
	public Vector2 getMousePositionOnMap();
	
	/**Usage for tiled map only. Get the position on map from position on screen.
	 * @param position position on screen
	 * @return position on map
	 */
	public Vector2 getPositionOnMap(Vector2 position);
	
	/**
	 * Translate the 2D camera
	 * @param i
	 * @param deltaY
	 * @param maxWidth
	 * @param maxHeight
	 * @return
	 */
	public void translateCamera(int i, int deltaY, int maxWidth, int maxHeight);
	/**
	 * Remove IDrawable object from current scene
	 * @param obj object to remove
	 * @return removed successfully or not.
	 */
	public boolean removeFromScene(IDrawable obj);
	/**
	 * Get position from screen to world
	 * @param x
	 * @param y
	 * @return
	 */
	public Vector2 getPositionOnWorld(float x, float y);
	/**
	 * When you call method keepAspectRatio from TiledScene
	 * , You use this method to get World Position from a position on screen 
	 * @param x
	 * @param y
	 * @return
	 */
	public Vector2 positionFromWorldUsingGameViewport(float x, float y);
	
	public void draw(Texture texture, Vector2 position, Rectangle source);
	public void draw(Texture texture, Vector2 position, Vector2 size, Rectangle source, Flip flip);
	public void draw(Texture texture, Vector2 position, Vector2 origin, Vector2 size, float scale, float rotation, Rectangle source, Flip flip);
	public void draw(Texture texture, Vector2 position, Vector2 origin, Vector2 size, Vector2 scale, float rotation, Rectangle source, Flip flip);
	public void draw(TextureRegion region,  Vector2 position, Vector2 size);
	public void draw(TextureRegion region,  Vector2 position, Vector2 origin, Vector2 size, float scale, float rotation);
	public void draw(TextureRegion region,  Vector2 position, Vector2 origin, Vector2 size, Vector2 scale, float rotation);
	
	public enum Flip {
	    None,
	    Vertical,
	    Horizontal,
	    Both
	}
}
