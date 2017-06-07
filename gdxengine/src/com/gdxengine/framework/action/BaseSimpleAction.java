package com.gdxengine.framework.action;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.gdxengine.framework.interfaces.IActionPerformer;

/**
 * Action used in component class that implements IActionCollection interface
 * the component will be add to ActionManager after all
 * 
 * The simple action, do work after each period of time forever
 * 
 * @author Vinh
 * 
 */
public abstract class BaseSimpleAction implements IActionPerformer {

    /**
     * Pause the action
     */
    boolean paused = false;
    /**
     * Time running in this task
     */
    float timeRunning = 0f;
    /**
     * Time Period to do the action (call perform method)
     */
    float elapseTimetoWork = 1f;

    /**
     * @return the paused
     */
    public boolean isPaused() {
	return paused;
    }

    /**
     * @param paused
     *            the paused to set
     */
    public void setPaused(boolean paused) {
	this.paused = paused;
    }

    /**
     * @return the elapseTimetoWork
     */
    public float getElapseTimetoWork() {
	return elapseTimetoWork;
    }

    /**
     * @param elapseTimetoWork
     *            the elapseTimetoWork to set
     */
    public void setElapseTimetoWork(float elapseTimetoWork) {
	this.elapseTimetoWork = elapseTimetoWork;
    }

    /**
     * Construct a action after elapseTime period using default setting
     * elapseTime is 1 second and paused is false
     */
    public BaseSimpleAction() {
    }

    /**
     * @param paused
     * @param numberOfRepeat
     * @param elapseTimetoWork
     * @param elapseTimetoDie
     */
    public BaseSimpleAction(boolean paused, float elapseTimetoWork) {
	super();
	this.paused = paused;
	this.elapseTimetoWork = elapseTimetoWork;
    }

    /**
     * Construct a action after elapseTime period
     * 
     * @param elapseTime
     */
    public BaseSimpleAction(float elapseTime, int numberOfRepeat) {
	setElapsedTime(elapseTime);
    }

    @Override
    public void update(float gameTime) {

	if (paused)
	    return;

	timeRunning += gameTime;
	if (timeRunning > elapseTimetoWork) {
	    onActionPerformance();
	    timeRunning = 0f;
	}
    }

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
	timeRunning = 0f;
    }

    @Override
    public void pause() {
	paused = true;
    }

    @Override
    public void replay() {
	paused = false;
	timeRunning = 0f;
    }

    @Override
    public void resume() {
	paused = false;
    }

    public float getTimeRunning() {
	return timeRunning;
    }

    public void setTimeRunning(float timeRunning) {
	this.timeRunning = timeRunning;
    }

}
