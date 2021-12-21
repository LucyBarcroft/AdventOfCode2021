package days;

import java.util.ArrayList;

import submarine.entertainment.OrigamiFolder;
import utils.GenericDay;

/**
 * --- Day 13: Transparent Origami ---
 *
 * Fold a transparent piece of paper with a pattern. Which points are marked?
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
    ArrayList<String> input = day.getInput(false);

    //
    // Part 1. Fold the paper once and work out how many spots marked
    //
    OrigamiFolder origamiFolder = new OrigamiFolder(input);
    origamiFolder.foldOnce();
    System.out.println(origamiFolder.getNumTrue());

    //
    // Part 2. This requires a user to look at the output letters and understand it.
    // Parsing the letters programmatically would be a lot of effort and I doubt that's
    // what they were intending...
    //
    origamiFolder = new OrigamiFolder(input);
    origamiFolder.foldAll();
    origamiFolder.printPaper();
  }
}
