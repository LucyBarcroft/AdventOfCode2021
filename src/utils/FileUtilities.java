package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * A class for parsing things from file.
 */
public class FileUtilities 
{	
	/** 
	 * @param filename  The name of the input file.
	 * 
	 * @return A list where each value is one line of the input file. 
	 */
	public static ArrayList<String> readStringsFromFile(String filename)
	{
		ArrayList<String> values = new ArrayList<String>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(filename)) )
		{
          String line;
		  
		  while ((line = br.readLine()) != null)
		  {
			 values.add(line);
		  }
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return values;
	}
	
	public static int[] readIntArrayFromSingleLineFile(String filename, String delimeter)
	{
		int[] values = null;
		try (BufferedReader br = new BufferedReader(new FileReader(filename)) )
		{
          String line = br.readLine();
		  values = Arrays.stream(line.split(delimeter)).mapToInt(x -> Integer.parseInt(x)).toArray();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return values;
	}

}
