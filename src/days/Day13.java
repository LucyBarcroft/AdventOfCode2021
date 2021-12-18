package days;

import java.util.ArrayList;

import utils.GenericDay;

/**
 * --- Day 13: Transparent Origami ---
 *
 * Fold a transparent piece of paper with a pattern. How many points appear marked?
 */
public class Day13 extends GenericDay
{
  public Day13()
  {
    super(13);
  }

  public static void main(String arg[])
  {
    Day13 day = new Day13();
    ArrayList<String> input = day.getInput(true);

    // fold along y
    // new array is half the number of rows - same number of columns
    // each point is marked if it was originally or (oldNumRows-point), column is marked

    // fold along y
    // new array is half the number of columns - same number of rows
    // each point is marked if it was originally or row, (oldNumColumns-point) is marked




  }
}
