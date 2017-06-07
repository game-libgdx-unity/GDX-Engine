package com.gdxengine.framework.test.physics.angrybirds;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.gdxengine.framework.Utils;
import com.gdxengine.framework.box2d.PhysicsManager;
import com.gdxengine.framework.effect.ParticleEffectManager;
import com.gdxengine.framework.interfaces.IDrawable;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.scene.BaseGameScene;
import com.gdxengine.framework.scene.PhysicsTiledScene;
import com.gdxengine.framework.scene.TiledScene;
import com.gdxengine.framework.test.physics.angrybirds.Bird.Status;

public class AngryBirdScene extends TiledScene {

    // font using to draw some texts...
    private BitmapFont bigFont;
    // current level
    public int gameLevel = 1;
    // Max number of level
    public static final int MAXGAMELEVEL = 3;
    // Game object
    private Sling sling;
    private Bird bird;
    // tile object indicate the position of sling
    TiledObject birdTile;
    // create particle effect
    ParticleEffectManager effects;
    // Physics manager
    PhysicsManager physicManager;

    public AngryBirdScene(IGameService gameService) {
	super(gameService);
	// construct the particle effect
	effects = new ParticleEffectManager(getGameService());
	effects.addParticleEffect("explosion",
		"angrybirds/particles/explosion.pp", "angrybirds/particles");
	// add effect to game service
	getGameService().addService(effects);

	// add effects to scene
	addDrawbleObject(effects);
	setEnableDefaultCameraUpdate(true);
	// keepGameAspectRatio();
	// setting physics manager
	physicManager = new PhysicsManager(gameService);
	// don't use the DebugRender
	physicManager.setDebugRenderEnabled(false);
	gameService.addService(physicManager);
	setupCamera();

	setupUIManager("data/skin/uiskin.json");
	uiManager.setMenuVisible(false);
    }

    @Override
    public void initialize() {
	// create level based the current level variable
	createLevel();
    }

