package com.gdxengine.framework.test.physics.angrybirds;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.gdxengine.framework.effect.ParticleEffectManager;
import com.gdxengine.framework.interfaces.IDrawable;
import com.gdxengine.framework.interfaces.IGameService;

public class CustomContact implements ContactListener {
	
	public static final float KILLBRICK = .5f;
	public static final float KILLPIG = .2f;
	
	private IGameService service;
	private ParticleEffectManager effects;
	public CustomContact(IGameService service) {
		super();
		this.service = service;
		
		effects = service.getService(ParticleEffectManager.class);
	}
	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		String dataA = fixtureA.getBody().getUserData().toString();
		String dataB = fixtureB.getBody().getUserData().toString();

		float force = impulse.getNormalImpulses()[0];
		
		if (fixtureA.getBody().getLinearVelocity().x < 1 && fixtureA.getBody().getLinearVelocity().y < 1){
			if (fixtureB.getBody().getLinearVelocity().x < 1 && fixtureB.getBody().getLinearVelocity().y < 1){
				return;
			}
		}
//		
//		if (dataA.equals("pig")) {
//
//			if(fixtureA.getBody().getUserData().getClass().toString().contains("String")){
//			
//			}
//			Pig pig = (Pig) fixtureA.getBody().getUserData();
//			effects.invokeEffect("explosion", pig.getCenterX(), pig.getCenterY());
//			AngryBirdScene scene = ((AngryBirdScene)getGameServices().getCurrentScene());
//			scene.remove((IDrawable) fixtureA.getBody().getUserData());
//			fixtureA.getBody().setUserData("remove");
//			if(scene.checkPlayerWon())
//			{
//				scene.gameLevel++;
//				scene.createLevel();
//			}
//		}
//
//		else if(dataA.equals("brick")){
//	
//			if(fixtureA.getBody().getUserData().getClass().toString().contains("String")){
//		
//			}
//			Wood wood = (Wood) fixtureA.getBody().getUserData();
//			effects.invokeEffect("explosion", wood.getCenterX(), wood.getCenterY());
//			
//			wood.takeDamage(force);
//		}
//		if (dataA.equals("pig")) {
//			
//			if(fixtureB.getBody().getUserData().getClass().toString().contains("String")){
//				return;
//			}
//			Pig pig = (Pig) fixtureB.getBody().getUserData();
//			effects.invokeEffect("explosion", pig.getCenterX(), pig.getCenterY());
//			AngryBirdScene scene = ((AngryBirdScene)getGameServices().getCurrentScene());
//			scene.remove((IDrawable) fixtureB.getBody().getUserData());
//			fixtureB.getBody().setUserData("remove");
//			if(scene.checkPlayerWon())
//			{
//				scene.gameLevel++;
//				scene.createLevel();
//			}
//		}
//
//		else if(dataA.equals("brick")){
//			
//			if(fixtureB.getBody().getUserData().getClass().toString().contains("String")){
//				
//				return;
//			}
//			Wood wood = (Wood) fixtureB.getBody().getUserData();
//			effects.invokeEffect("explosion", wood.getCenterX(), wood.getCenterY());
//			
//			wood.takeDamage(force);
//		}
//		switch (dataA) {
//			case "pig" :
//
//				if(fixtureA.getBody().getUserData().getClass().toString().contains("String")){
//					break;
//				}
//				Pig pig = (Pig) fixtureA.getBody().getUserData();
//				effects.invokeEffect("explosion", pig.getCenterX(), pig.getCenterY());
//				AngryBirdScene scene = ((AngryBirdScene)getGameServices().getCurrentScene());
//				scene.remove((IDrawable) fixtureA.getBody().getUserData());
//				fixtureA.getBody().setUserData("remove");
//				if(scene.checkPlayerWon())
//				{
//					scene.gameLevel++;
//					scene.createLevel();
//				}
//
//			break;
//			case "brick" :
//		
//				if(fixtureA.getBody().getUserData().getClass().toString().contains("String")){
//					break;
//				}
//				Wood wood = (Wood) fixtureA.getBody().getUserData();
//				effects.invokeEffect("explosion", wood.getCenterX(), wood.getCenterY());
//				
//				wood.takeDamage(force);
//			
//			break;
//		}
//		switch (dataB) {
//			case "pig" :
//				
//				if(fixtureB.getBody().getUserData().getClass().toString().contains("String")){
//					break;
//				}
//				Pig pig = (Pig) fixtureB.getBody().getUserData();
//				effects.invokeEffect("explosion", pig.getCenterX(), pig.getCenterY());
//				AngryBirdScene scene = ((AngryBirdScene)getGameServices().getCurrentScene());
//				scene.remove((IDrawable) fixtureB.getBody().getUserData());
//				fixtureB.getBody().setUserData("remove");
//				if(scene.checkPlayerWon())
//				{
//					scene.gameLevel++;
//					scene.createLevel();
//				}
//			break;
//			case "brick" :
//				if(fixtureB.getBody().getUserData().getClass().toString().contains("String")){
//					break;
//				}
//				Wood wood = (Wood) fixtureB.getBody().getUserData();
//				effects.invokeEffect("explosion", wood.getCenterX(), wood.getCenterY());
//				
//				wood.takeDamage(force);
//			break;
//		}
		
		
	}
	private IGameService getGameServices() {
		// TODO Auto-generated method stub
		return service;
	}
	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

}
