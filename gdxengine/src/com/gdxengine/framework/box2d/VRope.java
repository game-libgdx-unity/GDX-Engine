package com.gdxengine.framework.box2d;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class VRope {

	static float PTM_RATIO = 100;
	
	int numPoints;
	List<VPoint> vPoints;
	public List<VStick> vSticks;
	List<Image> ropeSprites;
	
	//CCSpriteBatchNode* spriteSheet;
	Group group;
	AtlasRegion region;
	
	float antiSagHack;
	GdxRopeJoint joint;
	
	public VRope(GdxRopeJoint aJoint, Group group, AtlasRegion region ) { //,  spriteSheet:(CCSpriteBatchNode*)spriteSheetArg {
		
		joint = aJoint;
		
		float xA = joint.joint.getAnchorA().x;
		float yA = joint.joint.getAnchorA().y;
		
		float xB = joint.joint.getAnchorB().x;
		float yB = joint.joint.getAnchorB().y;
		
		Vector2 pointA = new Vector2(xA*PTM_RATIO, yA*PTM_RATIO);
		Vector2 pointB = new Vector2(xB*PTM_RATIO, yB*PTM_RATIO);

		//	    spriteSheet = spriteSheetArg;
		this.group = group;
		this.region = region;
		
		createRope(pointA, pointB, joint.joint.getMaxLength()*PTM_RATIO);
	}

	public void reset() {
		Vector2 pointA = new Vector2(joint.joint.getAnchorA().x*PTM_RATIO, joint.joint.getAnchorA().y*PTM_RATIO);
		Vector2 pointB = new Vector2(joint.joint.getAnchorB().x*PTM_RATIO, joint.joint.getAnchorB().y*PTM_RATIO);
		
	   resetWithPoints(pointA, pointB);
	}

	public VRope cutRopeInStick(VStick stick, Body newBodyA, Body newBodyB) {
	    
	    // 1-First, find out where in your array the rope will be cut
	    int nPoint = vSticks.indexOf(stick);
		
	    // Instead of making everything again you'll just use the arrays of
	    // sticks, points and sprites you already have and split them
	    
	    // 2-This is the range that defines the new rope
	    //NSRange newRopeRange = (NSRange){nPoint, numPoints-nPoint-1};
	    
	    // 3-Keep the sticks in a new array
	    //NSArray *newRopeSticks = [vSticks subarrayWithRange:newRopeRange];
	    
	    List<VStick>  newRopeSticks = new ArrayList<VStick>();
	    int range = numPoints-nPoint-1;
	    //for (int i = nPoint; i < nPoint + range; i++)
	    for (int i = nPoint + range - 1; i >= nPoint; i--)	
	    {
	    	newRopeSticks.add(0, vSticks.get(i));
	    	vSticks.remove(i);
	    }
	    
	    // 4-and remove from this object's array
	    //[vSticks removeObjectsInRange:newRopeRange];
	    //vSticks = vSticks.subList(0, nPoint - 1);
	    
	    // 5-Same for the sprites
	    //NSArray *newRopeSprites = [ropeSprites subarrayWithRange:newRopeRange];
	    List<Image>  newRopeSprites = new ArrayList<Image>();
	    //for (int i = nPoint; i < nPoint + range; i++)
	    for (int i = nPoint + range - 1; i >= nPoint; i--)
	    {
	    	newRopeSprites.add(0, ropeSprites.get(i));
	    	ropeSprites.remove(i);
	    }
	    //[ropeSprites removeObjectsInRange:newRopeRange];
	    //ropeSprites = ropeSprites.subList(0, nPoint -1);
	    
	    // 6-Number of points is always the number of sticks + 1
	    //newRopeRange.length += 1;
	    range += 1;
	    //NSArray *newRopePoints = [vPoints subarrayWithRange:newRopeRange];
	    List<VPoint> newRopePoints = new ArrayList<VPoint>();
	    //for (int i = nPoint; i < vPoints.size(); i++)
	    for (int i = nPoint + range -1; i >= nPoint; i--)
	    {
	    	newRopePoints.add(0, vPoints.get(i));
	    	vPoints.remove(i);
	    }
	    //[vPoints removeObjectsInRange:newRopeRange];
	    //vPoints = vPoints.subList(0, nPoint-1);
	    
	    // 7-The removeObjectsInRange above removed the last point of
	    // this rope that now belongs to the new rope. You need to clone
	    // that VPoint and add it to this rope, otherwise you'll have a
	    // wrong number of points in this rope
	    VPoint pointOfBreak = newRopePoints.get(0);
	    VPoint newPoint = new VPoint(); // [[VPoint alloc] init];
	    newPoint.setPos(pointOfBreak.x, pointOfBreak.y);
	    vPoints.add(newPoint);
	    
	    // 7-And last: fix the last VStick of this rope to point to this new point
	    // instead of the old point that now belongs to the new rope
	    if (vSticks.size() > 0)
	    {
	    	VStick lastStick = vSticks.get(vSticks.size() - 1);
	    	lastStick.setPointB(newPoint);
	    }
	    
	    // 8-This will determine how long the rope is now and how long the new rope will be
	    float cutRatio = (float)nPoint / (numPoints - 1);
	    
	    // 9-Fix my number of points
	    numPoints = nPoint + 1;
	    
	    // Position in Box2d world where the new bodies will initially be
	    Vector2 newBodiesPosition = new Vector2(pointOfBreak.x / PTM_RATIO, pointOfBreak.y / PTM_RATIO);
	    
	    // Get a reference to the world to create the new joint
	    World world = newBodyA.getWorld();
	    
	    // 10-Re-create the joint used in this VRope since bRopeJoint does not allow
	    // to re-define the attached bodies
	    RopeJointDef jd = new RopeJointDef();
	    jd.bodyA = joint.joint.getBodyA();
	    jd.bodyB = newBodyB;
	    
	    Vector2 anchorA = joint.joint.getAnchorA(); //.localAnchorA;
	    jd.localAnchorA.set(anchorA.x, anchorA.y);
	    jd.localAnchorB.set(0, 0);
	    
	    jd.maxLength = joint.joint.getMaxLength() * cutRatio;
	    newBodyB.setTransform(newBodiesPosition, 0.0f);
	    
	    RopeJoint joint1 = (RopeJoint)world.createJoint(jd); //create joint
	    GdxRopeJoint newJoint1 = new GdxRopeJoint(joint1, jd.localAnchorA, jd.localAnchorB);
	    
	    // 11-Create the new rope joint
	    jd.bodyA = newBodyA;
	    jd.bodyB = joint.joint.getBodyB();
	    jd.localAnchorA.set(0, 0);
	    Vector2 anchorB = joint.localAnchorB; //.getAnchorB();
	    jd.localAnchorB.set(anchorB.x, anchorB.y);
	    
	    jd.maxLength = joint.joint.getMaxLength() * (1 - cutRatio);
	    newBodyA.setTransform(newBodiesPosition, 0.0f);
	    
	    RopeJoint joint2 = (RopeJoint)world.createJoint(jd); //create joint
	    GdxRopeJoint newJoint2 = new GdxRopeJoint(joint2, jd.localAnchorA, jd.localAnchorB);
	    
	    
	    // 12-Destroy the old joint and update to the new one
	    world.destroyJoint(joint.joint);
	    joint = newJoint1;
	    
	    // 13-Finally, create the new VRope
	    VRope newRope = new VRope(newJoint2,
	    							group, region, 
	                              //spriteSheet:spriteSheet
	                               newRopePoints,
	                               newRopeSticks,
	                               newRopeSprites);
	    return newRope;
	}

	public VRope(GdxRopeJoint aJoint,
				Group group, AtlasRegion region,
	           //(CCSpriteBatchNode*)spriteSheetArg
	                List<VPoint> points,
	                List<VStick> sticks,
	                List<Image> sprites) {
		
	        joint = aJoint;
//	        spriteSheet = spriteSheetArg;
	        this.group = group;
	        this.region = region;
	        
	        vPoints = points;
	        vSticks = sticks;
	        ropeSprites = sprites;
	        numPoints = vPoints.size();
	}
	                

	public void update(float dt) {
		
		float xA = joint.joint.getAnchorA().x;
		float yA = joint.joint.getAnchorA().y;
		
		float xB = joint.joint.getAnchorB().x;
		float yB = joint.joint.getAnchorB().y;
		
	    Vector2 pointA = new Vector2(xA*PTM_RATIO,yA*PTM_RATIO);
	    Vector2 pointB = new Vector2(xB*PTM_RATIO,yB*PTM_RATIO);
	    updateWithPoints(pointA , pointB, dt);
	}

	public VRope (Vector2 pointA, Vector2 pointB, Group group, AtlasRegion region) { //spriteSheet:(CCSpriteBatchNode*)spriteSheetArg {
//			spriteSheet = spriteSheetArg;
			this.group = group;
			this.region = region;
			createRope(pointA, pointB,  pointA.dst(pointB));
	}

	private void createRope(Vector2 pointA, Vector2 pointB, float distance) {
		vPoints = new ArrayList<VPoint>();
		vSticks = new ArrayList<VStick>();
		ropeSprites = new ArrayList<Image>();
	    int segmentFactor = 12; //increase value to have less segments per rope, decrease to have more segments
		numPoints = (int)distance/segmentFactor;
		
		Vector2 p = new Vector2(pointB.x, pointB.y);
		Vector2 diffVector = p.sub(pointA); //ccpSub(pointB,pointA);
		
		
		float multiplier = distance / (numPoints-1);
		antiSagHack = 0.1f; //HACK: scale down rope points to cheat sag. set to 0 to disable, max suggested value 0.1
		for(int i=0;i<numPoints;i++) {
			
			Vector2 v = new Vector2(diffVector.x, diffVector.y);
			v.nor();
			
			p = new Vector2(pointA.x, pointB.y);
			
			float scalar = multiplier*i*(1-antiSagHack);
			v.mul(scalar);
			
			Vector2 tmpVector = p.add( v );
			//CGPOINT tmpVector = ccpAdd(pointA, ccpMult(ccpNormalize(diffVector),multiplier*(1-antiSagHack)));
			
			
			VPoint tmpPoint = new VPoint();
			tmpPoint.setPos(tmpVector.x, tmpVector.y);
			vPoints.add(tmpPoint);
		}
		for(int i=0;i<numPoints-1;i++) {
			VStick tmpStick = new VStick(vPoints.get(i), vPoints.get(i+1));
			vSticks.add(tmpStick);
		}

		if(group!=null) {
			for(int i=0;i<numPoints-1;i++) {
				VPoint point1 = vSticks.get(i).getPointA();
				VPoint point2 = vSticks.get(i).getPointB();
				Vector2 stickVector = new Vector2(point1.x,point1.y).sub(new Vector2(point2.x,point2.y));
				float stickAngle = stickVector.angle();
	            
				Image tmpSprite = new Image(region);
				tmpSprite.setWidth(multiplier);
				tmpSprite.setOrigin(tmpSprite.getWidth()/2, tmpSprite.getHeight()/2);
//	            CCSprite *tmpSprite = [CCSprite spriteWithTexture:spriteSheet.texture
//	                                    rect:CGRectMake(0,0,
//	                                    multiplier,
//	                                    [[[spriteSheet textureAtlas] texture] pixelsHigh]/CC_CONTENT_SCALE_FACTOR())];
	            
//	            ccTexParams params = {GL_LINEAR,GL_LINEAR,GL_REPEAT,GL_REPEAT};
//				[tmpSprite.texture setTexParameters:&params];
				
				float x1 = Math.min(point1.x, point2.x);
				float x2 = Math.max(point1.x, point2.x);
				float y1 = Math.min(point1.y, point2.y);
				float y2 = Math.max(point1.y, point2.y);
				float x = (x2 - x1)/2 + x1;
				float y = (y2 - y1)/2 + y1;
				
				tmpSprite.setPosition(x - tmpSprite.getWidth()/2, y - tmpSprite.getHeight()/2);
				//tmpSprite.setPosition:ccpMidpoint(ccp(point1.x,point1.y),ccp(point2.x,point2.y))];
				
				tmpSprite.setRotation(stickAngle); //(1 * MathUtils.radiansToDegrees * stickAngle);
//				[spriteSheet addChild:tmpSprite];
				group.addActor(tmpSprite);
				ropeSprites.add(tmpSprite);
			}
		}
	}

	public void resetWithPoints(Vector2 pointA, Vector2 pointB) {
		float distance = pointA.dst(pointB);
		Vector2 diffVector = pointA.sub(pointB);
		float multiplier = distance / (numPoints - 1);
		for(int i=0;i<numPoints;i++) {
			Vector2 tmpVector = pointA.add(diffVector.nor().mul(multiplier*i*(1-antiSagHack)));
			VPoint tmpPoint = vPoints.get(i);
			tmpPoint.setPos(tmpVector.x, tmpVector.y);
			
		}
	}

	public void removeSprites() {
		//for(int i=0;i<numPoints-1;i++) {
		//	Image tmpSprite = ropeSprites.get(i);
//			[spriteSheet removeChild:tmpSprite cleanup:YES];
		//}
		group.clear();
		ropeSprites.clear();
	}

	public void updateWithPoints(Vector2 pointA, Vector2 pointB, float dt) {
		//manually set position for first and last point of rope
		vPoints.get(0).setPos(pointA.x, pointA.y);
		vPoints.get(numPoints-1).setPos(pointB.x, pointB.y);
		
		//update points, apply gravity
		for(int i=1;i<numPoints-1;i++) {
			vPoints.get(i).applyGravity(dt);
			vPoints.get(i).update();
		}
		
		//contract sticks
		int iterations = 4;
		for(int j=0;j<iterations;j++) {
			for(int i=0;i<numPoints-1;i++) {
				vSticks.get(i).contract();
			}
		}
	}

//	Array<Image> removeImages = new Array<Image>();
	public void updateSprites() {
		
//		if(removeImages.size > 0)
//		{
//			for (Image image : removeImages) {
//				ropeSprites.remove(image);
//			}
//			
//			removeImages.clear();
//		}
//		
		if(group!=null) {
			for(int i=0;i<numPoints-1;i++) {
				VPoint point1 = vSticks.get(i).getPointA();
				VPoint point2 = vSticks.get(i).getPointB();
				Vector2 point1_ = new Vector2(point1.x,point1.y);
				Vector2 point2_ = new Vector2(point2.x,point2.y);
				
				Vector2 p = new Vector2(point1_.x, point1_.y);
				Vector2 sp = p.sub(point2_);
				float stickAngle = sp.angle(); //ccpToAngle(ccpSub(point1_,point2_));

//				Image tmpSprite = null;
//				try {
				Image tmpSprite = ropeSprites.get(i);

					float x1 = Math.min(point1_.x, point2_.x);
					float x2 = Math.max(point1_.x, point2_.x);
					float y1 = Math.min(point1_.y, point2_.y);
					float y2 = Math.max(point1_.y, point2_.y);
					float x = (x2 - x1)/2 + x1;
					float y = (y2 - y1)/2 + y1;
					tmpSprite.setPosition(x - tmpSprite.getWidth()/2, y - tmpSprite.getHeight()/2);
					
					tmpSprite.setRotation(stickAngle); //( -RadianToDegree(stickAngle));
//				} catch (Exception e) {
//					continue;
//					//e.printStackTrace();
//				}
				
//				if(Math.abs(point1.x - point1.oldx) > 20){
//					removeImages.add(tmpSprite);
//				}
			}
		}	
	}

//	//-(void)debugDraw {
////		//Depending on scenario, you might need to have different Disable/Enable of Client States
////		//glDisableClientState(GL_TEXTURE_2D);
////		//glDisableClientState(GL_TEXTURE_COORD_ARRAY);
////		//glDisableClientState(GL_COLOR_ARRAY);
////		//set color and line width for ccDrawLine
////		glColor4f(0.0f,0.0f,1.0f,1.0f);
////		glLineWidth(5.0f);
////		for(int i=0;i<numPoints-1;i++) {
////			//"debug" draw
////			VPoint *pointA = [[vSticks objectAtIndex:i] getPointA];
////			VPoint *pointB = [[vSticks objectAtIndex:i] getPointB];
////			ccDrawPoint(ccp(pointA.x,pointA.y));
////			ccDrawPoint(ccp(pointB.x,pointB.y));
////			//ccDrawLine(ccp(pointA.x,pointA.y),ccp(pointB.x,pointB.y));
////		}
////		//restore to white and default thickness
////		glColor4f(1.0f,1.0f,1.0f,1.0f);
////		glLineWidth(1);
////		//glEnableClientState(GL_TEXTURE_2D);
////		//glEnableClientState(GL_TEXTURE_COORD_ARRAY);
////		//glEnableClientState(GL_COLOR_ARRAY);
//	//}
//
//	-(void)dealloc {
//		for(int i=0;i<numPoints;i++) {
//			[[vPoints objectAtIndex:i] release];
//			if(i!=numPoints-1)
//				[[vSticks objectAtIndex:i] release];
//		}
//		[vPoints removeAllObjects];
//		[vSticks removeAllObjects];
//		[vPoints release];
//		[vSticks release];
//		[super dealloc];
//	}

	

}
