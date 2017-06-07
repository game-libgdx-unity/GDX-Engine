package com.gdxengine.framework.box2d;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObject;
import com.badlogic.gdx.graphics.g2d.tiled.TiledObjectGroup;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gdxengine.framework.DrawableGameComponent;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.interfaces.IPhysicsObject;
import com.gdxengine.framework.interfaces.IService;
import com.gdxengine.framework.scene.TileRenderer;

public class PhysicsManager extends DrawableGameComponent implements IService {

    Array<IPhysicsObject> physicsObjects = new Array<IPhysicsObject>();

    /**
     * @return the physicsObjects
     */
    public Array<IPhysicsObject> getPhysicsObjects() {
	return physicsObjects;
    }

    // Some constants for physics game, you can change values as you want.
    public final static int SHAPE_BOX = 0;
    public final static int SHAPE_CIRCLE = 1;
    public final static int SHAPE_POLYGON = 2;
    public static float PTM_RATIO = 100;
    public static final float BOX_STEP = 1 / 45f;

    public static final int BOX_VELOCITY_ITERATIONS = 6;
    public static final int BOX_POSITION_ITERATIONS = 2;

    public static final float WORLD_TO_BOX = 0.01f; // rate to transform from
						    // game to box physics
    public static final float BOX_TO_WORLD = 100f; // rate to transform from box
						   // physics to game

    // For box2d debugger
    protected Box2DDebugRenderer debugRender;
    protected Matrix4 debugMatrix;

    public boolean box2dDebug = false;

    /**
     * @return the box2dDebugger
     */
    public boolean isBox2dDebugger() {
	return box2dDebug;
    }

    /**
     * @param box2dDebugger
     *            the box2dDebugger to set
     */
    public void setBox2dDebugger(boolean box2dDebugger) {
	this.box2dDebug = box2dDebugger;
    }

    /**
     * Enable the debug renderer of box2D
     */
    public boolean debugRenderEnabled = true;

    /**
     * @return the debugRenderEnabled
     */
    public boolean isDebugRenderEnabled() {
	return debugRenderEnabled;
    }

    /**
     * @param debugRenderEnabled
     *            the debugRenderEnabled to set
     */
    public void setDebugRenderEnabled(boolean debugRenderEnabled) {
	this.debugRenderEnabled = debugRenderEnabled;
    }

    // Physics World
    protected World world;

    /**
     * Create a physics scene
     * 
     * @param gameService
     */
    public PhysicsManager(IGameService gameService) {
	super(gameService);
	debugRender = new Box2DDebugRenderer();
	debugMatrix = new Matrix4(services.getCamera().combined);
	debugMatrix.scale(BOX_TO_WORLD, BOX_TO_WORLD, 1f);
    }

    /**
     * render the Box2d Debugger
     */
    public void renderBox2dDebugger() {
	if (debugRenderEnabled) {
	    debugMatrix = new Matrix4(services.getCamera().combined);
	    debugMatrix.scale(BOX_TO_WORLD, BOX_TO_WORLD, 1f);
	    debugRender.render(world, debugMatrix);
	}
    }

    public void renderBackground(float gameTime) {

    }

    public void loadStaticBodies(String bodylayer, TiledMap tiledMap) {

	world = new World(new Vector2(0, -5), true);
	// loop each object group.
	// a object group is all objects in a layer.
	for (int i = 0; i < tiledMap.objectGroups.size(); i++) {
	    TiledObjectGroup group = tiledMap.objectGroups.get(i);

	    // Find the object group of "static" layer
	    if (bodylayer.equals(group.name)) {
		// foreach objects in this group
		for (int j = 0; j < group.objects.size(); j++) {
		    TiledObject object = group.objects.get(j);
		    // create static body from the object
		    createStaticBody(object);
		}
	    }
	}
    }

