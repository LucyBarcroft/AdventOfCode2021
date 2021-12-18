/**
 *
 */
package days;

import submarine.seacreaturescanners.LanternfishScanner;
import utils.GenericDay;

/**
 * --- Day 6: Lanternfish ---
 *
 * Exponential growth of lanternfish. How many lanternfish?
 */
public class Day06 extends GenericDay
{
	/**
	 * The number of days after which to assess the number of lanternfish
	 * for part1
	 */
	private static final int NUM_DAYS_PART1 = 80;

	/**
	 * The number of days after which to assess the number of lanternfish
	 * for part2
	 */
	private static final int NUM_DAYS_PART2 = 256;

	/**
	 * CONSTRUCTOR
	 */
	protected Day06()
	{
		super(6);
	}

	public void doPart1(int[] startingLanternfish)
	{
    LanternfishScanner lanternfishScanner = new LanternfishScanner(startingLanternfish);
    long numLanternfish = lanternfishScanner.getNumLanternfish(NUM_DAYS_PART1);
    System.out.println("After " + NUM_DAYS_PART1 + " days, there are " + numLanternfish + " lanternfish");
	}

	public void doPart2(int[] startingLanternfish)
	{
    LanternfishScanner lanternfishScanner = new LanternfishScanner(startingLanternfish);
	  long numLanternfish = lanternfishScanner.getNumLanternfish(NUM_DAYS_PART2);
    System.out.println("After " + NUM_DAYS_PART2 + " days, there are " + numLanternfish + " lanternfish");
	}

	public void run(int[] startingLanternfish, boolean doPart1, boolean doPart2)
	{
	  if (doPart1) doPart1(startingLanternfish);
	  if (doPart2) doPart2(startingLanternfish);
	}

	public static void main(String[] args)
	{
		Day06 day = new Day06();
		int[] startingLanternfish = day.readInputAsIntArrayFromSingleLine(false, ",");
    day.run(startingLanternfish, true, true);
	}
}
