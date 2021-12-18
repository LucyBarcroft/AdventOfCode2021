package submarine.corefunction;

/**
 * Component responsible for moving the submarine. It holds the current position
 * of the submarine and can be given instructions for movement.
 */
public class SubmarineMotor 
{
	/**
	 * Position of the submarine - distance and depth.
	 */
	private int[] mPosition;
	
	/**
	 * Submarine aim. When moving with aim, this is a multiplier for how far 
	 * each movement command moves the submarine.
	 */
	private int mAim;

	/**
	 * CONSTRUCTOR
	 * Initialise a motor with the starting submarine position.
	 */
	public SubmarineMotor()
	{
		resetSubmarinePosition();
	}
	
	/**
	 * Possible direction instructions which this submarine understands
	 */
	public enum Direction
	{
		FORWARD,
		DOWN,
		UP
	}
	
	/**
	 * @return position of the submarine - distance and depth.
	 */
	public int[] getPosition()
	{
		return mPosition;
	}
	
	/**
	 * Reset the submarine to its starting position
	 */
	public void resetSubmarinePosition()
	{
		mPosition = new int[] {0,0};
		mAim = 0;
	}
	
	/**
	 * Submarine basic movement
	 *
	 * @param direction The direction in which the submarine should move
	 * @param distance  The distance in which the submarine should move in
	 *                  that direction.
	 */
	public void move(Direction direction, int distance)
	{
		switch(direction)
		{
		    case FORWARD:
		    {
		    	mPosition[0] += distance;
		    	break;
		    }
		    case DOWN:
		    {
		    	mPosition[1] += distance;
		    	break;
		    }
		    case UP: 
		    {
		    	mPosition[1] -= distance;
		    	break;
		    }
		    default:
		    {
		    	System.err.println("Unrecognised direction: " + direction);
		    }
		}
	}
	
	/**
	 * Move the submarine with aim. 
	 * Up and down commands just change the aim.
	 * Forward commands change the horizontal position by the direction, and
	 * the depth by the multiplier of the position and the aim.
	 *
	 * @param direction  The direction instruction
	 * @param distance   The distance the submarine should move
	 */
	public void moveWithAim(Direction direction, int distance)
	{
		switch(direction)
		{
		    case FORWARD:
		    {
		    	mPosition[0] += distance;
		    	mPosition[1] += (mAim * distance);
		    	break;
		    }
		    case DOWN:
		    {
		    	mAim += distance;
		    	break;
		    }
		    case UP: 
		    {
		    	mAim -= distance;
		    	break;
		    }
		    default:
		    {
		    	System.err.println("Unrecognised direction: " + direction);
		    }
		}
	}
	
}
