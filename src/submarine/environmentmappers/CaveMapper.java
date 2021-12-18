package submarine.environmentmappers;

import java.util.ArrayList;
import java.util.HashMap;

public class CaveMapper
{
  /**
   * A list of all caves, stored off by name - which is unique
   */
  private HashMap<String, Cave> mCaveList = new HashMap<String, Cave>();

  /**
   * CONSTRUCTOR
   * Create a CaveMapper from input
   *
   * @param caveInput    The input from which to populate the cave map.
   *                     Expected format, each line AX-bz indicates a path
   *                     between two caves with corresponding names
   */
  public CaveMapper(ArrayList<String> caveInput)
  {
    populateCaveList(caveInput);
  }

  /**
   * Populate the list of caves from the input
   *
   * @param caveInput    The input from which to populate the cave list
   */
  private void populateCaveList(ArrayList<String> caveInput)
  {
    for (String cavePath: caveInput)
    {
      //
      // Expected format of each line is e.g AX-bz
      // This indicates that cave names AX is linked to cave named bz
      // If we have already created a cave with each name, then find the
      // existing cave, if not then create a new one.
      // Add the caves as neighbours of each other.
      //
      String[] firstAndSecondCave = cavePath.split("-");
      String firstCaveName = firstAndSecondCave[0];
      String secondCaveName = firstAndSecondCave[1];
      Cave firstCave;
      Cave secondCave;

      if (mCaveList.containsKey(firstCaveName))
      {
        firstCave = mCaveList.get(firstCaveName);
      }
      else
      {
        firstCave = new Cave(firstCaveName);
        mCaveList.put(firstCaveName, firstCave);
      }

      if (mCaveList.containsKey(secondCaveName))
      {
        secondCave = mCaveList.get(secondCaveName);
      }
      else
      {
        secondCave = new Cave(secondCaveName);
        mCaveList.put(secondCaveName, secondCave);
      }

      firstCave.addNeighbourToBoth(secondCave);
    }
  }

  /**
   * @return    The number of paths from start to end, where each small cave
   *            can only be visited once.
   */
  public int getNumPaths(boolean canVisitTwice)
  {
    int numPaths = 0;
    Cave startCave = mCaveList.get("start");

    numPaths = startCave.getNumPaths(canVisitTwice);

    return numPaths;
  }

  /**
   * The type of cave.
   */
  public static enum CaveType
  {
    START,
    END,
    LARGE,
    SMALL
  }

  /**
   * Class representing a cave.
   *
   */
  public class Cave
  {
    private String mName;
    private CaveType mCaveType;
    private boolean mHasBeenVisited;
    private int mNumVisited = 0;
    private ArrayList<Cave> mNeighbours = new ArrayList<Cave>();

    public Cave(String name)
    {
      mName = name;

      if (name.toUpperCase().equals(String.valueOf(CaveType.START)) ||
          name.toUpperCase().equals(String.valueOf(CaveType.END)))
      {
        mCaveType = CaveType.valueOf(name.toUpperCase());
      }
      else if (name.equals(name.toUpperCase()))
      {
        mCaveType = CaveType.LARGE;
      }
      else
      {
        mCaveType = CaveType.SMALL;
      }
    }

    public void addNeighbourToBoth(Cave neighbour)
    {
      mNeighbours.add(neighbour);
      neighbour.addNeighbour(this);
    }

    public void addNeighbour(Cave neighbour)
    {
      mNeighbours.add(neighbour);
    }

    public boolean getHasBeenVisited()
    {
      return mHasBeenVisited;
    }

    public int getNumVisited()
    {
      return mNumVisited;
    }

    public CaveType getCaveType()
    {
      return mCaveType;
    }

    /**
     * Get the number of paths from this cave to the end, dependent on whether we
     * can visit small caves twice, or only once.
     * This presumes we can only visit one small cave twice.
     *
     * @param canVisitTwice      Whether or not we can visit small caves twice.
     * @return                   The number of paths from this cave to the end
     */
    public int getNumPaths(boolean canVisitTwice)
    {
      mNumVisited += 1;
      int numPaths = 0;

      if (canVisitTwice && mNumVisited == 2 && mCaveType.equals(CaveType.SMALL))
      {
        canVisitTwice = false;
      }

      for (Cave neighbour: mNeighbours)
      {
        boolean cantVisitSmallCave = canVisitTwice ? neighbour.getNumVisited() >= 2:
                                                     neighbour.getNumVisited() >= 1;

        if (!(cantVisitSmallCave && neighbour.getCaveType().equals(CaveType.SMALL)) &&
            !(neighbour.getCaveType().equals(CaveType.START)))
        {
          if (neighbour.getCaveType().equals(CaveType.END))
          {
            numPaths += 1;
          }
          else
          {
            numPaths += neighbour.getNumPaths(canVisitTwice);
          }
        }
      }

      mNumVisited -= 1;
      return numPaths;
    }
  }

}
