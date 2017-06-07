package com.gdxengine.framework.event.listener;

import com.gdxengine.framework.event.TouchEvent;


public interface TouchListener extends InputListener, TouchUpListener, TouchDownListener {
	public boolean onTouchDragged(TouchEvent e);
}
