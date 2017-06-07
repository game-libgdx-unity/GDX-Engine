package com.gdxengine.framework.test.towerdefense.gui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.gdxengine.framework.AssetLoader;
import com.gdxengine.framework.interfaces.IGameService;
import com.gdxengine.framework.test.towerdefense.GameAsset;
import com.gdxengine.framework.test.towerdefense.GameSpecs;
import com.gdxengine.framework.test.towerdefense.Player;
import com.gdxengine.framework.test.towerdefense.Tower;

public class EditWeaponGUI extends BaseGUI {

    TextButton btnUpgradeWeapon;
    TextButton btnSellWeapon;
    

    public EditWeaponGUI(Skin skin) {
	super(skin);
	
    }

    @Override
    public void addListener(final IGameService service, final int x, final int y) {
	// clear all added listener
	for (int i = 1; i < btnUpgradeWeapon.getListeners().size; i++) {
	    btnUpgradeWeapon.getListeners().removeIndex(i);
	    i--;
	}
	// add new listener
	btnUpgradeWeapon.addListener(new ChangeListener() {

	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		if (getTower().getWeapon().getLevel() < 9) {
		    Player player = service.getService(Player.class);
		    if (player.gold >= getTower().getWeapon().getCost()) {
			player.gold -= getTower().getWeapon().getCost();

			getTower().getWeapon().levelUp();
			Player.towerRange.setRange(getTower().getWeapon()
				.getRange());
			// reload the gui
			show();
		    }
		}
	    }
	});

	// clear all added listener
	for (int i = 1; i < btnSellWeapon.getListeners().size; i++) {
	    btnSellWeapon.getListeners().removeIndex(i);
	    i--;
	}
	// add new listener
	btnSellWeapon.addListener(new ChangeListener() {

	    @Override
	    public void changed(ChangeEvent event, Actor actor) {
		Player player = service.getService(Player.class);
		player.gold += getTower().getWeapon().getCost() / 2;
		tower.setWeapon(null);
		hide();
	    }
	});
    }

    @Override
    public void show() {
	
	if (getTower().getWeapon().getLevel() == 9) {
	    btnUpgradeWeapon.setColor(Color.RED);
	    btnUpgradeWeapon.setText("Lv.10 (Maximum Upgrade)");
	} else if (player.gold < getTower().getWeapon().getCost()) {
	    btnUpgradeWeapon.setColor(Color.RED);
	    btnUpgradeWeapon.setText("Upgrade cost: " + getTower().getWeapon().getCost() +" Not Enough $");
	} else {
	    if (defaultColor != null)
		btnUpgradeWeapon.setColor(defaultColor);
	    btnUpgradeWeapon.setText("Upgrade to Lv."
		    + (getTower().getWeapon().getLevel() + 2) + " ($"
		    + getTower().getWeapon().getCost() + ")");
	}

	btnUpgradeWeapon.pack();
	btnSellWeapon.setText("Sell ($" + getTower().getWeapon().getCost() / 2
		+ ")");
	btnSellWeapon.pack();
	pack();
	btnSellWeapon.setWidth(getWidth());
	super.show();
    }

    @Override
    protected void onBuild(Skin skin) {
	row().fill().expandX();
	btnUpgradeWeapon = new TextButton("Upgrade", skin);
	add(btnUpgradeWeapon).expandX();
	row().fill().expandX();
	btnSellWeapon = new TextButton("Sell", skin);
	add(btnSellWeapon).expandX();
	super.onBuild(skin);
    }
}
