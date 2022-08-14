package com.manyman.game.sprites.enemies;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.manyman.game.ManymanGame;
import com.manyman.game.screens.PlayScreen;

public class Turtle extends Enemy {
    public enum State {WALKING, SHELL}
    public State currentState;
    public State previousState;
    private float stateTime;
    private Animation<TextureRegion> walkAnimation;
    private Array<TextureRegion> frames;
    private TextureRegion shell;
    private boolean setToDestroy;
    private boolean destroyed;

    public Turtle(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        frames = new Array<>();
        frames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"), 0 ,0,16,24));
        frames.add(new TextureRegion(screen.getAtlas().findRegion("turtle"), 16 ,0,16,24));

        shell = new TextureRegion(screen.getAtlas().findRegion("turtle"), 64 ,0,16,24);
        walkAnimation = new Animation<TextureRegion>(0.2f, frames);
        currentState = previousState = State.WALKING;

        setBounds(getX(), getY(), 16/ ManymanGame.PPM, 24/ManymanGame.PPM);
    }

    @Override
    public void hitOnHead() {
        if(currentState != State.SHELL) {
            currentState = State.SHELL;
            velocity.x = 0;
        }
    }

    @Override
    protected void defineEnemy() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
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
        b2body.createFixture(fdef).setUserData(this);

        PolygonShape head = new PolygonShape();
        Vector2[] vertice = new Vector2[4];
        vertice[0] = new Vector2(-5, 8).scl(1 / ManymanGame.PPM);
        vertice[1] = new Vector2(5, 8).scl(1 / ManymanGame.PPM);
        vertice[2] = new Vector2(-3, 3).scl(1 / ManymanGame.PPM);
        vertice[3] = new Vector2(3, 3).scl(1 / ManymanGame.PPM);
        head.set(vertice);

        fdef.shape = head;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = ManymanGame.ENEMY_HEAD_BIT;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void update(float dt) {
        setRegion(getFrame(dt));
        if(currentState == State.SHELL && stateTime > 5) {
            currentState = State.WALKING;
            velocity.x = 1;
        }

        setPosition(b2body.getPosition().x - getWidth()/2, b2body.getPosition().y - 8 /ManymanGame.PPM);
        b2body.setLinearVelocity(velocity);
    }

    private TextureRegion getFrame(float dt) {
        TextureRegion region;

        switch (currentState){
            case SHELL:
                region = shell;
                break;
            case WALKING:
            default:
                region = walkAnimation.getKeyFrame(stateTime, true);
                break;
        }

        if(velocity.x > 0 && region.isFlipX() == false){
            region.flip(true, false);
        }
        if(velocity.x < 0 && region.isFlipX() == true){
            region.flip(true, false);
        }
        stateTime = currentState == previousState ? stateTime + dt : 0;
        previousState = currentState;
        return region;
    }
}
