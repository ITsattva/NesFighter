package com.manyman.game.sprites.tileobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.manyman.game.ManymanGame;
import com.manyman.game.scenes.Hud;
import com.manyman.game.screens.PlayScreen;
import com.manyman.game.sprites.items.ItemDef;
import com.manyman.game.sprites.items.Mushroom;

public class Coin extends InteractiveTileObject {
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
        if (getCell().getTile().getId() == BLANK_COIN) {
            ManymanGame.manager.get("sounds/bump.wav", Sound.class).play();
        } else {
            ManymanGame.manager.get("sounds/coin.wav", Sound.class).play();
            screen.spawnItem(new ItemDef(new Vector2(body.getPosition().x, body.getPosition().y + 16 / ManymanGame.PPM),
            Mushroom.class));
        }

        getCell().setTile(tileSet.getTile(BLANK_COIN));
        Hud.addScore(500);
    }
}
