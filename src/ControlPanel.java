import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ControlPanel extends JPanel
{
	private JButton _solveButton;
	private JButton _resetButton;
	private SudokuPanel _sudokuPanel;

	private final String SOLVE_NAME = "Solve Sudoku";
	private final String CLEAR_NAME = "Clear Solution";
	private final String RESET_NAME = "Reset Board";

	private final int GAP_FROM_BUTTOM = 70;
	private final int BUTTON_GAP = 40;

	private boolean _isBadSudoku;

	public ControlPanel(SudokuPanel sudokuPanel)
	{
		super();
		_sudokuPanel = sudokuPanel;
		_isBadSudoku = false;

		Font buttonFont = new Font("Arial", Font.BOLD, 16);
		_solveButton = new JButton(SOLVE_NAME);
		_solveButton.setFont(buttonFont);
		_resetButton = new JButton(RESET_NAME);
		_resetButton.setFont(buttonFont);

		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		this.add(Box.createHorizontalGlue()); // Push it towards the centre
		this.add(_solveButton);
		this.add(Box.createRigidArea(new Dimension(BUTTON_GAP, 0))); // Gap
		this.add(_resetButton);
		this.add(Box.createHorizontalGlue()); // Push it towards the centre
		this.setBorder(BorderFactory
				.createEmptyBorder(0, 0, GAP_FROM_BUTTOM, 0));

		ActionListener solveListener = new SolveListener();
		_solveButton.addActionListener(solveListener);
		ActionListener resetListener = new ResetListener();
		_resetButton.addActionListener(resetListener);
	}

	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		if (_isBadSudoku)
		{
			Graphics2D g2 = (Graphics2D) g;
			g2.setColor(new Color(204, 0, 0));
			g2.setFont(new Font("Arial", Font.BOLD, 30));

			String errorMsg = "Invalid Sudoku!";
			FontMetrics fm = g2.getFontMetrics();
			Rectangle2D r = fm.getStringBounds(errorMsg, g2);
			int x = (getWidth() - (int) r.getWidth()) / 2;
			int y = (getHeight() - (int) r.getHeight()) / 2 + fm.getAscent();

			g2.drawString(errorMsg, x, y);
			_isBadSudoku = false;
		}
	}

	private class SolveListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			if (_solveButton.getText().equals(SOLVE_NAME))
			{
				_sudokuPanel.requestFocus();
				try
				{
					_sudokuPanel.solveAction();
				} catch (IllegalArgumentException e)
				{
					_isBadSudoku = true;
					repaint();
					return;
				}
				_sudokuPanel.setEditable(false);
				_solveButton.setText(CLEAR_NAME);
			}
			else
			{
				_sudokuPanel.requestFocus();
				_sudokuPanel.clearSolution();
				_solveButton.setText(SOLVE_NAME);
				_sudokuPanel.setEditable(true);
			}
		}
	}

	private class ResetListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			_sudokuPanel.requestFocus();
			_sudokuPanel.resetGrid();
			_solveButton.setText(SOLVE_NAME);
			_sudokuPanel.setEditable(true);
		}

	}
}
