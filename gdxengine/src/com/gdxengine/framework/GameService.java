package com.gdxengine.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gdxengine.framework.interfaces.IDrawable;
import com.gdxengine.framework.interfaces.IGameAsset;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.interfaces.IScene;
import com.gdxengine.framework.interfaces.ISceneManager;
import com.gdxengine.framework.interfaces.IService;
import com.gdxengine.framework.scene.TiledScene;

public class GameService implements IGameService {

    private SpriteBatch batch;
    private IGameAsset asset;
    private BaseGameSetting setting;
    private OrthographicCamera camera;
    private boolean isDisposed = false;
    private ISceneManager sceneManager;

    public static int MAX_SERVICES = 10;
    IService[] services;

    public GameService(SpriteBatch batch, IGameAsset asset,
	    BaseGameSetting setting, OrthographicCamera camera,
	    ISceneManager sceneManager) {
	this.batch = batch;
	this.asset = asset;
	this.setting = setting;

	if (asset == null)
	    this.asset = new DefaultGameAsset();
	if (setting == null)
	    this.setting = new DefaultGameSetting();
	this.camera = camera;
	this.setChangeScene(sceneManager);
	Rectangle.tmp.set(0, 0, getWindowSize().x, getWindowSize().y);

	services = new IService[MAX_SERVICES];
    }

    @Override
    public SpriteBatch getSpriteBatch() {
	// TODO Auto-generated method stub
	return batch;
    }

    @Override
    public IGameAsset getGameAsset() {
	// TODO Auto-generated method stub
	return asset;
    }

    @Override
    public BaseGameSetting getGameSetting() {
	// TODO Auto-generated method stub
	return setting;
    }

    @Override
    public OrthographicCamera getCamera() {
	// TODO Auto-generated method stub
	return camera;
    }

    @Override
    public boolean isDisposed() {
	// TODO Auto-generated method stub
	return isDisposed;
    }

    /**
     * @param sceneManager
     *            the changeScene to set
     */
    void setChangeScene(ISceneManager sceneManager) {
	this.sceneManager = sceneManager;
    }

    @Override
    public void addService(IService value) {

	for (int i = 0; i < MAX_SERVICES; i++) {
	    if (services[i] != null)
		continue;

	    services[i] = value;
	    return;
	}

	throw new GdxRuntimeException("Services is full!");
    }

    @Override
    public void addService(IService value, int key) {
	if (key < 0 || key >= MAX_SERVICES)
	    throw new GdxRuntimeException(
		    "index is equal or greater than MAX SERVICES value!");
	services[key] = value;
    }

    @Override
    public boolean removeService(int key) {
	if (key < 0 || key >= MAX_SERVICES)
	    throw new GdxRuntimeException(
		    "index is equal or greater than MAX SERVICES value!");

	if (services[key] != null) {
	    services[key] = null;
	    return true;
	}

	return false;
    }

    @Override
    public Object getService(int key) {
	if (key < 0 || key >= MAX_SERVICES)
	    throw new GdxRuntimeException(
		    "index is equal or greater than MAX SERVICES value!");
	return services[key];
    }

    @Override
    public boolean removeService(Class type) {
	for (Object service : services) {
	    if (service == null)
		continue;
	    if (service.getClass() == type) {
		return true;
	    }
	}
	return false;
    }

    @Override
    public void drawText(BitmapFont font, CharSequence text, float X, float Y) {
	font.draw(batch, text, X, Y);
    }

    @Override
    public void drawTexture(Texture texture, float X, float Y) {
	batch.draw(texture, X, Y);
    }

    @Override
    public void drawTextureRegion(TextureRegion texture, float x, float y) {
	batch.draw(texture, x, y);
    }

    @Override
    public void playSound(Sound sound, float volume) {

	if (BaseGameSetting.isPlaySound)
	    sound.play(volume);
    }

    @Override
    public void playMusic(Music music) {

	if (BaseGameSetting.isPlayAudio)
	    music.play();

    }

