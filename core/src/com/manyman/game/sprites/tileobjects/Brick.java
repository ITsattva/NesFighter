package com.manyman.game.sprites.tileobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.manyman.game.ManymanGame;
import com.manyman.game.scenes.Hud;
import com.manyman.game.screens.PlayScreen;
import com.manyman.game.sprites.Mario;

public class Brick extends InteractiveTileObject{
    public Brick(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(ManymanGame.BRICK_BIT);
    }

    @Override
    public void onHeadHit(Mario mario) {
        if (mario.isBig()) {
            Gdx.app.log("Brick", "Colision");
            setCategoryFilter(ManymanGame.DESTROYED_BIT);
            getCell().setTile(null);
            Hud.addScore(200);
            ManymanGame.manager.get("sounds/sounds_breakblock.wav", Sound.class).play();
        } else {
            ManymanGame.manager.get("sounds/bump.wav", Sound.class).play();
        }
    }
}
