package days;

import java.util.ArrayList;
import java.util.Arrays;

import submarine.environmentmappers.VentMapper;
import utils.GenericDay;

/**
 * --- Day 5: Hydrothermal Venture ---
 *
 * In how many places do vents overlap?
 * I will come back and do day 5 at some point...
 */
public class Day05 extends GenericDay {

	/**
	 * CONSTRUCTOR
	 */
	public Day05()
	{
		super(5);
	}

	public static void main(String[] args)
	{
		Day05 day = new Day05();
		ArrayList<String> input = day.getInput(true);

		for (String line: input)
		{
		    String[] startAndFinish = line.split(" -> ");
		    int[] startCoord = Arrays.stream(startAndFinish[0].split(","))
							         .mapToInt(x -> Integer.valueOf(x))
							         .toArray();
		    int[] endCoord = Arrays.stream(startAndFinish[1].split(","))
							       .mapToInt(x -> Integer.valueOf(x))
							       .toArray();


	}

}