    /**
     * Create static body from a TiledObject Refer the box2d manual to
     * understand the static body
     * 
     * @param o
     *            TiledObject
     */
    public Body createStaticBody(TiledObject o) {
	BodyDef groundBodyDef = new BodyDef();
	groundBodyDef.type = BodyType.StaticBody;

	// get x from tile object
	float x = o.x;
	// transform into box2d
	x = x * WORLD_TO_BOX;

	// get position-y of object from map
	TileRenderer renderer = getGameService()
		.getService(TileRenderer.class);
	float y = renderer.getMapHeightUnits() - o.y - o.height;
	// transform into box2d
	y = y * WORLD_TO_BOX;

	groundBodyDef.position.set(x, y);
	Body groundBody = world.createBody(groundBodyDef);
	PolygonShape groundBox = new PolygonShape();

	// get the polygon point from map
	String[] strp = o.polygon.split(" ");
	Vector2[] apoints = new Vector2[strp.length];

	// for each points from objects
	for (int i = 0; i < strp.length; i++) {
	    x = Float.parseFloat(strp[i].split(",")[0]); // get x
	    x = x * WORLD_TO_BOX; // transform into box2d
	    y = -Float.parseFloat(strp[i].split(",")[1]); // get y
	    y = y * WORLD_TO_BOX; // transform into box2d

	    // insert the point into the array
	    apoints[i] = new Vector2(x, y);
	}

	// create static body from point array
	groundBox.set(apoints);
	groundBody.createFixture(groundBox, 0.0f);
	groundBody.setUserData("static");
	return groundBody;
    }

    /**
     * Create static body from a TiledObject Refer the box2d manual to
     * understand the static body
     * 
     * @param o
     *            TiledObject
     */
    public Body createStaticBoxBody(float x, float y, float width, float height) {
	BodyDef groundBodyDef = new BodyDef();
	groundBodyDef.type = BodyType.StaticBody;

	// transform into box2d
	x = x * WORLD_TO_BOX;

	// get position-y from map
	y = Gdx.graphics.getHeight() - y;
	// transform into box2d
	y = y * WORLD_TO_BOX;

	groundBodyDef.position.set(x, y);
	Body groundBody = world.createBody(groundBodyDef);
	PolygonShape polygon = new PolygonShape();
	((PolygonShape) polygon).setAsBox(width * WORLD_TO_BOX / 2, height
		* WORLD_TO_BOX / 2);
	groundBody.createFixture(polygon, 0.0f);
	groundBody.setUserData("static");
	return groundBody;
    }

    /**
     * Create static body from a TiledObject Refer the box2d manual to
     * understand the static body
     * 
     * @param o
     *            TiledObject
     */
    public Body createStaticCircleBody(float x, float y, float radius) {
	BodyDef groundBodyDef = new BodyDef();
	groundBodyDef.type = BodyType.StaticBody;
	// transform into box2d
	x = x * WORLD_TO_BOX;
	// get position-y of object from map
	y = Gdx.graphics.getHeight() - y;
	// transform into box2d
	y = y * WORLD_TO_BOX;

	groundBodyDef.position.set(x, y);
	Body groundBody = world.createBody(groundBodyDef);
	CircleShape shape = new CircleShape();
	((CircleShape) shape).setRadius(radius * WORLD_TO_BOX / 2);
	groundBody.createFixture(shape, 0.0f);
	groundBody.setUserData("static");
	return groundBody;
    }

    @Override
    public void update(float gameTime) {
	// implement the world physics
	world.step(BOX_STEP, BOX_VELOCITY_ITERATIONS, BOX_POSITION_ITERATIONS);
	world.clearForces();

	Iterator<Body> bi = world.getBodies();
	while (bi.hasNext()) {
	    Body b = bi.next();

	    if (b == null)
		continue;

	    if (b.getUserData() instanceof IPhysicsObject) {
		IPhysicsObject e = (IPhysicsObject) b.getUserData();
		// get the x, y position
		float x = b.getPosition().x * BOX_TO_WORLD - e.getRegionWidth()
			/ 2;
		float y = b.getPosition().y * BOX_TO_WORLD
			- e.getRegionHeight() / 2;
		// set position and rotation of sprite based the body in world
		// physics
		e.setX(x);
		e.setY(y);
		e.setRotation(MathUtils.radiansToDegrees * b.getAngle());
		e.update(gameTime);
	    } else if (b.getUserData() instanceof String) {
		String str = (String) b.getUserData();
		if ("remove".equals(str)) {
		    b.setUserData(null);
		    world.destroyBody(b);
		    b = null;
		}
	    }
	}
    }

