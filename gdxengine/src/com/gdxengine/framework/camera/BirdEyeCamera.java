package com.gdxengine.framework.camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.gdxengine.framework.Utils;

/**
 * the class is not works completely
 * 
 * @author Vinh
 *
 */
public class BirdEyeCamera extends PerspectiveCamera {
	 float leftrightRot;
     float updownRot;
     float cameraSpeed = 2f;
     
     public BirdEyeCamera(float fieldOfView, float viewportWidth, float viewportHeight,Vector3 position, float leftrightRot, float updownRot) {
 		super(fieldOfView, viewportWidth, viewportHeight);
 		
 		this.position.set(position);
 		this.leftrightRot = leftrightRot;
 		this.updownRot =updownRot;
 		
 		UpdateViewMatrix();
     }
     
     @Override
     public void update()
     {
    	 float gameTime = Gdx.graphics.getDeltaTime();
    	 
         if (Gdx.input.isKeyPressed(Keys.UP))      //Forward
             AddToCameraPosition(new Vector3(0, 0, -cameraSpeed * gameTime));
         if (Gdx.input.isKeyPressed(Keys.DOWN))    //Backward
             AddToCameraPosition(new Vector3(0, 0, cameraSpeed * gameTime));
         if (Gdx.input.isKeyPressed(Keys.RIGHT))   //Right
             AddToCameraPosition(new Vector3(cameraSpeed * gameTime, 0, 0));
         if (Gdx.input.isKeyPressed(Keys.LEFT))    //Left
             AddToCameraPosition(new Vector3(-cameraSpeed * gameTime, 0, 0));
         if (Gdx.input.isKeyPressed(Keys.Q))                                     //Up
             AddToCameraPosition(new Vector3(0, cameraSpeed * gameTime, 0));
         if (Gdx.input.isKeyPressed(Keys.Z))                                     //Down
             AddToCameraPosition(new Vector3(0, -cameraSpeed * gameTime, 0));                     
     }

     private void AddToCameraPosition(Vector3 vectorToAdd)
     {
         float moveSpeed = 0.05f;
         Matrix4 cameraRotation = new Matrix4();
         cameraRotation = Utils.createRotate(updownRot, leftrightRot, 0f, cameraRotation);
         Vector3 rotatedVector = Utils.Transform(vectorToAdd, cameraRotation);
         position.add(rotatedVector.mul(moveSpeed));
         UpdateViewMatrix();
     }

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
