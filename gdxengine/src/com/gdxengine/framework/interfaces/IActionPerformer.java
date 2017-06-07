/**
 * 
 */
package com.gdxengine.framework.interfaces;

/**
 * @author Vinh
 * an Action for game component object, action is task to do something after a period time...
 */
public interface IActionPerformer {
	/**
	 * update The time running of action
	 * @param gameTime
	 */
	public void update(float gameTime);
	
	/**
	 * set elapsed time to do action by onActionPerformance event.
	 * if time = 0, the action will be performed after each game loop (Not recommended).
	 * if time < 0, a exception will be thrown.
	 */
	public void setElapsedTime(float time);
	/**
	 * get process rate of task is performing
	 */
	public float getProcessRate();
	/**
	 * Reset time or something for reset entirely action
	 */
	public void resetTimeLine();
	/**
	 * stop the action
	 */
	public void pause();
	/**
	 * pause the action
	 */
	public void resume();
	/**
	 * perform a funny task of the action after time running reach to elapsed time period
	 * time running is set to zero after performance is done.
	 * @param gameTime
	 */
	public void onActionPerformance();
	/**
	 * Replay the action
	 */
	void replay();
	
}
