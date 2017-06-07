package com.gdxengine.framework;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.GdxRuntimeException;

/**
 * AnimationPlayer play animation automatically if you call update() method each gameLoop
 * Support many event useful like onFrameChanged, onAnimationChanged and you can setLooping, 
 * stop or replay Animation anytime!
 * 
 *  AnimationPlayer need a {@link Animation} instance to work.
 * 
 * @author Vinh
 * 
 */
public abstract class AnimationPlayer {

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

    public AnimationPlayer(Animation animation) {

	this.animation = animation;
    }

    public AnimationPlayer() {
    }

    /**
     * update the animation
     * 
     * @param gameTime
     */
    public void updateAnimation(float gameTime) {
	animationTimeline += gameTime;
	while (animationTimeline > animation.frameTime) {
	    animationTimeline -= animation.frameTime;

	    // Advance the frame index; looping or clamping as appropriate.
	    if (animation.isLooping) {
		frameIndex = (frameIndex + 1) % animation.numFrames;
	    } else {

		if (frameIndex == animation.numFrames - 1) {
		    return;
		}
		frameIndex = Math.min(frameIndex + 1, animation.numFrames - 1);
	    }

	    if (frameIndex == animation.numFrames - 1)
		onFinishedLooping();

	    onFrameChanged();
	}
    }

    /**
     * Call when the animation finished a animation loop, not depend on the
     * isLooping's value of animation
     */
    public abstract void onFinishedLooping();

    /**
     * Set Texture region after passing one frame The frameIndex should be used
     * to update texture region The method will be called after frameIndex of
     * animation changed
     */
    public abstract void onFrameChanged();

    /**
     * Call when the animation of Sprite is changed You can set Texture Region
     * new position, new size... of first frame of new animation so Texture
     * Region will make itself update with new animation
     */
    public abstract void onAnimationChanged();

    public void PlayAnimation(Animation animation) {
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

    public void PlayAnimation(Sprite sprite, Animation animation,
	    Rectangle newRegionOnTexture) {
	if (animation == null && this.animation == null)
	    throw new GdxRuntimeException("No animation is currently playing.");
	// If this animation is already running, do not restart it.
	if (this.animation == animation)
	    return;

	// Start the new animation.
	this.animation = animation;
	this.frameIndex = 0;
	this.animationTimeline = 0.0f;

	sprite.setRegionX((int) newRegionOnTexture.x);
	sprite.setRegionY((int) newRegionOnTexture.y);
	sprite.setRegionWidth((int) newRegionOnTexture.getWidth());
	sprite.setRegionHeight((int) newRegionOnTexture.getHeight());
	onAnimationChanged();
    }

    public void replayAnimation() {
	// Start the new animation.
	this.frameIndex = 0;
	this.animationTimeline = 0.0f;
	onFrameChanged();
    }

    public void stopAnimation() {
	// Start the new animation.
	this.frameIndex = animation.numFrames - 1;
	this.animationTimeline = 0.0f;
    }
    public boolean isStop(){
	return frameIndex == animation.numFrames - 1;
    }
}