    /**
     * Apply a force to physics object
     * 
     * @param data
     * @param force
     */
    protected void applyForce(IPhysicsObject data, Vector2 force) {
	applyForce(data, force, null);
    }

    /**
     * Apply a force to physics object with a force apply position
     * 
     * @param data
     * @param force
     * @param applyPoint
     */
    protected void applyForce(IPhysicsObject data, Vector2 force,
	    Vector2 applyPoint) {
	Iterator<Body> bi = world.getBodies();
	while (bi.hasNext()) {
	    Body b = bi.next();
	    IPhysicsObject e;
	    try {
		e = (IPhysicsObject) b.getUserData(); // get the IPhysicsObject
	    } catch (Exception ex) {
		e = null;
	    }
	    if (e != null) {
		if (e == data) {
		    if (applyPoint == null)
			b.applyForceToCenter(force);
		    else
			b.applyForce(force, applyPoint);
		}
	    }
	}
    }

    /**
     * Create dynamic body from a TiledObject, refer the javadoc of method
     * addDynamicObject() to know other parameters
     * 
     * @param data
     *            the physics sprite reference
     */
    public Body addDynamicBoxObject(IPhysicsObject data) {
	return addDynamicObject(data, SHAPE_BOX, data.getRegionWidth(),
		data.getRegionHeight(), Vector2.Zero, null, 1f, 0.5f, 0.2f);
    }

    /**
     * Create dynamic body from a TiledObject, refer the full parameter method
     * javadoc to know other parameters
     * 
     * @param data
     *            the physics sprite reference
     */
    public Body addDynamicBoxObject(IPhysicsObject data, Vector2 force) {
	return addDynamicObject(data, SHAPE_BOX, data.getRegionWidth(), 0,
		force, null, 1f, 0.5f, 0.2f);
    }

    /**
     * Create dynamic body from a TiledObject, refer the full parameter method
     * javadoc to know other parameters
     * 
     * @param data
     *            the physics sprite reference
     */
    public Body addDynamicBoxObject(IPhysicsObject data, int width, int height) {
	return addDynamicObject(data, SHAPE_BOX, width, height, Vector2.Zero,
		null, 1f, 0.1f, 0.2f);
    }

    /**
     * Create dynamic body from a TiledObject, refer the full parameter method
     * javadoc to know other parameters
     * 
     * @param data
     *            the physics sprite reference
     */
    public Body addDynamicBoxObject(IPhysicsObject data, int width, int height,
	    float density, float friction, float restitution) {
	return addDynamicObject(data, SHAPE_BOX, width, height, Vector2.Zero,
		null, density, friction, restitution);
    }

    /**
     * Create dynamic body from a TiledObject, refer the full parameter method
     * javadoc to know other parameters
     * 
     * @param data
     *            the physics sprite reference
     */
    public Body addDynamicCircleObject(IPhysicsObject data) {
	return addDynamicObject(data, SHAPE_CIRCLE, data.getRegionWidth(), 0,
		Vector2.Zero, null, 1f, 0.5f, 0.2f);
    }

    /**
     * Create dynamic body from a TiledObject, refer the full parameter method
     * javadoc to know other parameters
     * 
     * @param data
     *            the physics sprite reference
     */
    public Body addDynamicCircleObject(IPhysicsObject data, Vector2 force) {
	return addDynamicObject(data, SHAPE_CIRCLE, data.getRegionWidth(), 0,
		force, null, 1f, 0.5f, 0.2f);
    }

    /**
     * Create dynamic body from a TiledObject
     * 
     * @param data
     *            the physics sprite reference
     * @param data
     * @param size
     */
    public Body addDynamicCircleObject(IPhysicsObject data, int size) {
	return addDynamicObject(data, SHAPE_CIRCLE, size, 0, Vector2.Zero,
		null, 1f, 0.5f, 0.2f);
    }

    /**
     * Create dynamic body from a TiledObject
     * 
     * @param data
     *            the physics sprite reference
     * @param data
     * @param size
     */
    public Body addDynamicCircleObject(IPhysicsObject data, int size,
	    float density, float friction, float restitution) {
	return addDynamicObject(data, SHAPE_CIRCLE, size, 0, Vector2.Zero,
		null, density, friction, restitution);
    }

