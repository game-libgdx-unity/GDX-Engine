package com.gdxengine.framework.box2d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gdxengine.framework.interfaces.IDrawable;
import com.gdxengine.framework.interfaces.IGameService;

public class Rope extends Group implements InputProcessor, IDrawable {
	
	static float PTM_RATIO = 100;
	static final float BOX_STEP = 1 / 45f;
	static final int BOX_VELOCITY_ITERATIONS = 8; //6;
	static final int BOX_POSITION_ITERATIONS = 1; //2;	

	public World world;
	public Body groundBody;
	public MouseJoint _mouseJoint;
	public IGameService services;
	
    //AtlasRegion ropeRegion;
    List<VRope> ropes;
    List<Body> bodies;
	private Vector2 prev;
	
	private Image candy;

	public Rope(World world,Body groundBody, IGameService services)
	{
		this.ropes = new ArrayList<VRope>();
		this.bodies = new ArrayList<Body>();

		this.services = services;
		this.world = world;
		this.world.setContinuousPhysics(true);
		this.groundBody = groundBody;
	}
	public Body createBodyAt(Vector2 pt, AtlasRegion frame)
	{
	    // Get the sprite from the sprite sheet
	    candy = new Image(frame);
	    candy.setOrigin(candy.getWidth()/2, candy.getHeight()/2);
	    this.addActor(candy);
		
	    // Defines the body of your candy
	    BodyDef bodyDef = new BodyDef();
	    bodyDef.type = BodyType.DynamicBody;
	    bodyDef.position.set(new Vector2(pt.x/PTM_RATIO, pt.y/PTM_RATIO));
	    bodyDef.linearDamping = 0.3f;
	    Body body = world.createBody(bodyDef);
	    body.setUserData(candy);
	    
	    // Define the fixture as a polygon
	    FixtureDef fixtureDef = new FixtureDef();
	    PolygonShape spriteShape = new PolygonShape();

	    Vector2[] verts = {
	        new Vector2((-candy.getWidth()/2) / PTM_RATIO,
	               (-candy.getHeight()/2) / PTM_RATIO),
	        
	        new Vector2((candy.getWidth()/2) / PTM_RATIO,
	               (-candy.getHeight()/2) / PTM_RATIO),
	        
	        new Vector2((candy.getWidth()/2) / PTM_RATIO,
	               (candy.getHeight()/2) / PTM_RATIO),

	        new Vector2((-candy.getWidth()/2) / PTM_RATIO,
	               (candy.getHeight()/2) / PTM_RATIO),
	    };
	    
	    spriteShape.set(verts);
	    fixtureDef.shape = spriteShape;
	    fixtureDef.density = 30.0f;
	    fixtureDef.friction = 0.2f;
	    fixtureDef.restitution = 0.9f;
	    fixtureDef.filter.categoryBits = 0x01;
	    fixtureDef.filter.maskBits = 0x01;
	    body.createFixture(fixtureDef);
	    
	    bodies.add(body);
	    
	    return body;
	}

	private void createRope(Body bodyA, Vector2 anchorA, Body bodyB, Vector2 anchorB, float sag, AtlasRegion ropeRegion)
	{
	    RopeJointDef jd = new RopeJointDef();
	    jd.bodyA = bodyA;
	    jd.bodyB = bodyB;
	    jd.localAnchorA.set(anchorA.x, anchorA.y);
	    jd.localAnchorB.set(anchorB.x, anchorB.y);	    
	    // Max length of joint = current distance between bodies * sag
	    float ropeLength =  bodyA.getWorldPoint(anchorA).sub(bodyB.getWorldPoint(anchorB)).len() * sag;
	    jd.maxLength = ropeLength;
	    
	    // Create joint
	    RopeJoint joint = (RopeJoint)world.createJoint(jd);
	    GdxRopeJoint ropeJoint = new GdxRopeJoint(joint, jd.localAnchorA, jd.localAnchorB);
	    
	    VRope newRope = new VRope(ropeJoint, this, ropeRegion);
	    
	    ropes.add(newRope);
	}

	public void createBodyAndRopes(Vector2[] pts, AtlasRegion Bodyframe, Vector2 pt, AtlasRegion RopeFrame)
	{
	    Body body1 = createBodyAt(pt, Bodyframe);
	    
	    // Add a bunch of ropes
	    for (int i = 0; i < pts.length; i++)
	    {
	    	createRope(groundBody, new Vector2(pts[i].x/PTM_RATIO, pts[i].y/PTM_RATIO),
	                        body1, body1.getLocalCenter(), 1.0f, RopeFrame);
	    }
	    
	    // Advance the world by a few seconds to stabilize everything.
	    int n = 10 * 60;
	    int velocityIterations = 8;
	    int positionIterations = 1;
	    float dt = 1.0f / 60.0f;
	    while (n-- >0)
	    {
	        // Instruct the world to perform a single step of simulation.
	        world.step(dt, velocityIterations, positionIterations);
	        for (VRope rope : ropes)
	        {
	            rope.update(dt);
	        }
	    }
	 
	    // This last update takes care of the texture repositioning.
	    this.update(dt);
	}

