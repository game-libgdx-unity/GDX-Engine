package com.gdxengine.framework.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.RopeJoint;

public class GdxRopeJoint {

	public RopeJoint joint;
	public final Vector2 localAnchorA;
	public final Vector2 localAnchorB;
	
	public GdxRopeJoint(RopeJoint joint, Vector2 localAnchorA , Vector2 localAnchorB) {
		
		this.joint = joint;
		this.localAnchorA = localAnchorA;
		this.localAnchorB = localAnchorB;
		
	}

	
	
}
