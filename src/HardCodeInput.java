
public class HardCodeInput implements SudokuConverter{

	@Override
	public int[][] getArray() 
	{
		
		int i[][] = {{0, 0, 0, 0, 0, 0, 0, 0, 0},
					 {0, 0, 0, 0, 0, 0, 0, 0, 0},
					 {0, 0, 0, 0, 0, 0, 0, 0, 0},
					 {0, 0, 0, 0, 0, 0, 0, 0, 0},
					 {0, 0, 0, 0, 0, 0, 0, 0, 0},
					 {0, 0, 0, 0, 0, 0, 0, 0, 0},
					 {0, 0, 0, 0, 0, 0, 0, 0, 0},
					 {0, 0, 0, 0, 0, 0, 0, 0, 0},
					 {0, 0, 0, 0, 0, 0, 0, 0, 0}};
					 
		int r[][] = {{6, 2, 0, 3, 0, 0, 0, 0, 9},
					 {0, 7, 0, 6, 0, 1, 0, 0, 0},
					 {0, 0, 1, 2, 0, 5, 0, 0, 4},
					 {0, 0, 0, 0, 0, 0, 4, 3, 5},
					 {5, 3, 0, 9, 0, 4, 0, 1, 7},
					 {4, 6, 7, 0, 0, 0, 0, 0, 0},
					 {9, 0, 0, 4, 0, 6, 5, 0, 0},
					 {0, 0, 0, 1, 0, 9, 0, 4, 0},
					 {1, 0, 0, 0, 0, 2, 0, 7, 3}};
		int a[][] = {{1, 2, 3},
					 {4, 5, 6},
					 {7, 8, 0}};
		
		return i;
	}	
}
