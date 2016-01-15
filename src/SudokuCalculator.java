/**
 * 
 * @author YuFan Dong
 * 
 */
public class SudokuCalculator
{

	// public static void main(String[] args)
	// {
	// RunTimer runTime = RunTimer.getRunTimeInstance();
	// runTime.start();
	//
	// File file = new File(
	// "C:\\Users\\Sheridan\\workspace\\Sudoku\\src\\Sudoku Files\\EvilTest1");
	// FileConverter fileConvert = new FileConverter(file);
	//
	// int[][] array = fileConvert.getArray();
	//
	// if (array == null)
	// return;
	//
	// SudokuCalculator solcal = new SudokuCalculator(fileConvert.getArray());
	// // HardCodeInput hardCodeInput = new HardCodeInput();
	// // SolutionCalculator solcal = new
	// // SolutionCalculator(hardCodeInput.getArray());
	//
	// solcal.print2DArray(solcal.solveSudoku());
	//
	// System.out.println("Runtime = " + runTime.stop());
	//
	// /*
	// * for (int row = 0; row < solcal.sudokuArray.length; row++) { for (int
	// * col = 0; col < solcal.sudokuArray[0].length; col++) {
	// * System.out.println(); for (int pos = 0; pos <
	// * solcal.posArray[0][0].length; pos++) {
	// * System.out.print(solcal.posArray[row][col][pos]); } } }
	// */
	// }

	private int[][] _sudokuArray; // Current sudoku
	private int[][][] _posArray; // Possibilities left in each cell
	private int[][][] _posArrayCopy; // Copy of posibilities, for determining if
										// we're stuck

	public SudokuCalculator(int[][] sudokuArray)
	{
		_sudokuArray = copy2DArray(sudokuArray);
		posArrayGen();
		_posArrayCopy = new int[_sudokuArray.length][_sudokuArray[0].length][9];
		// calculate();
	}

	public int[][] solveSudoku()
	{
		if (!isSudokuLegit())
			throw new IllegalArgumentException();
		calculate();
		return _sudokuArray;
	}

	private void calculate()
	{
		while (!isSolved())
		{
			for (int row = 0; row < _sudokuArray.length; row++)
			{
				for (int col = 0; col < _sudokuArray[0].length; col++)
				{
					if (_sudokuArray[row][col] == 0)
					{
						squareCheck(row, col);
						rowCheck(row, col);
						colCheck(row, col);
						posSolve(row, col);
						if (isNoMorePos(row, col))
							return;
					}
				}
			}

			if (isStuck())
			{
				// print2DArray(_sudokuArray);
				treeingCalculate();
				if (!isSolved())
				{
					return;
				}
				// System.out.println("Not Solvable");
				// System.exit(1);
			}
		}

	}

	// Generate the posArray with all possbility there
	private void posArrayGen()
	{
		_posArray = new int[_sudokuArray.length][_sudokuArray[0].length][9];

		for (int row = 0; row < _sudokuArray.length; row++)
		{
			for (int col = 0; col < _sudokuArray[0].length; col++)
			{
				for (int pos = 0; pos < 9; pos++)
				{
					try
					{
						_posArray[row][col][pos] = pos + 1;
					} catch (ArrayIndexOutOfBoundsException e)
					{
						System.err.println(row + " " + col + " " + pos);
					}
				}
			}
		}
	}

	private void squareCheck(int r, int c)
	{

		int sqrR = r / 3 * 3;
		int sqrC = c / 3 * 3;

		for (int row = sqrR; row < sqrR + 3; row++)
		{
			for (int col = sqrC; col < sqrC + 3; col++)
			{
				tickOff(_posArray[r][c], _sudokuArray[row][col]);
			}
		}
	}

	private void rowCheck(int r, int c)
	{
		for (int col = 0; col < _sudokuArray[0].length; col++)
		{
			tickOff(_posArray[r][c], _sudokuArray[r][col]);
		}
	}

	private void colCheck(int r, int c)
	{
		for (int row = 0; row < _sudokuArray.length; row++)
		{
			tickOff(_posArray[r][c], _sudokuArray[row][c]);
		}
	}

	// Search through an array of possible numbers.
	// If num exist in the list, "tick it off" as not possible.
	private void tickOff(int[] list, int num)
	{
		for (int i = 0; i < list.length; i++)
		{
			if (list[i] == num)
			{
				list[i] = 0;
			}
		}
	}

	// Check if entire sudoku is solved
	protected boolean isSolved()
	{
		for (int row = 0; row < _sudokuArray.length; row++)
		{
			for (int col = 0; col < _sudokuArray[0].length; col++)
			{
				if (_sudokuArray[row][col] == 0)
				{
					return false;
				}
			}
		}
		return true;
	}

