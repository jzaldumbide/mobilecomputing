package au.edu.unimelb.comp90018.brickbreaker.framework;

/**
 * 
 * We need it to solve a little MVC problem: when do we play sound effects? 
 * We could just add invocations of Assets.playSound()to the respective simulation classes, 
 * but that’s not very clean.
 * 
 * @author Diego
 *
 */

public interface WorldListener {
	
	public void hitPaddle ();

	public void hitBrick ();
	
	public void lifeLost();
	
	public void gameOver();
	
}
