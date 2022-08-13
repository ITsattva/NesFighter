package com.manyman.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.manyman.game.ManymanGame;
import com.manyman.game.scenes.Hud;
import com.manyman.game.screens.PlayScreen;

public class Coin extends InteractiveTileObject{
    private static TiledMapTileSet tileSet;
    private final int BLANK_COIN = 28;

    public Coin(PlayScreen screen, Rectangle bounds) {
        super(screen, bounds);
        tileSet = map.getTileSets().getTileSet("tileset_gutter");
        fixture.setUserData(this);
        setCategoryFilter(ManymanGame.COIN_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Coin", "Colision");
        if(getCell().getTile().getId() == BLANK_COIN)
            ManymanGame.manager.get("sounds/bump.wav", Sound.class).play();
        else
            ManymanGame.manager.get("sounds/coin.wav", Sound.class).play();


        getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addScore(500);
    }
}
