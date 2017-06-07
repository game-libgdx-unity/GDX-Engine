package com.gdxengine.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

/**
 * The class will make you code game easily! The utility methods used to
 * manipulate Vector3, Matrix4, Mathematics or do some stuffs from Game input,
 * check collision, etc ...
 * 
 * @author Vinh
 * 
 */
public class Utils {
    /**
     * Minus v1 and v2, v1 and v2 will not be changed.
     * 
     * @param v1
     * @param v2
     * @return
     */
    public static Vector3 minusVector(Vector3 v1, Vector3 v2) {
	return new Vector3(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    }

    /**
     * Add v1 and v2, v1 and v2 will not be changed.
     * 
     * @param v1
     * @param v2
     * @return
     */
    public static Vector3 addVector(Vector3 v1, Vector3 v2) {
	return new Vector3(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }

    /**
     * Multiply the mx1 and mx2, mx1 and mx2 will not be changed
     * 
     * @param m1
     * @param m2
     * @return
     */
    public static Matrix4 mulMatrix(Matrix4 m1, Matrix4 m2) {
	Matrix4 mx1 = new Matrix4(m1.val);
	Matrix4 mx2 = new Matrix4(m2.val);

	return mx1.mul(mx2);
    }

    /**
     * Create new Matrix that rotate around three axis-x, axis-y, axis-z in
     * value of x, y, z parameters from the existing Matrix: matrixToRotate
     * matrixToRotate will not be changed. The value of parameter is in degree
     * 
     * @param x
     * @param y
     * @param z
     * @param matrixToRotate
     * @return
     */
    public static Matrix4 createRotate(float x, float y, float z,
	    Matrix4 matrixToRotate) {
	Matrix4 mx1 = matrixToRotate.rotate(1, 0, 0, x);
	Matrix4 mx2 = matrixToRotate.rotate(0, 1, 0, y);
	Matrix4 mx3 = matrixToRotate.rotate(0, 0, 1, z);
	Matrix4 matrix = new Matrix4(mulMatrix(mx3, mx2));
	Matrix4 finalMatrix = new Matrix4(mulMatrix(matrix, mx1));

	return finalMatrix;
    }

    /**
     * Create completely new Matrix that rotate around axis-x, value of input
     * parameter is in Degree
     * 
     * @param angle
     *            Angle to rotate
     * @return
     */
    public static Matrix4 createRotateX(float angle) {
	Matrix4 mx = new Matrix4();
	return mx.rotate(1, 0, 0, angle);
    }

    /**
     * Create completely new Matrix that rotate around axis-y, value of input
     * parameter is in Degree
     * 
     * @param angle
     *            Angle to rotate
     * @return
     */
    public static Matrix4 createRotateY(float angle) {
	Matrix4 mx = new Matrix4();
	return mx.rotate(0, 1, 0, angle);
    }

    /**
     * Create completely new Matrix that rotate around axis-z, value of input
     * parameter is in Degree
     * 
     * @param angle
     *            Angle to rotate
     * @return
     */
    public static Matrix4 createRotateZ(float angle) {
	Matrix4 mx = new Matrix4();
	return mx.rotate(0, 0, 1, angle);
    }

    /**
     * Transform a Vector3 using a Transformation Matrix
     * 
     * @param vector
     *            Vector to transform
     * @param transform
     *            The Transformation matrix
     * @return
     */
    public static Vector3 Transform(Vector3 vector, Matrix4 transform) {
	Vector3 result = new Vector3((vector.x * transform.val[Matrix4.M00])
		+ (vector.y * transform.val[Matrix4.M10])
		+ (vector.z * transform.val[Matrix4.M20])
		+ transform.val[Matrix4.M30],
		(vector.x * transform.val[Matrix4.M01])
			+ (vector.y * transform.val[Matrix4.M11])
			+ (vector.z * transform.val[Matrix4.M21])
			+ transform.val[Matrix4.M31],
		(vector.x * transform.val[Matrix4.M02])
			+ (vector.y * transform.val[Matrix4.M12])
			+ (vector.z * transform.val[Matrix4.M22])
			+ transform.val[Matrix4.M32]);

	return result;
    }

    public static boolean pointInRectangle(Rectangle r, Vector3 tmp) {
	return r.x <= tmp.x && r.x + r.width >= tmp.x && r.y <= tmp.y
		&& r.y + r.height >= tmp.y;
    }
    public static boolean pointInRectangle(Rectangle r, Vector2 tmp) {
	return r.x <= tmp.x && r.x + r.width >= tmp.x && r.y <= tmp.y
		&& r.y + r.height >= tmp.y;
    }

    public static boolean touchInRectangle(Rectangle r) {
	if (Gdx.input.isTouched()) {
	    Vector3 touchPos = new Vector3();
	    touchPos.set(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY(), 0);
	    return r.x <= touchPos.x && r.x + r.width >= touchPos.x
		    && r.y <= touchPos.y && r.y + r.height >= touchPos.y;
	}
	return false;
    }
    public static boolean touchInRectangle(Rectangle r, OrthographicCamera camera) {
	if (Gdx.input.isTouched()) {
	    Vector3 touchPos = new Vector3();
	    touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
	    camera.unproject(touchPos);
	    return r.x <= touchPos.x && r.x + r.width >= touchPos.x
		    && r.y <= touchPos.y && r.y + r.height >= touchPos.y;
	}
	return false;
    }

    /**
     * Check the point has location-x and location-y is in the Rectangle
     * 
     * @param r
     *            the Rectangle
     * @param x
     *            the location-x
     * @param y
     *            the location-y
     * @return
     */
    public static boolean pointInRectangle(Rectangle r, float x, float y) {
	return r.x <= x && r.x + r.width >= x && r.y <= y
		&& r.y + r.height >= y;
    }

    /**
     * Check the key is pressed or not
     * 
     * @param key
     * @return
     */
    public static boolean isKeyDown(int key) {
	return Gdx.input.isKeyPressed(key);
    }

    /**
     * Calculate the distance of two position
     * 
     * @param position1
     * @param position2
     * @return
     */
    public static float distanceOfVector3(Vector3 position1, Vector3 position2) {
	return (float) Math.sqrt((position1.x - position2.x)
		* (position1.x - position2.x) + (position1.y - position2.y)
		* (position1.y - position2.y) + (position1.z - position2.z)
		* (position1.z - position2.z));
    }

    /**
     * Linearly interpolates between two values.
     * 
     * @param num1
     *            Source value1
     * @param num2
     *            Source value2
     * @param interpolated
     *            Value between 0 and 1 indicating the weight of value2.
     * @return
     */
    public static float Lerp(float num1, float num2, float interpolated) {
	// TODO Auto-generated method stub
	return num2 * interpolated + num1 * (1 - interpolated);
    }

    /**
     * Linearly interpolates between two vector.
     * 
     * @param v1
     *            Source vector1
     * @param v2
     *            Source vector2
     * @param interpolated
     *            Value between 0 and 1 indicating the weight of vector2.
     * @return
     */
    public static Vector3 Lerp(Vector3 v1, Vector3 v2, float interpolated) {
	float x = Lerp(v1.x, v2.x, interpolated);
	float y = Lerp(v1.y, v2.y, interpolated);
	float z = Lerp(v1.z, v2.z, interpolated);
	return new Vector3(x, y, z);
    }

    public static float distance(float x, float y, float x2, float y2) {
	final float x_d = x2 - x;
	final float y_d = y2 - y;
	return (float) Math.sqrt(x_d * x_d + y_d * y_d);
    }
}
