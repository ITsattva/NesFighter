package com.manyman.game.sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.manyman.game.ManymanGame;
import com.manyman.game.screens.PlayScreen;


public class Goomba extends Enemy {
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;

    public Goomba(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<>();
        for (int i = 0; i < 2; i++) {
            frames.add(new TextureRegion(screen.getAtlas().findRegion("goomba"), i * 16, 0, 16, 16));
        }
        walkAnimation = new Animation<TextureRegion>(0.4f, frames);
        stateTime = 0;
        setBounds(getX(), getY(), 16 / ManymanGame.PPM, 16 / ManymanGame.PPM);
    }

    public void update(float dt){
        stateTime += dt;
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(walkAnimation.getKeyFrame(stateTime, true));
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / ManymanGame.PPM, 32 / ManymanGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / ManymanGame.PPM);
        fdef.filter.categoryBits = ManymanGame.ENEMY_BIT;
        fdef.filter.maskBits = ManymanGame.GROUND_BIT |
                ManymanGame.COIN_BIT |
                ManymanGame.ENEMY_BIT |
                ManymanGame.OBJECT_BIT |
                ManymanGame.MARIO_BIT |
                ManymanGame.BRICK_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