    public void createLevel() {

	services.getCamera().position.set(Gdx.graphics.getWidth() / 2,
		Gdx.graphics.getHeight() / 2, 0);
	// clear all object from last level
	clear();
	// add physics manager as game component
	addDrawbleObject(physicManager);
	// check if level is maximum
	if (gameLevel > MAXGAMELEVEL)
	    gameLevel = 1;
	// load the tiled map
	loadTileMapRenderer("angrybirds/level" + String.valueOf(gameLevel)
		+ "/map2.tmx", "angrybirds/level" + String.valueOf(gameLevel));
	// create static body from "static" layer in tmx file
	physicManager.loadStaticBodies("static", tiledMap);
	// loop each object group.
	// a object group is all objects in a layer in tmx file.
	for (int i = 0; i < tiledMap.objectGroups.size(); i++) {
	    TiledObjectGroup group = tiledMap.objectGroups.get(i);

	    // Find the object group of "horizonal wood" layer
	    if ("horizonalwood".equals(group.name)) {
		// foreach objects in this group
		for (int j = 0; j < group.objects.size(); j++) {
		    TiledObject object = group.objects.get(j);
		    // create static body from the object
		    Wood brick = new Wood(getGameService(), services
			    .getGameAsset().loadTextureRegion(
				    Asset.longwood,
				    new Rectangle(0, 0, object.width,
					    object.height)));
		    // init object based the tiled object
		    initilizeTileObject(brick, object);
		    // create and add the dynamic object to scene
		    Body body = physicManager.addDynamicBoxObject(brick,
			    object.width, object.height);
		}
	    }
	    // Find the object group of "vertical wood" layer
	    else if ("verticalwood".equals(group.name)) {
		// foreach objects in this group
		for (int j = 0; j < group.objects.size(); j++) {
		    TiledObject object = group.objects.get(j);
		    // create static body from the object
		    Wood brick = new Wood(getGameService(), services
			    .getGameAsset().loadTextureRegion(
				    Asset.tallwood,
				    new Rectangle(0, 0, object.width,
					    object.height)));
		    // init object based the tiled object
		    initilizeTileObject(brick, object);
		    // create and add the dynamic object to scene
		    Body body = physicManager.addDynamicBoxObject(brick,
			    object.width, object.height);

		}
	    }
	    // Find the object group of "block wood" layer
	    else if ("blockwood".equals(group.name)) {
		// foreach objects in this group
		for (int j = 0; j < group.objects.size(); j++) {
		    TiledObject object = group.objects.get(j);
		    // create static body from the object
		    Wood brick = null;
		    // create a block wood or a small block wood depend on its
		    // size
		    if (object.width > 30) {
			brick = new Wood(getGameService(), services
				.getGameAsset().loadTextureRegion(Asset.block));
			initilizeTileObject(brick, object,
				Asset.block.getWidth());
		    } else {
			brick = new Wood(getGameService(), services
				.getGameAsset().loadTextureRegion(
					Asset.smallblock));
			initilizeTileObject(brick, object,
				Asset.smallblock.getWidth());
		    }
		    // init object based the tiled object

		    // create and add the dynamic object to scene
		    Body body = physicManager.addDynamicBoxObject(brick,
			    object.width, object.height);

		}
	    } else if ("pig".equals(group.name)) {
		// foreach objects in this group
		for (int j = 0; j < group.objects.size(); j++) {
		    TiledObject object = group.objects.get(j);
		    // create static body from the object
		    TextureRegion tr = services.getGameAsset()
			    .loadTextureRegion(Asset.pig);
		    Pig pig = new Pig(getGameService(), tr, object,
			    tr.getRegionWidth());
		    // create and add the dynamic object to scene
		    Body body = physicManager.addDynamicCircleObject(pig,
			    object.width);
		}
	    } else if ("sling".equals(group.name)) {
		TiledObject object = group.objects.get(0);

		sling = new Sling(getGameService(), Asset.sling);
		initilizeTileObject(sling, object);
		addItem(sling);

		birdTile = object;

	    }
	}

	createBird();
	CustomContact contacts = new CustomContact(getGameService());
	physicManager.getWorld().setContactListener(contacts);
	setupCamera();
    }

    public boolean checkPlayerWon() {
	for (IDrawable obj : objectCollection)
	    // if the "pig" is existing is game, player won't win.
	    if ("pig".equals(obj.toString()))
		return false;

	return true;
    }

    /**
     * create bird from tile
     * 
     * @param object
     *            tile object
     */
    private void createBird() {
	removeInputProcessor(bird);
	bird = new Bird(getGameService(), Asset.bird, sling);
	bird.setPosition(sling.getPosition().add(bird.getWidth() / 2,
		bird.getHeight() / 2));
	// add bird to scene
	addDrawbleObject(bird);
	// set input so you can control the bird by dragging and dropping
	addInputProcessor(bird);
    }

    @Override
    public void update(float gameTime) {
	super.update(gameTime);
	if (Utils.isKeyDown(Keys.SPACE)) {
	    // create a new bird after press space key if the bird is not
	    // existed
	    if (!checkExistingBird())
		createBird();
	}

	if (Gdx.input.isKeyPressed(Keys.CONTROL_LEFT)) {
	    if (!uiManager.isMenuVisible()) {
		uiManager.setMenuVisible(true);
	    }
	}
    }

    public boolean checkExistingBird() {
	for (IDrawable obj : getObjectCollection()) {
	    try {
		Bird bird = (Bird) obj;
		if (bird.status == Status.normal)
		    return true;
	    } catch (Exception ex) {
		continue;
	    }
	}
	return false;
    }

    float max = 0f;

    public TileMapRenderer getTiledRenderer() {
	// TODO Auto-generated method stub
	return tileMapRenderer;
    }

    @Override
    public void renderBackground(float gameTime) {
	getGameService().drawTexture(Asset.background, 0, 0);
    }

    @Override
    public void onRenderForeground(float gameTime) {
	// TODO Auto-generated method stub
	
    }
}
