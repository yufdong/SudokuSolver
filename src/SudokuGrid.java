import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

public class SudokuGrid
{

	private SudokuCell[][] _grid;
	private int[][] _intGrid;
	private int[][] _solutionGrid;
	private boolean _isEditable;

	public static final int NUM_OF_ROWS = 9;
	public static final int NUM_OF_COLUMNS = 9;
	public static final int WIDTH = 600;
	public static final int HEIGHT = 600;
	public static final int X_PAD = WIDTH / 10;
	public static final int Y_PAD = HEIGHT / 10;

	private int corX;
	private int corY;
	private int realX;
	private int realY;
	private int realWidth;
	private int realHeight;

	private int _cellWidth;
	private int _cellHeight;

	private int _curSelectRow;
	private int _curSelectCol;

	public SudokuGrid(boolean isEditable)
	{
		_grid = new SudokuCell[NUM_OF_ROWS][NUM_OF_COLUMNS];
		_intGrid = new int[NUM_OF_ROWS][NUM_OF_COLUMNS];
		_isEditable = isEditable;

		_curSelectRow = -1;
		_curSelectCol = -1;

		realX = X_PAD;
		realY = Y_PAD;
		realWidth = WIDTH - (2 * X_PAD);
		realHeight = HEIGHT - (2 * Y_PAD);
		_cellWidth = realWidth / NUM_OF_ROWS;
		_cellHeight = realHeight / NUM_OF_COLUMNS;

		for (int row = 0; row < NUM_OF_ROWS; row++)
		{
			for (int col = 0; col < NUM_OF_COLUMNS; col++)
			{
				_grid[row][col] = new SudokuCell(realX + col * _cellWidth,
						realY + row * _cellHeight, _cellWidth, _cellHeight);
			}
		}
	}

	public void draw(Graphics2D g2)
	{

		for (int row = 0; row < NUM_OF_ROWS; row++)
		{
			for (int col = 0; col < NUM_OF_COLUMNS; col++)
			{
				_grid[row][col].draw(g2);
			}
		}

		drawGrid(g2);

	}

	private void drawGrid(Graphics2D g2)
	{
		g2.setColor(new Color(105, 105, 105));
		int thick = 4;
		int thin = 2;

		for (int row = 0; row <= NUM_OF_ROWS; row++)
		{
			if (row % 3 == 0)
			{
				g2.setStroke(new BasicStroke(thick));

			} else
			{
				g2.setStroke(new BasicStroke(thin));
			}
			g2.draw(new Line2D.Float(realX, row * _cellHeight + realY,
					NUM_OF_COLUMNS * _cellWidth + realX, row * _cellHeight
							+ realY));
		}

		for (int col = 0; col <= NUM_OF_COLUMNS; col++)
		{
			if (col % 3 == 0)
			{
				g2.setStroke(new BasicStroke(thick));

			} else
			{
				g2.setStroke(new BasicStroke(thin));
			}
			g2.draw(new Line2D.Float(col * _cellWidth + realX, realY, col
					* _cellHeight + realX, NUM_OF_ROWS * _cellHeight + realY));
		}
	}

	public boolean selectAction(int x, int y)
	{
		boolean withinX = (x >= realX)
				&& (x <= NUM_OF_COLUMNS * _cellWidth + realX);
		boolean withinY = (y >= realY)
				&& (y <= NUM_OF_ROWS * _cellHeight + realY);

		if (withinX && withinY)
		{
			int col = (x - realX) / _cellWidth;
			int row = (y - realY) / _cellHeight;
			_grid[row][col].setIsSelected(true);

			if (hasSelected())
			{
				_grid[_curSelectRow][_curSelectCol].setIsSelected(false);
			}
			_curSelectRow = row;
			_curSelectCol = col;

			return true;
		}
		return false;
	}

	public boolean deselectAction()
	{
		if (hasSelected())
		{
			_grid[_curSelectRow][_curSelectCol].setIsSelected(false);
			_curSelectRow = -1;
			_curSelectCol = -1;
			return true;
		}
		return false;
	}

