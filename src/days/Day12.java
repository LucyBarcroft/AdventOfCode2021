package days;

import java.util.ArrayList;

import submarine.environmentmappers.CaveMapper;
import utils.GenericDay;

/**
 * --- Day 12: Passage Pathing ---
 *
 * Find the number of possible paths through a cavesystem.
 */
public class Day12 extends GenericDay
{
  /**
   * CONSTRUCTOR
   */
  public Day12()
  {
    super(12);
  }

  public static void main(String[] args)
  {
    Day12 day = new Day12();
    ArrayList<String> caveInput = day.getInput(false);
    CaveMapper caveMapper = new CaveMapper(caveInput);

    // Part 1
    System.out.println(caveMapper.getNumPaths(false));

    // Part 2
    System.out.println(caveMapper.getNumPaths(true));
  }
}
