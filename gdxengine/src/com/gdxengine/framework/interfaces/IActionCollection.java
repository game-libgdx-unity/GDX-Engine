package com.gdxengine.framework.interfaces;

import java.util.ArrayList;

import com.gdxengine.framework.BaseManager;

@SuppressWarnings("hiding")
public interface IActionCollection<IActionPerformer> extends Iterable<IActionPerformer> {
	public ArrayList<IActionPerformer> getActions();
}