	public boolean numberAction(int num)
	{
		try
		{
			if (hasSelected())
			{
				setCell(_curSelectRow, _curSelectCol, num);

				return true;
			}
		} catch (IllegalArgumentException e)
		{
			return false;
		}
		return false;
	}

	protected void incrementCursor()
	{
		if (hasSelected()) // Set focus to the next cell
		{
			_grid[_curSelectRow][_curSelectCol].setIsSelected(false);

			_curSelectCol++;
			if (_curSelectCol > (NUM_OF_COLUMNS - 1))
			{
				_curSelectCol = 0;
				_curSelectRow++;

			}
			if (_curSelectRow > (NUM_OF_ROWS - 1))
				_curSelectRow = 0;

			_grid[_curSelectRow][_curSelectCol].setIsSelected(true);
		}
	}

	protected void decrementCursor()
	{
		if (hasSelected())
		{
			if (_grid[_curSelectRow][_curSelectCol].getValue() == 0)
			{
				_grid[_curSelectRow][_curSelectCol].setIsSelected(false);

				_curSelectCol--;
				if (_curSelectCol < 0 && _curSelectRow > 0)
				{
					_curSelectCol = (NUM_OF_COLUMNS - 1);
					_curSelectRow--;
				} else if (_curSelectCol < 0 && _curSelectRow <= 0)
				{
					_curSelectCol = 0;
					_curSelectRow = 0;
				}
				_grid[_curSelectRow][_curSelectCol].setIsSelected(true);
			}
		}
	}

	public void solveAction()
	{
		// Convert SudokuCell array to int array
		for (int row = 0; row < NUM_OF_ROWS; row++)
		{
			for (int col = 0; col < NUM_OF_COLUMNS; col++)
			{
				_intGrid[row][col] = _grid[row][col].getValue();
			}
		}

//		SudokuCalculator.print2DArray(_intGrid);

		SudokuCalculator solver = new SudokuCalculator(_intGrid);

//		SudokuCalculator.print2DArray(solver.solveSudoku());
		_solutionGrid = solver.solveSudoku();

		drawSolution();
	}

	private void drawSolution()
	{
		for (int row = 0; row < NUM_OF_ROWS; row++)
		{
			for (int col = 0; col < NUM_OF_COLUMNS; col++)
			{
				if (_intGrid[row][col] == 0)
				{
					_grid[row][col].setIsSolution(true);
					_grid[row][col].setValue(_solutionGrid[row][col]);
				}
			}
		}
	}

	public void clearSolution()
	{
		for (int row = 0; row < NUM_OF_ROWS; row++)
		{
			for (int col = 0; col < NUM_OF_COLUMNS; col++)
			{
				if (_grid[row][col].isSolution())
				{
					setCell(row, col, 0);
					_grid[row][col].setIsSolution(false);
				}
			}
		}
	}

	public void setGrid(int[][] grid)
	{
		for (int row = 0; row < NUM_OF_ROWS; row++)
		{
			for (int col = 0; col < NUM_OF_COLUMNS; col++)
			{
				setCell(row, col, grid[row][col]);
			}
		}
	}

	public void setCell(int row, int col, int value)
	{
		_grid[row][col].setValue(value);
	}

	private boolean hasSelected()
	{
		return _curSelectRow != -1 && _curSelectCol != -1;
	}

	public boolean isEditable()
	{
		return _isEditable;
	}

	public void setEditable(boolean isEditable)
	{
		_isEditable = isEditable;
		for (int row = 0; row < NUM_OF_ROWS; row++)
		{
			for (int col = 0; col < NUM_OF_COLUMNS; col++)
			{
				_grid[row][col].setEditable(isEditable);
			}
		}
	}
	
	public int getX()
	{
		return corX;
	}

	public void setX(int corX)
	{
		this.corX = corX;
	}

	public int getY()
	{
		return corY;
	}

	public void setY(int corY)
	{
		this.corY = corY;
	}

}
