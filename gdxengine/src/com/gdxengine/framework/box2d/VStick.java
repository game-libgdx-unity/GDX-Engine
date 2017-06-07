package com.gdxengine.framework.box2d;

import com.badlogic.gdx.math.Vector2;

public class VStick {

	VPoint pointA;
	VPoint pointB;
	float hypotenuse;

	public VStick(VPoint argA, VPoint argB) {
			this.pointA = argA;
			this.pointB = argB;
			
			Vector2 v1 = new Vector2(pointA.x, pointA.y);
			Vector2 v2 = new Vector2(pointB.x, pointB.y);
			hypotenuse = v1.dst(v2);
			
	}

	public void contract() {
		float dx = pointB.x - pointA.x;
		float dy = pointB.y - pointA.y;
		
		float h = new Vector2(pointA.x, pointA.y).dst(new Vector2(pointB.x, pointB.y));
		
		float diff = hypotenuse - h;
		float offx = (diff * dx / h) * 0.5f;
		float offy = (diff * dy / h) * 0.5f;
		pointA.x-=offx;
		pointA.y-=offy;
		pointB.x+=offx;
		pointB.y+=offy;
	}
	
	public VPoint getPointA() {
		return pointA;
	}
	
	public VPoint getPointB() {
		return pointB;
	}

	public void setPointB(VPoint point) {
	    pointB = point;
	}
	
}