	// Check if a single block of posArray has only one possibility left,
	// thus is the solution
	private boolean posSolve(int row, int col)
	{

		int posNum = 0;
		for (int pos = 0; pos < _posArray[row][col].length; pos++)
		{
			if (posNum == 0 && _posArray[row][col][pos] != 0)
			{
				posNum = _posArray[row][col][pos];
			} else
			{
				if (posNum != 0 && _posArray[row][col][pos] != 0)
				{
					return false;
				}
			}
		}

		_sudokuArray[row][col] = posNum;
		return true;
	}

	private boolean isNoMorePos(int row, int col)
	{
		if (_posArray[row][col].length == 0)
		{
			return true;
		} else
		{
			return false;
		}
	}

	// Print the solved sudoku
	public static void print2DArray(int[][] ary)
	{

		for (int row = 0; row < ary.length; row++)
		{
			if (row != 0 && (row % 3 == 0))
				System.out.println();

			for (int col = 0; col < ary[0].length; col++)
			{
				if (col != 0 && (col % 3 == 0))
					System.out.print(" ");
				System.out.print(ary[row][col]);
			}

			System.out.println();
		}
		System.out.println("\n");
	}

	public static void print3DArray(int[][][] ary)
	{
		for (int row = 0; row < ary.length; row++)
		{
			for (int col = 0; col < ary[0].length; col++)
			{
				for (int z = 0; z < ary[0][0].length; z++)
				{
					System.out.print(ary[row][col][z]);
				}
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	private boolean isStuck()
	{
		for (int row = 0; row < _posArray.length; row++)
		{
			for (int col = 0; col < _posArray[0].length; col++)
			{
				for (int z = 0; z < _posArray[0][0].length; z++)
				{
					if (_posArray[row][col][z] != _posArrayCopy[row][col][z])
					{
						copy3DArray(_posArray, _posArrayCopy);
						return false;
					}
				}
			}
		}
		/*
		 * print3DArray(posArray); System.out.println();
		 * print3DArray(posArray_copy); System.out.println();
		 * print2DArray(sudokuArray);
		 */
		return true;
	}

	private void copy3DArray(int[][][] org, int[][][] cpy)
	{
		for (int row = 0; row < org.length; row++)
		{
			for (int col = 0; col < org[0].length; col++)
			{
				for (int z = 0; z < org[0][0].length; z++)
				{
					cpy[row][col][z] = org[row][col][z];
				}
			}
		}
	}

	private void treeingCalculate()
	{
		for (int row = 0; row < _sudokuArray.length; row++)
		{
			for (int col = 0; col < _sudokuArray[0].length; col++)
			{
				if (_sudokuArray[row][col] == 0)
				{
					for (int index = 0; index < _posArray[row][col].length; index++)
					{
						if (_posArray[row][col][index] != 0)
						{
							_sudokuArray[row][col] = _posArray[row][col][index];

							int copyArray[][] = copy2DArray(_sudokuArray);

							SudokuCalculator nestedCal = new SudokuCalculator(
									copyArray);

							nestedCal.solveSudoku();

							if (nestedCal.isSolved())
							{
								_sudokuArray = nestedCal.getSolvedSudoku();
								return;
							}
						}
					}
					// print2DArray(_sudokuArray);
					// System.out.println();System.out.println();
					return;
				}
			}
		}
	}

	private int[][] copy2DArray(int[][] array)
	{
		int copyArray[][] = new int[array.length][array[0].length];
		for (int i = 0; i < array.length; i++)
		{
			int[] temp = array[i];
			System.arraycopy(temp, 0, copyArray[i], 0, array[i].length);
		}
		return copyArray;
	}

	private boolean isSudokuLegit()
	{
		for (int row = 0; row < _sudokuArray.length; row++)
		{
			for (int col = 0; col < _sudokuArray[0].length; col++)
			{
				int num = _sudokuArray[row][col];
				if (num != 0)
				{
					// Check numbers in the same row
					for (int c = 0; c < _sudokuArray[row].length; c++)
					{
						if (num == _sudokuArray[row][c] && col != c)
							return false;
					}
					// Check numbers in the same column
					for (int r = 0; r < _sudokuArray.length; r++)
					{
						if (num == _sudokuArray[r][col] && row != r)
							return false;
					}
					// Check numbers in the same square
					int sqrR = row / 3 * 3;
					int sqrC = col / 3 * 3;
					for (int r = sqrR; r < sqrR + 3; r++)
					{
						for (int c = sqrC; c < sqrC + 3; c++)
						{
							if (num == _sudokuArray[r][c] && row != r
									&& col != c)
								return false;
						}
					}
				}
			}
		}

		return true;
	}

	public int[][] getSolvedSudoku()
	{
		return _sudokuArray;
	}

	// public void set_sudokuArray(int[][] sudokuArray)
	// {
	// _sudokuArray = sudokuArray;
	// }
}
