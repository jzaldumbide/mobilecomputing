package au.edu.unimelb.comp90018.brickbreaker.actors;

import au.edu.unimelb.comp90018.brickbreaker.framework.GameObject;

/**
 * Generic touching Button
 * @author Diego
 *
 */
public class Button extends GameObject {
		
	public enum ButtonSize {
	    SMALL_SQUARE(16,16),
	    MEDIUM_SQUARE(32,32),
	    LARGE_SQUARE(48,48),
	    XLARGE_SQUARE(64,64),
	    SMALL_RECTANGLE(16,8),
	    MEDIUM_RECTNGLE(32,16),
	    LARGE_RECTANGLE(64,32),
	    XLARGE_RECTANGLE(160,32);
	    
	    private int buttonWidth; 
		private int buttonHeight;

		/**
		 * ButtonSize Constructor
		 * @param width
		 * @param height
		 */		 
        private ButtonSize(int width, int height) {
                this.setButtonWidth(width);
                this.setButtonHeight(height);
        }

        /**
         * Get Button Width
         * @return buttonWidth
         */
		public int getButtonWidth() {
			return buttonWidth;
		}

		/**
		 * Set button Width
		 * @param buttonWidth
		 */
		public void setButtonWidth(int buttonWidth) {
			this.buttonWidth = buttonWidth;
		}

		/**
		 * Get button Height
		 * @return buttonHeight
		 */
		public int getButtonHeight() {
			return buttonHeight;
		}

		/**
		 * Set button Height
		 * @param buttonHeight
		 */
		public void setButtonHeight(int buttonHeight) {
			this.buttonHeight = buttonHeight;
		}
	    
	}	

	/**
	 * Button Constructor 
	 * @param x
	 * @param y
	 * @param size
	 */
	public Button(float x, float y, ButtonSize size) {
		super(x, y, size.getButtonWidth(), size.getButtonHeight());
	}

	public void update(float deltaTime) {

	}
}
