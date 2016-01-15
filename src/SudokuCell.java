import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import javax.swing.UIManager;

public class SudokuCell
{

	private int _value;
	private boolean _isSelected;
	private boolean _isFilled;
	private boolean _isSolution;
	private boolean _isEditable;

	private int corX;
	private int corY;

	private int width;
	private int height;

	public SudokuCell(int x, int y, int w, int h)
	{
		setValue(0);
		_isSelected = false;
		setIsSolution(false);
		corX = x;
		corY = y;
		width = w;
		height = h;
	}

	public SudokuCell(int num)
	{
		setValue(num);
		_isSelected = false;

	}

	public void draw(Graphics2D g2)
	{
		drawHighlite(g2);
		drawNumber(g2);
	}

	private void drawHighlite(Graphics2D g2)
	{
		Color defaultColor = UIManager.getColor("Panel.background");

		if (_isSelected)
		{
			g2.setColor(new Color(255, 255, 102));
		} else
		{
			g2.setColor(defaultColor);
		}
		g2.fill(new Rectangle2D.Double(corX, corY, width, height));
	}

	private void drawNumber(Graphics2D g2)
	{
		if (_isFilled)
		{
			if(_isSolution)
				g2.setColor(new Color(0, 180, 0));
			else
				g2.setColor(Color.BLACK);

			String numStr = Integer.toString(_value);
			g2.setFont(new Font("Arial", Font.BOLD, 36));

			FontMetrics fm = g2.getFontMetrics();
	        Rectangle2D r = fm.getStringBounds(numStr, g2);
	        int x = (width - (int) r.getWidth()) / 2 + corX;
	        int y = (height - (int) r.getHeight()) / 2 + fm.getAscent() + corY;
	        g2.drawString(numStr, x, y);
		}
	}

	public void setValue(int num)
	{
		if (num == 0)
		{
			_value = num;
			_isFilled = false;

		} else if (num >= 1 && num <= 9)
		{
			_value = num;
			_isFilled = true;

		} else
		{
			throw new IllegalArgumentException();
		}
	}
	
	public int getValue() 
	{
		return _value;
	}

	public void setIsSelected(Boolean b)
	{
		_isSelected = b;
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

	public boolean isSolution()
	{
		return _isSolution;
	}

	public void setIsSolution(boolean _isSolution)
	{
		this._isSolution = _isSolution;
	}

	public boolean isEditable()
	{
		return _isEditable;
	}

	public void setEditable(boolean _isEditable)
	{
		this._isEditable = _isEditable;
	}

}