	public boolean checkLineIntersection(Vector2 p1, Vector2 p2, Vector2 p3, Vector2 p4)
	{
	    // http://local.wasp.uwa.edu.au/~pbourke/geometry/lineline2d/
	    float denominator;
		try {
			denominator = (p4.y - p3.y) * (p2.x - p1.x) - (p4.x - p3.x) * (p2.y - p1.y);
		} catch (Exception e) {
			return false;
		}
	    
	    // In this case the lines are parallel so you assume they don't intersect
	    if (denominator == 0.0f)
	        return false;
	    float ua = ((p4.x - p3.x) * (p1.y - p3.y) - (p4.y - p3.y) * (p1.x - p3.x)) / denominator;
	    float ub = ((p2.x - p1.x) * (p1.y - p3.y) - (p2.y - p1.y) * (p1.x - p3.x)) / denominator;
	    
	    if (ua >= 0.0 && ua <= 1.0 && ub >= 0.0 && ub <= 1.0)
	    {
	        return true;
	    }
	    
	    return false;
	}

	private Body createRopeTipBody()
	{
	    BodyDef bodyDef = new BodyDef();
	    bodyDef.type = BodyType.DynamicBody;
	    bodyDef.linearDamping = 0.5f;
	    Body body = world.createBody(bodyDef);
	    
	    FixtureDef circleDef = new FixtureDef();
	    CircleShape circle = new CircleShape();
	    circle.setRadius(1.0f/PTM_RATIO);
	    circleDef.shape = circle;
	    circleDef.density = 10.0f;
	    
	    // Since these tips don't have to collide with anything
	    // set the mask bits to zero
	    circleDef.filter.maskBits = 0x01; //0;
	    body.createFixture(circleDef);
	    
	    return body;
	}

	public void cutTheRobe(Vector2 pt0, Vector2 pt1)
	{
	    for (VRope rope : ropes)
	    {
	        for (VStick stick : rope.vSticks)
	        {
	        	Vector2 pa = stick.getPointA().point();
	            Vector2 pb = stick.getPointB().point();
	            if (checkLineIntersection(pt0, pt1, pa, pb))
	            {
	                Body newBodyA = createRopeTipBody();
	                Body newBodyB = createRopeTipBody();
	                
	                VRope newRope = rope.cutRopeInStick(stick, newBodyA, newBodyB);
	                ropes.add(newRope);
	                
	                return;
	            }
	        }
	    }
	}

	public void touchesBegan(Vector2 location) {
	    
	    if (_mouseJoint != null) return;
	    
	    Vector2 locationWorld = new Vector2(location.x/PTM_RATIO, location.y/PTM_RATIO);
	    
     	Iterator<Body> bi = world.getBodies();
	    while (bi.hasNext())
	    {
	    	Body b = bi.next();
	    	
	    	if(b == null)
	    		continue;
	    	
	    	if(!(b.getUserData() instanceof Image))
	    		continue;
	    	
	        ArrayList<Fixture> af = b.getFixtureList();
	        Image myActor = (Image)b.getUserData();
	        if (myActor != null)
	        {
	        	Fixture f = af.get(0);
	        	if (f.testPoint(locationWorld)) {
	                MouseJointDef md = new MouseJointDef();
	                md.bodyA = groundBody;
	                md.bodyB = b;
	                md.target.set(locationWorld.x, locationWorld.y);
	                md.collideConnected = true;
	                md.maxForce = 10000.0f * b.getMass();
	                
	                _mouseJoint = (MouseJoint)world.createJoint(md);
	                b.setAwake(true);
	                return;
	            }
	        }
	    }
	}

	public void TouchesMoved(Vector2 location, Vector2 prev) {
	    
	    if (_mouseJoint == null)
	    {
	        cutTheRobe(prev, location);
	    }
	    else
	    {
	        Vector2 locationWorld = new Vector2(location.x/PTM_RATIO, location.y/PTM_RATIO);
	        _mouseJoint.setTarget(locationWorld);
	    }
	}

	public void TouchesCancelled() {
	    
	    if (_mouseJoint != null) {
	        world.destroyJoint(_mouseJoint);
	        _mouseJoint = null;
	    }
	    
	}

	public void TouchesEnded() {
	    if (_mouseJoint != null) {
	        world.destroyJoint(_mouseJoint);
	        _mouseJoint = null;
	    }
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		y = Gdx.graphics.getHeight() - y;
		touchesBegan(new Vector2(x, y));
		prev = new Vector2(x, y);
		
		return false;
	}
	
	@Override
	public boolean touchDragged(int x, int y, int pointer) {

		y = Gdx.graphics.getHeight() - y;
		Vector2 location = new Vector2(x, y);
		TouchesMoved(location, prev);

		return false;
	}
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {

		TouchesEnded();
		prev = null;
		
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IGameService getGameService() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(float gameTime) {
		// Update all the ropes
	    for (VRope rope : ropes)
	    {
	    	rope.update(gameTime);
	    	rope.updateSprites();
	    }
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setEnabled(boolean enable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setDead(boolean value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float gameTime) {
		SpriteBatch sp = services.getSpriteBatch();
		draw(sp, 1f);
	}
	public Image getCandy() {
		return candy;
	}
	public void setCandy(Image candy) {
		this.candy = candy;
	}

}