    /**
     * Create dynamic body from a TiledObject
     * 
     * @param data
     *            the physics sprite reference
     * @param ShapeType
     *            type of shape, SHAPE_BOX or SHAPE_CIRCLE
     * @param width
     *            width of shape
     * @param height
     *            height of shape
     * @param force
     *            force apply to shape
     * @param point
     *            force apply point
     * @param density
     * @param friction
     * @param restitution
     */
    public Body addDynamicObject(IPhysicsObject data, int ShapeType,
	    float width, float height, Vector2 force, Vector2 point,
	    float density, float friction, float restitution) {

	return addDynamicObject(data, ShapeType, width, height, force, point,
		density, friction, restitution, true);
    }

    /**
     * Create dynamic body from a TiledObject, option for set UserData for body
     * or not
     * 
     * @param data
     *            the physics sprite reference
     * @param ShapeType
     *            type of shape, SHAPE_BOX or SHAPE_CIRCLE
     * @param width
     *            width of shape
     * @param height
     *            height of shape
     * @param force
     *            force apply to shape
     * @param point
     *            force apply point
     * @param density
     * @param friction
     * @param restitution
     * @param setUserData
     *            set UserData for body or not
     * @return
     */
    public Body addDynamicObject(IPhysicsObject data, int ShapeType,
	    float width, float height, Vector2 force, Vector2 point,
	    float density, float friction, float restitution,
	    boolean setUserData) {
	// Dynamic Body
	BodyDef bodyDef = new BodyDef();
	if (box2dDebug)
	    bodyDef.type = BodyType.StaticBody;
	else
	    bodyDef.type = BodyType.DynamicBody;

	float x = data.getX();
	// transform into box2d
	x = x * WORLD_TO_BOX;

	float y = data.getY();
	// transform into box2d
	y = y * WORLD_TO_BOX;

	bodyDef.position.set(x, y);

	Body body = world.createBody(bodyDef);

	Shape shape = null;

	if (ShapeType == SHAPE_BOX) {
	    shape = new PolygonShape();
	    ((PolygonShape) shape).setAsBox(width * WORLD_TO_BOX / 2, height
		    * WORLD_TO_BOX / 2);
	} else if (ShapeType == SHAPE_CIRCLE) {

	    shape = new CircleShape();
	    ((CircleShape) shape).setRadius(width * WORLD_TO_BOX / 2);
	}

	FixtureDef fixtureDef = createFixtureDef(density, friction,
		restitution, shape);
	body.createFixture(fixtureDef);
	if (setUserData) {
	    body.setUserData(data);
	    data.setBody(body);
	}

	// data.setRegion(0, 0, width, height);
	if (force.x != 0 && force.y != 0) {
	    if (point == null)
		body.applyForce(force, null);
	    else
		body.applyForce(force, point);
	}

	if (setUserData)
	    body.setUserData(data);
	physicsObjects.add(data);
	return body;
    }

    /**
     * Create dynamic game object from tile and instance of FixtureDef, refer
     * the javadoc of method addDynamicObject() to know other parameters
     * 
     * @param data
     *            the physics sprite reference
     */
    public Body addDynamicBoxObject(IPhysicsObject data, FixtureDef def) {
	return addDynamicObject(data, SHAPE_BOX, data.getRegionWidth(),
		data.getRegionHeight(), Vector2.Zero, null, def);
    }

    /**
     * Create dynamic game object from tile and instance of FixtureDef, refer
     * the full parameter method javadoc to know other parameters
     * 
     * @param data
     *            the physics sprite reference
     */
    public Body addDynamicBoxObject(IPhysicsObject data, Vector2 force,
	    FixtureDef def) {
	return addDynamicObject(data, SHAPE_BOX, data.getRegionWidth(), 0,
		force, null, def);
    }

    /**
     * Create dynamic game object from tile and instance of FixtureDef, refer
     * the full parameter method javadoc to know other parameters
     * 
     * @param data
     *            the physics sprite reference
     */
    public Body addDynamicBoxObject(IPhysicsObject data, int width, int height,
	    FixtureDef def) {
	return addDynamicObject(data, SHAPE_BOX, width, height, Vector2.Zero,
		null, def);
    }

