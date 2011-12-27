package org.mowitnow;

import org.apache.log4j.Logger;

/**
 * This Class Represents the Position 
 * Of the Mower and implements Method 
 * For moving it inside the Lawn
 * @author saadrguig
 *
 */
public class Position {

	private int x;
	private int y;
	private char orientation;
	protected static Logger log = Logger.getLogger(Position.class);
	
	/**
	 * Position Constructor.
	 * @param x
	 * @param y
	 * @param orientation
	 */
	public Position(int x, int y, char orientation) {
		super();
		this.x = x;
		this.y = y;
		this.orientation = orientation;
	}

	public Position() {
		super();
	}

	/**
	 * Method Generates the New Position
	 * the mower is going to move to !
	 * @param instruction
	 * @param topY
	 * @param rightX
	 * @return Position (Aimed Position)
	 */
	public Position processAction(char instruction,int topY, int rightX){
		Position newPosition = new Position(getX(),getY(),getOrientation());
		// Update position details when instruction is 'A'
		if (instruction == Constants.INSTRUCTION_AVANCE){
			switch(orientation){
			case Constants.POSITION_EST:
				newPosition.setX(getX()+1);
				break;
			case Constants.POSITION_OUEST:
				newPosition.setX(getX()-1);
				break;
			case Constants.POSITION_NORD:
				newPosition.setY(getY()+1);
				break;
			case Constants.POSITION_SUD:
				newPosition.setY(getY()-1);
				break;
			}
			//Check Condition is called only when instruction is 'A'
			if(checkCondition(newPosition,topY,rightX))
				return newPosition;
			else
				return null;
		}
		else{
			// For instruction 'G' and 'D' only update orientation
			newPosition.setOrientation(getNewOrientation(instruction));
			return newPosition;
		}
	}
	/**
	 * Method check if the next Position is
	 * possible or not.
	 * @param position
	 * @param topY
	 * @param rightX
	 * @return boolean
	 */
	// Check if the new Position will be vaild
	public static boolean checkCondition(Position position,int topY, int rightX){
		if(	position.getX() <0 ||position.getX() >rightX ||
				position.getY() <0 ||position.getY()>topY){
			log.warn("Conditions failed on Position:"+position);
			return false;
		}
		else 
			return true;
	}


	/**
	 * Method apply instruction to the current Positon
	 * and return the new direction
	 * 
	 * @param instruction
	 * @return char
	 */
	public char getNewOrientation(char instruction) {
		switch(instruction){
		case Constants.INSTRUCTION_AVANCE:
			return orientation;
		case Constants.INSTRUCTION_DROITE:
			switch(orientation){
			case Constants.POSITION_NORD:
				return Constants.POSITION_EST;
			case Constants.POSITION_EST:
				return Constants.POSITION_SUD;
			case Constants.POSITION_SUD:
				return Constants.POSITION_OUEST;
			case Constants.POSITION_OUEST:
				return Constants.POSITION_NORD;
			}
		case Constants.INSTRUCTION_GAUCHE:
			switch(orientation){
			case Constants.POSITION_NORD:
				return Constants.POSITION_OUEST;
			case Constants.POSITION_OUEST:
				return Constants.POSITION_SUD;
			case Constants.POSITION_SUD:
				return Constants.POSITION_EST;
			case Constants.POSITION_EST:
				return Constants.POSITION_NORD;
			}
		}
		return 0;
	}

	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public char getOrientation() {
		return orientation;
	}
	public void setOrientation(char orientation) {
		this.orientation = orientation;
	}

	@Override
	public String toString() {
		return "Position [x=" + x + ", y=" + y + ", orientation=" + orientation
		+ "]";
	}
	
	/**
	 * Method formats the Position to be inserted in the
	 * output file.
	 * @return Formated String
	 */
	public String toFormatedString() {
		return x + " " + y + " " + orientation +"\n";
	}
	
	
}
