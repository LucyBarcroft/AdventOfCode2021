package submarine.environmentmappers;

public class SeaFloorMapper
{
  /**
   * Each depth measurement of the sea floor
   */
  private int[] mSeaFloorDepths;

  /**
   * CONSTRUCTOR
   * populates the sea floor depth measurements
   *
   * @param depthMeasurements    The depth measurements of the sea floor.
   */
  public SeaFloorMapper(int[] depthMeasurements)
  {
    mSeaFloorDepths = depthMeasurements;
  }

  /**
   * Calculates the number of sea floor positions where the depth has
   * increased since the measurement the supplied index number prior
   *
   * @param increaseIndex    The number of measurements previously to compare
   *                         each depth to
   * @return      The number of times a depth measurement has increased
   *              since the previous measurement we care about.
   */
  public int measureIncreases(int increaseIndex)
  {
    int numberOfIncreases = 0;

    for (int i=increaseIndex; i < mSeaFloorDepths.length; i++)
    {
      if (mSeaFloorDepths[i] > mSeaFloorDepths[i-increaseIndex])
      {
        numberOfIncreases++;
      }
    }

    return numberOfIncreases;
  }
}