    /**
     * Create dynamic game object from tile and instance of FixtureDef, refer
     * the full parameter method javadoc to know other parameters
     * 
     * @param data
     *            the physics sprite reference
     */
    public Body addDynamicCircleObject(IPhysicsObject data, FixtureDef def) {
	return addDynamicObject(data, SHAPE_CIRCLE, data.getRegionWidth(), 0,
		Vector2.Zero, null, def);
    }

    /**
     * Create dynamic game object from tile and instance of FixtureDef, refer
     * the full parameter method javadoc to know other parameters
     * 
     * @param data
     *            the physics sprite reference
     */
    public Body addDynamicCircleObject(IPhysicsObject data, Vector2 force,
	    FixtureDef def) {
	return addDynamicObject(data, SHAPE_CIRCLE, data.getRegionWidth(), 0,
		force, null, def);
    }

    /**
     * Create dynamic game object from tile and instance of FixtureDef. refer
     * the full parameter method javadoc to know other parameters
     * 
     * @param data
     *            the physics sprite reference
     * @param data
     * @param size
     */
    public Body addDynamicCircleObject(IPhysicsObject data, int size,
	    FixtureDef def) {
	return addDynamicObject(data, SHAPE_CIRCLE, size, 0, Vector2.Zero,
		null, def);
    }

    /**
     * Create dynamic game object from tile and instance of FixtureDef
     * 
     * @param data
     * @param ShapeType
     * @param width
     * @param height
     * @param force
     * @param point
     * @param fixtureDef
     * @return
     */
    public Body addDynamicObject(IPhysicsObject data, int ShapeType,
	    float width, float height, Vector2 force, Vector2 point,
	    FixtureDef fixtureDef) {

	return addDynamicObject(data, ShapeType, width, height, force, point,
		fixtureDef, true);
    }

    /**
     * Create dynamic game object from tile and instance of FixtureDef and
     * option to set UserData for body
     * 
     * @param data
     * @param ShapeType
     * @param width
     * @param height
     * @param force
     * @param point
     * @param fixtureDef
     * @param setUserData
     * @return
     */
    public Body addDynamicObject(IPhysicsObject data, int ShapeType,
	    float width, float height, Vector2 force, Vector2 point,
	    FixtureDef fixtureDef, boolean setUserData) {
	// Dynamic Body
	BodyDef bodyDef = new BodyDef();
	if (box2dDebug)
	    bodyDef.type = BodyType.StaticBody;
	else
	    bodyDef.type = BodyType.DynamicBody;

	float x = data.getX();
	// transform into box2d
	x = x * WORLD_TO_BOX;

	float y = data.getY();
	// transform into box2d
	y = y * WORLD_TO_BOX;

	bodyDef.position.set(x, y);

	Body body = world.createBody(bodyDef);

	Shape shape = null;

	if (ShapeType == SHAPE_BOX) {
	    shape = new PolygonShape();
	    ((PolygonShape) shape).setAsBox(width * WORLD_TO_BOX / 2, height
		    * WORLD_TO_BOX / 2);
	} else if (ShapeType == SHAPE_CIRCLE) {

	    shape = new CircleShape();
	    ((CircleShape) shape).setRadius(width * WORLD_TO_BOX / 2);
	}

	if (fixtureDef == null)
	    throw new GdxRuntimeException("fixtureDef cannot be null!");
	fixtureDef.shape = shape;
	body.createFixture(fixtureDef);
	if (setUserData) {
	    body.setUserData(data);
	    data.setBody(body);
	}
	// data.setRegion(0, 0, width, height);
	if (force.x != 0 && force.y != 0) {
	    if (point == null)
		body.applyForce(force, null);
	    else
		body.applyForce(force, point);
	}
	if (setUserData)
	    physicsObjects.add(data);

	return body;
    }

