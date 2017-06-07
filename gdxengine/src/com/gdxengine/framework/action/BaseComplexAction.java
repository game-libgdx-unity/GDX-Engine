package com.gdxengine.framework.action;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gdxengine.framework.interfaces.IActionPerformer;
/**
 * Action used in component class that implements IActionCollection interface
 * the component will be add to ActionManager after all
 * 
 * Do the most works among the actions. support numberOfRepeat, timeToDie. onActionProgress and onActionRemove Event
 * @author Vinh
 *
 */
public abstract class BaseComplexAction implements IActionPerformer {

	/**
	 * Pause the action
	 */
	boolean paused = false;
	/**
	 * Count the number of task have done. Always less than or equal to
	 * numberOfRepeat
	 */
	int taskCount = 0;
	/**
	 * Number of repeat action Zero value will make action perform forever
	 */
	int numberOfRepeat = 3;
	/**
	 * Time running in this task
	 */
	float timeRunning = 0f;
	/**
	 * Time Period to do the action (call perform method)
	 */
	float elapseTimetoWork = 1f;
	/**
	 * Time Period to remove the action.
	 *  Set zero value will make action removed after done all work
	 */
	float elapseTimetoDie = 0f;

	/**
	 * @return the paused
	 */
	public boolean isPaused() {
		return paused;
	}

	/**
	 * @param paused the paused to set
	 */
	public void setPaused(boolean paused) {
		this.paused = paused;
	}

	/**Count the number of task have done. Always less than or equal to numberOfRepeat
	 * @return the taskCount
	 */
	public int getTaskCount() {
		return taskCount;
	}

	/**Count the number of task have done. Always less than or equal to numberOfRepeat
	 * @param taskCount the taskCount to set
	 */
	public void setTaskCount(int taskCount) {
		this.taskCount = taskCount;
	}

	/**Number of repeat action Zero value will make action perform forever
	 * @return the numberOfRepeat
	 */
	public int getNumberOfRepeat() {
		return numberOfRepeat;
	}

	/**Number of repeat action Zero value will make action perform forever
	 * @param numberOfRepeat the numberOfRepeat to set
	 */
	public void setNumberOfRepeat(int numberOfRepeat) {
		this.numberOfRepeat = numberOfRepeat;
	}

	/**
	 * @return the elapseTimetoWork
	 */
	public float getElapseTimetoWork() {
		return elapseTimetoWork;
	}

	/**
	 * @param elapseTimetoWork the elapseTimetoWork to set
	 */
	public void setElapseTimetoWork(float elapseTimetoWork) {
		this.elapseTimetoWork = elapseTimetoWork;
	}

	/**
	 * Time Period to remove the action.
	 *  Set zero value will make action removed after done all work
	 */
	public float getElapseTimetoDie() {
		return elapseTimetoDie;
	}

	/**
	 * Time Period to remove the action.
	 *  Set zero value will make action removed after done all work at once
	 */
	public void setElapseTimetoDie(float elapseTimetoDie) {
		this.elapseTimetoDie = elapseTimetoDie;
	}

	/**
	 * Construct a action after elapseTime period using default setting number
	 * of repeat is 3 elapseTime is 1 second paused is false
	 */
	public BaseComplexAction() {
	}

	/**Construct a action after elapseTime period
	 * @param numberOfRepeat
	 * @param elapseTimetoWork
	 * @param elapseTimetoDie
	 */
	public BaseComplexAction(int numberOfRepeat, float elapseTimetoWork,
			float elapseTimetoDie) {
		super();
		this.numberOfRepeat = numberOfRepeat;
		this.elapseTimetoWork = elapseTimetoWork;
		this.elapseTimetoDie = elapseTimetoDie;
	}

	/**
	 * @param paused
	 * @param numberOfRepeat
	 * @param elapseTimetoWork
	 * @param elapseTimetoDie
	 */
	public BaseComplexAction(boolean paused, int numberOfRepeat,
			float elapseTimetoWork, float elapseTimetoDie) {
		super();
		this.paused = paused;
		this.numberOfRepeat = numberOfRepeat;
		this.elapseTimetoWork = elapseTimetoWork;
		this.elapseTimetoDie = elapseTimetoDie;
	}

	/**
	 * Construct a action after elapseTime period
	 * 
	 * @param paused
	 * @param numberOfRepeat
	 * @param elapseTime
	 */
	public BaseComplexAction(boolean paused, int numberOfRepeat, float elapseTime) {
		super();
		this.paused = paused;
		this.numberOfRepeat = numberOfRepeat;
		this.elapseTimetoWork = elapseTime;
	}

	/**
	 * Construct a action after elapseTime period
	 * 
	 * @param elapseTime
	 */
	public BaseComplexAction(float elapseTime, int numberOfRepeat) {
		setElapsedTime(elapseTime);
	}

	@Override
	public void update(float gameTime) {

		if (paused) {
			if(taskCount >= numberOfRepeat){
				timeRunning += gameTime;
				if(timeRunning > elapseTimetoDie){
					timeRunning = 0f;
					onActionRemove();
					paused = true;
				}
			}
			return;
		}

		timeRunning += gameTime;
		onActionProgress(getProcessRate());
		if (timeRunning > elapseTimetoWork) {
			onActionPerformance();
			timeRunning = 0f;

			if (numberOfRepeat != 0) {
				taskCount++;
				if (taskCount >= numberOfRepeat) {
					taskCount = numberOfRepeat;
					paused = true;
					timeRunning = 0f;
				}
			}
		}
	}

	/**
	 * Remove the action from ActionCollection
	 */
	public abstract void onActionRemove();
	/**
	 * be called after update the action in game loop 
	 * @param progressRate
	 */
	public abstract void onActionProgress(float progressRate);
	@Override
	public abstract void onActionPerformance();
	
	@Override
	public void setElapsedTime(float time) {
		if (time < 0)
			throw new GdxRuntimeException(
					"Time must be greater than or equal zero!");

		elapseTimetoWork = time;
	}

	@Override
	public float getProcessRate() {
		// TODO Auto-generated method stub
		return timeRunning / elapseTimetoWork;
	}

	@Override
	public void resetTimeLine() {
		taskCount = 0;
		timeRunning = 0f;
	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {
		paused = false;
		
		if(numberOfRepeat != 0){
			// reset is action is finished its work
			if (taskCount >= numberOfRepeat)
				resetTimeLine();
		}
	}

	@Override
	    public void replay() {
	    taskCount = 0;
		paused = false;
		timeRunning = 0f;
	    }

}
