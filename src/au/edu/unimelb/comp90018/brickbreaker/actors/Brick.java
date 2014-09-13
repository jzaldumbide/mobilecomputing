package au.edu.unimelb.comp90018.brickbreaker.actors;

public interface Brick {

	public boolean isPulverised();
	
	public void hitMe();

	public void update(float deltaTime);

}