    /**
     * Create a fixture, usage of creation of compound body
     * 
     * @param object
     * @param center
     * @param ShapeType
     * @param angle
     * @return
     */
    public FixtureDef addFixtureDef(TiledObject object, Vector2 center,
	    int ShapeType, double angle) {
	FixtureDef fixtureDef = new FixtureDef();
	Shape shape = null;

	float x = object.x + object.width / 2 - center.x;
	// transform into box2d
	x = x * WORLD_TO_BOX;

	float y = Gdx.graphics.getHeight() - object.height / 2 - object.y
		- center.y;
	// transform into box2d
	y = y * WORLD_TO_BOX;

	if (ShapeType == SHAPE_BOX) {
	    shape = new PolygonShape();
	    ((PolygonShape) shape).setAsBox(object.width * WORLD_TO_BOX / 2,
		    object.height * WORLD_TO_BOX / 2, new Vector2(x, y),
		    (float) angle);
	} else if (ShapeType == SHAPE_CIRCLE) {
	    shape = new CircleShape();

	    ((CircleShape) shape).setPosition(new Vector2(x, y));
	    ((CircleShape) shape).setRadius(object.width * WORLD_TO_BOX / 2);
	} else {
	    shape = new PolygonShape();

	    // get the polygon point from map
	    String[] strp = object.polygon.split(" ");
	    Vector2[] apoints = new Vector2[strp.length];

	    // for each points from objects
	    for (int i = 0; i < strp.length; i++) {
		x = Float.parseFloat(strp[i].split(",")[0]) - center.x; // get x
		x = x * WORLD_TO_BOX; // transform into box2d
		y = Gdx.graphics.getHeight()
			- Float.parseFloat(strp[i].split(",")[1]) - center.y; // get
									      // y
		y = y * WORLD_TO_BOX; // transform into box2d

		// insert the point into the array
		apoints[i] = new Vector2(x, y);
	    }

	    // create static body from point array
	    ((PolygonShape) shape).set(apoints);
	}
	fixtureDef.shape = shape;
	return fixtureDef;
    }

    /**
     * Create a fixture, usage of creation of compound body
     * 
     * @return
     */
    public FixtureDef addFixtureDef(TiledObject object, Vector2 center,
	    int ShapeType) {
	return addFixtureDef(object, center, ShapeType, 0f);

    }

    /**
     * Create a fixture, based float density, float friction,float restitution
     * 
     * @return
     */
    public FixtureDef createFixtureDef(float density, float friction,
	    float restitution) {
	FixtureDef output = new FixtureDef();
	output.density = density;
	output.friction = friction;
	output.restitution = restitution;

	return output;
    }

    /**
     * Create a fixture, based float density, float friction,float restitution,
     * shape
     * 
     * @return
     */
    public FixtureDef createFixtureDef(float density, float friction,
	    float restitution, Shape shape) {
	FixtureDef output = new FixtureDef();
	output.density = density;
	output.friction = friction;
	output.restitution = restitution;
	output.shape = shape;
	return output;
    }

    /**
     * Create a fixture, based float density, float friction,float restitution,
     * category bits
     */
    public FixtureDef createFixtureDef(float density, float friction,
	    float restitution, short categoryBits) {
	return createFixtureDef(density, friction, restitution, categoryBits,
		categoryBits);
    }

    /**
     * Create a fixture, based float density, float friction,float restitution,
     * category bits and mask bits
     */
    public FixtureDef createFixtureDef(float density, float friction,
	    float restitution, short categoryBits, short maskBits) {
	FixtureDef output = new FixtureDef();
	output.density = density;
	output.friction = friction;
	output.restitution = restitution;
	output.filter.categoryBits = categoryBits;
	output.filter.maskBits = maskBits;
	return output;
    }

    /**
     * create Revolute Joint
     */
    public RevoluteJoint createRevoluteJoint(Body bodyA, Body bodyB,
	    Vector2 jointPositionA, Vector2 jointPositionB,
	    boolean enabledMotor, int maxMotorTorque, int motorSpeed) {
	RevoluteJointDef rJoint = new RevoluteJointDef();
	rJoint.bodyA = bodyA;
	rJoint.bodyB = bodyB;
	rJoint.localAnchorA.set(jointPositionA.x * WORLD_TO_BOX,
		jointPositionA.y * WORLD_TO_BOX);
	rJoint.localAnchorB.set(jointPositionB.x * WORLD_TO_BOX,
		jointPositionB.y * WORLD_TO_BOX);

	rJoint.enableMotor = enabledMotor;
	rJoint.maxMotorTorque = maxMotorTorque;
	rJoint.motorSpeed = motorSpeed;

	RevoluteJoint joint = (RevoluteJoint) world.createJoint(rJoint);
	return joint;
    }

