package days;

import submarine.environmentmappers.SeaFloorMapper;
import utils.GenericDay;

/**
 * --- Day 1: Sonar Sweep ---
 *
 * How often does the water depth around a submarine increase?
 */
public class Day01 extends GenericDay
{
  /**
   * Initialise Day1 for utilities functions.
   */
  protected Day01()
  {
    super(1);
  }

  /**
   * Run Day1.
   *
   * @param doPart1       whether to run day 1 part 1
   * @param doPart2       whether to run day 1 part 2
   */
  public void run(boolean doPart1, boolean doPart2)
  {
    int[] depthMeasurements = this.getInputAsIntArray(false);

    SeaFloorMapper seaFloorMapper = new SeaFloorMapper(depthMeasurements);

    //
    // For part 1, we care about the number of times a measurement has
    // increased from the previous measurement
    // For part 2, we care about the number of times the sum of the previous
    // three measurements is higher than the previous sum - which equates to
    // the number of times the depth measurement has increased since the reading
    // three prior
    //
    if (doPart1) System.out.println("number of increase: " + seaFloorMapper.measureIncreases(1));
    if (doPart2) System.out.println("number of increase2: " + seaFloorMapper.measureIncreases(3));
  }

  /**
   * Main method. Run day1.
   */
  public static void main(String[] args)
  {
    Day01 day = new Day01();
    day.run(true, true);
  }
}
