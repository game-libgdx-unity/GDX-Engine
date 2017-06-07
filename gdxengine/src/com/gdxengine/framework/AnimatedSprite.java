package com.gdxengine.framework;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gdxengine.framework.interfaces.IGameService;

/**
 * Extended class from DefaultSprite
 * Make the sprite can be animated by using frameIndex to update region of Texture Region
 * @author Vinh
 *
 */
public abstract class AnimatedSprite extends DefaultSprite {

	/**
	 * Current active animation
	 */
	public Animation animation;
	/**
	 * Timer for playing animation
	 */
	private float animationTimeline;
	/**
	 * The index of current frame of animation.
	 */
	protected int frameIndex;
	
	public AnimatedSprite(IGameService services, TextureRegion region,
			Animation animation) {
		super(services, region);
		this.animation = animation;
	}
	
	public AnimatedSprite(IGameService services, Texture texture,
			Animation animation) {
		super(services, texture);
		this.animation = animation;
	}

	@Override
	public void update(float gameTime) {
        // Process passing time.
        updateAnimation(gameTime);
	}

	/**
	 * @param gameTime
	 */
	private void updateAnimation(float gameTime) {
		animationTimeline += gameTime;
        while (animationTimeline > animation.frameTime)
        {
            animationTimeline -= animation.frameTime;

            // Advance the frame index; looping or clamping as appropriate.
            if (animation.isLooping)
            {
                frameIndex = (frameIndex + 1) % animation.numFrames;
            }
            else
            {        
                frameIndex = Math.min(frameIndex + 1, animation.numFrames - 1);
            }
            
            onFrameChanged();
        }
	}
	
	/**
	 * Set Texture region after passing one frame
	 * The frameIndex should be used to update texture region
	 * The method will be called after frameIndex of animation changed
	 */
	public abstract void onFrameChanged();
	
	/**
	 * Call when the animation of Sprite is changed
	 * You can set Texture Region new position, new size... of first frame of new animation so Texture Region will make itself update with new animation
	 */
	protected abstract void onAnimationChanged();
	
	public void PlayAnimation(Animation animation)
    {
		if (animation == null && this.animation == null)
            throw new GdxRuntimeException("No animation is currently playing.");
        // If this animation is already running, do not restart it.
        if (this.animation == animation)
            return;

        // Start the new animation.
        this.animation = animation;
        this.frameIndex = 0;
        this.animationTimeline = 0.0f;
        onAnimationChanged();
    }
	public void PlayAnimation(Animation animation, Rectangle newRegionOnTexture)
    {
		if (animation == null && this.animation == null)
            throw new GdxRuntimeException("No animation is currently playing.");
        // If this animation is already running, do not restart it.
        if (this.animation == animation)
            return;

        // Start the new animation.
        this.animation = animation;
        this.frameIndex = 0;
        this.animationTimeline = 0.0f;
        
        this.setRegionX((int) newRegionOnTexture.x);
        this.setRegionY((int) newRegionOnTexture.y);
        this.setRegionWidth((int) newRegionOnTexture.getWidth());
        this.setRegionHeight((int) newRegionOnTexture.getHeight());
        onAnimationChanged();
    }
	
}
