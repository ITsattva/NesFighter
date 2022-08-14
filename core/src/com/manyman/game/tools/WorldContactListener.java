package com.manyman.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.manyman.game.ManymanGame;
import com.manyman.game.sprites.Mario;
import com.manyman.game.sprites.enemies.Enemy;
import com.manyman.game.sprites.items.Item;
import com.manyman.game.sprites.tileobjects.InteractiveTileObject;

public class WorldContactListener implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cdef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;



        switch (cdef) {
            case ManymanGame.MARIO_HEAD_BIT | ManymanGame.BRICK_BIT:
            case ManymanGame.MARIO_HEAD_BIT | ManymanGame.COIN_BIT:
                if (fixA.getFilterData().categoryBits == ManymanGame.MARIO_HEAD_BIT){
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit();
                } else {
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit();
                }
                break;
            case ManymanGame.MARIO_BIT | ManymanGame.ENEMY_HEAD_BIT:
                if (fixA.getFilterData().categoryBits == ManymanGame.ENEMY_HEAD_BIT)
                    ((Enemy) fixA.getUserData()).hitOnHead();
                else
                    ((Enemy) fixB.getUserData()).hitOnHead();
                break;
            case ManymanGame.ENEMY_BIT | ManymanGame.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == ManymanGame.ENEMY_BIT)
                    ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case ManymanGame.MARIO_BIT | ManymanGame.ENEMY_BIT:
                Gdx.app.log("MARIO DIED", "");
                break;
            case ManymanGame.ENEMY_BIT | ManymanGame.ENEMY_BIT:
                ((Enemy) fixA.getUserData()).reverseVelocity(true, false);
                ((Enemy) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case ManymanGame.ITEM_BIT | ManymanGame.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == ManymanGame.ITEM_BIT)
                    ((Item) fixA.getUserData()).reverseVelocity(true, false);
                else
                    ((Item) fixB.getUserData()).reverseVelocity(true, false);
                break;
            case ManymanGame.ITEM_BIT | ManymanGame.MARIO_BIT:
                if (fixA.getFilterData().categoryBits == ManymanGame.ITEM_BIT)
                    ((Item) fixA.getUserData()).use((Mario) fixB.getUserData());
                else
                    ((Item) fixB.getUserData()).use((Mario) fixA.getUserData());
                break;

        }

    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
