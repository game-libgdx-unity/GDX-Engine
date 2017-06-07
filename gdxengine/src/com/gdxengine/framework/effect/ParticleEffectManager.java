package com.gdxengine.framework.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gdxengine.framework.BaseManager;
import com.gdxengine.framework.interfaces.IDrawable;
import com.gdxengine.framework.interfaces.IGameService;

public class ParticleEffectManager extends BaseManager<String, ParticleEffectPool> implements IDrawable {
	
	Array<PooledEffect> effects = new Array<PooledEffect>();
	protected IGameService services;
	public ParticleEffectManager(IGameService services) {

		this.services = services;
	}

	/**
	 * Add new (and activate) the effect just has been added.
	 * @param key key of effect
	 * @param particleEffectFilePath File path of effect file
	 * @param dirPathOfImages path of folder that contains images for effect 
	 * @param isActiveEffect set Active Effect for effect just has been added.
	 */
	public void addParticleEffect(String key, String particleEffectFilePath, String dirPathOfImages){
		
		if(collection.containsKey(key))
		{
			throw new GdxRuntimeException("Key for the particle effect is existing!");
		}
		
		ParticleEffect effect = new ParticleEffect();
		effect.load(Gdx.files.internal(particleEffectFilePath),Gdx.files.internal(dirPathOfImages));
		ParticleEffectPool pool =  new ParticleEffectPool(effect, 1, 2);
		collection.put(key, pool);
	}
	/**
	 * Invoke effect for special effect by its key. Change the current active key by key input parameter
	 * @param x position-x of location where the effect happen.
	 * @param y position-y of location where the effect happen.
	 * @param key key for special effect
	 */
	public void invokeEffect(String key, float x, float y) {
		
		PooledEffect effect = collection.get(key).obtain();
		effect.setPosition(x, y);
		effects.add(effect);
	}
	
	/**
	 * Used for reseting effect.
	 */
	public void clear()
	{
		for (int i = effects.size - 1; i >= 0; i--)
		    effects.get(i).free();
		effects.clear();
		
		collection.clear();
	}
	
	@Override
	public void render(float gameTime) {
		for (int i = effects.size - 1; i >= 0; i--) {
	        PooledEffect effect = effects.get(i);
	        effect.draw(getGameService().getSpriteBatch(), gameTime);
	        if (effect.isComplete()) {
	                effect.free();
	                effects.removeIndex(i);
	        }
		}
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IGameService getGameService() {
		// TODO Auto-generated method stub
		return services;
	}

	@Override
	public void update(float gameTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
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
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void setVisible(boolean visible) {
		// TODO Auto-generated method stub
		
	}
}
