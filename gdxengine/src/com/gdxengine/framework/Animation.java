package com.gdxengine.framework;

/**
 * The class encapsulate some attributes for one animation.
 * @author Vinh
 *
 */
public class Animation {
	/**
	 * animation is looping?
	 */
	public boolean isLooping;
	
	/**
	 * How long one frame of animation should be displayed?
	 */
	public float frameTime;
	/**
	 * How many frame in this animation?
	 */
	public int numFrames;
	/**
	 * Constructor of new Animation
	 * @param isLooping animation is looping?
	 * @param frameTime How long one frame of animation should be displayed?
	 * @param numFrame How many frame in this animation?
	 */
	public Animation(boolean isLooping, float frameTime,int numFrame) {
		super();
		this.numFrames = numFrame;
		this.isLooping = isLooping;
		this.frameTime = frameTime;
	}
}
