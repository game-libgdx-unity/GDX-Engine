package com.gdxengine.framework.ui;

import javax.swing.text.ChangedCharSetException;
import javax.swing.text.html.HTMLDocument.HTMLReader.HiddenAction;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.WidgetGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;

public class TabPane extends Table {

	Array<TextButton> tabHeaders;
	Array<ITabContent> contents;

	Table content;
	int tabCount = 2;
	
	float preferedContentWidth = 0;
	float preferedContentHeight = 0;
	
	public TabPane(int tabCount) {
		super();
		this.tabCount = tabCount;
		tabHeaders = new Array<TextButton>(tabCount);
		contents = new Array<ITabContent>(tabCount);
		content = new Table();
	}

	public void addTab(String tabTitle, final ITabContent tabContent,
			Skin skin) {

		TextButton button = new TextButton(tabTitle, skin);
		button.align(Align.left);
		//button.setFillParent(true);
		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				tabContent.setContent(content);
				content.pack();
				setWidth(preferedContentWidth);
				setHeight(preferedContentHeight);
				
			}
		});
		
		tabHeaders.add(button);
		contents.add(tabContent);
		
		preferedContentWidth = Math.max(preferedContentWidth, tabContent.getWidth());
		preferedContentHeight = Math.max(preferedContentHeight, tabContent.getHeight() + tabHeaders.get(0).getHeight());
	}

	public void removeTab(String tabTitle) {
		for (int i = tabHeaders.size - 1; i >= 0; i--) {
			if (tabHeaders.get(i).getText().equals(tabTitle)) {
				tabHeaders.removeIndex(i);
				pack();
			}
		}
	}
	
	public void setActiveTab(int index){
		pack();
		ITabContent tab = contents.get(index);
		tab.setContent(content);
		content.pack();
		setWidth(preferedContentWidth);
		setHeight(preferedContentHeight);
	}

	@Override
	public void pack() {
		this.clear();
		row().fill().expand();
		for (TextButton btn : tabHeaders)
			add(btn).align(Align.left);
		row();
		
		add(content).colspan(tabHeaders.size);
		
		super.pack();
		
		setWidth(preferedContentWidth);
		setHeight(preferedContentHeight);
	}

}