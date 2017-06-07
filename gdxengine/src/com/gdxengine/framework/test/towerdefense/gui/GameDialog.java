package com.gdxengine.framework.test.towerdefense.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.gdxengine.framework.test.towerdefense.GameSpecs;
import com.gdxengine.framework.test.towerdefense.Level;
import com.gdxengine.framework.test.towerdefense.TowerDefenseScene;

public abstract class GameDialog extends Window {

    Label waveLevel;
    Label monsHP;
    Label monsBonus;
    Label monsSpeed;
    Label monsNumber;
    TextButton btnOK;

    public GameDialog(Skin skin) {
	super("", skin);

	waveLevel = new Label("", skin);
	monsHP = new Label("", skin);
	monsBonus = new Label("", skin);
	monsSpeed = new Label("", skin);
	monsNumber = new Label("", skin);
	btnOK = new TextButton("Okay, Let them come!", skin);
	btnOK.addListener(new ChangeListener() {

	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		// TODO Auto-generated method stub
		setVisible(false);

	    }
	});
	setTitle(" There are more monsters are coming to you... ");
    }

    public void build(int index) {
	clear();
	if (index < Level.MAX_INDEX && index > -1) {
	    setTitle(" There are more monsters are coming to you... ");
	    row().fill().expand();
	    waveLevel.setText("Level Wave : " + (index + 1)+"/15");
	    add(waveLevel).fill().expand();
	    row().fill().expand();
	    monsHP.setText("Hit point: " + GameSpecs.hitPoint[index]);
	    add(monsHP).fill().expand();
	    row().fill().expand();
	    monsBonus.setText("Bonus: " + GameSpecs.bonus[index]);
	    add(monsBonus).fill().expand();
	    row().fill().expand();
	    monsSpeed.setText("Speed: " + GameSpecs.speed[index]);
	    add(monsSpeed).fill().expand();
	    row().fill().expand();
	    monsNumber.setText("Quantity: " + GameSpecs.numberOfMonster[index]);
	    add(monsNumber).fill().expand();
	    row().fill().expand();
	    add(btnOK).fill().expand();
	} else if(index >= Level.MAX_INDEX) {
	    setTitle("You won, Congratulation!");
	    row().fill().expand();
	    waveLevel
		    .setText("Too scare by your army, the monsters just ran away tragically!\nThank you for your time\nPowered by GDX Engine - Free Opensource Game Project\nHand-coded by Akemi-san\nMrThanhVinh168@gmail.com");
	    add(waveLevel).fill().expand();
	    row().fill().expand();
	    btnOK.setText("Great! Take me to GDX Engine Official Website!");
	    btnOK.addListener(new ChangeListener() {

		@Override
		public void changed(ChangeEvent event, Actor actor) {
		    if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			try {
			    desktop.browse(new URI("http://làmgameandroid.vn"));
			} catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			} catch (URISyntaxException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
		    }
		}
	    });
	    add(btnOK).fill().expand();
	} else {
	    setTitle("You lose!");
	    row().fill().expand();
	    waveLevel.setText("Too many monster came to city, your mission is not complete!\nThank you for playing\nThe game was powered by GdxEngine\nHand-coded by Akemi-san");
	    add(waveLevel).fill().expand();
	    row().fill().expand();
	    btnOK.setText("What's GDX Engine?");
	    btnOK.addListener(new ChangeListener() {

		@Override
		public void changed(ChangeEvent event, Actor actor) {
		    if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			try {
			    desktop.browse(new URI("http://làmgameandroid.vn"));
			} catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			} catch (URISyntaxException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
		    }
		}
	    });
	    add(btnOK).fill().expand();
	    row().fill().expand();
//	    btnRetry.setText("I Want to try again!");
//	    btnRetry.addListener(new ChangeListener() {
//
//		@Override
//		public void changed(ChangeEvent event, Actor actor) {
//		    restartGame();
//		}
//
//		
//	    });
//	    add(btnRetry).fill().expand();
	}
	
	pack();
	setPosition((Gdx.graphics.getWidth() - getWidth()) / 2f,
		(Gdx.graphics.getHeight() - getHeight()) / 2f);
	setVisible(true);
    }
    
    public abstract void restartGame();
}
