package days;

import java.util.ArrayList;

import submarine.seacreaturescanners.OctopusScanner;
import utils.GenericDay;

/**
 * --- Day 11: Dumbo Octopus ---
 *
 * Track the energy level of a set of octopi.
 */
public class Day11 extends GenericDay
{
  /**
   * The number of turns to take
   */
  private final int NUM_TURNS = 100;

  /**
   * CONSTRUCTOR
   */
  public Day11()
  {
    super(11);
  }

  /**
   * Run Day 11 part 1
   * Take 100 turns, and count the total number of flashes
   *
   * @param octopusRows   List of rows of octopus energy levels
   */
  public void doPart1(ArrayList<String> octopusRows)
  {
    OctopusScanner octopusScanner = new OctopusScanner(octopusRows);
    int numFlashes = octopusScanner.takeTurns(NUM_TURNS, false);
    System.out.println("num flashes: " + numFlashes);
  }

  /**
   * Run Day 11 part 2
   * Find out on which turn all octopi first flash simultaneously.
   *
   * @param octopusRows   List of rows of octopus energy levels
   */
  public void doPart2(ArrayList<String> octopusRows)
  {
    OctopusScanner octopusScanner = new OctopusScanner(octopusRows);
    int turnSimultaneous = octopusScanner.findSimultaneousFlashes();
    System.out.println("simultaneous flashes: " + turnSimultaneous);
  }

  /**
   * Run this day
   *
   * @param octopusRows    List of rows of octopus energy levels
   * @param doPart1        Whether to run part1
   * @param doPart2        Whether to run part2
   */
  public void run(ArrayList<String> octopusRows, boolean doPart1, boolean doPart2)
  {
    if (doPart1) doPart1(octopusRows);
    if (doPart2) doPart2(octopusRows);
  }

  /**
   * MAIN METHOD
   */
  public static void main(String args[])
  {
    Day11 day = new Day11();
    ArrayList<String> octopusRows = day.getInput(false);
    day.run(octopusRows, false, true);
  }

}
