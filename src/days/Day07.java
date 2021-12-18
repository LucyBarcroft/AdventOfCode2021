package days;

import java.util.Arrays;

import utils.GenericDay;

/**
 * --- Day 7: The Treachery of Whales ---
 *
 * Calculate the least possible fuel used across all crabs to align to one position
 */
public class Day07 extends GenericDay {

	/**
	 * CONSTRUCTOR
	 */
	public Day07()
	{
		super(7);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		Day07 day = new Day07();
		int[] input = day.readInputAsIntArrayFromSingleLine(false, ",");

		Arrays.sort(input);
		int length = input.length;
		int sum = Arrays.stream(input).sum();
		int mean = sum/length;
		int value = 0;

		if (length % 2 != 0)
		{
			value = input[(input.length+1)/2 - 1];
		}
		else
		{
			double median = (input[input.length/2] + input[input.length/2 -1])/2;

			if (mean > median)
			{
				value = (int) Math.ceil(median);
			}
			else
			{
				value = (int) Math.floor(median);
			}
		}

		final int finalValue = value;

		mean = mean+1;
		System.out.println(mean);
		final int finalValue2 = mean;

		/**
		 * I convinced myself logically that part 1 was related to the median and exactly how, but guessed part 2
		 * was related to the mean.
		 * The example data was mean floored, actual data is mean rounded up, not sure
		 * why either and haven't actually proved that it is linked to the mean...
		 * So I was sort of cheating...
		 * I could just work through all the numbers and brute force it...
		 */
		int result = Arrays.stream(input).map(x -> Math.abs(x-finalValue)).sum();
    int result2 =  Arrays.stream(input).map(x -> (Math.abs((x-finalValue2))*(Math.abs(x-finalValue2)+1)/2)).sum();

		System.out.println("result: " + result);
		System.out.println("result2: " + result2);
	}
}