    public RevoluteJoint createRevoluteJoint(Body bodyA, Body bodyB,
	    Vector2 jointPositionA, Vector2 jointPositionB) {
	RevoluteJointDef rJoint = new RevoluteJointDef();
	rJoint.bodyA = bodyA;
	rJoint.bodyB = bodyB;
	rJoint.localAnchorA.set(jointPositionA.x * WORLD_TO_BOX,
		jointPositionA.y * WORLD_TO_BOX);
	rJoint.localAnchorB.set(jointPositionB.x * WORLD_TO_BOX,
		jointPositionB.y * WORLD_TO_BOX);
	RevoluteJoint joint = (RevoluteJoint) world.createJoint(rJoint);
	return joint;
    }

    public DistanceJoint createDistanceJoint(Body bodyA, Body bodyB,
	    Vector2 localA, Vector2 localB, int length) {
	DistanceJointDef dJoint = new DistanceJointDef();
	dJoint.bodyA = bodyA;
	dJoint.bodyB = bodyB;
	dJoint.localAnchorA.set(localA.x * WORLD_TO_BOX, localA.y
		* WORLD_TO_BOX);
	dJoint.localAnchorB.set(localB.x * WORLD_TO_BOX, localB.y
		* WORLD_TO_BOX);
	dJoint.length = length * WORLD_TO_BOX;
	return (DistanceJoint) world.createJoint(dJoint);
    }

    public DistanceJoint createDistanceJoint(Body bodyA, Body bodyB, int length) {
	return createDistanceJoint(bodyA, bodyB, Vector2.Zero, Vector2.Zero,
		length);
    }

    public Body createTempBodyDistanceJoint(Body bodyA, Body bodyB,
	    Vector2 localA, Vector2 localB, int length) {
	DistanceJointDef dJoint = new DistanceJointDef();
	FixtureDef fixtureDef = createFixtureDef(1f, 0.5f, 0.2f, (short) 0x0004);
	IPhysicsObject sprite = (IPhysicsObject) bodyA.getUserData();
	Body temp = createTempBody(sprite.getX(), sprite.getY(), fixtureDef);
	createRevoluteJoint(bodyA, temp, localA, Vector2.Zero);
	dJoint.bodyA = temp;
	dJoint.bodyB = bodyB;
	dJoint.localAnchorA.set(localA.x * WORLD_TO_BOX, localA.y
		* WORLD_TO_BOX);
	dJoint.localAnchorB.set(localB.x * WORLD_TO_BOX, localB.y
		* WORLD_TO_BOX);
	dJoint.length = length * WORLD_TO_BOX;
	world.createJoint(dJoint);
	return temp;
    }

    public Body createTempBody(float x, float y, FixtureDef fixtureDef) {
	// Dynamic Body
	BodyDef bodyDef = new BodyDef();
	if (box2dDebug)
	    bodyDef.type = BodyType.StaticBody;
	else
	    bodyDef.type = BodyType.DynamicBody;

	// transform into box2d
	x = x * WORLD_TO_BOX;
	y = y * WORLD_TO_BOX;

	bodyDef.position.set(x, y);

	Body body = world.createBody(bodyDef);

	Shape shape = new CircleShape();
	((CircleShape) shape).setRadius(1 * WORLD_TO_BOX);

	if (fixtureDef == null)
	    throw new GdxRuntimeException("fixtureDef cannot be null!");
	fixtureDef.shape = shape;
	body.createFixture(fixtureDef);
	return body;
    }

    @Override
    public void render(float gameTime) {
	for (IPhysicsObject obj : physicsObjects) {
	    obj.render(gameTime);
	}
    }

    /**
     * Get the world of physics manager
     * 
     * @return
     */
    public World getWorld() {
	// TODO Auto-generated method stub
	return world;
    }
}
