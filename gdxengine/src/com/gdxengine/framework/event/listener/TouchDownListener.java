package com.gdxengine.framework.event.listener;

import com.gdxengine.framework.event.TouchEvent;

public interface TouchDownListener extends InputListener {
	public boolean onTouchDown(TouchEvent e);
}
