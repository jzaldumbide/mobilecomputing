package au.edu.unimelb.comp90018.brickbreaker.framework.impl;

import au.edu.unimelb.comp90018.brickbreaker.framework.Collideable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

@Deprecated
public class BrickBreakerContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		Collideable obj1 = (Collideable) contact.getFixtureA().getBody().getUserData();
		Collideable obj2 = (Collideable) contact.getFixtureB().getBody().getUserData();
		obj1.contact(obj2);
		obj2.contact(obj1);
		Gdx.app.log("beginContact", "between " + obj1.toString() + " and " + obj2.toString());
	}

	@Override
	public void endContact(Contact contact) {
		Collideable obj1 = (Collideable) contact.getFixtureA().getBody().getUserData();
		Collideable obj2 = (Collideable) contact.getFixtureB().getBody().getUserData();
		obj1.contact(obj2);
		obj2.contact(obj1);
		Gdx.app.log("endContact", "between " + obj1.toString() + " and " + obj2.toString());

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}
