import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class SudokuPanel extends JPanel
{

	private SudokuGrid puzzleGrid;
	private JPanel controlPanel;
	private boolean isEditable;

	public SudokuPanel()
	{
		super();
		resetGrid();
		isEditable = true;

		MouseListener clickListener = new ClickListener();
		this.addMouseListener(clickListener);

		KeyListener inputListener = new InputListener();
		this.addKeyListener(inputListener);

		this.setFocusable(true);
		this.requestFocusInWindow();

		this.setLayout(new BorderLayout());
		controlPanel = new ControlPanel(this);
		super.add(controlPanel, BorderLayout.SOUTH);
	}

	public void resetGrid()
	{
		puzzleGrid = new SudokuGrid(true);
		puzzleGrid.setX(0);
		puzzleGrid.setY(0);
		repaint();
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		puzzleGrid.draw(g2);

	}

	private void clickAction(int x, int y)
	{
		this.requestFocus();
		if (puzzleGrid.selectAction(x, y))
		{
			repaint();

		} else if (puzzleGrid.deselectAction())
		{
			repaint();
		}
	}

	private void keyAction(char key)
	{	
		if(!isEditable)
		{
			return;
		}
		
		int num = Character.getNumericValue(key);

		if (key == KeyEvent.VK_BACK_SPACE)
		{
			puzzleGrid.decrementCursor();
			puzzleGrid.numberAction(0);
			repaint();
			return;
		}


		if(key == KeyEvent.VK_SPACE || key == KeyEvent.VK_DELETE)
		{
			num = 0;
		}
		if (puzzleGrid.numberAction(num))
		{
			puzzleGrid.incrementCursor();
			repaint();
		}
	}
	
	protected void clearSolution()
	{
		puzzleGrid.clearSolution();
		repaint();
	}
	
	protected void solveAction() 
	{
		puzzleGrid.solveAction();
		repaint();
	}

	public boolean isEditable()
	{
		return isEditable;
	}

	public void setEditable(boolean isEditable)
	{
		this.isEditable = isEditable;
		puzzleGrid.setEditable(isEditable);
	}

	private class ClickListener implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent event)
		{
		}

		@Override
		public void mouseEntered(MouseEvent event)
		{
		}

		@Override
		public void mouseExited(MouseEvent event)
		{
		}

		@Override
		public void mousePressed(MouseEvent event)
		{
			int clickX = event.getX();
			int clickY = event.getY();
			clickAction(clickX, clickY);
		}

		@Override
		public void mouseReleased(MouseEvent event)
		{
		}
	}

	private class InputListener implements KeyListener
	{

		@Override
		public void keyPressed(KeyEvent event)
		{
			keyAction(event.getKeyChar());
		}

		@Override
		public void keyReleased(KeyEvent event)
		{
		}

		@Override
		public void keyTyped(KeyEvent event)
		{
		}

	}
}
