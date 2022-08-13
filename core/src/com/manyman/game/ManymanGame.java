package com.manyman.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.manyman.game.screens.PlayScreen;

public class ManymanGame extends Game {
	public SpriteBatch batch;
	public static final int V_WIDTH = 400;
	public static final float V_HEIGHT = 208;
	public static final float PPM = 100;

	public static final short GROUND_BIT = 1;
	public static final short MARIO_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;

	public static AssetManager manager;

	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("music/mario_music.ogg", Music.class);
		manager.load("sounds/coin.wav", Sound.class);
		manager.load("sounds/bump.wav", Sound.class);
		manager.load("sounds/sounds_breakblock.wav", Sound.class);
		manager.finishLoading();

		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		manager.dispose();
		batch.dispose();
	}
}
