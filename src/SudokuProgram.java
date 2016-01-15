import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SudokuProgram extends JFrame
{

	public final static int WIDTH = 600;
	public final static int HEIGHT = 700;

	private JPanel displayPanel;

	public static void main(String[] args)
	{
		JFrame frame = new SudokuProgram();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// frame.pack();
		frame.setVisible(true);

	}

	private SudokuProgram()
	{
		super("Sudoku Solver");

		super.setSize(WIDTH, HEIGHT);
		super.setResizable(false);

		displayPanel = new SudokuPanel();

		super.setLocationRelativeTo(null);

		super.add(displayPanel);

	}
}
