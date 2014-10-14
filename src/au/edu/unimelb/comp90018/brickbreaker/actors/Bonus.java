package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.DynamicGameObject;

import com.badlogic.gdx.math.Vector2;
/**
 * Bonus generic class allow us to create Bonus object that can be a coin, virus, extra life, etc.
 * @author Diego
 *
 */
public class Bonus extends DynamicGameObject {

	private BonusType type;
	
	/**
	 * Type of Bonuses
	 * @author Diego
	 *
	 */
	public enum BonusType {
	    COIN(20,20),
	    VIRUS(20,20),
	    EXTRA_LIFE(20,20);
	    
	    private int bonusWidth; 
		private int bonusHeight;

		/**
		 * Constructor
		 * @param width
		 * @param height
		 */
        private BonusType(int width, int height) {
                this.setBonusWidth(width);
                this.setBonusHeight(height);
        }

        /**
         * Get bonus Width
         * @return bonusWidth
         */
		public int getBonusWidth() {
			return bonusWidth;
		}

		/**
		 * Set Bonus Width
		 * @param bonusWidth
		 */
		public void setBonusWidth(int bonusWidth) {
			this.bonusWidth = bonusWidth;
		}
		
		/**
		 * Get Bonus Height
		 * @return
		 */
		public int getBonusHeight() {
			return bonusHeight;
		}

		/**
		 * Set Bonus Height
		 * @param bonusHeight
		 */
		public void setBonusHeight(int bonusHeight) {
			this.bonusHeight = bonusHeight;
		}
	}
	
	/**
	 * Constructor
	 * @param x
	 * @param y
	 * @param type
	 * @param velocity
	 */
	public Bonus(float x, float y, BonusType type, Vector2 velocity) {
		super(x, y, type.getBonusWidth(), type.getBonusHeight());
		this.velocity.set(velocity);
		this.setType(type);
	}

	/**
	 * Update screen
	 * @param deltaTime
	 */
	public void update(float deltaTime) {

		position.add(0, -9.8f * deltaTime * velocity.y);
		bounds.y = position.y - bounds.height / 2;
		
		if (position.y < 0)
			pulverize();
		
	}

	/**
	 * Pulverize object
	 */
	public void pulverize() {
		//disapear
		position.y = -10000;
		bounds.y = position.y - bounds.height / 2;
	}

	/**
	 * Return type of Bonus
	 * @return type
	 */	
	public BonusType getType() {
		return type;
	}

	/**
	 * Set type of Bonus
	 * @param type
	 */
	public void setType(BonusType type) {
		this.type = type;
	}
}
