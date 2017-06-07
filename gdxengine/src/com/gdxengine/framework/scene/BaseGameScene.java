package com.gdxengine.framework.scene;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.gdxengine.framework.ObjectCollection;
import com.gdxengine.framework.Utils;
import com.gdxengine.framework.event.TouchEvent;
import com.gdxengine.framework.event.listener.TouchDownListener;
import com.gdxengine.framework.event.listener.TouchListener;
import com.gdxengine.framework.interfaces.ICollectionItem;
import com.gdxengine.framework.interfaces.IDrawable;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.interfaces.IScene2D;
import com.gdxengine.framework.ui.UIManager;

/**
 * 
 * Abstract class for all scene 2D of the game. Objects that implement Drawable
 * interface should be added to scene's collection by using addDrawableObject()
 * method to make the object can be auto-update and auto-render
 * 
 * @author Vinh
 * 
 */
public abstract class BaseGameScene extends ObjectCollection<IDrawable>
	implements IScene2D, InputProcessor {

    /*
     * Stage for manage UI components
     */
    protected boolean zoomUI = false;
    protected SpriteBatch uiBatch;
    public UIManager uiManager;
    public Skin skin;
    // 800 and 480 is default value for target resolution screen, of course it
    // maybe different from your games
    public static int TARGET_WIDTH = 800;
    public static int TARGET_HEIGHT = 480;
    // 1024 is default value, of course it maybe different from your games
    public static int WORLD_WIDTH = 1024;
    public static int WORLD_HEIGHT = 1024;

    public static final float XSCALE = (float) TARGET_WIDTH
	    / Gdx.graphics.getWidth();
    public static final float YSCALE = (float) TARGET_HEIGHT
	    / Gdx.graphics.getHeight();
    public static final float SCREEN_SCALE = Math.max(XSCALE, YSCALE);
    public static final float SCREEN_RATIO = Gdx.graphics.getWidth()
	    / (float) Gdx.graphics.getHeight();
    /**
     * The value of needContinueInit, if true, it calls initialize() method
     * every time when scene is activated if false, the initialize() method will
     * be called only one time when the first time the scene is activated.
     */
    private boolean needContinueInit = true;
    /**
     * indicate the scene is paused
     */
    private boolean isPaused = false;
    /**
     * indicate the scene is initialized in first time
     */
    private boolean initializeTheFirstTime = true;
    /**
     * Reference to all inputProcessor objects in scene
     */
    protected ArrayList<TouchListener> inputs = new ArrayList<TouchListener>(0);
    private TouchEvent touchEvent = new TouchEvent();

    /**
     * Constructor of BaseGameScene, set needContinueInit = true
     * 
     * @param gameService
     *            the service of game
     */
    public BaseGameScene(IGameService gameService) {
	this(gameService, true);
    }

    /**
     * Constructor of BaseGameScene
     * 
     * @param gameService
     *            service of game
     * @param continueInit
     *            if true, it calls initilize() method every time when scene is
     *            activated if false, the initilize() method will be called only
     *            one time when the first time the scene is activated.
     */
    public BaseGameScene(IGameService gameService, boolean continueInit) {
	super(gameService);
	this.needContinueInit = continueInit;
	Gdx.input.setInputProcessor(this);
    }

    public void setupUIManager(String skinFilePath) {
	skin = new Skin(Gdx.files.internal(skinFilePath));
	uiManager = new UIManager("dialog", skin, getGameService());
	uiBatch = uiManager.getSpriteBatch();
	uiManager.getCamera().update();
	uiBatch.setTransformMatrix(uiManager.getCamera().combined);
	// Gdx.graphics.setDisplayMode(Gdx.graphics.getWidth(),
	// Gdx.graphics.getH, true);
	if (isZoomUI()) {
	    OrthographicCamera uiCamera = (OrthographicCamera) uiManager
		    .getCamera();
	    uiCamera.zoom = services.getCamera().zoom;
	}
    }

    @Override
    public void update(float gameTime) {

	if (uiManager.isMenuVisible()) {
	    uiManager.act(gameTime);
	    return;
	}

	if (!isPaused) {
	    for (int i = 0; i < objectCollection.size(); i++) {
		ICollectionItem object = objectCollection.get(i);

		if (object.isDead()) {
		    objectCollection.remove(i);
		    i--;
		    continue;
		}
		if (object.isEnabled())
		    object.update(gameTime);
	    }
	}
    }

    @Override
    public void render(float gameTime) {
	for (IDrawable obj : objectCollection)
	    if (obj.isVisible())
		obj.render(gameTime);
    }

    @Override
    public void pause() {
	for (IDrawable obj : objectCollection)
	    obj.pause();
	getGameService().saveSetting();
	isPaused = true;
    }

    @Override
    public void resume() {

	for (IDrawable obj : objectCollection)
	    obj.resume();
	isPaused = false;
    }

    @Override
    public void initScene() {
	if (initializeTheFirstTime || needContinueInit) {
	    initialize();
	    initializeTheFirstTime = false;
	}
    }

    @Override
    public void initialize() {
	for (IDrawable obj : objectCollection)
	    obj.initialize();
    }

    @Override
    public void addDrawbleObject(IDrawable obj) {
	objectCollection.add(obj);
    }

    @Override
    public boolean isContinueNeedInit() {
	// TODO Auto-generated method stub
	return needContinueInit;
    }

    @Override
    public void setContinueNeedInit(boolean value) {
	needContinueInit = value;

    }

    @Override
    public SpriteBatch getSpriteBatch() {
	return getGameService().getSpriteBatch();
    }

    @Override
    public ArrayList<IDrawable> getObjectCollection() {
	return objectCollection;
    }

    @Override
    public void addOrRecycleObject(Object basicObject) {
	for (IDrawable b : objectCollection) {
	    if (b.isDead()) {
		if (b.getClass() == basicObject.getClass()) {
		    b = (IDrawable) basicObject;
		}
	    }
	}

	addDrawbleObject((IDrawable) basicObject);
    }

    public void addInputProcessor(TouchListener obj) {
	inputs.add(obj);
    }

    public void removeInputProcessor(TouchListener obj) {
	inputs.remove(obj);
    }

    public ArrayList<TouchListener> getInputProcessors() {
	return inputs;
    }

    @Override
    public boolean keyDown(int keycode) {

	return uiManager.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {

	return uiManager.keyUp(keycode);
    }

    @Override
    public boolean keyTyped(char character) {
	return uiManager.keyTyped(character);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

	if (uiManager.touchDown(screenX, screenY, pointer, button))
	    return true;

	if (uiManager.isMenuVisible())
	    return true;

	Vector3.tmp.set( Gdx.input.getX(), Gdx.input.getY(), 0);
	touchEvent.setScreenX((int) Vector3.tmp.x);
	touchEvent.setScreenY(Gdx.graphics.getHeight() - (int) Vector3.tmp.y);
	services.getCamera().unproject(Vector3.tmp);
	touchEvent.setX((int) Vector3.tmp.x);
	touchEvent.setY((int) Vector3.tmp.y);
	touchEvent.setButton(button);
	touchEvent.setPointer(pointer);

	for (int i = inputs.size()-1; i >-1;i--)
	{
	    TouchListener input= inputs.get(i);
	    if (Utils.pointInRectangle(input.getBoundingRectangle(), Vector3.tmp))
		return input.onTouchDown(touchEvent);
	}
	return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
	if (uiManager.touchUp(screenX, screenY, pointer, button))
	    return true;

	if (uiManager.isMenuVisible())
	    return true;

	Vector3.tmp.set( Gdx.input.getX(), Gdx.input.getY(), 0);
	touchEvent.setScreenX((int) Vector3.tmp.x);
	touchEvent.setScreenY(Gdx.graphics.getHeight() - (int) Vector3.tmp.y);
	services.getCamera().unproject(Vector3.tmp);
	touchEvent.setX((int) Vector3.tmp.x);
	touchEvent.setY((int) Vector3.tmp.y);
	touchEvent.setButton(button);
	touchEvent.setPointer(pointer);

	for (int i = inputs.size()-1; i >-1;i--)
	{
	    TouchListener input= inputs.get(i);
	    if (Utils.pointInRectangle(input.getBoundingRectangle(), Vector3.tmp))
		return input.onTouchUp(touchEvent);
	}
	return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
	if (uiManager.touchDragged(screenX, screenY, pointer))
	    return true;

	if (uiManager.isMenuVisible())
	    return true;

	Vector3.tmp.set( Gdx.input.getX(), Gdx.input.getY(), 0);
	touchEvent.setScreenX((int) Vector3.tmp.x);
	touchEvent.setScreenY(Gdx.graphics.getHeight() - (int) Vector3.tmp.y);
	services.getCamera().unproject(Vector3.tmp);
	touchEvent.setX((int) Vector3.tmp.x);
	touchEvent.setY((int) Vector3.tmp.y);

	for (int i = inputs.size()-1; i >-1;i--)
	{
	    TouchListener input= inputs.get(i);
	    if (Utils.pointInRectangle(input.getBoundingRectangle(), Vector3.tmp))
		if (input.onTouchDragged(touchEvent))
		    return true;
	//move the camera
	    if(isDragScreenEnable())
	getGameService().translateCamera(-Gdx.input.getDeltaX(),
		Gdx.input.getDeltaY(), WORLD_WIDTH, WORLD_HEIGHT);
	}

	return false;
    }

    protected boolean isDragScreenEnable() {
	// TODO Auto-generated method stub
	return true;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
	return uiManager.mouseMoved(screenX, screenY);
    }

    @Override
    public boolean scrolled(int amount) {

	if (uiManager.scrolled(amount))
	    return true;

	if (!uiManager.isMenuVisible()) {
	    if (amount > 0)
		services.getCamera().zoom += -.05f;
	    if (amount < 0)
		services.getCamera().zoom += .05f;
	}
	return false;
    }

    /**
     * Automatically setup the camera with proper zoom based screen resolution
     * and position at screen center
     */
    protected void setupCamera() {
	OrthographicCamera cam = services.getCamera();
	cam.zoom = SCREEN_SCALE;
	cam.position.set(Gdx.graphics.getWidth() / 2f,
		Gdx.graphics.getHeight() / 2f, 0);
    }

    /**
     * Setup camera at specific zoom and position
     * 
     * @param cameraPosition
     * @param zoom
     */
    protected void setupCamera(Vector2 cameraPosition, float zoom) {
	setupCamera(cameraPosition.x, cameraPosition.y, zoom);
    }

    /**
     * Setup camera at specific zoom and position
     * 
     * @param zoom
     */
    protected void setupCamera(float cameraPosX, float cameraPosY, float zoom) {
	OrthographicCamera cam = services.getCamera();
	cam.zoom = zoom;
	cam.position.set(cameraPosX, cameraPosY, 0);
    }

    /**
     * Setup camera at specific zoom and position at map center
     * 
     * @param zoom
     */
    protected void setupCamera(float zoom) {
	OrthographicCamera cam = services.getCamera();
	cam.zoom = zoom;
	cam.position.set(Gdx.graphics.getWidth() / 2f,
		Gdx.graphics.getHeight() / 2f, 0);
    }

    /**
     * @return the zoomUI
     */
    public boolean isZoomUI() {
	return zoomUI;
    }

    /**
     * @param zoomUI
     *            the zoomUI to set
     */
    public void setZoomUI(boolean zoomUI) {
	this.zoomUI = zoomUI;
    }

    public abstract void onRenderForeground(float gameTime);

    public void renderUIandFG(float gameTime) {
	uiBatch.begin();
	onRenderForeground(gameTime);
	uiManager.draw();
	uiBatch.end();

    }

    public TouchEvent getTouchEvent() {
        return touchEvent;
    }
}
