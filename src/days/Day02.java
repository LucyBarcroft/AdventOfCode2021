package days;

import java.util.ArrayList;

import submarine.Submarine;
import submarine.corefunction.SubmarineMotor;
import utils.GenericDay;

/**
 * --- Day 2: Dive! ---
 *
 * Move the submarine according to commands and obtain the final position.
 */
public class Day02 extends GenericDay
{
	/**
	 * Initialise Day2 for utilities functions.
	 */
	protected Day02()
	{
		super(2);
	}

	/**
	 * Run Day2.
	 *
	 * @param doPart1       whether to run day 2 part 1
	 * @param doPart2       whether to run day 2 part 2
	 */
	public void run(boolean doPart1, boolean doPart2)
	{
	  // I could probably commonalise part1 and part2 a bit more if I wanted to.
		// They are doing the same thing, but one move() and one moveWithAim()
		if (doPart1) doPart1();
		if (doPart2) doPart2();
	}

	/**
	 * Run day2 part 1 - move the submarine based on simple movement commands.
	 */
	public void doPart1()
	{
		ArrayList<String> input = this.getInput(false);
		Submarine submarine = new Submarine();
		SubmarineMotor subMotor = submarine.getMotor();

		//---------------------------------------------------------------------
		// Each input is a simple movement instruction.
		//---------------------------------------------------------------------
		for (String instruction : input)
		{
			String[] directionAndDistance = instruction.split(" ");

			SubmarineMotor.Direction direction =
					          SubmarineMotor.Direction.valueOf(
							            directionAndDistance[0].toUpperCase());
			int distance = Integer.parseInt(directionAndDistance[1]);
			subMotor.move(direction, distance);
		}

		//---------------------------------------------------------------------
		// Having followed every movement instruction, the final answer is
		// the multiplier of its co-ordinates.
		//---------------------------------------------------------------------
		int[] finalPosition = submarine.getMotor().getPosition();
		int result = finalPosition[0] * finalPosition[1];

		System.out.println("result: " + result);
	}

	/**
	 * Run day2 part 2 - move the submarine based on moving with aim.
	 */
	public void doPart2()
	{
		ArrayList<String> input = this.getInput(false);
		Submarine submarine = new Submarine();
		SubmarineMotor subMotor = submarine.getMotor();

		//---------------------------------------------------------------------
		// Each input is an instruction to move with aim, or change aim.
		//---------------------------------------------------------------------
		for (String instruction : input)
		{
			String[] directionAndDistance = instruction.split(" ");

			SubmarineMotor.Direction direction =
					      SubmarineMotor.Direction.valueOf(
							            directionAndDistance[0].toUpperCase());
			int distance = Integer.parseInt(directionAndDistance[1]);
			subMotor.moveWithAim(direction, distance);
	    }

		//---------------------------------------------------------------------
		// Having followed every movement instruction, the final answer is
		// the multiplier of its co-ordinates.
		//---------------------------------------------------------------------
		int[] finalPosition = submarine.getMotor().getPosition();
		int result = finalPosition[0] * finalPosition[1];

		System.out.println("result: " + result);
	}

	/**
	 * Main method.
	 * Run day2.
	 */
	public static void main(String[] args)
	{
    Day02 day = new Day02();
    day.run(false, true);
	}
}
