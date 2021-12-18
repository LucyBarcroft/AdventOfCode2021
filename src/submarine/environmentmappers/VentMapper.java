package submarine.environmentmappers;

import java.util.ArrayList;
import java.util.Arrays;

public class VentMapper
{

  private ArrayList<VentLocation> mVentLocations = new ArrayList<VentLocation>();

  public VentMapper()
  {
    // TODO Auto-generated constructor stub
  }

  private class VentLocation
  {
    private int[] mCoord;
    private int mNumberOfVents;

    public boolean locationMatches(int[] coord)
    {
      return ((mCoord[0] == coord[0]) &&
              (mCoord[1] == coord[1]));
    }

    public boolean equals(VentLocation location)
    {
      return ((mCoord[0] == location.getCoord()[0]) &&
              (mCoord[1] == location.getCoord()[1]));
    }

    public int[] getCoord()
    {
      return mCoord;
    }

    public int getNumVents()
    {
      return mNumberOfVents;
    }

    public void incrementNumVents()
    {
      mNumberOfVents++;
    }
  }


  public static boolean isHorizontal(int[] startCoords, int[] finishCoords)
  {
    if (startCoords.length != 2 || finishCoords.length != 2)
    {
      System.err.println("Wrong number of co-ordinates! \n" +
                         "Start: " + Arrays.toString(startCoords) + "\n" +
                         "Finish: " + Arrays.toString(finishCoords));
    }

    return (startCoords[1] == finishCoords[1]);
  }

  public static boolean isVertical(int[] startCoords, int[] finishCoords)
  {
    if (startCoords.length != 2 || finishCoords.length != 2)
    {
      System.err.println("Wrong number of co-ordinates! \n" +
                         "Start: " + Arrays.toString(startCoords) + "\n" +
                         "Finish: " + Arrays.toString(finishCoords));
    }

    return (startCoords[0] == finishCoords[0]);
  }

  public void addVent(int[] startCoord, int[] endCoord)
  {

  }



}
