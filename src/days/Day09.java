/**
 *
 */
package days;

import java.util.ArrayList;

import submarine.environmentmappers.BasinMapper;
import utils.GenericDay;

/**
 * --- Day 9: Smoke Basin ---
 *
 * From a heightmap, determine the low points, and the size of the basins.
 */
public class Day09 extends GenericDay
{
  /**
   * CONSTRUCTOR
   */
  public Day09()
  {
    super(9);
  }

  public static void main(String[] args)
  {
    Day09 day = new Day09();
    ArrayList<String> input = day.getInput(true);
    BasinMapper basinMapper = new BasinMapper(input);
    System.out.println("risk levels: " + basinMapper.getSumOfRiskLevels());

    int largestBasinScore = basinMapper.calculateLargestBasins(input);
    System.out.println("largest basin score: " + largestBasinScore);
  }
}
