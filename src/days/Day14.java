package days;

import java.util.ArrayList;

import submarine.corefunction.PolymerAnalyser;
import utils.GenericDay;

/**
 * --- Day 14: Extended Polymerization ---
 *
 * Follow a bunch of rules, to insert more letters into a polymer sequence between
 * occurrences of pairs of letters.
 */
public class Day14 extends GenericDay
{
  private static final int S_NUM_INSERTS_PART1 = 10;
  private static final int S_NUM_INSERTS_PART2 = 40;

  public Day14()
  {
    super(14);
  }

  public static void main(String args[])
  {
    Day14 day = new Day14();
    ArrayList<String> input = day.getInput(false);
    PolymerAnalyser polymerAnalyser = new PolymerAnalyser(input);
    polymerAnalyser.doInserts(S_NUM_INSERTS_PART2);
    System.out.println(polymerAnalyser.getResult());
  }
}
