package com.adidesi95.nodechess;

import com.adidesi95.nodechess.screens.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class MainGame extends Game {

	public static final int UWIDTH = 8;


	public static Game game;

	@Override
	public void create () {
		Gdx.graphics.setContinuousRendering(false);
		game = this;
		this.setScreen(new MenuScreen());
	}

}
