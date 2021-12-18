package submarine.seacreaturescanners;

import java.util.ArrayList;
import java.util.List;

/**
 * Tracks all octopi and their energy levels.
 * The red underline I see in Eclipse suggests octopi is not the plural of
 * octopus!
 */
public class OctopusScanner
{
  /**
   * Number of rows of octopi
   */
  private final int NUM_ROWS = 10;

  /**
   * Number of columns of octopi
   */
  private final int NUM_COLS = 10;

  /**
   * All octopi
   */
  private Octopus[][] mOctopi = new Octopus[NUM_ROWS][NUM_COLS];

  /**
   * CONSTRUCTOR
   * populate the octopi list from the input
   *
   * @param input A list of rows of octopus, containing their energy level
   */
  public OctopusScanner(ArrayList<String> input)
  {
    populateOctopi(input);
  }

  /**
   * Populate our list of octopi
   *
   * @param input  A list of rows of octopus, containing their energy level
   */
  private void populateOctopi(ArrayList<String> input)
  {
    for (int rowIndex=0; rowIndex<input.size(); rowIndex++)
    {
      String octopusRow = input.get(rowIndex);

      for (int columnIndex=0; columnIndex<octopusRow.length(); columnIndex++)
      {
        //
        // Create an octopus and add it to our list of octopus
        //
        int energyLevel = Character.getNumericValue(octopusRow.toCharArray()[columnIndex]);
        Octopus octopus = new Octopus(energyLevel);
        mOctopi[rowIndex][columnIndex]=octopus;

        //
        // Populate octopus neighbours.
        // Only neighbours from the above row, and the neighbour immediately to the
        // left will have been created and populated.
        // This adds the neighbour above or left to this octopus' list of neighbours
        // but also adds this octopus to each neighbours' list of neighbours.
        // So the neighbours right and below will get added later when those octopi are
        // created.
        //
        if (rowIndex != 0)
        {
          octopus.addNeighbourToBoth(mOctopi[rowIndex-1][columnIndex]);

          if (columnIndex != 0)
          {
            octopus.addNeighbourToBoth(mOctopi[rowIndex-1][columnIndex-1]);
          }
          if (columnIndex+1 < NUM_COLS)
          {
            octopus.addNeighbourToBoth(mOctopi[rowIndex-1][columnIndex+1]);
          }
        }

        if (columnIndex != 0)
        {
          octopus.addNeighbourToBoth(mOctopi[rowIndex][columnIndex-1]);
        }
      }
    }
  }

  /**
   * Take the specified number of turns
   *
   * @param numTurns   The number of turns to take
   * @param printBoard Whether to print the full list of octopus energy levels each turn
   * @return           The number of flashes that occurred over these turns
   */
  public int takeTurns(int numTurns, boolean printBoard)
  {
    int numFlashes = 0;

    for (int turn=1; turn<=numTurns; turn++)
    {
      if (printBoard) System.out.println("Before turn: " + turn);

      //
      // Each turn, we first increment every octopus' energy level. This may cause
      // it to flash and increment its neighbours again.
      //
      for (Octopus[] row: mOctopi)
      {
        for (Octopus octopus: row)
        {
          if (printBoard) System.out.print(octopus.getEnergyLevel() + " ");
          octopus.incrementEnergyLevel();
        }

        if (printBoard) System.out.println();
      }

      if (printBoard) System.out.println();

      //
      // Once we have incremented every octopus, and it has maybe flashed, we will
      // reset the energy level of all octopi which have flashed to 0, since their
      // energy has now been exhausted.
      //
      for (Octopus[] row: mOctopi)
      {
        for (Octopus octopus: row)
        {
          boolean hasFlashed = octopus.maybeResetEnergyLevel();
          if (hasFlashed)
          {
            numFlashes++;
          }
        }
      }
    }

    return numFlashes;
  }

  /**
   * Find the first turn number on which every octopus flashes simultaneously
   *
   * @return the turn number of simultaneous flashes.
   */
  public int findSimultaneousFlashes()
  {
    boolean foundSimultaneousFlashes = false;
    int turn = 0;

    while (!foundSimultaneousFlashes)
    {
      turn++;
      int numFlashes = takeTurns(1, false);

      if (numFlashes == (NUM_ROWS * NUM_COLS))
      {
        foundSimultaneousFlashes = true;
      }
    }

    return turn;
  }

  /**
   * A dumbo octopus.
   */
  public class Octopus
  {
    /**
     * The energy level of this octopus
     */
    private int mEnergyLevel;

    /**
     * Every octopus which neighbours this octopus
     */
    private List<Octopus> mNeighbours = new ArrayList<Octopus>();

    /**
     * Whether this octopus has flashed.
     */
    private boolean mHasFlashed;

    /**
     * CONSTRUCTOR
     * Create an octopus with the specified energy level
     * @param energyLevel The energy levle of this octopus
     */
    public Octopus(int energyLevel)
    {
      mEnergyLevel = energyLevel;
    }

    /**
     * Add the specified neighbour as a neighbour to this octopus, and
     * add this octopus as a neighbour to the specified octopus.
     *
     * @param neighbourOctopus    the neighbour octopus to add.
     */
    public void addNeighbourToBoth(Octopus neighbourOctopus)
    {
      this.addNeighbour(neighbourOctopus);
      neighbourOctopus.addNeighbour(this);
    }

    /**
     * Add the specified neighbour as a neighbour to this octopus, but
     * DON'T add this octopus as a neighbour to it.
     *
     * @param neighbourOctopus    the neighbour octopus to add
     */
    private void addNeighbour(Octopus neighbourOctopus)
    {
      mNeighbours.add(neighbourOctopus);
    }

    /**
     * Increment the energy level of this octopus. Flash, if that causes us
     * to do so.
     */
    public void incrementEnergyLevel()
    {
      if (!mHasFlashed)
      {
        mEnergyLevel++;
        maybeFlash();
      }
    }

    /**
     * Flash, if the energy level is high enough.
     */
    private void maybeFlash()
    {
      if (mEnergyLevel > 9)
      {
        mHasFlashed = true;

        for (Octopus neighbour: mNeighbours)
        {
          neighbour.incrementEnergyLevel();
        }
      }
    }

    /**
     * If we have flashed, reset the energy level to 0
     *
     * @return whether we have flashed
     */
    public boolean maybeResetEnergyLevel()
    {
      boolean hasFlashed = mHasFlashed;
      if (mHasFlashed)
      {
        mEnergyLevel = 0;
        mHasFlashed = false;
      }

      return hasFlashed;
    }

    /**
     * @return   The energy level of this octopus
     */
    public int getEnergyLevel()
    {
      return mEnergyLevel;
    }
  }

}