    @Override
    public void playSound(Sound sound) {
	playSound(sound, 1f);
    }

    @Override
    public void changeScene(int gameScene) {
	sceneManager.changeScene(gameScene);
    }

    @Override
    public void modifySetting(Object value, int key) {
	setting.modifySetting(key, value);
    }

    @Override
    public void loadSetting() {

	setting.loadSetting();
    }

    @Override
    public void saveSetting() {
	setting.saveSetting();

    }

    @Override
    public void loadContent() {
	asset.load();

    }

    @Override
    public Object getSetting(int key) {
	return setting.getSetting(key);
    }

    @Override
    public void changeScene(Class SceneType) {
	sceneManager.changeScene(SceneType);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getService(Class<T> t) {
	for (int i = 0; i < MAX_SERVICES; i++) {
	    if (services[i] == null)
		continue;
	    if (services[i].getClass() == t) {
		return (T) services[i];
	    }
	}
	throw new GdxRuntimeException("The services cannot be found!");
    }

    @Override
    public Rectangle getScreenBound() {
	// TODO Auto-generated method stub
	return Rectangle.tmp;
    }

    @Override
    public Vector2 getWindowSize() {
	// TODO Auto-generated method stub
	return new Vector2(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public IScene getScene(int sceneIndex) {
	// TODO Auto-generated method stub
	return sceneManager.getScene(sceneIndex);
    }

    @Override
    public <T> T getScene(Class<T> sceneClass) {
	// TODO Auto-generated method stub
	return sceneManager.getScene(sceneClass);
    }

    @Override
    public void setCurrentScene(Class sceneClass) {
	// TODO Auto-generated method stub
	sceneManager.changeScene(sceneClass);
    }

    @Override
    public void setCurrentScene(int sceneIndex) {
	// TODO Auto-generated method stub
	sceneManager.changeScene(sceneIndex);
    }

    @Override
    public IScene getCurrentScene() {
	// TODO Auto-generated method stub
	return sceneManager.getScene();
    }

    @Override
    public boolean removeFromScene(IDrawable obj) {
	// TODO Auto-generated method stub
	return sceneManager.getScene().getObjectCollection().remove(obj);
    }

    /**
     * Return position of mouse on the tiledmap based camera.
     * 
     * @return
     */
    @Override
    public Vector2 getMousePositionOnMap() {
	return getPositionOnWorld(Gdx.input.getX(), Gdx.input.getY());
    }

    @Override
    public Vector2 getPositionOnMap(Vector2 position) {
	Vector3 vec = new Vector3(position.x, position.y, 0);
	position.y = Gdx.graphics.getHeight() - position.y - 1;
	vec.x = (2 * position.x) / Gdx.graphics.getWidth() - 1;
	vec.y = (2 * position.y) / Gdx.graphics.getHeight() - 1;
	vec.z = 2 * vec.z - 1;
	vec.prj(camera.invProjectionView);

	position.x = vec.x;
	position.y = vec.y;

	return position;
    }

    @Override
    public Vector2 getPositionOnWorld(float x, float y) {
	Vector3 vec = new Vector3(x, y, 0);
	y = Gdx.graphics.getHeight() - y - 1;
	vec.x = (2 * x) / Gdx.graphics.getWidth() - 1;
	vec.y = (2 * y) / Gdx.graphics.getHeight() - 1;
	vec.z = 2 * vec.z - 1;
	vec.prj(camera.invProjectionView);

	return new Vector2(vec.x, vec.y);
    }

    @Override
    public Vector2 positionFromWorldUsingGameViewport(float x, float y) {
	Vector3 vec = new Vector3(x, y, 0);
	x = x - Game.viewportX;
	y = Gdx.graphics.getHeight() - y - 1;
	y = y - Game.viewportY;
	vec.x = (2 * x) / Game.viewportWidth - 1;
	vec.y = (2 * y) / Game.viewportHeight - 1;
	vec.z = 2 * vec.z - 1;
	vec.prj(camera.invProjectionView);

	return new Vector2(vec.x, vec.y);
    }

    @Override
    public void translateCamera(int x, int y, int maxWidth, int maxHeight) {
	float oldX = camera.position.x;
	float oldY = camera.position.y;

	camera.translate(x, y);

	if (camera.position.x < Gdx.graphics.getWidth() / (2 / camera.zoom)
		|| camera.position.x > maxWidth - Gdx.graphics.getWidth()
			/ (2 / camera.zoom)) {

	    camera.position.x = oldX;
	}
	if (camera.position.y < Gdx.graphics.getHeight() / (2 / camera.zoom)
		|| camera.position.y > maxHeight - Gdx.graphics.getHeight()
			/ (2 / camera.zoom)) {
	    camera.position.y = oldY;
	}
    }

    @Override
    public void draw(Texture texture, Vector2 position, Rectangle source) {
	batch.draw(texture, position.x, position.y, (int) source.getX(),
		(int) source.getY(), (int) source.getWidth(),
		(int) source.getHeight());
    }

    @SuppressWarnings("incomplete-switch")
    @Override
    public void draw(Texture texture, Vector2 position, Vector2 size,
	    Rectangle source, Flip flip) {
	boolean flipX = false;
	boolean flipY = false;
	switch (flip) {
	case Both:
	    flipX = true;
	    flipY = true;
	    break;
	case Horizontal:
	    flipX = true;
	    break;
	case Vertical:
	    flipY = true;
	}
	batch.draw(texture, position.x, position.y, size.x, size.y,
		(int) source.getX(), (int) source.getY(),
		(int) source.getWidth(), (int) source.getHeight(), flipX, flipY);
    }

    @SuppressWarnings("incomplete-switch")
    @Override
    public void draw(Texture texture, Vector2 position, Vector2 origin,
	    Vector2 size, float scale, float rotation, Rectangle source,
	    Flip flip) {
	boolean flipX = false;
	boolean flipY = false;
	switch (flip) {
	case Both:
	    flipX = true;
	    flipY = true;
	    break;
	case Horizontal:
	    flipX = true;
	    break;
	case Vertical:
	    flipY = true;
	}
	batch.draw(texture, position.x, position.y, origin.x, origin.y, size.x,
		size.y, scale, scale, rotation, (int) source.getX(),
		(int) source.getY(), (int) source.getWidth(),
		(int) source.getHeight(), flipX, flipY);

    }

    @SuppressWarnings("incomplete-switch")
    @Override
    public void draw(Texture texture, Vector2 position, Vector2 origin,
	    Vector2 size, Vector2 scale, float rotation, Rectangle source,
	    Flip flip) {
	boolean flipX = false;
	boolean flipY = false;
	switch (flip) {
	case Both:
	    flipX = true;
	    flipY = true;
	    break;
	case Horizontal:
	    flipX = true;
	    break;
	case Vertical:
	    flipY = true;
	}
	batch.draw(texture, position.x, position.y, origin.x, origin.y, size.x,
		size.y, scale.x, scale.y, rotation, (int) source.getX(),
		(int) source.getY(), (int) source.getWidth(),
		(int) source.getHeight(), flipX, flipY);
    }

    @Override
    public void draw(TextureRegion region, Vector2 position, Vector2 size) {
	batch.draw(region, position.x, position.y, size.x, size.y);
    }

    @Override
    public void draw(TextureRegion region, Vector2 position, Vector2 origin,
	    Vector2 size, float scale, float rotation) {
	batch.draw(region, position.x, position.y, origin.x, origin.y, size.x,
		size.y, scale, scale, rotation);

    }

    @Override
    public void draw(TextureRegion region, Vector2 position, Vector2 origin,
	    Vector2 size, Vector2 scale, float rotation) {
	batch.draw(region, position.x, position.y, origin.x, origin.y, size.x,
		size.y, scale.x, scale.y, rotation);

    }

}
