package com.gdxengine.framework.interfaces;

import com.badlogic.gdx.math.Rectangle;

public interface UIComponent {

	public abstract Object getContent();

	public abstract Object getBackground();

	public abstract void setupLayoutContent();

	public abstract Rectangle getBound();

}