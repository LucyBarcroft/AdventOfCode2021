package submarine.environmentmappers;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Mapper for basins.
 * Can calculate the low points, and the sizes of all basins.
 */
public class BasinMapper
{
  /**
   * The height map of the cave floor.
   * Not entirely sure why I went for an ArrayList of int arrays, but it works.
   */
  private ArrayList<int[]> mHeightMap = new ArrayList<int[]>();

  /**
   * The sizes of all basins
   */
  private ArrayList<Integer> mBasinSizes = new ArrayList<Integer>();

  /**
   * A list of low points on the cave floor.
   */
  private ArrayList<Integer> mLowPoints = new ArrayList<Integer>();

  /**
   * Create this basin mapper from a heightmap input
   *
   * @param heightMapInput
   */
  public BasinMapper(ArrayList<String> heightMapInput)
  {
    populateLowPoints(heightMapInput);
  }

  /**
   * Returns the multiplier of the three largest basins.
   *
   * @param heightMapInput The heightmap from which to assess basins.
   */
  public int calculateLargestBasins(ArrayList<String> heightMapInput)
  {
    int[] largestBasinSizes = new int[3];

    for (int row=0; row<mHeightMap.size(); row++)
    {
      for (int column=0; column<mHeightMap.get(0).length; column++)
      {
        //
        // Loop over each point and determine the number of points in its basin.
        // This does loop over every point, if a point is not in a basin (height 9)
        // or is in a basin which has already been determined, this will return 0
        //
        int numInBasin = getNumInBasin(row, column);

        if (numInBasin != 0)
        {
          mBasinSizes.add(numInBasin);
        }

        if (numInBasin > largestBasinSizes[0])
        {
          largestBasinSizes[0] = numInBasin;
          Arrays.sort(largestBasinSizes);
        }
      }
    }

    return (largestBasinSizes[0] * largestBasinSizes[1] * largestBasinSizes[2]);
  }

  /**
   * Get the number of points which are in the basin containing the specified point.
   *
   * @param row      The row of the point to consider
   * @param column   The column of the point to consider
   * @return         The number of points in the basin containing this point.
   *                 Zero if this point is a 9 (not in a basin, or basin already considered)
   */
  private int getNumInBasin(int row, int column)
  {
    int numInBasin = 0;

    //
    // If the value of this point is a 9, that means it is not in a basin (or that
    // this basin has already been mapped.
    //
    if (mHeightMap.get(row)[column] != 9)
    {
      //
      // This point has now been counted.
      // Set the value of this point to 9 so that we know we have already counted it
      // and we don't count it again.
      //
      mHeightMap.get(row)[column] = 9;

      numInBasin = 1;

      //
      // Check recursively if each of its neighbours is also in the basin.
      // First and last rows and columns have fewer neighbours than the rest, so
      // we only check valid neighbours
      //
      if (row != 0)
      {
        numInBasin += getNumInBasin(row-1, column);
      }

      if (column != 0)
      {
        numInBasin += getNumInBasin(row, column-1);
      }

      if (row+1 != mHeightMap.size())
      {
        numInBasin += getNumInBasin(row+1, column);
      }

      if (column+1 != mHeightMap.get(0).length)
      {
        numInBasin += getNumInBasin(row, column+1);
      }
    }

    return numInBasin;
  }

  /**`
   * Populate the height map and list of low points for this basin.
   *
   * @param input the input
   */
  private void populateLowPoints(ArrayList<String> heightMapInput)
  {
    for (int row=0; row<=heightMapInput.size(); row++)
    {
      int[] rowHeights;

      if (row != heightMapInput.size())
      {
        rowHeights = Arrays.stream(heightMapInput.get(row)
                                                 .split("\\B"))
                                                 .mapToInt(x->Integer.valueOf(x))
                                                 .toArray();
        mHeightMap.add(rowHeights);
      }

      //
      // We can't assess this row for low points yet, since we don't know the next row.
      // I could have populated the whole list first and then iterated through it which may
      // have made for simpler code, but I was trying to be clever...
      //
      // We will know if the previous row has any low points, so check the previous row
      // to find any low points
      //
      int rowConsidered = row-1;

      if (rowConsidered >= 0)
      {
        int[] fullRowConsidered = mHeightMap.get(rowConsidered);

        for (int column=0; column<fullRowConsidered.length; column++)
        {
          int valueConsidered = fullRowConsidered[column];

          boolean lowerThanLeft = ((column == 0) ||
                                   (fullRowConsidered[column-1] > valueConsidered));

          boolean lowerThanRight = ((column+1 == fullRowConsidered.length) ||
                                    (fullRowConsidered[column+1] > valueConsidered));

          boolean lowerThanAbove = ((rowConsidered == 0) ||
                                    (mHeightMap.get(rowConsidered-1)[column] > valueConsidered));

          boolean lowerThanBelow = ((rowConsidered+1 == heightMapInput.size()) ||
                                    (mHeightMap.get(rowConsidered+1)[column] > valueConsidered));

          //
          // Check that the value considered is lower than any adjacent values - if those adjacent values
          // exist.
          // For example on the first column, there will be no adjacent value to the left.
          //
          if (lowerThanLeft && lowerThanRight && lowerThanAbove && lowerThanBelow)
          {
            mLowPoints.add(valueConsidered);
          }
        }
      }
    }
  }

  /**
   * Get the sum of risk levels across all basins
   *
   * @return sum of risk levels of basins
   */
  public int getSumOfRiskLevels()
  {
    //
    // The risk level for a low point is the value of the low point + 1
    //
    System.out.println("num low points: " + mLowPoints.size());
    return mLowPoints.stream().mapToInt(Integer::valueOf).sum() + mLowPoints.size();
  }
}
