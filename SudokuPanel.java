package sudokugame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SudokuPanel extends JPanel {
	private SudokuSolver puzzle;
	private int currentlySelectedCol;
	private int currentlySelectedRow;
	private int fontSize;
	private int usedWidth;
	private int usedHeight;
	private JFrame frame;

	public SudokuPanel() {
		this.setPreferredSize(new Dimension(540, 450));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.puzzle = new SudokuPuzzle();
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
		fontSize = 26;
	}

	public SudokuPanel(SudokuSolver puzzle) {
		this.setPreferredSize(new Dimension(540, 450));
		this.addMouseListener(new SudokuPanelMouseAdapter());
		this.puzzle = puzzle;
		currentlySelectedCol = -1;
		currentlySelectedRow = -1;
		fontSize = 26;
	}
	/**
	 * makes a new Sudoku puzzle
	 * @param puzzle the puzzle
	 */
	public void newSudokuPuzzle(SudokuSolver puzzle) {
		this.puzzle = puzzle;
	}
	/**
	 * Set the front size
	 * @param fontSize
	 */
	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}

	/**
	 * Draws the sudoku
	 * @param g
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(1.0f, 1.0f, 1.0f));

		int slotWidth = this.getWidth() / 9;
		int slotHeight = this.getHeight() / 9;

		usedWidth = slotWidth * 9;
		usedHeight = slotHeight * 9;

		g2d.fillRect(0, 0, usedWidth, usedHeight);

		g2d.setColor(new Color(0.0f, 0.0f, 0.0f));
		
		//draws Vertical lines
		for (int x = 0; x <= usedWidth; x += slotWidth) {
			if ((x / slotWidth) % 3 == 0) { //thick lines
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(x, 0, x, usedHeight);
			} else {
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(x, 0, x, usedHeight);
			}
		}
		//draws Horzontal lines
		for (int y = 0; y <= usedHeight; y += slotHeight) {
			if ((y / slotHeight) % 3 == 0) { //thick lines
				g2d.setStroke(new BasicStroke(2));
				g2d.drawLine(0, y, usedWidth, y);
			} else {
				g2d.setStroke(new BasicStroke(1));
				g2d.drawLine(0, y, usedWidth, y);
			}
		}

		Font f = new Font("Times New Roman", Font.PLAIN, fontSize);
		g2d.setFont(f);
		FontRenderContext fContext = g2d.getFontRenderContext();
		
		// adds the digits
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				if (!(puzzle.get(row, col) == 0)) {
					String numberstr = Integer.toString(puzzle.get(row, col));
					int textWidth = (int) f.getStringBounds(numberstr, fContext).getWidth();
					int textHeight = (int) f.getStringBounds(numberstr, fContext).getHeight();
					g2d.drawString(numberstr, (col * slotWidth) + ((slotWidth / 2) - (textWidth / 2)), (row * slotHeight) + ((slotHeight / 2) + (textHeight / 2)));
				}
			}
		}
		//makes it possible to click in the sudoku
		if (currentlySelectedCol != -1 && currentlySelectedRow != -1) {
			g2d.setColor(new Color(0.0f, 0.0f, 1.0f, 0.3f));
			g2d.fillRect(currentlySelectedCol * slotWidth, currentlySelectedRow * slotHeight, slotWidth, slotHeight);
		}
	}
	/**
	 * Makes it possible to write numbers in the sudoku by implementing KeyListener
	 */
	public class NumKeyListener implements KeyListener {
		@Override
		public void keyTyped(KeyEvent ke) {//do nothing
		}

		@Override
		public void keyPressed(KeyEvent ke) {
			if (currentlySelectedCol != -1 && currentlySelectedRow != -1) {
				char ch = ke.getKeyChar();
				if (ch == '1' || ch == '2' || ch == '3' || ch == '4' || ch == '5' || ch == '6' || ch == '7' || ch == '8'
						|| ch == '9') {
					int digit = Integer.parseInt(String.valueOf(ch));
					puzzle.add(currentlySelectedRow, currentlySelectedCol, digit);
				} else {
					puzzle.add(currentlySelectedRow, currentlySelectedCol, 0);
				}
				repaint();
			}
		}

		@Override
		public void keyReleased(KeyEvent ke) {//do nothing
		}
	}
	/**
	 * Creates the clear button by implementing ActionListener
	 */
	public class ClearActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			puzzle.clear();
			repaint();
		}
	}
	/**
	 * Creates the solve button by implementing ActionListener
	 */
	public class SolveActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (puzzle.solve() == true) {
				puzzle.solve();
				repaint();
			} else {
				JOptionPane.showMessageDialog(frame, "It can't be solved");
			}
		}
	}
	/**
	 * Makes it possible to to select a cell by extending MouseInputAdapter
	 */
	private class SudokuPanelMouseAdapter extends MouseInputAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				currentlySelectedRow = e.getY() / (usedHeight / 9);
				currentlySelectedCol = e.getX() / (usedWidth / 9);
				e.getComponent().repaint();
			}
		}
	}
}