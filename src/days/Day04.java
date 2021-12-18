package days;

import java.util.ArrayList;

import submarine.Submarine;
import submarine.entertainment.BingoSubsystem;
import utils.GenericDay;

/**
 * --- Day 4: Giant Squid ---
 *
 * Play Bingo!
 */
public class Day04 extends GenericDay
{
  /**
   * Set up day 4
   */
  public Day04()
  {
    super(4);
  }

  /**
   * @param args
   */
  public static void main(String[] args)
  {
    Day04 day = new Day04();
    ArrayList<String> input = day.getInput(false);

    Submarine submarine = new Submarine();
    BingoSubsystem bingoSubsystem = submarine.getBingoSubsytsem();
    bingoSubsystem.populateBingoSubsystemFromInput(input);
    bingoSubsystem.playBingo();
    bingoSubsystem.loseAtBingo();
  }

}
