import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileConverter implements SudokuConverter
{

	private File _file;
	private int[][] _sudokuArray;

	public FileConverter()
	{
		_sudokuArray = new int[9][9];
	}

	public FileConverter(File file)
	{

		this._file = file;

		_sudokuArray = new int[9][9];

	}

	public void setFile(File file)
	{
		this._file = file;
	}

	public int[][] getArray()
	{
		if (fileToArray())
		{
			return _sudokuArray;
		} else
		{
			return null;
		}
	}

	private boolean fileToArray()
	{

		int[] numList = new int[_sudokuArray.length * _sudokuArray[0].length];
		int numListCounter = 0;
		String strLine = null;

		BufferedReader fileInput = null;
		try
		{
			fileInput = new BufferedReader(new FileReader(_file));
			strLine = fileInput.readLine();

			while (strLine != null)
			{

				char[] charLine = strLine.toCharArray();
				for (int i = 0; i < charLine.length; i++)
				{
					if (numListCounter >= numList.length)
					{
						System.err
								.println("Wrong file format! (Too many elements)");
						fileInput.close();
						return false;
					}
					if (Character.isDigit(charLine[i]))
					{
						numList[numListCounter] = Character
								.getNumericValue(charLine[i]);
						numListCounter++;
					}
				}
				strLine = fileInput.readLine(); // Reads for next iteration
			}
			fileInput.close();

		} catch (FileNotFoundException e1)
		{
			System.err.println("File Not Found");
			e1.printStackTrace();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

		if (numListCounter < numList.length)
		{
			System.err.println("Wrong file format! (Not enough elements)");
			System.out.println(numListCounter + " " + numList.length);
			return false;
		}

		numListCounter = 0;

		for (int i1 = 0; i1 < _sudokuArray.length; i1++)
		{
			for (int i2 = 0; i2 < _sudokuArray[i1].length; i2++)
			{
				_sudokuArray[i1][i2] = numList[numListCounter];
				numListCounter++;
			}
		}

		return true;
	}
}
