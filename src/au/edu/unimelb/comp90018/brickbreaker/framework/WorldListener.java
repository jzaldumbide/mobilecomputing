package au.edu.unimelb.comp90018.brickbreaker.framework;

/**
 * 
 * We need it to solve a little MVC problem: when do we play sound effects? We
 * could just add invocations of Assets.playSound() to the respective simulation
 * classes, but that’s not very clean.
 * 
 */

public interface WorldListener {

	public void getBonusLife();

	public void getBonusCoins();

	public void getBonusBad();

	public void hitHardBrick();

	public void gameWin();

	public void hitWall();

	public void hitPaddle();

	public void hitBrick();

	public void lifeLost();

	public void gameOver();

}
