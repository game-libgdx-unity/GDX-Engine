package com.gdxengine.framework.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.gdxengine.framework.Utils;

/**
 * The FreeCamera that you can move the camera position and rotate camera freely!
 * Currently, This works for desktop game only because i only have set some Keys of Keyboards
 * You can change the input in update() method to make the camera works for your platforms
 * 
 * @author Vinh
 *
 */
public class FreeCamera extends PerspectiveCamera {
	 /**
	 * Rotation in Y axis of camera
	 */
	float leftrightRot;
     /**
     * Rotation in X axis of camera
     */
    float updownRot;
    /**
     * Speed for camera rotation
     */
     float cameraSpeed = 20f;
     
     public FreeCamera(float fieldOfView, float viewportWidth, float viewportHeight,Vector3 position, float leftrightRot, float updownRot) {
 		super(fieldOfView, viewportWidth, viewportHeight);
 		
 		this.position.set(position);
 		this.leftrightRot = leftrightRot;
 		this.updownRot =updownRot;
 		
 		UpdateViewMatrix();
     }
     
     @Override
     public void update()
     {
    	 float deltaTime = Gdx.graphics.getDeltaTime();
                      
             float xDifference = 0f;
             float yDifference = 0f;
             
             if(Gdx.input.isKeyPressed(Keys.UP))
            	 yDifference = cameraSpeed * deltaTime;
             if(Gdx.input.isKeyPressed(Keys.DOWN))
            	 yDifference = -cameraSpeed * deltaTime;
             if(Gdx.input.isKeyPressed(Keys.LEFT))
            	 xDifference = cameraSpeed * deltaTime * 10;
             if(Gdx.input.isKeyPressed(Keys.RIGHT))
            	 xDifference = -cameraSpeed * deltaTime * 10;
             leftrightRot -= xDifference;
             updownRot -= yDifference;
             
             MathUtils.clamp(leftrightRot, -180, 180);
             MathUtils.clamp(updownRot, -180, 180);
             UpdateViewMatrix();                
         
         
         if (Gdx.input.isKeyPressed(Keys.W))      //Forward
             AddToCameraPosition(new Vector3(0, 0, -1));
         if (Gdx.input.isKeyPressed(Keys.S))    //Backward
             AddToCameraPosition(new Vector3(0, 0, 1));
         if (Gdx.input.isKeyPressed(Keys.D))   //Right
             AddToCameraPosition(new Vector3(1, 0, 0));
         if (Gdx.input.isKeyPressed(Keys.A))    //Left
             AddToCameraPosition(new Vector3(-1, 0, 0));
         if (Gdx.input.isKeyPressed(Keys.Q))                                     //Up
             AddToCameraPosition(new Vector3(0, 1, 0));
         if (Gdx.input.isKeyPressed(Keys.Z))                                     //Down
             AddToCameraPosition(new Vector3(0, -1, 0));                     
     }

     /**
      * Move the camera following the new Postion
     * @param newPosition
     */
    private void AddToCameraPosition(Vector3 newPosition)
     {
         float moveSpeed = 0.05f;
         Matrix4 cameraRotation = new Matrix4();
         cameraRotation = Utils.createRotate(updownRot, leftrightRot, 0f, cameraRotation);
         Vector3 rotatedVector = Utils.Transform(newPosition, cameraRotation);
         position.add(rotatedVector.mul(moveSpeed));
         UpdateViewMatrix();
     }

     /**
     * Update camera's matrix
     */
    private void UpdateViewMatrix()
     {
         Matrix4 cameraRotation = new Matrix4();
         cameraRotation = Utils.createRotate(updownRot, leftrightRot, 0f, cameraRotation);

         Vector3 cameraOriginalTarget = new Vector3(0, 0, -1);
         Vector3 cameraOriginalUpVector = new Vector3(0, 1, 0);

         // cameraRotation.rotate(1, 0, 0, cameraOriginalTarget.x);
         Vector3 cameraRotatedTarget = Utils.Transform(cameraOriginalTarget, cameraRotation);
         Vector3 cameraFinalTarget =  Utils.addVector(position,cameraRotatedTarget);

         up.set(Utils.Transform(cameraOriginalUpVector, cameraRotation));
         
         view.setToLookAt(position, cameraFinalTarget, up);
         float aspect = viewportWidth / viewportHeight;
 		projection.setToProjection(Math.abs(near), Math.abs(far), fieldOfView, aspect);
 		combined.set(projection);
		Matrix4.mul(combined.val, view.val);

		invProjectionView.set(combined);
		Matrix4.inv(invProjectionView.val);
		frustum.update(invProjectionView);
		
     }
}
